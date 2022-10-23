package com.example.fybproject.listView.cart;

import android.content.ContentValues;
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

import java.util.ArrayList;

public class CartListItemAdapter extends RecyclerView.Adapter<CartListItemAdapter.ItemViewHolder> {
    View view;

    private ArrayList<CartListItem> listData;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlist_item, parent, false);

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
        ImageView img, selectItemCancel;
        LinearLayout layout, selectAction;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cartItemName);
            note = itemView.findViewById(R.id.cartItemNote);
            price = itemView.findViewById(R.id.cartItemPrice);
            img = itemView.findViewById(R.id.cartItemImage);
            layout = itemView.findViewById(R.id.cartItem);

            selectAction = view.findViewById(R.id.selectAction);
            selectItemCancel = view.findViewById(R.id.selectItemCancel);
        }

        void onBind(CartListItem data) {
            name.setText(data.getPname());
            note.setText(data.getNotes());
            price.setText(String.valueOf(data.getPrice()));

            name.setFocusableInTouchMode(false);
            note.setFocusableInTouchMode(false);
            price.setFocusableInTouchMode(false);

            layout.setOnClickListener(listener);
            name.setOnClickListener(listener);
            note.setOnClickListener(listener);
            price.setOnClickListener(listener);

            selectItemCancel.setOnClickListener(selectedListener);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(ContentValues.TAG, "아이템 클릭됨");

                name.setFocusableInTouchMode(true);
                note.setFocusableInTouchMode(true);
                price.setFocusableInTouchMode(true);

                selectItemCancel.setVisibility(View.VISIBLE);
                selectAction.setVisibility(View.VISIBLE);
            }
        };

        View.OnClickListener selectedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(ContentValues.TAG, "선택된 아이템 버튼이 클릭됨");

                switch (view.getId()) {
                    case R.id.selectItemCancel:
                        layout.setBackground(view.getResources().getDrawable(R.drawable.cart_list_bg));

                        name.setFocusableInTouchMode(false);
                        note.setFocusableInTouchMode(false);
                        price.setFocusableInTouchMode(false);

                        selectItemCancel.setVisibility(View.GONE);
                        selectAction.setVisibility(View.GONE);
                        break;
                    /*case R.id.changeCartItem:
                        layout.setBackground(view.getResources().getDrawable(R.drawable.cart_list_bg));

                        name.setFocusableInTouchMode(false);
                        note.setFocusableInTouchMode(false);
                        price.setFocusableInTouchMode(false);

                        selectItemCancel.setVisibility(View.GONE);
                        selectAction.setVisibility(View.GONE);
                        break;
                    case R.id.deleteCartItem:
                        layout.setBackground(view.getResources().getDrawable(R.drawable.cart_list_bg));

                        name.setFocusableInTouchMode(false);
                        note.setFocusableInTouchMode(false);
                        price.setFocusableInTouchMode(false);

                        selectItemCancel.setVisibility(View.GONE);
                        selectAction.setVisibility(View.GONE);
                        break;
                    case R.id.goCartItemUrl:
                        layout.setBackground(view.getResources().getDrawable(R.drawable.cart_list_bg));

                        name.setFocusableInTouchMode(false);
                        note.setFocusableInTouchMode(false);
                        price.setFocusableInTouchMode(false);

                        selectItemCancel.setVisibility(View.GONE);
                        selectAction.setVisibility(View.GONE);
                        break;*/
                }
            }
        };
    }
}
