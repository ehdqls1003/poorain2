package com.kplo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipesearchActivity extends AppCompatActivity implements TextWatcher,RecipesearchActivity_Adapter.MyRecyclearViewClickListener {

    TextView home,place,partylist,myparty;
    EditText search_edit;
    RecyclerView recyclerView;
    String TAG = "RecipesearchActivity";

    AppCompatButton bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12,bt13,bt14,bt15,bt16,bt17,bt18,bt19,bt20,bt21,btA;

    ArrayList<Recipejson> rList = new ArrayList<>();
    ArrayList<Recipejson> rList2 = new ArrayList<>();
    RecipesearchActivity_Adapter adapter;
    ArrayList<String> rrList = new ArrayList<>();

    boolean sad = false;

    String irdnt_nms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipesearch);

        //데이터들고옴
        ReadFriendsDataU();

        home = findViewById(R.id.home);
        place = findViewById(R.id.place);
        partylist = findViewById(R.id.partylist);
        myparty = findViewById(R.id.myparty);
        search_edit = findViewById(R.id.search_edit);

        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);
        bt6 = findViewById(R.id.bt6);
        bt7 = findViewById(R.id.bt7);
        bt8 = findViewById(R.id.bt8);
        bt9 = findViewById(R.id.bt9);
        bt10 = findViewById(R.id.bt10);
        bt11 = findViewById(R.id.bt11);
        bt12 = findViewById(R.id.bt12);
        bt13 = findViewById(R.id.bt13);
        bt14 = findViewById(R.id.bt14);
        bt15 = findViewById(R.id.bt15);
        bt16 = findViewById(R.id.bt16);
        bt17 = findViewById(R.id.bt17);
        bt18 = findViewById(R.id.bt18);
        bt19 = findViewById(R.id.bt19);
        bt20 = findViewById(R.id.bt20);
        bt21 = findViewById(R.id.bt21);
        btA = findViewById(R.id.btA);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, MainActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipehelperActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });
        partylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        myparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, MyinfoActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });



        /*버튼시작*/

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","밥");
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","떡/한과");
                startActivity(intent);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","만두/면류");
                startActivity(intent);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","국");
                startActivity(intent);
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","나물/생채/샐러드");
                startActivity(intent);
            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","구이");
                startActivity(intent);
            }
        });
        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","볶음");
                startActivity(intent);
            }
        });
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","밑반찬/김치");
                startActivity(intent);
            }
        });
        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","조림");
                startActivity(intent);
            }
        });
        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","찜");
                startActivity(intent);
            }
        });
        bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","튀김/커틀릿");
                startActivity(intent);
            }
        });
        bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","찌개/전골/스튜");
                startActivity(intent);
            }
        });
        bt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","도시락/간식");
                startActivity(intent);
            }
        });
        bt14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","부침");
                startActivity(intent);
            }
        });
        bt15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","양식");
                startActivity(intent);
            }
        });
        bt16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","샌드위치/햄버거");
                startActivity(intent);
            }
        });
        bt17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","빵/과자");
                startActivity(intent);
            }
        });
        bt18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","양념장");
                startActivity(intent);
            }
        });
        bt19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","음료");
                startActivity(intent);
            }
        });
        bt20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","피자");
                startActivity(intent);
            }
        });
        bt21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","그라탕/리조또");
                startActivity(intent);
            }
        });

        btA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesearchActivity.this, RecipesearchActivity_ty.class);
                intent.putExtra("종류","전체목록");
                startActivity(intent);
            }
        });

        /*버튼 끝*/




        search_edit.addTextChangedListener(this);

        recyclerView = findViewById(R.id.recycler) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        recyclerView.setVisibility(View.INVISIBLE);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new RecipesearchActivity_Adapter(rList) ;
        recyclerView.setAdapter(adapter) ;
        //이거안해주면 리스너안먹힘
        adapter.setOnClickListener(this);

        if(rrList.size() == 0){
            rrList.add(rList.get(0).getTY_NM());
        }
        for(int i = 1; i < rList.size(); i++) {

            String TYNM = rList.get(i).getTY_NM();

            for(int j = 0 ; j < rrList.size(); j++) {
                if(rrList.get(j).equals(TYNM)){
                    sad = false;
                    break;
                }
            }
            if (sad){
                rrList.add(TYNM);
            }
            sad = true;
        }

        for(int i = 0; i < rrList.size(); i++) {

            if(i == 0) {
                irdnt_nms = "재료 : " + rrList.get(i);
            }else{
                irdnt_nms =  irdnt_nms+","+rrList.get(i);
            }

        }/*
        test.setText(irdnt_nms);*/
        Log.i(TAG, "종류 :"+irdnt_nms);

        Log.i(TAG, "종류 :"+rrList.size());

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {



            recyclerView.setVisibility(View.VISIBLE);
            adapter.getFilter().filter(s);

        /*버튼안보이게함*/
        bt1.setVisibility(View.INVISIBLE);
        bt2.setVisibility(View.INVISIBLE);
        bt3.setVisibility(View.INVISIBLE);
        bt4.setVisibility(View.INVISIBLE);
        bt5.setVisibility(View.INVISIBLE);
        bt6.setVisibility(View.INVISIBLE);
        bt7.setVisibility(View.INVISIBLE);
        bt8.setVisibility(View.INVISIBLE);
        bt9.setVisibility(View.INVISIBLE);
        bt10.setVisibility(View.INVISIBLE);
        bt11.setVisibility(View.INVISIBLE);
        bt12.setVisibility(View.INVISIBLE);
        bt13.setVisibility(View.INVISIBLE);
        bt14.setVisibility(View.INVISIBLE);
        bt15.setVisibility(View.INVISIBLE);
        bt16.setVisibility(View.INVISIBLE);
        bt17.setVisibility(View.INVISIBLE);
        bt18.setVisibility(View.INVISIBLE);
        bt19.setVisibility(View.INVISIBLE);
        bt20.setVisibility(View.INVISIBLE);
        bt21.setVisibility(View.INVISIBLE);
        btA.setVisibility(View.INVISIBLE);

            Log.i(TAG, "onTextChanged: 들어옴"+s.length());
            if(s.length() == 0){
                recyclerView.setVisibility(View.INVISIBLE);
                /*버튼보이게함*/
                bt1.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.VISIBLE);
                bt3.setVisibility(View.VISIBLE);
                bt4.setVisibility(View.VISIBLE);
                bt5.setVisibility(View.VISIBLE);
                bt6.setVisibility(View.VISIBLE);
                bt7.setVisibility(View.VISIBLE);
                bt8.setVisibility(View.VISIBLE);
                bt9.setVisibility(View.VISIBLE);
                bt10.setVisibility(View.VISIBLE);
                bt11.setVisibility(View.VISIBLE);
                bt12.setVisibility(View.VISIBLE);
                bt13.setVisibility(View.VISIBLE);
                bt14.setVisibility(View.VISIBLE);
                bt15.setVisibility(View.VISIBLE);
                bt16.setVisibility(View.VISIBLE);
                bt17.setVisibility(View.VISIBLE);
                bt18.setVisibility(View.VISIBLE);
                bt19.setVisibility(View.VISIBLE);
                bt20.setVisibility(View.VISIBLE);
                bt21.setVisibility(View.VISIBLE);
                btA.setVisibility(View.VISIBLE);
            }



    }

    @Override
    public void afterTextChanged(Editable s) {

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

    @Override
    public void onItemClicked(int position,String win) {/*
        Toast.makeText(this,"아이템클릭"+win,Toast.LENGTH_SHORT).show();
*/

        Intent intent = new Intent(RecipesearchActivity.this, ShowrecipeActivity.class);
        intent.putExtra("win",win);
        startActivity(intent);


    }


}
