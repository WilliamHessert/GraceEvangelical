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

public class PrayerAdapter extends BaseAdapter {

    Context context;
    ArrayList<PrayerItem> items;
    private static LayoutInflater inflater = null;

    public PrayerAdapter(Context context, ArrayList<PrayerItem> items) {
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
            vi = inflater.inflate(R.layout.prayer_item, null);

        PrayerItem item = items.get(position);

        TextView title = (TextView) vi.findViewById(R.id.pName);
        title.setText(item.getName());

        TextView status = (TextView) vi.findViewById(R.id.pRequest);
        String cut = item.getRequest().substring(0, 50)+"...";
        status.setText(cut);

        return vi;
    }
}
