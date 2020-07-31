package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class Recent_RecipeActivity extends AppCompatActivity implements Recent_RecipeActivity_Adapter.MyRecyclearViewClickListener {


    ArrayList<Recipejson> rList = new ArrayList<>();
    ArrayList<Recipejson> recent = new ArrayList<>();
    ArrayList<star_Item> recent2 = new ArrayList<>();
    Recent_RecipeActivity_Adapter adapter;
    RecyclerView recyclerView;

    ImageView back_icon;
    TextView title;
    String user_id;

    private static String IP_ADDRESS = "192.168.0.94";
    String mJsonString;
    String TAG = "종류";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent__recipe);


        //로그인데이터값
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("user_login",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        user_id = sf.getString("id","");

        //인텐트 유효성감서하고
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //이웃보기 일때는 이웃아이디를 가져온다.
            Intent intent = getIntent();
            user_id = intent.getStringExtra("이웃보기");
        }

        ReadFriendsDataU();

        back_icon = findViewById(R.id.back_icon);
        title = findViewById(R.id.title);
/*
        //음식종류를받아옴
        Intent intent = getIntent();
        ty = intent.getStringExtra("종류");*//*
        Log.i(TAG, "onCreate: "+ty);*/


        //sql 에서 star데이터 가져오는법
        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson_star.php");

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
/*
    //shared 배열출력 카트리스트정보
    private ArrayList<Recipejson> ReadFriendsDataU4() {
        SharedPreferences sharedPrefs = getSharedPreferences("recipe_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("recent", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson>>() {
        }.getType();
        recent = gson.fromJson(json, type);
        return recent;
    }*/

    @Override
    public void onItemClicked(int position, String win) {

        Intent intent = new Intent(Recent_RecipeActivity.this, ShowrecipeActivity.class);
        intent.putExtra("win",win);
        startActivity(intent);
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

            recyclerView = findViewById(R.id.recycler) ;
            recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            adapter = new Recent_RecipeActivity_Adapter(recent) ;
            recyclerView.setAdapter(adapter) ;/*
            adapter.getFilter().filter(ty);*/
            //이거안해주면 리스너안먹힘
            adapter.setOnClickListener(this);




        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    private ArrayList<Recipejson> ReadFriendsDataU() {
        SharedPreferences sharedPrefs = getSharedPreferences("recipe_list",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("recipe", "EMPTY");
        Type type = new TypeToken<ArrayList<Recipejson>>() {
        }.getType();
        rList = gson.fromJson(json, type);
        return rList;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        recent = new ArrayList<>();
        recent2 = new ArrayList<>();
        //sql 에서 star데이터 가져오는법
        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson_star.php");
        adapter.notifyDataSetChanged();

    }
}