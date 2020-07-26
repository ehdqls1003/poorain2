package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Recipe_rendom extends AppCompatActivity {

    ArrayList<Recipejson> rList = new ArrayList<>();
    ArrayList<Recipejson2> rList2 = new ArrayList<>();
    ArrayList<Recipejson3> rList3 = new ArrayList<>();

    TextView food_name,food_sumry;
    AppCompatButton button_p;
    ImageView food_img;

    Handler han;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_rendom);

        //리스트 쉐어드로 불러오기
        ReadFriendsDataU();
        ReadFriendsDataU2();
        ReadFriendsDataU3();

        food_img = findViewById(R.id.food_img);
        food_name = findViewById(R.id.food_name);
        food_sumry = findViewById(R.id.food_sumry);
        button_p = findViewById(R.id.button_p);

        han = new Handler(){
            @Override
            public void handleMessage(Message msg){
              super.handleMessage(msg);

                Glide.with(Recipe_rendom.this)
                        .load(rList.get(msg.arg1).getIMG_URL())
                        .centerCrop()
                        .into(food_img);
                food_sumry.setText(rList.get(msg.arg1).getSUMRY());
                food_name.setText(rList.get(msg.arg1).getRECIPE_NM_KO());


            }
        };

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    Message msg = han.obtainMessage();
                    Random rnd = new Random();
                    int num = rnd.nextInt(500);
                    msg.arg1 = num;
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }

            }
        });
        th.start();









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