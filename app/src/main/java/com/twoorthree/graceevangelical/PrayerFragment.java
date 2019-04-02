package com.twoorthree.graceevangelical;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class PrayerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public PrayerFragment() { }

    public static PrayerFragment newInstance(String param1, String param2) {
        PrayerFragment fragment = new PrayerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.prayer_request, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Prayer Request");

        final EditText name = v.findViewById(R.id.enterName);
        final EditText pray = v.findViewById(R.id.enterPrayer);
        Button submit = v.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRequest(name, pray);
            }
        });

//        Button viewAll = v.findViewById(R.id.pView);
//        viewAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), PrayerView.class);
//                getContext().startActivity(i);
//            }
//        });

        return v;
    }


    private void submitRequest(EditText n, EditText p) {
        String name = n.getText().toString();
        String pray = p.getText().toString();
        String rId = createrId();

        if(name.equals("") || pray.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Submission Failed");
            builder.setMessage("Please make sure you fill out all fields.");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            builder.create().show();
        }
        else {
            DatabaseReference ref1 = FirebaseDatabase.
                    getInstance().getReference().child("Requests").child(rId).child("name");
            DatabaseReference ref2 = FirebaseDatabase.
                    getInstance().getReference().child("Requests").child(rId).child("request");

            ref1.setValue(name);
            ref2.setValue(pray);

            n.setText("");
            p.setText("");
        }
    }

    private String createrId() {
        StringBuilder s = new StringBuilder();

        String r = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int l = r.length();
        Random ran = new Random();

        for(int i=0; i<20; i++) {
            s.append(""+(r.charAt(ran.nextInt(l))));
        }

        return s.toString();
    }

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

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

