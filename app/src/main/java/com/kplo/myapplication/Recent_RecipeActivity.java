package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Recent_RecipeActivity extends AppCompatActivity implements Recent_RecipeActivity_Adapter.MyRecyclearViewClickListener {


    ArrayList<Recipejson> recent = new ArrayList<>();
    Recent_RecipeActivity_Adapter adapter;
    RecyclerView recyclerView;

    ImageView back_icon;
    TextView title;/*
    String ty;*/
    String TAG = "종류";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent__recipe);

        //예외처리
        SharedPreferences preferences = getSharedPreferences("recipe_list",MODE_PRIVATE);
        String text = preferences.getString("recent","");

        if(text.equals("")) {
        }else{
            ReadFriendsDataU4();
        }

        back_icon = findViewById(R.id.back_icon);
        title = findViewById(R.id.title);
/*
        //음식종류를받아옴
        Intent intent = getIntent();
        ty = intent.getStringExtra("종류");*//*
        Log.i(TAG, "onCreate: "+ty);*/

        recyclerView = findViewById(R.id.recycler) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new Recent_RecipeActivity_Adapter(recent) ;
        recyclerView.setAdapter(adapter) ;/*
        adapter.getFilter().filter(ty);*/
        //이거안해주면 리스너안먹힘
        adapter.setOnClickListener(this);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

    //shared 배열출력 카트리스트정보
    private ArrayList<Recipejson> ReadFriendsDataU4() {
        SharedPreferences sharedPrefs = getSharedPreferences("recipe_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("recent", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson>>() {
        }.getType();
        recent = gson.fromJson(json, type);
        return recent;
    }

    @Override
    public void onItemClicked(int position, String win) {

        Intent intent = new Intent(Recent_RecipeActivity.this, ShowrecipeActivity.class);
        intent.putExtra("win",win);
        startActivity(intent);
    }
}