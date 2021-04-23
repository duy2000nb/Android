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
import com.pqduy.btl.object.Chap;

import java.util.ArrayList;
import java.util.List;

public class ListChapAdapter extends ArrayAdapter<Chap> {
    private Context context;
    private ArrayList<Chap> arrChap;
    int layoutResource;

    public ListChapAdapter(@NonNull Context context, int resource, @NonNull List<Chap> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrChap = new ArrayList<>(objects);
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView             = inflater.inflate(layoutResource, null);
        }
        if(arrChap.size()>0){
            Chap chap = this.arrChap.get(position);

            TextView txvNameChap, txvTimeUpload;
            txvNameChap     = (TextView) convertView.findViewById(R.id.txvNameChap);
            txvTimeUpload   = (TextView) convertView.findViewById(R.id.txvTimeUpload);
            txvNameChap.setText("Chương " +chap.getNumberChapter() + ": " + chap.getNameChap());
            txvTimeUpload.setText(chap.getTimeUpload());
        }
        return convertView;
    }
}
