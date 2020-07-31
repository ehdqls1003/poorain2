package com.kplo.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class AddfriendActivity_Adapter extends RecyclerView.Adapter<AddfriendActivity_Adapter.ViewHolder> implements Filterable {

    private ArrayList<Signup_Item> mData = null;
    private ArrayList<Signup_Item> mData2 = null;
    private ArrayList<Signup_Item> mDataListAll = null;
    boolean yes = false;

    //버튼정의
    public interface MyRecyclearViewClickListener {
        void onItemClicked(int position,AddfriendActivity_Adapter.ViewHolder holder);
        void followClicked(int position, AddfriendActivity_Adapter.ViewHolder holder);
        void follow_cClicked(int position,AddfriendActivity_Adapter.ViewHolder holder);

    }

    private MyRecyclearViewClickListener mListener;





    //외부에서 지정할수있도록?
    public void setOnClickListener(MyRecyclearViewClickListener listener) {
        mListener = listener;
    }



    // 생성자에서 데이터 리스트 객체를 전달받음.
    AddfriendActivity_Adapter(ArrayList<Signup_Item> list,ArrayList<Signup_Item> list2) {
        mData = list;
        mDataListAll = new ArrayList<>(list);
        mData2 = list2;

    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    // 아이템뷰 지정
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_id;
        AppCompatButton follow,follow_c;

        ViewHolder(final View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong referetext1nce)
            user_id = itemView.findViewById(R.id.how_r);
            follow = itemView.findViewById(R.id.follow);
            follow_c = itemView.findViewById(R.id.follow_c);



        }
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public AddfriendActivity_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_addfriend_recyclerview_item, parent, false);
        AddfriendActivity_Adapter.ViewHolder vh = new AddfriendActivity_Adapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    //실질적인 클릭이벤트도 정의됨
    @Override
    public void onBindViewHolder(final AddfriendActivity_Adapter.ViewHolder holder, final int position) {

        Signup_Item item = mData.get(position);
/*

        Glide.with(holder.itemView.getContext())
                .load(item.getIMG_URL())
                .centerCrop()
                .into(holder.food_img);
        holder.food_name.setText(item.getRECIPE_NM_KO());
*/
        holder.user_id.setText(item.getId());
        holder.follow.setVisibility(View.VISIBLE);
        holder.follow_c.setVisibility(View.INVISIBLE);

        Log.i("소름","소름" + mData2.size());
        for (int i = 0; i < mData2.size() ; i++) {

            String same = mData2.get(i).getId();

            if (holder.user_id.getText().toString().equals(same)) {
                holder.follow.setVisibility(View.INVISIBLE);
                holder.follow_c.setVisibility(View.VISIBLE);
            }

        }


        if (mListener != null) {
            final int pos = position;

            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos,holder);

                }
            });

            holder.follow.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mListener.followClicked(pos,holder);
                    Log.i("소름","소름2" + holder.user_id.getText().toString());

                    for(int i = 0; i < mData.size(); i++ ) {

                        if (mData.get(i).getId().equals(holder.user_id.getText().toString())) {
                            Log.i("소름","소름" + "드감");
                            Signup_Item item = new Signup_Item();

                            item.setId(mData.get(i).getId());
                            item.setId(mData.get(i).getPw());

                            mData2.add(item);


                        }
                    }


                    Log.i("소름","소름3" + mData2.size());

                    notifyItemChanged(pos);



                }
            });

            holder.follow_c.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mListener.follow_cClicked(pos,holder);

                    for(int i = 0; i < mData2.size(); i++ ) {
                        if(mData2.get(i).getId().equals(holder.user_id.getText().toString())){
                            mData2.remove(i);
                        }
                    }
                    notifyItemChanged(pos);


                }
            });





        }

    }


    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        Log.i("바뀌라","ㄴㅇㄹ"+mData.size());
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

