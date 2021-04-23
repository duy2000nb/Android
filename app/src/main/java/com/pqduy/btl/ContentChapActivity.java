package com.pqduy.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pqduy.btl.api.ApiGetContentChap;
import com.pqduy.btl.interfaces.GetContentChap;
import com.pqduy.btl.object.Chap;
import com.pqduy.btl.object.ContentChap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentChapActivity extends AppCompatActivity implements GetContentChap {
    Integer numberChapter, idNovel;
    String nameNovel;
    ContentChap ctChap;
    Context context = this;
    Button btNext1, btNext2, btForwar1, btForwar2;
    TextView txvNameNovel, txvNameChap, txvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_chap);
        anhXa();
        init();
        setClick();
        setUp();
        ApiGetContentChap getContentChapAsynTask = new ApiGetContentChap(this, numberChapter, idNovel);
        getContentChapAsynTask.execute();
    }
    private void init(){
        Bundle bundle   = getIntent().getExtras();
        numberChapter   = bundle.getInt("numberChapter");
        idNovel         = bundle.getInt("idNovel");
        nameNovel      = bundle.getString("nameNovel");
        txvNameNovel.setText(nameNovel);
        if(numberChapter == 1){
            btForwar1.setEnabled(false);
            btForwar2.setEnabled(false);
        }
        SQLiteDatabase db = openOrCreateDatabase("novel.db", Context.MODE_PRIVATE, null);
        String sql = "SELECT totalChap FROM listNovel WHERE id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{idNovel.toString()});
        cursor.moveToFirst();
        if(numberChapter == cursor.getInt(0)){
            btNext1.setEnabled(false);
            btNext2.setEnabled(false);
        }
        cursor.close();
        db.close();
    };
    private void anhXa(){
        btNext1 = (Button) findViewById(R.id.btNext1);
        btNext2 = (Button) findViewById(R.id.btNext2);
        btForwar1 = (Button) findViewById(R.id.btForwar1);
        btForwar2 = (Button) findViewById(R.id.btForwar2);
        txvNameNovel = (TextView) findViewById(R.id.txvNameNovelContent);
        txvNameChap = (TextView) findViewById(R.id.txvNameChapContent);
        txvContent = (TextView) findViewById(R.id.txvContent);
    };
    private void setClick(){
        btNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentChapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameNovel", nameNovel);
                bundle.putInt("numberChapter", numberChapter+1);
                bundle.putInt("idNovel", idNovel);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
        btNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentChapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameNovel", nameNovel);
                bundle.putInt("numberChapter", numberChapter+1);
                bundle.putInt("idNovel", idNovel);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
        btForwar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentChapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameNovel", nameNovel);
                bundle.putInt("numberChapter", numberChapter-1);
                bundle.putInt("idNovel", idNovel);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
        btForwar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentChapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameNovel", nameNovel);
                bundle.putInt("numberChapter", numberChapter-1);
                bundle.putInt("idNovel", idNovel);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
    };
    private void setUp(){};

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(data);
            jsonObject = jsonArray.getJSONObject(0);
            ctChap = new ContentChap(jsonObject);
        }
        catch (JSONException e){
            return;
        }
        txvNameChap.setText("Chương " + ctChap.getNumberChapter().toString() + ": " + ctChap.getName());
        txvContent.setText(ctChap.getContent());
    }

    @Override
    public void biLoi() {

    }
}