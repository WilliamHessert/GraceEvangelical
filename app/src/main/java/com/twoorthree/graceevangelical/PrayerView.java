package com.twoorthree.graceevangelical;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PrayerView extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.prayer_view, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("All Prayers");

        ListView list = v.findViewById(R.id.aList);
        final ArrayList<PrayerItem> prayers = new ArrayList<>();
        final PrayerAdapter ad = new PrayerAdapter(getContext(), prayers);
        list.setAdapter(ad);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child("Approved");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String req = dataSnapshot.child("request").getValue().toString();

                        PrayerItem p = new PrayerItem(key, name, req);
                        prayers.add(p);
                        ad.notifyDataSetChanged();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String req = dataSnapshot.child("request").getValue().toString();

                        PrayerItem p = new PrayerItem(key, name, req);

                        for(int i=0; i<prayers.size(); i++) {
                            if(prayers.get(i).getKey().equals(key)) {
                                prayers.set(i, p);
                                ad.notifyDataSetChanged();
                                i = prayers.size();
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();

                        for(int i=0; i<prayers.size(); i++) {
                            if(prayers.get(i).getKey().equals(key)) {
                                prayers.remove(i);
                                ad.notifyDataSetChanged();
                                i = prayers.size();
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                PrayerItem p = prayers.get(position);

                builder.setTitle(p.getName());
                builder.setMessage(p.getRequest());
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });

        return v;
    }
}
