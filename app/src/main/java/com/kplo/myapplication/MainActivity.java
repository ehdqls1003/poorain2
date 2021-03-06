package com.kplo.myapplication;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.ItemTouchHelper;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Canvas;
        import android.media.Image;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.PopupMenu;
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


public class MainActivity extends AppCompatActivity implements MainActivity_Adapter.MyRecyclearViewClickListener{

    TextView home,place,partylist,myparty;
    String user_id;
    ImageView addfriend;


    ArrayList<Signup_Item> uList = new ArrayList<>();
    ArrayList<Signup_Item> uList2 = new ArrayList<>();
    ArrayList<friend_Item> fList = new ArrayList<>();


    String TAG = "메인";
    String mJsonString ;
    RecyclerView recyclerView;
    MainActivity_Adapter adapter;
    private static String IP_ADDRESS = "192.168.0.94";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인데이터값
        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("user_login",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        user_id = sf.getString("id","");

        home = findViewById(R.id.home);
        place = findViewById(R.id.place);
        partylist = findViewById(R.id.partylist);
        myparty = findViewById(R.id.myparty);
        addfriend = findViewById(R.id.addfriend);


        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php");

        GetData2 task2 = new GetData2();
        task2.execute( "http://" + IP_ADDRESS + "/getjson_friend.php");

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipehelperActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });
        partylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RecipesearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        myparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyinfoActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });

        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddfriendActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });



    }


    @Override
    public void onItemClicked(int position,MainActivity_Adapter.ViewHolder holder) {
/*
        String id = holder.how_r.getText().toString();
        Intent intent = new Intent(MainActivity.this, Recent_RecipeActivity.class);
        intent.putExtra("이웃보기",id);
        intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/

    }


    @Override
    public void onButton_1Clicked(final int position, View v, final MainActivity_Adapter.ViewHolder holder) {

        PopupMenu p = new PopupMenu(
                getApplicationContext(), // 현재 화면의 제어권자
                v); // anchor : 팝업을 띄울 기준될 위젯
        getMenuInflater().inflate(R.menu.context_menu, p.getMenu());
        // 이벤트 처리
        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:

                String id = holder.how_r.getText().toString();
                Intent intent = new Intent(MainActivity.this, Recent_RecipeActivity.class);
                intent.putExtra("이웃보기",id);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                break;

            case R.id.menu2:

                DeleteData task = new DeleteData();
                task.execute("http://" + IP_ADDRESS + "/delete_friend.php", user_id, adapter.mData.get(position).getId());

                adapter.mData.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                adapter.notifyDataSetChanged();
                break;

        }

                return false;
            }
        });
        p.show(); // 메뉴를 띄우기






    }

    private class GetData2 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
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

    private void showResult2() {
/*
        Log.i("뭐지",""+mJsonString);*/
        String TAG_JSON = "friend";
        String TAG_ID = "id";
        String TAG_ID_FRIEND = "id_friend";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            //제이슨 어레이를 서버에서 받아오고
            for (int i = 0; i < jsonArray.length(); i++) {

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


            for (int i = 0; i < fList.size(); i++) {

                if (fList.get(i).getId().equals(user_id)) {

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

            }

            recyclerView = findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            adapter = new MainActivity_Adapter(uList2);
            recyclerView.setAdapter(adapter);/*
            adapter.getFilter().filter(ty);*/
            //이거안해주면 리스너안먹힘
            adapter.setOnClickListener(this);


/*
                    DeleteData task = new DeleteData();
                    task.execute("http://" + IP_ADDRESS + "/delete_friend.php", user_id, adapter.mData.get(position).getId());

                    adapter.mData.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                    adapter.notifyDataSetChanged();*/





        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
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

    @Override
    protected void onRestart() {
        super.onRestart();

        uList = new ArrayList<>();
        uList2 = new ArrayList<>();
        fList = new ArrayList<>();


        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php");

        GetData2 task2 = new GetData2();
        task2.execute( "http://" + IP_ADDRESS + "/getjson_friend.php");



        adapter.notifyDataSetChanged();


        Log.i("이거되나","이거됨?");



    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }
    public void dd(View v) {
        Toast.makeText(getApplicationContext(), "dd", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == 1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
