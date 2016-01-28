package com.armin.caloriemeter.LazyList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.armin.caloriemeter.R;

import java.util.ArrayList;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    private ArrayList<String> results;
    private ArrayList<String> results2;
    
    public LazyAdapter(Context context, ArrayList results, ArrayList results2) {
//        activity = a;
//        data=d;
        this.results = results;
        this.results2 = results2;
//        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(context);
        imageLoader=new ImageLoader(context.getApplicationContext());
    }

    public int getCount() {
        return results.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
//        View vi=convertView;
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.result_row, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.result_text_view);
            holder.imageView = (ImageView) convertView.findViewById(R.id.result_image_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(results.get(position));
        imageLoader.DisplayImage(results2.get(position), holder.imageView);
        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}