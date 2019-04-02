package com.twoorthree.graceevangelical;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OfferFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FirebaseDatabase db;

    private ListView list;
    private ArrayList<OfferItem> items;
    private OfferAdapter ad;

    public OfferFragment() {
        // Required empty public constructor
    }


    public static OfferFragment newInstance() {
        OfferFragment fragment = new OfferFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.offer, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("What We Offer");

        db = FirebaseDatabase.getInstance();
        list = v.findViewById(R.id.oList);
        items = new ArrayList<OfferItem>();

        ad = new OfferAdapter(getContext(), items);
        list.setAdapter(ad);
        setItems();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final OfferItem e = items.get(position);

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

    private void setItems() {
        DatabaseReference graceEvents = db.getReference().child("Offers");
        graceEvents.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String desc = dataSnapshot.child("desc").getValue().toString();
                        String contact = dataSnapshot.child("contact").getValue().toString();
                        String dateTime = dataSnapshot.child("datetime").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String picture = dataSnapshot.child("pic").getValue().toString();
                        String link = dataSnapshot.child("link").getValue().toString();

                        OfferItem e = new OfferItem(
                                key, name, picture, desc, contact, dateTime, link);
                        items.add(e);
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
                        String desc = dataSnapshot.child("desc").getValue().toString();
                        String contact = dataSnapshot.child("contact").getValue().toString();
                        String dateTime = dataSnapshot.child("datetime").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String picture = dataSnapshot.child("pic").getValue().toString();
                        String link = dataSnapshot.child("link").getValue().toString();

                        OfferItem o = new OfferItem(
                                key, name, picture, desc, contact, dateTime, link);

                        for(int i=0; i<items.size(); i++) {
                            if(items.get(i).getId().equals(key)) {
                                items.set(i, o);
                                ad.notifyDataSetChanged();
                                i = items.size();
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

                        for(int i=0; i<items.size(); i++) {
                            if(items.get(i).getId().equals(key)) {
                                items.remove(i);
                                ad.notifyDataSetChanged();
                                i = items.size();
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
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

