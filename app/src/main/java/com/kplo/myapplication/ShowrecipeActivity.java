package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class ShowrecipeActivity extends AppCompatActivity {



    ArrayList<Recipejson> rList = new ArrayList<>();
    ArrayList<Recipejson2> rList2 = new ArrayList<>();
    ArrayList<Recipejson2> rList4 = new ArrayList<>();
    ArrayList<Recipejson3> rList3 = new ArrayList<>();
    ArrayList<Recipejson3> rList5 = new ArrayList<>();
    ArrayList<Recipejson> recent = new ArrayList<>();
    ArrayList<star_Item> recent2 = new ArrayList<>();

    Handler han;
    Thread th;
    boolean stop = true;

    ShowrecipeActivity_Adapter adapter;

    private static String IP_ADDRESS = "192.168.0.94";
    String mJsonString;

    ImageView food_img,star,star2;
    TextView food_name,irdnt_nm;
    AppCompatButton replay_b;
    String user_id;
    //win = getrecipeID를 받았따.
    String win;
    String irdnt_nms;
    int type;
    String TAG = "star";
    boolean no = true;
    /*
    int win2 = Integer.parseInt(win);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrecipe);



        //로그인데이터값
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("user_login",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        user_id = sf.getString("id","");


        //리스트 쉐어드로 불러오기
        ReadFriendsDataU();
        ReadFriendsDataU2();
        ReadFriendsDataU3();



/*


        //예외처리
        SharedPreferences preferences = getSharedPreferences("recipe_list",MODE_PRIVATE);
        String text = preferences.getString("recent","");

        if(text.equals("")) {
        }else{
            ReadFriendsDataU4();
        }*/

        food_img = findViewById(R.id.food_img);
        food_name = findViewById(R.id.food_name);
        irdnt_nm = findViewById(R.id.irdnt_nm);
        star = findViewById(R.id.star);
        star2 = findViewById(R.id.star2);
        replay_b = findViewById(R.id.replay_b);

        replay_b.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        win = intent.getStringExtra("win");
        type = intent.getIntExtra("type",0);
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

        //sql 에서 star데이터 가져오는법
        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson_star.php");






        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                star.setVisibility(View.INVISIBLE);
                star2.setVisibility(View.VISIBLE);

                for (int i = 0; i < rList.size(); i++) {

                    if (rList.get(i).getRECIPE_ID().equals(win)) {

                        Recipejson recipe = new Recipejson();

                        recipe.setRECIPE_ID(rList.get(i).getRECIPE_ID());
                        recipe.setIMG_URL(rList.get(i).getIMG_URL());
                        recipe.setRECIPE_NM_KO(rList.get(i).getRECIPE_NM_KO());
                        recipe.setSUMRY(rList.get(i).getSUMRY());
                        recipe.setTY_NM(rList.get(i).getTY_NM());

                        recent.add(recipe);


                    }
                }

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert_star.php", user_id, win);


                //mysql 에 데이터가들어가야함

            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                star2.setVisibility(View.INVISIBLE);
                star.setVisibility(View.VISIBLE);

                for (int i = 0; i < recent.size(); i++) {

                    if (recent.get(i).getRECIPE_ID().equals(win)){
                        recent.remove(i);
                    }

                }

                DeleteData task = new DeleteData();
                task.execute("http://" + IP_ADDRESS + "/delete_star.php", user_id, win);

                //mysql 에 데이터가나가야함

            }
        });

        //type 이있을때 다시하기버튼이 보이게한다
        if (type == 0){
        }else{
            replay_b.setVisibility(View.VISIBLE);
        }

        replay_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type == 1){
                    Intent intent = new Intent(ShowrecipeActivity.this, Recipe.class);
                    intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }else if(type == 2){
                    Intent intent = new Intent(ShowrecipeActivity.this, Recipe_rendom.class);
                    intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }

            }
        });



    }

    private class GetData extends AsyncTask<String, Void, String> {

        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result == null){

                Log.d(TAG, "회원정보불러오기 " + result);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            //검색할거보내는거다



            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(7000);
                httpURLConnection.setConnectTimeout(7000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                //outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }
    //받아온데이터 리스트에 저장해주는것
    private void showResult(){
/*
        Log.i("뭐지",""+mJsonString);*/
        String TAG_JSON="star";
        String TAG_ID = "id";
        String TAG_RECIPE_ID = "RECIPE_ID";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            //제이슨 어레이를 서버에서 받아오고
            for(int i=0;i<jsonArray.length();i++){

                //제이슨 배열형식 내용을 각 변수에 뿌려줌

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String RECIPE_ID = item.getString(TAG_RECIPE_ID);

                star_Item star = new star_Item();

                star.setId(id);
                star.setRECIPE_ID(RECIPE_ID);
                //지금현제 엑티비티 배열에추가해주고

                recent2.add(star);//리스트에저장



            }

            Log.i("사이즈",""+recent2.size());

            for(int i = 0 ; i < recent2.size(); i++) {

                if(recent2.get(i).getId().equals(user_id)){

                    for (int j = 0; j < rList.size(); j++) {

                        if (rList.get(j).getRECIPE_ID().equals(recent2.get(i).getRECIPE_ID())) {

                            Recipejson recipe = new Recipejson();

                            recipe.setRECIPE_ID(rList.get(j).getRECIPE_ID());
                            recipe.setIMG_URL(rList.get(j).getIMG_URL());
                            recipe.setRECIPE_NM_KO(rList.get(j).getRECIPE_NM_KO());
                            recipe.setSUMRY(rList.get(j).getSUMRY());
                            recipe.setTY_NM(rList.get(j).getTY_NM());

                            recent.add(recipe);


                        }
                    }

                }

            }

            for (int i = 0; i < recent.size(); i++) {

                Log.i("사이즈","sfff"+recent.get(i).getRECIPE_ID());

                if (recent.get(i).getRECIPE_ID().equals(win)){
                    star.setVisibility(View.INVISIBLE);
                    star2.setVisibility(View.VISIBLE);
                    Log.i("사이즈","도는중"+win);
                    break;
                }

            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    class InsertData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String RECIPE_ID = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&RECIPE_ID=" + RECIPE_ID;

            Log.d("phptest","포스트말론:"+postParameters);


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class DeleteData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String RECIPE_ID = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&RECIPE_ID=" + RECIPE_ID;

            Log.d("phptest","포스트말론:"+postParameters);


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
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
/*

    private ArrayList<Recipejson> ReadFriendsDataU4() {
        SharedPreferences sharedPrefs = getSharedPreferences("recipe_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("recent", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson>>() {
        }.getType();
        recent = gson.fromJson(json, type);
        return recent;
    }
*/
/*
    private void SaveFriendData4(ArrayList<Recipejson> friends) {
        SharedPreferences preferences = getSharedPreferences("recipe_list",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        editor.putString("recent", json);
        editor.commit();
    }*/


    @Override
    protected void onStart() {
        super.onStart();



    }
}