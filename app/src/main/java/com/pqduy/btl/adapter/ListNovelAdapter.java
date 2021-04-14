package com.pqduy.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.pqduy.btl.R;
import com.pqduy.btl.object.Novel;

import java.util.ArrayList;
import java.util.List;

public class ListNovelAdapter extends ArrayAdapter<Novel> {
    private Context context;
    private ArrayList<Novel> arrNovel;
    int layoutResource;

    public ListNovelAdapter(@NonNull Context context, int resource, @NonNull List<Novel> objects) {
        super(context, resource, objects);
        this.context        = context;
        this.arrNovel       = new ArrayList<>(objects);
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView             = inflater.inflate(layoutResource, null);
        }
        if(arrNovel.size()>0){
            Novel novel = this.arrNovel.get(position);

            ImageView imgNovel          = (ImageView)convertView.findViewById(R.id.imgNovel);
            TextView txtName            = (TextView)convertView.findViewById(R.id.txvNameNovel);
            TextView txtNumberChaper    = (TextView)convertView.findViewById(R.id.txvNumberChapter);

            Glide.with(context).load(novel.getLinkImage()).into(imgNovel);
            txtName.setText(novel.getName());
            txtNumberChaper.setText("Chương " + novel.getTotalChap());
        }
        return convertView;
    }
}
