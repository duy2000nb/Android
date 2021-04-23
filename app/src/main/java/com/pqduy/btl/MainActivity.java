package com.pqduy.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
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
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListNovelAdapter listNovelAdapter;
    Button btLogin;
    Button btSignUp;
    Button btLogout;
    EditText edtSearch;
    TextView txvMenuUsername;

    ArrayList<Novel> arrNovel;
    Context context;
    SQLiteDatabase db;
    final String dbName = "novel.db";
    String username = "Guest";
    Integer id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent getIntent = getIntent();
        Bundle getBundle = getIntent.getExtras();
        if(getBundle != null){
            username    = getBundle.getString("username");
            id          = getBundle.getInt("id");
        }

        anhXa();
        init();
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
        txvMenuUsername.setText(username);
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
                    "idUser INTEGER," +
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
        toolbar         = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout    = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView  = (NavigationView) findViewById(R.id.navigationView);
        btLogin         = (Button) findViewById(R.id.btMenuLogin);
        btSignUp        = (Button) findViewById(R.id.btMenuSignUp);
        btLogout        = (Button) findViewById(R.id.btMenuLogout);
        txvMenuUsername = (TextView) findViewById(R.id.txvMenuUsername);
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
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        gdvListNovel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Novel novel = arrNovel.get(position);
                Intent intent =  new Intent(context, NovelInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("novel", novel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        acctionToolBar();
    }
    private void acctionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void sortNovel(String s){
        s = s.toUpperCase();
        arrNovel.clear();
        db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        String sql = "SELECT * FROM listNovel WHERE UPPER(name) LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[] {"%"+s+"%"});
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                int idUser = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                int totalChap = Integer.parseInt(cursor.getString(3));
                String linkImage = cursor.getString(4);
                arrNovel.add(new Novel(id, idUser, name, totalChap, linkImage));
            }
            while (cursor.moveToNext());
        };
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
            int idUser = Integer.parseInt(cursor.getString(1));
            String name = cursor.getString(2);
            int totalChap = Integer.parseInt(cursor.getString(3));
            String linkImage = cursor.getString(4);
            arrNovel.add(new Novel(id, idUser, name, totalChap, linkImage));
        }
        while (cursor.moveToNext());
        cursor.close();
        db.close();
        Toast.makeText(context, "Lay du lieu offline", Toast.LENGTH_SHORT).show();
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
            String idUser       = String.valueOf(novel.getIdUser());
            String name         = novel.getName();
            String totalChap    = String.valueOf(novel.getTotalChap());
            String linkImage    = novel.getLinkImage();
            sql = "INSERT INTO listNovel VALUES (?,?,?,?,?)";
            db.execSQL(sql, new String[]{id, idUser, name, totalChap, linkImage});
        }
        db.close();
        Toast.makeText(context, "Lay du lieu online", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void biLoi() {
        Toast.makeText(context, "Loi ket noi", Toast.LENGTH_SHORT).show();
    }
}