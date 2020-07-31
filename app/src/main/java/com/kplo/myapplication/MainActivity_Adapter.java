package com.kplo.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

//잠시보류


public class MainActivity_Adapter extends RecyclerView.Adapter<MainActivity_Adapter.ViewHolder> implements Filterable {

    protected ArrayList<Signup_Item> mData = null;
    private ArrayList<Signup_Item> mDataListAll = null;



    //버튼정의
    public interface MyRecyclearViewClickListener {
        void onItemClicked(int position,MainActivity_Adapter.ViewHolder holder);
        void onButton_1Clicked(int position,View v,MainActivity_Adapter.ViewHolder holder);

    }

    private MyRecyclearViewClickListener mListener;

    //외부에서 지정할수있도록?
    public void setOnClickListener(MyRecyclearViewClickListener listener) {
        mListener = listener;
    }



    // 생성자에서 데이터 리스트 객체를 전달받음.
    MainActivity_Adapter(ArrayList<Signup_Item> list) {
        mData = list;
        mDataListAll = new ArrayList<>(list);
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    // 아이템뷰 지정
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView how_r;
        ImageView button_1;
        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong referetext1nce)
            how_r = itemView.findViewById(R.id.how_r);
            button_1 = itemView.findViewById(R.id.button_1);



        }


    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MainActivity_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_main_recyclerview_item, parent, false);
        MainActivity_Adapter.ViewHolder vh = new MainActivity_Adapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    //실질적인 클릭이벤트도 정의됨
    @Override
    public void onBindViewHolder(final MainActivity_Adapter.ViewHolder holder, final int position) {

        final Signup_Item item = mData.get(position);

        holder.how_r.setText(item.getId());



/*
        Glide.with(holder.itemView.getContext())
                .load(item.getIMG_URL())
                .centerCrop()
                .into(holder.food_img);
        holder.food_name.setText(item.getRECIPE_NM_KO());*/




        if (mListener != null) {
            final int pos = position;

            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos,holder);


                }
            });


            holder.button_1.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mListener.onButton_1Clicked(pos,v,holder);


                }
            });





        }






    }




    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 데이터 필터 검색 Filterable implement ---------------------------------
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Signup_Item> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Signup_Item item : mDataListAll) {
                    //TODO filter 대상 setting
                    if (item.getId().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };



}

