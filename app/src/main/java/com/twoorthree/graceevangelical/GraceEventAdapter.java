package com.twoorthree.graceevangelical;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twoorthree.graceevangelical.GraceEvent;
import com.twoorthree.graceevangelical.R;

import java.util.ArrayList;

public class GraceEventAdapter extends BaseAdapter {

    Context context;
    ArrayList<GraceEvent> items;
    private static LayoutInflater inflater = null;

    public GraceEventAdapter(Context context, ArrayList<GraceEvent> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.grace_event, null);

        GraceEvent item = items.get(position);

        TextView title = (TextView) vi.findViewById(R.id.eTitle);
        String name = item.getName();

        if(name.length() < 20) {
            title.setText(name);
        }
        else {
            name = name.substring(0, 17)+"...";
            title.setText(name);
        }

        TextView status = (TextView) vi.findViewById(R.id.eDate);
        String stat = item.getElimDate()+" "+item.getStartTime()+"-"+item.getEndTime();
        status.setText(stat);

        ImageView image = (ImageView) vi.findViewById(R.id.ePic);
        byte[] decodedString = Base64.decode(item.getPic(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        image.setImageBitmap(decodedByte);

        return vi;
    }
}
