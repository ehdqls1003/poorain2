package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddfriendActivity extends AppCompatActivity implements AddfriendActivity_Adapter.MyRecyclearViewClickListener, TextWatcher {

    String mJsonString;
    String TAG = "친구";
    RecyclerView recyclerView;
    AddfriendActivity_Adapter adapter;
    private static String IP_ADDRESS = "192.168.0.94";
    ArrayList<Signup_Item> uList = new ArrayList<>();
    ArrayList<Signup_Item> uList2 = new ArrayList<>();
    ArrayList<friend_Item> fList = new ArrayList<>();
    EditText search_edit;
    TextView howabout;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);


        //로그인데이터값
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("user_login",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        user_id = sf.getString("id","");

        search_edit = findViewById(R.id.search_edit);
        howabout = findViewById(R.id.howabout);

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php");

        GetData2 task2 = new GetData2();
        task2.execute( "http://" + IP_ADDRESS + "/getjson_friend.php");


        search_edit.addTextChangedListener(this);

    }

    @Override
    public void onItemClicked(int position,AddfriendActivity_Adapter.ViewHolder holder) {

        String id = holder.user_id.getText().toString();
        Intent intent = new Intent(AddfriendActivity.this, Recent_RecipeActivity.class);
        intent.putExtra("이웃보기",id);
        intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }

    @Override
    public void followClicked(int position, AddfriendActivity_Adapter.ViewHolder holder) {

        holder.follow.setVisibility(View.INVISIBLE);
        holder.follow_c.setVisibility(View.VISIBLE);
        String User_pos = holder.user_id.getText().toString();



        InsertData task = new InsertData();
        task.execute("http://" + IP_ADDRESS + "/insert_friend.php", user_id, User_pos);



    }

    @Override
    public void follow_cClicked(int position, AddfriendActivity_Adapter.ViewHolder holder) {

        holder.follow.setVisibility(View.VISIBLE);
        holder.follow_c.setVisibility(View.INVISIBLE);
        String User_pos = holder.user_id.getText().toString();

        DeleteData task = new DeleteData();
        task.execute("http://" + IP_ADDRESS + "/delete_friend.php", user_id, User_pos);


    }

    //에딧테스트 검색
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        adapter.getFilter().filter(s);


        recyclerView.setVisibility(View.VISIBLE);
        howabout.setVisibility(View.INVISIBLE);
       if (s.length() == 0){
            Log.i("보임?","들옹ㅁ");
            recyclerView.setVisibility(View.INVISIBLE);
            howabout.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddfriendActivity.this,
                    "Please Wait", null, true, true);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

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
        String TAG_JSON="user_data";
        String TAG_ID = "id";
        String TAG_PW = "pw";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            //제이슨 어레이를 서버에서 받아오고
            for(int i=0;i<jsonArray.length();i++){

                //제이슨 배열형식 내용을 각 변수에 뿌려줌

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String pw = item.getString(TAG_PW);

                Signup_Item user = new Signup_Item();

                user.setId(id);
                user.setPw(pw);
                //지금현제 엑티비티 배열에추가해주고

                if(!id.equals(user_id)) {
                    uList.add(user);//리스트에저장
                }

            }







        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    private class GetData2 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AddfriendActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){

                Log.d(TAG, "회원정보불러오기 " + result);
            }
            else {

                mJsonString = result;
                showResult2();
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

    private void showResult2(){
/*
        Log.i("뭐지",""+mJsonString);*/
        String TAG_JSON="friend";
        String TAG_ID = "id";
        String TAG_ID_FRIEND = "id_friend";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            //제이슨 어레이를 서버에서 받아오고
            for(int i=0;i<jsonArray.length();i++){

                //제이슨 배열형식 내용을 각 변수에 뿌려줌

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String id_friend = item.getString(TAG_ID_FRIEND);

                friend_Item user = new friend_Item();

                user.setId(id);
                user.setId_friend(id_friend);
                //지금현제 엑티비티 배열에추가해주고


                fList.add(user);//리스트에저장


            }


            for(int i = 0 ; i < fList.size(); i++) {

                if(fList.get(i).getId().equals(user_id)){

                    for (int j = 0; j < uList.size(); j++) {

                        if (uList.get(j).getId().equals(fList.get(i).getId_friend())) {

                            Signup_Item user = new Signup_Item();

                            user.setId(uList.get(j).getId());
                            user.setPw(uList.get(j).getPw());
                            //지금현제 엑티비티 배열에추가해주고

                            uList2.add(user);//리스트에저장

                        }
                    }

                }

            }/*
            Log.i("드가라","가자"+uList2.size());

            if (uList2.size() == 0){
                Log.i("드가라","가자");
                Signup_Item user = new Signup_Item();

                user.setId("a");
                user.setPw("a");
                //지금현제 엑티비티 배열에추가해주고

                uList2.add(user);//리스트에저장
            }*/


            recyclerView = findViewById(R.id.recycler) ;
            recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            adapter = new AddfriendActivity_Adapter(uList,uList2);
            recyclerView.setAdapter(adapter) ;/*
            adapter.getFilter().filter(ty);*/
            //이거안해주면 리스너안먹힘
            adapter.setOnClickListener(this);




        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    //데이터추가삭제

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
            String id_friend = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&id_friend=" + id_friend;

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
            String id_friend = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&id_friend=" + id_friend;

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


}