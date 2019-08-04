package com.example.recyclerviewpractice1;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView TextView_Title;
        public TextView TextView_Content;
        public ImageView imageview;
        public MyViewHolder(View v) {
            super(v);

            TextView_Title      =   v.findViewById(R.id.TextView_Title);
            TextView_Content    =   v.findViewById(R.id.TextView_Content);
            imageview           =   v.findViewById(R.id.imageview);
        }
    }


    public MyAdapter(List<NewsData> myDataset, Context context) {
        mDataset = myDataset;

    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsData news = mDataset.get(position);

        holder.TextView_Title.setText(news.getTitle());
        holder.TextView_Content.setText(news.getContent());
        Uri uri = Uri.parse(news.getUrlToImage());
        holder.imageview.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return mDataset== null ? 0 : mDataset.size();
    }
}