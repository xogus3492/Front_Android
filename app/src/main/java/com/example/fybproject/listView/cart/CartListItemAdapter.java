package com.example.fybproject.listView.cart;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.shopDTO.AnalyzeDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.listView.home.RecommendShopListItem;
import com.example.fybproject.mediator.MainUserDataMediator;
import com.example.fybproject.service.ShopService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListItemAdapter extends RecyclerView.Adapter<CartListItemAdapter.ItemViewHolder> {
    private ArrayList<CartListItem> listData;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlist_item, parent, false);
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

    public void setList(ArrayList<CartListItem> list){
        this.listData = list;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        EditText name, note, price;
        ImageView img;
        LinearLayout layout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cartItemName);
            note = itemView.findViewById(R.id.cartItemNote);
            price = itemView.findViewById(R.id.cartItemPrice);
            img = itemView.findViewById(R.id.cartItemImage);
            layout = itemView.findViewById(R.id.cartItem);
        }

        void onBind(CartListItem data) {
            name.setText(data.getPname());
            note.setText(data.getNotes());
            price.setText(String.valueOf(data.getPrice()));

            name.setFocusable(false);
            note.setFocusable(false);
            price.setFocusable(false);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
