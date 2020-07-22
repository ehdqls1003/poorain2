package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
/*
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;*/

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
public class LoginActivity extends AppCompatActivity {


    private static String TAG = "phptest";
    private static String IP_ADDRESS = "192.168.234.139";
    String mJsonString;
    TextView findid,findpw;
    AppCompatButton loginbutton,signup;
    EditText inputid,inputpw;
    boolean b_auto;
    boolean login;
    String check_id;
    String check_pw;

    ArrayList<Signup_Item> uList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findid = findViewById(R.id.findid);
        findpw = findViewById(R.id.findpw);
        loginbutton = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signup);
        inputid = findViewById(R.id.inputid);
        inputpw = findViewById(R.id.inputpw);

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Login_id = inputid.getText().toString();
                String Login_pw = inputpw.getText().toString();

                for(int i = 0; i < uList.size(); i ++){

                    check_id = uList.get(i).getId();

                    if(Login_id.equals(check_id)){

                        Log.i("Login_Activity","아이디있음"+i);

                        check_pw = uList.get(i).getPw();

                        if(Login_pw.equals(check_pw)) {

                            login = true;
                            break;
                        }else{
                        }


                    }else{
                    }

                }
                if(login) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast myToast = Toast.makeText(LoginActivity.this,"회원정보를 확인해주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }

            }
        });


    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this,
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