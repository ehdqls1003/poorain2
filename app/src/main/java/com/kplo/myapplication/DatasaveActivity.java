package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class DatasaveActivity extends AppCompatActivity {

    ArrayList<Recipejson> rList = new ArrayList<>();
    ArrayList<Recipejson2> rList2 = new ArrayList<>();
    ArrayList<Recipejson3> rList3 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasave);


        jsonParsing(getJsonString());
        jsonParsing2(getJsonString2());
        jsonParsing3(getJsonString3());

        SaveFriendData(rList);
        SaveFriendData2(rList2);
        SaveFriendData3(rList3);


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
                recipe.setTY_NM(movieObject.getString("TY_NM"));

                rList.add(recipe);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getJsonString2()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("레시피과정.json");
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

    private void jsonParsing2(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray movieArray = jsonObject.getJSONArray("data");

            for(int i=0; i<movieArray.length(); i++)
            {
                JSONObject movieObject = movieArray.getJSONObject(i);

                Recipejson2 recipe = new Recipejson2();

                recipe.setRECIPE_ID(movieObject.getString("RECIPE_ID"));
                recipe.setCOOKING_NO(movieObject.getString("COOKING_DC"));
                recipe.setCOOKING_DC(movieObject.getString("COOKING_NO"));

                rList2.add(recipe);



            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getJsonString3()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("레시피재료.json");
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

    private void jsonParsing3(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray movieArray = jsonObject.getJSONArray("data");

            for(int i=0; i<movieArray.length(); i++)
            {
                JSONObject movieObject = movieArray.getJSONObject(i);

                Recipejson3 recipe = new Recipejson3();

                recipe.setRECIPE_ID(movieObject.getString("RECIPE_ID"));
                recipe.setIRDNT_NM(movieObject.getString("IRDNT_NM"));
                recipe.setIRDNT_CPCTY(movieObject.getString("IRDNT_CPCTY"));

                rList3.add(recipe);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SaveFriendData(ArrayList<Recipejson> friends) {
        SharedPreferences preferences = getSharedPreferences("recipe_list",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        editor.putString("recipe", json);
        editor.commit();
    }

    private void SaveFriendData2(ArrayList<Recipejson2> friends) {
        SharedPreferences preferences = getSharedPreferences("recipe_list",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        editor.putString("recipe2", json);
        editor.commit();
    }

    private void SaveFriendData3(ArrayList<Recipejson3> friends) {
        SharedPreferences preferences = getSharedPreferences("recipe_list",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        editor.putString("recipe3", json);
        editor.commit();
    }


}