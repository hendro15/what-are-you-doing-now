package com.example.sonic.whatdoyoudo.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonic.whatdoyoudo.R;
import com.example.sonic.whatdoyoudo.model.Axis;
import com.example.sonic.whatdoyoudo.model.CalculateAxis;
import com.example.sonic.whatdoyoudo.utils.Dataset;
import com.example.sonic.whatdoyoudo.utils.Permissions;
import com.opencsv.CSVWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainFragment extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrainFragment newInstance(String param1, String param2) {
        TrainFragment fragment = new TrainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Bind(R.id.tv_xValue)
    TextView tv_xValue;
    @Bind(R.id.tv_yValue)
    TextView tv_yValue;
    @Bind(R.id.tv_zValue)
    TextView tv_zValue;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.tv_trainStatus)
    TextView status;
    @Bind(R.id.btnStart)
    Button btn_start;
    @Bind(R.id.btnStop)
    Button btn_stop;
    @Bind(R.id.cb_newDataset)
    CheckBox cbNewDataset;
    @Bind(R.id.cb_oldDataset)
    CheckBox cbOldDataset;

    ArrayAdapter<String> adapter;
    List<String> list;
    Axis axis;
    SensorManager sensorManager;
    Sensor accelero;
    Permissions permissions = new Permissions();
    CalculateAxis calculateAxis = new CalculateAxis();
    Dataset dataset = new Dataset();

    private int window = 20;
    private String category;
    private List<Float> data;
    private File path;
    private boolean newStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_train, container, false);
        if (shouldAskPermissions()) {
            permissions.verifyStoragePermissions(this.getActivity());
        }
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Train Smart Machine");
        spinnerElement();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        onClick();
        onCheckedChange();
        return v;
    }

    private void spinnerElement() {
        list = new ArrayList<String>();
        list.add("Jogging");
        list.add("Walking");
        list.add("Standing");
        list.add("Sitting");
        adapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
    }

    public void onClick() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbNewDataset.isChecked() && !cbOldDataset.isChecked()) {
                    Toast.makeText(getContext(), "Checkbox belum dipilih", Toast.LENGTH_SHORT).show();
                } else {
                    category = spinner.getSelectedItem().toString();
                    status.setText("Record training...");
                    registerListener();
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = calculateAxis.calculate(window, axis.getxList(), axis.getyList(), axis.getzList());
                status.setText(dataset.createDataset(createData(), newStatus));
                unregisterSensor();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tv_xValue.setText(String.valueOf(event.values[0]));
        tv_yValue.setText(String.valueOf(event.values[1]));
        tv_zValue.setText(String.valueOf(event.values[2]));
        axis = new Axis(event.values[0], event.values[1], event.values[1]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void registerListener() {
        sensorManager.registerListener(this, accelero, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterSensor() {
        sensorManager.unregisterListener(this, accelero);
    }

    private String[] createData() {
        String[] entries = new String[]{String.valueOf(data.get(0)),
                String.valueOf(data.get(1)),
                String.valueOf(data.get(2)),
                String.valueOf(data.get(3)),
                String.valueOf(data.get(4)),
                String.valueOf(data.get(5)),
                String.valueOf(data.get(6)),
                String.valueOf(data.get(7)),
                String.valueOf(data.get(8)),
                String.valueOf(data.get(9)),
                String.valueOf(data.get(10)),
                String.valueOf(data.get(11)),
                category};

        return entries;
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void onCheckedChange() {
        cbNewDataset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newStatus = true;
                    cbOldDataset.setChecked(false);
                }
            }
        });
        cbOldDataset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newStatus = false;
                    cbNewDataset.setChecked(false);
                }
            }
        });
//        switch (view.getId()) {
//            case R.id.cb_newDataset:
//                newStatus = true;
//                cbOldDataset.setChecked(false);
//                break;
//            case R.id.cb_oldDataset:
//                newStatus = false;
//                cbNewDataset.setChecked(true);
//                break;
//        }
    }
}

