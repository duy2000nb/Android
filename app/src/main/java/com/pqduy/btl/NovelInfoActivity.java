package com.pqduy.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pqduy.btl.adapter.ListChapAdapter;
import com.pqduy.btl.api.ApiGetListChap;
import com.pqduy.btl.interfaces.GetListChap;
import com.pqduy.btl.object.Chap;
import com.pqduy.btl.object.Novel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NovelInfoActivity extends AppCompatActivity implements GetListChap {
    TextView txvName;
    ImageView imgNovel;
    Novel novel;
    Context context = this;
    ListView lsvListChap;
    ArrayList<Chap> arrChap = new ArrayList<>();
    ListChapAdapter listChapAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_info);
        init();
        anhXa();
        setUp();
        setClick();
        ApiGetListChap getListChapAsynTask = new ApiGetListChap(novel.getId(), (GetListChap)context);
        getListChapAsynTask.execute();
    }
    private void init(){
        Bundle bundle = getIntent().getExtras();
        novel = (Novel) bundle.getSerializable("novel");
    };
    private void anhXa(){
        txvName     = (TextView) findViewById(R.id.txvNameNovelInfo);
        imgNovel    = (ImageView) findViewById(R.id.imgNovelInfo);
        lsvListChap = (ListView) findViewById(R.id.lsvListChap);
    };
    private void setUp(){
        txvName.setText(novel.getName());
        Glide.with(context).load(novel.getLinkImage()).into(imgNovel);
    };
    private void setClick(){
        lsvListChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chap chap = arrChap.get(position);
                Intent intent = new Intent(context, ContentChapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("numberChapter", chap.getNumberChapter());
                bundle.putInt("idNovel", chap.getIdNovel());
                bundle.putString("nameNovel", novel.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    };

    @Override
    public void batDau() {
        Toast.makeText(context, "Lấy danh sách chap", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try{
            arrChap.clear();
            JSONArray arrJSON = new JSONArray(data);
            for(int i = 0; i<arrJSON.length(); i++) {
                JSONObject objJSON = arrJSON.getJSONObject(i);
                arrChap.add(new Chap(objJSON, novel.getId()));
            }
            listChapAdapter = new ListChapAdapter(context, R.layout.item_chap_novel, arrChap);
            lsvListChap.setAdapter(listChapAdapter);
        }
        catch (JSONException e){
            return;
        }
    }

    @Override
    public void biLoi() {
        Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
    }
}