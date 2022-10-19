package com.example.fybproject.listView.search;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchlist_item, parent, false);
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
        TextView shop;
        ImageView img;
        LinearLayout searchItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            shop = itemView.findViewById(R.id.searchShopName);
            img = itemView.findViewById(R.id.searchShopImg);
            searchItem = itemView.findViewById(R.id.searchItem);
        }

        void onBind(SearchListItem data) {
            Log.d(TAG, "data.getShop() : " + data.getShop());
            shop.setText(data.getShop());

            searchItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://" + data.getSurl();
                    Context context = view.getContext();

                    Log.d(TAG, "쇼핑몰 주소 : " + url);
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intentUrl);
                }
            });
        }
    }
}