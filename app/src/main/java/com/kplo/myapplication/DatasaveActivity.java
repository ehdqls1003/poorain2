package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
                recipe.setRECIPE_ID(movieObject.getString("IRDNT_NM"));
                recipe.setRECIPE_ID(movieObject.getString("IRDNT_CPCTY"));

                rList3.add(recipe);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


}