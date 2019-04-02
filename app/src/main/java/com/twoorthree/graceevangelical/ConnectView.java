package com.twoorthree.graceevangelical;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConnectView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_view);

        ListView list = findViewById(R.id.cList);
        final ArrayList<Contact> contacts = new ArrayList<>();
        final ContactAdapter ad = new ContactAdapter(this, contacts);
        list.setAdapter(ad);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child("Contact");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String number = dataSnapshot.child("number").getValue().toString();
                        String role = dataSnapshot.child("role").getValue().toString();

                        Contact c = new Contact(key, email, name, number, role);
                        contacts.add(c);
                        ad.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
                AlertDialog.Builder builder = new AlertDialog.Builder(ConnectView.this);
                Contact c = contacts.get(position);
                builder.setTitle(c.getName());

                String mess = "Role: "+c.getRole();

                if(!c.getEmail().equals("")) {
                    final String email = c.getEmail();

                    builder.setPositiveButton("Send Email", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendEmail(email);
                        }
                    });

                    mess += "\nEmail: "+email;
                }
                if(!c.getNumber().equals("")) {
                    mess += "\nNumber: "+c.getNumber();
                }

                builder.setMessage(mess);
                builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });
    }

    private void sendEmail(String email) {
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        startActivity(Intent.createChooser(i, "Send email"));
    }
}
