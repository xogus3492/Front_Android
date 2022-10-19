package com.example.fybproject.listView.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;

import java.util.ArrayList;

public class SearchListItemAdapter extends RecyclerView.Adapter<SearchListItemAdapter.ItemViewHolder> {
    private ArrayList<SearchListItem> listData;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommenlist_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        if (listData != null) {
            return listData.size();
        }
        return 0;
    }

    public void setList(ArrayList<SearchListItem> list){
        this.listData = list;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView shop1, shop2;
        LinearLayout layout, firstItem, secondItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            shop1 = itemView.findViewById(R.id.shopName1);
            shop2 = itemView.findViewById(R.id.shopName2);
            layout = itemView.findViewById(R.id.recommendItem);
            firstItem = itemView.findViewById(R.id.firstItem);
            secondItem = itemView.findViewById(R.id.secondItem);
        }

        void onBind(SearchListItem data) {
            shop1.setText(data.getName1());
            shop2.setText(data.getName2());

            if(shop2 == null) secondItem.setVisibility(View.GONE);

            firstItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            secondItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}