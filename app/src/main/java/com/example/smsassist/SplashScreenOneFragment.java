package com.example.smsassist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SplashScreenOneFragment extends Fragment {

    Button oneNextButton;

    public SplashScreenOneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen_one, container, false);
        oneNextButton = view.findViewById(R.id.oneNextButton);
        oneNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new SplashScreenTwoFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }
}
