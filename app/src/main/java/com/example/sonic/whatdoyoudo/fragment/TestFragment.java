package com.example.sonic.whatdoyoudo.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sonic.whatdoyoudo.R;
import com.example.sonic.whatdoyoudo.classifier.Bayes;
import com.example.sonic.whatdoyoudo.model.Axis;
import com.example.sonic.whatdoyoudo.model.CalculateAxis;
import com.example.sonic.whatdoyoudo.utils.Permissions;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import weka.core.converters.ConverterUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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
    @Bind(R.id.tv_testStatus)
    TextView status;
    @Bind(R.id.btnStart)
    Button btn_start;
    @Bind(R.id.btnStop)
    Button btn_stop;

    private Permissions permissions = new Permissions();
    private SensorManager sensorManager;
    private Sensor accelero;
    private Axis axis;
    private Bayes bayes;
    private CalculateAxis calculateAxis = new CalculateAxis();
    private int window = 20;
    private List<Float> data;
    private Environment environment;
    private String path;
    private String stringDataset;
    AssetManager assetManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Test Your Action");
        if (shouldAskPermissions()) {
            permissions.verifyStoragePermissionsWrite(getActivity());
            permissions.verifyStoragePermissionsRead(getActivity());
        }
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        bayes = new Bayes();
        path = environment.getExternalStorageDirectory().getPath();
        stringDataset = path + File.separator + "dataset.csv";
        assetManager = getContext().getAssets();
        onClick();
        return v;
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

    private void onClick() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSensor();
                status.setText("Start recording...");
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
//                    source = new ConverterUtils.DataSource("/sdcard/dataset.csv");
                    File file = new File("dataset.csv");
                    Log.i("err", String.valueOf(file.exists()));
                    data = calculateAxis.calculate(window, axis.getxList(), axis.getyList(), axis.getzList());
                    bayes.fileToInstances(ConverterUtils.DataSource.read(assetManager.open("sdcard/dataset.csv"))); //isi data train
                    status.setText("You are " + bayes.bayes(val()) + " now");
                }catch (Exception e){
                    status.setText("Dataset not found at : " + e.getMessage().toString());
                }
                unregisterSensor();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tv_xValue.setText(String.valueOf(event.values[0]));
        tv_yValue.setText(String.valueOf(event.values[1]));
        tv_zValue.setText(String.valueOf(event.values[2]));
        axis = new Axis(event.values[0], event.values[1], event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    protected void registerSensor() {
        sensorManager.registerListener(this, accelero, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void unregisterSensor() {
        sensorManager.unregisterListener(this);
    }

    private double[] val() {
        double[] test = new double[]{
                data.get(0),
                data.get(1),
                data.get(2),
                data.get(3),
                data.get(4),
                data.get(5),
                data.get(6),
                data.get(7),
                data.get(8),
                data.get(9),
                data.get(10),
                data.get(11)
        };
        return test;
    }
}
