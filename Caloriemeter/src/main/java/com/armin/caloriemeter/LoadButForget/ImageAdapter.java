/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.armin.caloriemeter.LoadButForget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.armin.caloriemeter.LoadButForget.ImageDownloader;
import com.armin.caloriemeter.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private static final String[] URLS = {
            "http://static.caloriecount.about.com/images/medium/salad-dressing-thousand-island-158394.jpg",
            "http://www.mrbreakfast.com/cereal/c_3.jpg"
    };
    ArrayList<String> results;
    ArrayList<String> results2;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Context context, ArrayList<String> results, ArrayList<String> results2) {
        this.results = results;
        this.results2 = results2;
        layoutInflater = LayoutInflater.from(context);

    }

    private final ImageDownloader imageDownloader = new ImageDownloader();

    public int getCount() {
        return results.size();
    }

    public String getItem(int position) {
        return results.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public ImageDownloader getImageDownloader() {
        return imageDownloader;
    }

//    public View getView(int position, View view, ViewGroup parent) {
//        if (view == null) {
//            view = new ImageView(parent.getContext());
//            view.setPadding(6, 6, 6, 6);
//        }
//        if(position < getCount())
//            imageDownloader.download(results.get(position), (ImageView) view);
//
//        return view;
//    }

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

        holder.textView.setText(results.get(position));
        if (holder.imageView != null) {
//            new ImageDownloaderTask(holder.imageView).execute(results2.get(position));
            imageDownloader.download(results2.get(position), holder.imageView);
        }
        return convertView;
    }


    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

}
