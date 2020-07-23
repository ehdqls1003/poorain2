package com.kplo.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;

public class MakepartyActivity extends AppCompatActivity {

    AppCompatButton button,button2;
    ImageView imageView;
    EditText product_name,product_price,product_text;

    int PICK_IMAGE = 2948;
    Uri aUri;
    String bUri;
    ArrayList<Makeparty_Item> mList = new ArrayList<Makeparty_Item>();
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeparty);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_text = findViewById(R.id.product_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override //이미지 불러오기기(갤러리 접근)
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_IMAGE);
                //PICK_IMAGE에는 본인이 원하는 상수넣으면된다.
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override //이미지 불러오기기(갤러리 접근)
            public void onClick(View v) {


                String name = product_name.getText().toString();
                String price = product_price.getText().toString();
                String text = product_text.getText().toString();



                addItem(bUri,name,price,text,1);


                Log.i("glide값", "in = "+aUri);
                Uri d = Uri.parse(bUri);
                Log.i("glide값", "img = "+d);

                Log.i("glide값","glide값:"+bUri);

              /*  Intent intent = new Intent(MakepartyActivity.this, product_list.class);


                startActivity(intent);
                finish();*/
/*
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert_productadd.php", icon,name,price,text,count);*/

            }
        });




    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    //imageView.setImageBitmap(img);
                    //이걸로가져와서 하면될듯
                    Log.i("glide값", "data.getData() = "+data.getData());

                    aUri = data.getData();
                    bUri = aUri.toString();
                    Log.i("glide값", "in = "+aUri);

                    Log.i("나오냐?", ""+aUri);
                    //imageView.setImageURI(aUri);

                    Glide.with(this)
                            .load(aUri)
                            .into(imageView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public void addItem(String icon, String product_name, String product_price, String product_text, int product_count) {


        Makeparty_Item item = new Makeparty_Item();

        item.setImg(icon);
        item.setName(product_name);
        item.setPrice(product_price);
        item.setText(product_text);
        item.setCount(product_count);

        mList.add(item);

    }






}