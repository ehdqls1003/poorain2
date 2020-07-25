package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShowrecipeActivity extends AppCompatActivity {

    ArrayList<Recipejson> rList = new ArrayList<>();
    ArrayList<Recipejson2> rList2 = new ArrayList<>();
    ArrayList<Recipejson2> rList4 = new ArrayList<>();
    ArrayList<Recipejson3> rList3 = new ArrayList<>();
    ArrayList<Recipejson3> rList5 = new ArrayList<>();
    ShowrecipeActivity_Adapter adapter;

    ImageView food_img;
    TextView food_name,irdnt_nm;
    //win = getrecipeID를 받았따.
    String win;
    String irdnt_nms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrecipe);

        //리스트 쉐어드로 불러오기
        ReadFriendsDataU();
        ReadFriendsDataU2();
        ReadFriendsDataU3();

        food_img = findViewById(R.id.food_img);
        food_name = findViewById(R.id.food_name);
        irdnt_nm = findViewById(R.id.irdnt_nm);


        Intent intent = getIntent();
        win = intent.getStringExtra("win");
        Log.i("TAG", "win : "+win);

        for(int i = 0; i < rList.size(); i++){

            if(rList.get(i).getRECIPE_ID().equals(win)){

                Glide.with(ShowrecipeActivity.this)
                        .load(rList.get(i).getIMG_URL())
                        .centerCrop()
                        .into(food_img);
                food_name.setText(rList.get(i).getRECIPE_NM_KO());
            }
        }

        for(int i = 0; i < rList2.size(); i++){

            if(rList2.get(i).getRECIPE_ID().equals(win)){

                Recipejson2 item = new Recipejson2();
                item.setRECIPE_ID(rList2.get(i).getRECIPE_ID());
                item.setCOOKING_NO(rList2.get(i).getCOOKING_NO());
                item.setCOOKING_DC(rList2.get(i).getCOOKING_DC());

                rList4.add(item);

            }
        }
        Log.i("TAG", "rList3.size() "+rList3.size());

        Log.i("TAG", "rList3.size() "+rList3.get(0).getRECIPE_ID());

        for(int i = 0; i < rList3.size(); i++){

            if(rList3.get(i).getRECIPE_ID().equals(win)){
                Recipejson3 item = new Recipejson3();
                item.setRECIPE_ID(rList3.get(i).getRECIPE_ID());
                item.setIRDNT_NM(rList3.get(i).getIRDNT_NM());
                rList5.add(item);
            }
        }

        Log.i("TAG", "rList5.size() "+rList5.size());
        for(int i = 0; i < rList5.size(); i++) {

            if(i == 0) {
                irdnt_nms = "재료 : " + rList5.get(i).getIRDNT_NM();
            }else{
                irdnt_nms =  irdnt_nms+","+rList5.get(i).getIRDNT_NM();
            }

        }

        irdnt_nm.setText(irdnt_nms);
        Log.i("TAG", "irdnt_nm: "+irdnt_nms);



        RecyclerView recyclerView = findViewById(R.id.recycler) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new ShowrecipeActivity_Adapter(rList4) ;
        recyclerView.setAdapter(adapter) ;



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
    private ArrayList<Recipejson2> ReadFriendsDataU2() {
        SharedPreferences sharedPrefs = getSharedPreferences("recipe_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("recipe2", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson2>>() {
        }.getType();
        rList2 = gson.fromJson(json, type);
        return rList2;
    }
    private ArrayList<Recipejson3> ReadFriendsDataU3() {
        SharedPreferences sharedPrefs = getSharedPreferences("recipe_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("recipe3", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson3>>() {
        }.getType();
        rList3 = gson.fromJson(json, type);
        return rList3;
    }


}