package com.example.search_child.Nav;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.search_child.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_account#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class nav_account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    private String mParam1;

    View rootView;

    TextView textView;

    public static nav_account newInstance(String param1) {
        nav_account fragment = new nav_account();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public nav_account() {
        // Required empty public constructor

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

        if(rootView==null)
        {
            rootView = inflater.inflate(R.layout.fragment_nav_account, container, false);
        }
        // 开始进行操作
        InitView();

       return rootView;
    }

    private void InitView() {


    }

}