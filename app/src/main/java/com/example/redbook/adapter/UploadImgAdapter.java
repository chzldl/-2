package com.example.redbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.redbook.R;

import java.util.List;



public class UploadImgAdapter extends BaseAdapter {
    private Context context;
    private List<String> stringList;
    private OnUploadListener listener;

    public UploadImgAdapter(Context context, List<String> stringList, OnUploadListener listener){
        this.context = context;
        this.stringList =stringList;
        this.listener = listener;
    }

    public void addNewData(List<String> newString){
        stringList= newString ;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return stringList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder =null;
        if (convertView ==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_upload,null);
            holder.upload = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position <stringList.size()){
            Glide.with(context).load(stringList.get(position)).into(holder.upload);
        }else if(position ==stringList.size() && position <9){
            holder.upload.setBackgroundResource(R.drawable.ic_baseline_add_24);
        }else {
            holder.upload.setVisibility(View.GONE);
        }
        holder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    if (position == stringList.size()){
                        listener.onUploadClick();
                    }
                }

            }
        });
        return convertView;
    }
    class ViewHolder{
        ImageView upload;
    }
}
