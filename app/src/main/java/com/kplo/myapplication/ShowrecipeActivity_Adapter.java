package com.kplo.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ShowrecipeActivity_Adapter extends RecyclerView.Adapter<ShowrecipeActivity_Adapter.ViewHolder> {

    private ArrayList<Recipejson2> mData = null;

    //버튼정의
    public interface MyRecyclearViewClickListener {
        void onItemClicked(int position);

        void rewright(int position);
    }

    private MyRecyclearViewClickListener mListener;

    //외부에서 지정할수있도록?
    public void setOnClickListener(MyRecyclearViewClickListener listener) {
        mListener = listener;
    }


    // 생성자에서 데이터 리스트 객체를 전달받음.
    ShowrecipeActivity_Adapter(ArrayList<Recipejson2> list) {
        mData = list;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    // 아이템뷰 지정
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView number_r, how_r;


        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong referetext1nce)
            number_r = itemView.findViewById(R.id.number_r);
            how_r = itemView.findViewById(R.id.how_r);


        }
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ShowrecipeActivity_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_showrecipe_recyclerview_item, parent, false);
        ShowrecipeActivity_Adapter.ViewHolder vh = new ShowrecipeActivity_Adapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    //실질적인 클릭이벤트도 정의됨
    @Override
    public void onBindViewHolder(ShowrecipeActivity_Adapter.ViewHolder holder, final int position) {

        final Recipejson2 item = mData.get(position);


        holder.how_r.setText(item.getCOOKING_NO());
        holder.number_r.setText(item.getCOOKING_DC());




        if (mListener != null) {
            final int pos = position;

        }

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

}

