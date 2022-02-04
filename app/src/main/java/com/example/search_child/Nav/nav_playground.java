package com.example.search_child.Nav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.search_child.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_playground#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_playground extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;


    TextView title;

    ImageView btn_share;

    View rootView;


    public nav_playground() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static nav_playground newInstance(String param1) {
        nav_playground fragment = new nav_playground();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView =inflater.inflate(R.layout.fragment_nav_face, container, false);
        }
        InitView();

        return rootView;
    }

    private void InitView() {
        title = rootView.findViewById(R.id.title_text);
        title.setText(mParam1);
        btn_share =rootView.findViewById(R.id.btn_share);
    }

}