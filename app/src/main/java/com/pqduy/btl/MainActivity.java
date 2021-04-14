package com.pqduy.btl;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.pqduy.btl.adapter.ListNovelAdapter;
import com.pqduy.btl.api.ApiGetNovel;
import com.pqduy.btl.interfaces.GetNovel;
import com.pqduy.btl.object.Novel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetNovel {
    GridView gdvListNovel;
    ListNovelAdapter listNovelAdapter;
    ArrayList<Novel> arrNovel;
    Context context;
    SQLiteDatabase db;
    final String dbName = "novel.db";
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ac = getSupportActionBar();
        ac.hide();

        init();
        anhXa();
        setUp();
        setClick();
        ApiGetNovel getNovelAsynTask = new ApiGetNovel(this);
        getNovelAsynTask.execute();
    }

    private void init(){
        context             = this;
        arrNovel            = new ArrayList<>();
        listNovelAdapter    = new ListNovelAdapter(context, R.layout.item_novel, arrNovel);
        db                  = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        createTableListNovel();
        createTableTimeUpdate();
        db.close();
    }

    private boolean isTableExist(SQLiteDatabase db, String table) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{table});
        boolean tableExist = (cursor.getCount() != 0);
        cursor.close();
        return tableExist;
    }

    private void createTableListNovel(){
        if(!isTableExist(db, "listNovel")) {
            String sql = "CREATE TABLE listNovel(id INTEGER PRIMARY KEY," +
                    "name VARCHAR(50)," +
                    "totalChap INT," +
                    "linkImage TEXT)";
            db.execSQL(sql);
        }
    }

    private void createTableTimeUpdate(){
        if(!isTableExist(db, "timeUpdate")){
            String sql = "CREATE TABLE timeUpdate(id INTEGER PRIMARY KEY AUTOINCREMENT, time DATETIME)";
            db.execSQL(sql);
            sql = "INSERT INTO timeUpdate VALUES (NULL, DATETIME('now'))";
            db.execSQL(sql);
        }
    }

    private void anhXa(){
        edtSearch       = (EditText) findViewById(R.id.edtSearch);
        gdvListNovel    = (GridView) findViewById(R.id.gdvListNovel);
    }

    private void setUp(){
        gdvListNovel.setAdapter(listNovelAdapter);
    }

    private void setClick(){
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = edtSearch.getText().toString();
                sortNovel(name);
            }
        });
    }

    private void sortNovel(String s){
        s = s.toUpperCase();
        arrNovel.clear();
        db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        String sql = "SELECT * FROM listNovel WHERE UPPER(name) like '%?%'";
        Cursor cursor = db.rawQuery(sql, new String[] {s});
        cursor.moveToFirst();
        do {
            int id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            int totalChap = Integer.parseInt(cursor.getString(2));
            String linkImage = cursor.getString(3);
            arrNovel.add(new Novel(id, name, totalChap, linkImage));
        }
        while (cursor.moveToNext());
        cursor.close();
        db.close();
        listNovelAdapter = new ListNovelAdapter(context, R.layout.item_novel, arrNovel);
        gdvListNovel.setAdapter(listNovelAdapter);
    }

    @Override
    public void batDau() {
        Toast.makeText(context, "Dang lay ve", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc() {
        db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        String sql = "SELECT * FROM listNovel";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        do {
            int id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            int totalChap = Integer.parseInt(cursor.getString(2));
            String linkImage = cursor.getString(3);
            arrNovel.add(new Novel(id, name, totalChap, linkImage));
        }
        while (cursor.moveToNext());
        cursor.close();
        db.close();
        Toast.makeText(context, "Lay du lieu thanh cong", Toast.LENGTH_SHORT);
        listNovelAdapter = new ListNovelAdapter(context, R.layout.item_novel, arrNovel);
        gdvListNovel.setAdapter(listNovelAdapter);
    }

    @Override
    public void ketThuc(String data) {
        try{
            arrNovel.clear();
            JSONArray arrJSON = new JSONArray(data);
            for(int i = 0; i<arrJSON.length(); i++) {
                JSONObject objJSON = arrJSON.getJSONObject(i);
                arrNovel.add(new Novel(objJSON));
            }
            listNovelAdapter = new ListNovelAdapter(context, R.layout.item_novel, arrNovel);
            gdvListNovel.setAdapter(listNovelAdapter);
        }
        catch (JSONException e){
            return;
        }
        db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        String sql = "DROP TABLE listNovel";
        db.execSQL(sql);
        createTableListNovel();
        for(Novel novel:arrNovel){
            String id           = String.valueOf(novel.getId());
            String name         = novel.getName();
            String totalChap    = String.valueOf(novel.getTotalChap());
            String linkImage    = novel.getLinkImage();
            sql = "INSERT INTO listNovel VALUES (?,?,?,?)";
            db.execSQL(sql, new String[]{id, name, totalChap, linkImage});
        }
        db.close();
        Toast.makeText(context, "Lay du lieu thanh cong", Toast.LENGTH_SHORT);
    }

    @Override
    public void biLoi() {
        Toast.makeText(context, "Loi ket noi", Toast.LENGTH_SHORT).show();
    }
}