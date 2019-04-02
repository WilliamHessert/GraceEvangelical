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

import java.util.ArrayList;

/**
 * Created by williamhessert on 9/1/18.
 */

public class HomeAdapter extends BaseAdapter {

    Context context;
    ArrayList<HomeItem> items;
    private static LayoutInflater inflater = null;

    public HomeAdapter(Context context, ArrayList<HomeItem> items) {
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
            vi = inflater.inflate(R.layout.home_item, null);

        HomeItem item = items.get(position);

        TextView title = (TextView) vi.findViewById(R.id.hTitle);
        title.setText(item.getName());

        TextView status = (TextView) vi.findViewById(R.id.hDesc);
        status.setText(item.getDesc());

        ImageView image = (ImageView) vi.findViewById(R.id.hImage);
        image.setImageDrawable(item.getPic());

        return vi;
    }
}
