package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrecipe);

        //리스트 쉐어드로 불러오기
        ReadFriendsDataU();


        


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




    //shared 배열출력 카트리스트정보
    private ArrayList<Recipejson> ReadFriendsDataU() {
        SharedPreferences sharedPrefs = getSharedPreferences("user_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("User", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson>>() {
        }.getType();
        rList = gson.fromJson(json, type);
        return rList;
    }


}