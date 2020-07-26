package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Random;

public class Recipe extends AppCompatActivity {

    ArrayList<Recipejson> rList = new ArrayList<>();

    ImageView food1,food2,win_img,vs;
    TextView food1_t,food2_t,win_sumry,win_name,gang;
    AppCompatButton button_r;

    ArrayList<Integer> fList = new ArrayList<>();//64강
    ArrayList<Integer> fList2 = new ArrayList<>();//32강
    ArrayList<Integer> fList3 = new ArrayList<>();//16강
    ArrayList<Integer> fList4 = new ArrayList<>();//8강
    ArrayList<Integer> fList5 = new ArrayList<>();//4강
    ArrayList<Integer> fList6 = new ArrayList<>();//2강

    int win = 0;

    boolean wh = true;
    int f_a = 0 ;
    int f_b = 1 ;
    int stage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        food1 = findViewById(R.id.food1);
        food2 = findViewById(R.id.food2);
        food1_t = findViewById(R.id.food1_t);
        food2_t = findViewById(R.id.food2_t);
        win_img = findViewById(R.id.win_img);
        win_name = findViewById(R.id.win_name);
        win_sumry = findViewById(R.id.win_sumry);
        button_r = findViewById(R.id.button_r);
        gang = findViewById(R.id.gang);
        vs = findViewById(R.id.vs);

        gang.setText("64 강");

        //rlist 생성
        jsonParsing(getJsonString());


        //64개 랜덤숫자넣기
        Random rnd = new Random();
        for(int i = 0; i < 64 ; ){
            wh=true;
            int num = rnd.nextInt(500);
            for(int j = 0; j < i; j++) {
                if(fList.get(j) == num) {
                    wh = false;
                    break;
                }
            }
            if(wh){
                fList.add(num);
                i++;
            }
        }

        for(int i = 0; i < 64 ; i++){
            Log.i("시불",""+fList.get(i)+" i : "+i);

        }




        Glide.with(this)
                .load(rList.get(fList.get(f_a)).getIMG_URL())
                .centerCrop()
                .into(food1);

        food1_t.setText(rList.get(fList.get(f_a)).getRECIPE_NM_KO());

        Glide.with(this)
                .load(rList.get(fList.get(f_b)).getIMG_URL())
                .centerCrop()
                .into(food2);
        food2_t.setText(rList.get(fList.get(f_b)).getRECIPE_NM_KO());


        food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stage == 0) {
                    if(f_a < 62) {
                        fList2.add(fList.get(f_a));

                        f_a += 2;
                        f_b += 2;
                        Log.i("번호","f_a :"+f_a);

                        Glide.with(Recipe.this)
                                .load(rList.get(fList.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList2.add(fList.get(f_a));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"32강.", Toast.LENGTH_SHORT);
                        myToast.show();

                        gang.setText("32 강");

                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList2.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList2.get(f_b)).getRECIPE_NM_KO());
                    }
                }else if(stage == 1){
                    if(f_a < 30) {
                        fList3.add(fList2.get(f_a));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList2.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList2.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList3.add(fList2.get(f_a));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"16강.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("16 강");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList3.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList3.get(f_b)).getRECIPE_NM_KO());
                    }


                }else if(stage == 2){
                    if(f_a < 14) {
                        fList4.add(fList3.get(f_a));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList3.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList3.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList4.add(fList3.get(f_a));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"8강.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("8 강");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList4.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList4.get(f_b)).getRECIPE_NM_KO());
                    }
                }else if(stage == 3){

                    if(f_a < 6) {
                        fList5.add(fList4.get(f_a));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList4.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList4.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList5.add(fList4.get(f_a));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"4강.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("4 강");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList5.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList5.get(f_b)).getRECIPE_NM_KO());
                    }

                }else if(stage == 4){
                    if(f_a < 2) {
                        fList6.add(fList5.get(f_a));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList5.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList5.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList6.add(fList5.get(f_a));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"결승.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("결승전");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList6.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList6.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList6.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList6.get(f_b)).getRECIPE_NM_KO());
                    }
                }else if(stage == 5){

                        win = fList6.get(f_a);

                        Glide.with(Recipe.this)
                                .load(rList.get(win).getIMG_URL())
                                .centerCrop()
                                .into(win_img);

                    win_name.setText(rList.get(win).getRECIPE_NM_KO());
                    win_sumry.setText(rList.get(win).getSUMRY());
                    win_img.setVisibility(View.VISIBLE);
                    win_name.setVisibility(View.VISIBLE);
                    win_sumry.setVisibility(View.VISIBLE);
                    button_r.setVisibility(View.VISIBLE);
                    food1.setVisibility(View.INVISIBLE);
                    food2.setVisibility(View.INVISIBLE);
                    food1_t.setVisibility(View.INVISIBLE);
                    food2_t.setVisibility(View.INVISIBLE);
                    vs.setVisibility(View.INVISIBLE);
                    gang.setVisibility(View.INVISIBLE);

                    Toast myToast = Toast.makeText(Recipe.this,"끝.", Toast.LENGTH_SHORT);
                    myToast.show();
                    }
                }






        });





        food2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stage == 0) {
                    if(f_a < 62) {
                        fList2.add(fList.get(f_b));

                        f_a += 2;
                        f_b += 2;
                        Log.i("번호","f_a :"+f_a);

                        Glide.with(Recipe.this)
                                .load(rList.get(fList.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList2.add(fList.get(f_b));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"32강.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("32 강");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList2.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList2.get(f_b)).getRECIPE_NM_KO());
                    }
                }else if(stage == 1){
                    if(f_a < 30) {
                        fList3.add(fList2.get(f_b));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList2.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList2.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList2.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList3.add(fList2.get(f_b));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"16강.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("16 강");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList3.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList3.get(f_b)).getRECIPE_NM_KO());
                    }


                }else if(stage == 2){
                    if(f_a < 14) {
                        fList4.add(fList3.get(f_b));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList3.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList3.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList3.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList4.add(fList3.get(f_b));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"8강.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("8 강");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList4.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList4.get(f_b)).getRECIPE_NM_KO());
                    }
                }else if(stage == 3){

                    if(f_a < 6) {
                        fList5.add(fList4.get(f_b));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList4.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList4.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList4.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList5.add(fList4.get(f_b));
                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"4강.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("4 강");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList5.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList5.get(f_b)).getRECIPE_NM_KO());
                    }

                }else if(stage == 4){
                    if(f_a < 2) {
                        fList6.add(fList5.get(f_b));

                        f_a += 2;
                        f_b += 2;

                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList5.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList5.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList5.get(f_b)).getRECIPE_NM_KO());
                    }else{
                        fList6.add(fList5.get(f_b));

                        f_a = 0;
                        f_b = 1;
                        stage ++;
                        Toast myToast = Toast.makeText(Recipe.this,"결승.", Toast.LENGTH_SHORT);
                        myToast.show();
                        gang.setText("결승전");
                        Glide.with(Recipe.this)
                                .load(rList.get(fList6.get(f_a)).getIMG_URL())
                                .centerCrop()
                                .into(food1);

                        food1_t.setText(rList.get(fList6.get(f_a)).getRECIPE_NM_KO());

                        Glide.with(Recipe.this)
                                .load(rList.get(fList6.get(f_b)).getIMG_URL())
                                .centerCrop()
                                .into(food2);
                        food2_t.setText(rList.get(fList6.get(f_b)).getRECIPE_NM_KO());
                    }
                }else if(stage == 5){

                    win = fList6.get(f_b);

                    Glide.with(Recipe.this)
                            .load(rList.get(win).getIMG_URL())
                            .centerCrop()
                            .into(win_img);

                    win_name.setText(rList.get(win).getRECIPE_NM_KO());
                    win_sumry.setText(rList.get(win).getSUMRY());
                    win_img.setVisibility(View.VISIBLE);
                    win_name.setVisibility(View.VISIBLE);
                    win_sumry.setVisibility(View.VISIBLE);
                    button_r.setVisibility(View.VISIBLE);
                    food1.setVisibility(View.INVISIBLE);
                    food2.setVisibility(View.INVISIBLE);
                    food1_t.setVisibility(View.INVISIBLE);
                    food2_t.setVisibility(View.INVISIBLE);
                    vs.setVisibility(View.INVISIBLE);
                    gang.setVisibility(View.INVISIBLE);

                    Toast myToast = Toast.makeText(Recipe.this,"끝.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }




        });



        button_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Recipe.this, ShowrecipeActivity.class);
                intent.putExtra("win",rList.get(win).getRECIPE_ID());
                startActivity(intent);
                finish();

            }
        });

    }

    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("레시피기본.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    private void jsonParsing(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray movieArray = jsonObject.getJSONArray("data");

            for(int i=0; i<movieArray.length(); i++)
            {
                JSONObject movieObject = movieArray.getJSONObject(i);

                Recipejson recipe = new Recipejson();

                recipe.setRECIPE_ID(movieObject.getString("RECIPE_ID"));
                recipe.setIMG_URL(movieObject.getString("IMG_URL"));
                recipe.setRECIPE_NM_KO(movieObject.getString("RECIPE_NM_KO"));
                recipe.setSUMRY(movieObject.getString("SUMRY"));

                rList.add(recipe);

                /*SaveFriendData(rList);*/

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }




/*

    //Shared 카트리스트 배열저장
    private void SaveFriendData(ArrayList<Recipejson> friends) {
        SharedPreferences preferences = getSharedPreferences("recipe_list",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        editor.putString("recipe", json);
        editor.commit();
    }
*/




/*
    //shared 배열출력 카트리스트정보
    private ArrayList<Signup_Item> ReadFriendsDataU() {
        SharedPreferences sharedPrefs = getSharedPreferences("user_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("User", "EMPTY");
        Type type = new TypeToken<ArrayList<Signup_Item>>() {
        }.getType();
        uList = gson.fromJson(json, type);
        return uList;
    }*/




}