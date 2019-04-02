package com.twoorthree.graceevangelical;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by williamhessert on 9/1/18.
 */

public class HomeFragment extends Fragment {

    private ListView list;
    private ArrayList<HomeItem> items;
    private HomeAdapter adapter;

    public HomeFragment() { }

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
        View v = inflater.inflate(R.layout.home, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");

        list = v.findViewById(R.id.homeList);
        items = new ArrayList<HomeItem>();
        adapter = new HomeAdapter(getContext(), items);

        list.setAdapter(adapter);
        setHomeItems();
        setOnClick();

        return v;
    }

    private void setHomeItems() {
        HomeItem item = new HomeItem(
                "Welcome",
                "We're glad you could join us",
                getResources().getDrawable(R.drawable.welcome));
        items.add(item);

        item = new HomeItem(
                "Contacts",
                "Connect with us",
                getResources().getDrawable(R.drawable.rsz_contact));
        items.add(item);

        item = new HomeItem(
                "What We Offer",
                "Take a look",
                getResources().getDrawable(R.drawable.offer));
        items.add(item);

        item = new HomeItem(
            "Special Events",
                "Grace's biggest events",
                getResources().getDrawable(R.drawable.rsz_specialevents));
        items.add(item);

        item = new HomeItem(
                "Weekly Highlights",
                "Happening this week",
                getResources().getDrawable(R.drawable.highlights));
        items.add(item);

        item = new HomeItem(
                "Calendar",
                "Check out what's going on",
                getResources().getDrawable(R.drawable.calendar_pic));
        items.add(item);

        item = new HomeItem(
                "Location",
                "Services every Sunday at 10 a.m.",
                getResources().getDrawable(R.drawable.location));
        items.add(item);

        item = new HomeItem(
                "Worship",
                "Get next Sunday's playlist",
                getResources().getDrawable(R.drawable.worship));
        items.add(item);

        item = new HomeItem(
                "Givings",
                "Support Grace",
                getResources().getDrawable(R.drawable.givings));
        items.add(item);

        adapter.notifyDataSetChanged();
    }

    private void setOnClick() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String n = items.get(position).getName();

                if(n.equals("Welcome")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Welcome");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.setMessage("Grace Evangelical Free Church exists to connect people" +
                            " with God regardless of their existing relationship with Him.\n\n" +
                            "To help believers strengthen their faith and to help nonbelievers " +
                            "discover their faith, this app provides access to life changing " +
                            "audio sermons, connects to upcoming events, provides a place for " +
                            "prayer requests to be sent and received, and displays the contact " +
                            "info of the major contacts within the church.\n\n241 Courtland " +
                            "Ave\nStamford, CT 06906\n(203) 323-6737\noffice@gracestamford.org"
                    );

                    builder.create().show();
                }
                else if(n.equals("Contacts")) {
                    Intent i = new Intent(getContext(), ConnectView.class);
                    startActivity(i);
                }
                else if(n.equals("What We Offer")) {
                    Fragment someFragment = new OfferFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(n.equals("Special Events")) {
                    Fragment someFragment = new EventsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(n.equals("Weekly Highlights")) {
                    Fragment someFragment = new HighlightsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(n.equals("Calendar")) {
                    Fragment someFragment = new CalendarFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(n.equals("Location")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Location");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.setMessage("241 Courtland Ave\nStamford, CT 06906\n\nWe meet " +
                            "every Sunday at 10 a.m. for worship. If you would like to find " +
                            "out more, please contact our office at:\n\n(203) 323-6737\n" +
                            "office@gracestamford.org\n\nWe would love to see you this Sunday!"
                    );

                    builder.create().show();
                }
                else if(n.equals("Worship")) {
                    final DatabaseReference ref =
                            FirebaseDatabase.getInstance().getReference().child("Worship");
                    ref.child("playlist").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                final String playlist = dataSnapshot.getValue().toString();

                                ref.child("date")
                                        .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            String date = dataSnapshot.getValue().toString();

                                            if(playlist.equals("")||date.equals("||"))
                                                noPlaylist();
                                            else {
                                                AlertDialog.Builder builder =
                                                        new AlertDialog.Builder(getContext());
                                                builder.setTitle("Worship");

                                                builder.setPositiveButton("Okay",
                                                        new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                                builder.setNegativeButton("Open Playlist",
                                                        new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog, int which) {
                                                        openUrl(playlist);
                                                        dialog.dismiss();
                                                    }
                                                });

                                                builder.setMessage(
                                                        "Worship for "+date+"\n\n"+playlist);

                                                builder.create().show();
                                            }
                                        } catch (Exception e) {
                                            noPlaylist();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } catch (Exception e) {
                                noPlaylist();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if(n.equals("Givings")) {
                    openUrl("https://app.clovergive.com/dl/?uid=grac241157");
                }
            }
        });
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent.setPackage(null);
            getContext().startActivity(intent);
        }
    }

    private void noPlaylist() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Welcome");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setMessage("Sorry, we don't have a playlist for you at this time");

        builder.create().show();
    }
}
