package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecipesearchActivity_ty extends AppCompatActivity implements RecipesearchActivity_ty_Adapter.MyRecyclearViewClickListener {


    ArrayList<Recipejson> rList = new ArrayList<>();
    ArrayList<Recipejson> rList2 = new ArrayList<>();
    RecipesearchActivity_ty_Adapter adapter;
    RecyclerView recyclerView;

    EditText search_edit;
    ImageView back_icon;
    TextView title;
    String ty;
    String TAG = "종류";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipesearch_ty);

        ReadFriendsDataU();

        back_icon = findViewById(R.id.back_icon);
        title = findViewById(R.id.title);
        search_edit = findViewById(R.id.search_edit);

        //음식종류를받아옴
        Intent intent = getIntent();
        ty = intent.getStringExtra("종류");
        Log.i(TAG, "onCreate: "+ty);
        title.setText(ty);
        recyclerView = findViewById(R.id.recycler) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new RecipesearchActivity_ty_Adapter(rList) ;
        recyclerView.setAdapter(adapter) ;

        if(ty.equals("전체목록")){
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            search_edit.setText(ty);


            adapter.getFilter().filter(ty);
        }
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
    private ArrayList<Recipejson> ReadFriendsDataU() {
        SharedPreferences sharedPrefs = getSharedPreferences("recipe_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("recipe", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson>>() {
        }.getType();
        rList = gson.fromJson(json, type);
        return rList;
    }


    @Override
    public void onItemClicked(int position,String win) {
        /* Toast.makeText(this,"아이템클릭"+win,Toast.LENGTH_SHORT).show(); */

        Intent intent = new Intent(RecipesearchActivity_ty.this, ShowrecipeActivity.class);
        intent.putExtra("win",win);
        startActivity(intent);
    }


}