package com.example.sonic.whatdoyoudo.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sonic.whatdoyoudo.MainActivity;
import com.example.sonic.whatdoyoudo.R;
import com.example.sonic.whatdoyoudo.commons.CommonFunctions;
import com.example.sonic.whatdoyoudo.commons.GridSpacingItemDecoration;
import com.example.sonic.whatdoyoudo.commons.RecyclerViewClickListener;
import com.example.sonic.whatdoyoudo.commons.RecyclerViewTouchListener;
import com.example.sonic.whatdoyoudo.model.MenuAdapter;
import com.example.sonic.whatdoyoudo.model.MenuElement;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.sonic.whatdoyoudo.R.id.rv_menu;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Bind(R.id.rv_menu)
    RecyclerView rv_menu;
    AlertDialog.Builder builder;

    MainActivity activity;
    List<MenuElement> menuElementList;
    MenuAdapter menuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        setLayout();

        return v;
    }

    public void setLayout() {
        getActivity().setTitle("Smart Detector");
        menuElementList = new ArrayList<>();
        getData();
        menuAdapter = new MenuAdapter(getContext(), menuElementList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rv_menu.setLayoutManager(layoutManager);
        rv_menu.addItemDecoration(new GridSpacingItemDecoration(3, new CommonFunctions(getActivity()).dpToPx(10), true));
        rv_menu.setItemAnimator(new DefaultItemAnimator());
        rv_menu.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity().getApplicationContext(), rv_menu, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                Fragment newFragment = new HomeFragment();
                String title = "";
                Log.i("debugs", "HomeFragment onItemClick position : " + position);
                switch (position) {
                    case 0:
                        newFragment = new TrainFragment();
                        title = "Train Your Machine";
                        break;
                    case 1:
                        newFragment = new TestFragment();
                        title = "Test Your Action";
                        break;
                }
                activity.switchFragment(newFragment, title, "");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        rv_menu.setAdapter(menuAdapter);
    }

    public void getData() {
        MenuElement myMenu = new MenuElement("Test", R.mipmap.ic_fitness_center_black_24dp);
        menuElementList.add(myMenu);
        myMenu = new MenuElement("Train", R.mipmap.ic_play_circle_filled_black_24dp);
        menuElementList.add(myMenu);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    activity.finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
            }
        }
    };

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
        this.activity = (MainActivity) context;
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
}
