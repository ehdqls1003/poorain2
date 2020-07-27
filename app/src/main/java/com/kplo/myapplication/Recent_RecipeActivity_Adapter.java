package com.kplo.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class Recent_RecipeActivity_Adapter extends RecyclerView.Adapter<Recent_RecipeActivity_Adapter.ViewHolder> implements Filterable {

    private ArrayList<Recipejson> mData = null;
    private ArrayList<Recipejson> mDataListAll = null;

    //버튼정의
    public interface MyRecyclearViewClickListener {
        void onItemClicked(int position, String win);

    }

    private MyRecyclearViewClickListener mListener;





    //외부에서 지정할수있도록?
    public void setOnClickListener(MyRecyclearViewClickListener listener) {
        mListener = listener;
    }



    // 생성자에서 데이터 리스트 객체를 전달받음.
    Recent_RecipeActivity_Adapter(ArrayList<Recipejson> list) {
        mData = list;
        mDataListAll = new ArrayList<>(list);
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    // 아이템뷰 지정
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView food_name;
        ImageView food_img;

        ViewHolder(final View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong referetext1nce)
            food_img = itemView.findViewById(R.id.food_img);
            food_name = itemView.findViewById(R.id.food_name);




        }
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public Recent_RecipeActivity_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_recipesearch_recyclerview_item, parent, false);
        Recent_RecipeActivity_Adapter.ViewHolder vh = new Recent_RecipeActivity_Adapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    //실질적인 클릭이벤트도 정의됨
    @Override
    public void onBindViewHolder(Recent_RecipeActivity_Adapter.ViewHolder holder, final int position) {

        final Recipejson item = mData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getIMG_URL())
                .centerCrop()
                .into(holder.food_img);
        holder.food_name.setText(item.getRECIPE_NM_KO());


        if (mListener != null) {
            final int pos = position;

            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos,mData.get(pos).getRECIPE_ID());
                    Log.i("뭐얌", "mData.get(pos).getRECIPE_ID(): " +mData.get(pos).getRECIPE_ID());


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
            ArrayList<Recipejson> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Recipejson item : mDataListAll) {
                    //TODO filter 대상 setting
                    if (item.getRECIPE_NM_KO().toLowerCase().contains(filterPattern)) {
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

