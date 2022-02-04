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
 * Use the {@link nav_face#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_face extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    TextView title;

    ImageView btn_share;

    View rootView;

    public nav_face() {

        // Required empty public constructor
    }



    public static nav_face newInstance(String param1) {
        nav_face fragment = new nav_face();
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