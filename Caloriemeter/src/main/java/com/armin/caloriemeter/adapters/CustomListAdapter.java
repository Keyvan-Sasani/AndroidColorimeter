package com.armin.caloriemeter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.armin.caloriemeter.R;
import java.util.ArrayList;

/**
 * Created by Keyvan on 1/23/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<String> listData;
    private ArrayList<String> listData2;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, ArrayList listData, ArrayList listData2) {
        this.listData = listData;
        this.listData2 = listData2;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.result_row, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.result_text_view);
            holder.imageView = (ImageView) convertView.findViewById(R.id.result_image_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        NewsItem newsItem = (NewsItem) listData.get(position);
//        holder.textView.setText(newsItem.getHeadline());
//        holder.reporterNameView.setText("By, " + newsItem.getReporterName());
//        holder.reportedDateView.setText(newsItem.getDate());
        holder.textView.setText(listData.get(position));
        if (holder.imageView != null) {
            new ImageDownloaderTask(holder.imageView).execute(listData2.get(position));
        }
        return convertView;
    }



        class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
