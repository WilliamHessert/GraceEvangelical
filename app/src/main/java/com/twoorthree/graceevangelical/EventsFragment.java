package com.twoorthree.graceevangelical;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class EventsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FirebaseDatabase db;

    private ListView list;
    private ArrayList<GraceEvent> events;
    private GraceEventAdapter ad;

    public EventsFragment() {
        // Required empty public constructor
    }


    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.events, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Events");

        db = FirebaseDatabase.getInstance();
        list = v.findViewById(R.id.eList);
        events = new ArrayList<GraceEvent>();

        ad = new GraceEventAdapter(getContext(), events);
        list.setAdapter(ad);
        setEvents();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final GraceEvent e = events.get(position);

                builder.setTitle(e.getName());

                String message = e.getDesc();

                if(!e.getContact().equals("")) {
                    message += "\n\n"+e.getContact();
                    builder.setNegativeButton("Contact", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            contactEmail(e.getName(), e.getContact());
                        }
                    });
                }

                if(!e.getLink().equals("")) {
                    message += "\n"+e.getLink();
                    builder.setPositiveButton("Open Link", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openLink(e.getLink());
                        }
                    });
                }

                builder.setMessage(message);

                builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
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

    private void setEvents() {
        DatabaseReference graceEvents = db.getReference().child("Events");
        graceEvents.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String contact = dataSnapshot.child("contact").getValue().toString();
                        String desc = dataSnapshot.child("desc").getValue().toString();
                        String elimDate = dataSnapshot.child("elimDate").getValue().toString();
                        String endTime = dataSnapshot.child("endTime").getValue().toString();
                        String link = dataSnapshot.child("link").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String picture = dataSnapshot.child("picture").getValue().toString();
                        String startTime = dataSnapshot.child("startTime").getValue().toString();

                        GraceEvent e = new GraceEvent(
                                key, contact, desc, elimDate, endTime, link, name, picture, startTime);
                        events.add(e);
                        ad.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String contact = dataSnapshot.child("contact").getValue().toString();
                        String desc = dataSnapshot.child("desc").getValue().toString();
                        String elimDate = dataSnapshot.child("elimDate").getValue().toString();
                        String endTime = dataSnapshot.child("endTime").getValue().toString();
                        String link = dataSnapshot.child("link").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String picture = dataSnapshot.child("picture").getValue().toString();
                        String startTime = dataSnapshot.child("startTime").getValue().toString();

                        GraceEvent e = new GraceEvent(
                                key, contact, desc, elimDate, endTime, link, name, picture, startTime);

                        for(int i=0; i<events.size(); i++) {
                            if(events.get(i).getKey().equals(key)) {
                                events.set(i, e);
                                ad.notifyDataSetChanged();
                                i = events.size();
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

                        for(int i=0; i<events.size(); i++) {
                            if(events.get(i).getKey().equals(key)) {
                                events.remove(i);
                                ad.notifyDataSetChanged();
                                i = events.size();
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
    }

    private void contactEmail(String name, String email) {
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, name);
        startActivity(Intent.createChooser(i, "Send email"));
    }

    private void openLink(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
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

