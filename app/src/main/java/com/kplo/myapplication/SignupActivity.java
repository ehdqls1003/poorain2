package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignupActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "192.168.0.94";
    private static String TAG = "phptest";

    private EditText inputid;
    private EditText inputpassword;
    AppCompatButton complete,idcheck;
    String mJsonString;
    String check_id;
    boolean check = true;
    String chang_id;

    ArrayList<Signup_Item> uList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php");

        inputid = (EditText)findViewById(R.id.inputid);
        inputpassword = (EditText)findViewById(R.id.inputpassword);
        idcheck = findViewById(R.id.idcheck);
        complete = findViewById(R.id.complete);

        idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = inputid.getText().toString();
                chang_id = id;

                if(!id.isEmpty()) {

                    for (int i = 0; i < uList.size(); i++) {

                        check_id = uList.get(i).getId();

                        if (id.equals(check_id)) {
                            check = true;
                            break;
                        } else {
                            check = false;
                        }

                    }

                    if(!check){
                        Toast myToast = Toast.makeText(SignupActivity.this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT);
                        myToast.show();
                    }else{
                        Toast myToast = Toast.makeText(SignupActivity.this, "사용할수 없는 아이디입니다.", Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                }else{

                    Toast myToast = Toast.makeText(SignupActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }

            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = inputid.getText().toString();
                String pw = inputpassword.getText().toString();

                if (!id.isEmpty()) {
                    if (!pw.isEmpty()) {
                        if (!check) {
                            if (id.equals(chang_id)) {

                                InsertData task = new InsertData();
                                task.execute("http://" + IP_ADDRESS + "/insert.php", id, pw);
                                Toast myToast = Toast.makeText(SignupActivity.this, "회원가입을 완료하셨습니다.", Toast.LENGTH_SHORT);
                                myToast.show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast myToast = Toast.makeText(SignupActivity.this, "중복검사를 실시해주세요.", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        } else {
                            Toast myToast = Toast.makeText(SignupActivity.this, "중복검사를 실시해주세요.", Toast.LENGTH_SHORT);
                            myToast.show();

                            idcheck.requestFocus();

                        }
                    }else{
                        Toast myToast = Toast.makeText(SignupActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT);
                        myToast.show();
                        inputpassword.requestFocus();
                    }

                }else{
                    Toast myToast = Toast.makeText(SignupActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                    inputid.requestFocus();
                }
            }
        });

    }



        class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SignupActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[1];
            String pw = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "id=" + id + "&pw=" + pw;

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




    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SignupActivity.this,
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


                uList.add(user);//리스트에저장


            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }





}