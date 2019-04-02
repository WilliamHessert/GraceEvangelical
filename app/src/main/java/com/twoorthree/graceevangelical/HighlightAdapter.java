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

public class HighlightAdapter extends BaseAdapter {

    Context context;
    ArrayList<Highlight> items;
    private static LayoutInflater inflater = null;

    public HighlightAdapter(Context context, ArrayList<Highlight> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.highlight, null);

        Highlight item = items.get(position);

        TextView nameText = vi.findViewById(R.id.hName);
        String name = item.getName();
        nameText.setText(name);

        TextView weekText = vi.findViewById(R.id.hWeek);
        String week = item.getWeek();
        weekText.setText(week);

        return vi;
    }
}
