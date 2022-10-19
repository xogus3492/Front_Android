package com.example.fybproject.listView.home;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;

import java.util.ArrayList;

public class RecommendShopListItemAdapter extends RecyclerView.Adapter<RecommendShopListItemAdapter.ItemViewHolder> {
    private ArrayList<RecommendShopListItem> listData;

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

    public void setList(ArrayList<RecommendShopListItem> list){
        this.listData = list;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView rank, shop;
        ImageView img;
        LinearLayout layout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.rank);
            shop = itemView.findViewById(R.id.recommendKrName);
            img = itemView.findViewById(R.id.recommenImg);
            layout = itemView.findViewById(R.id.recommendItem);
        }

        void onBind(RecommendShopListItem data) {
            rank.setText(data.getRank());
            shop.setText(data.getShop());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://" + data.getUrl();
                    Context context = view.getContext();

                    Log.d(TAG, "쇼핑몰 주소 : " + url);
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intentUrl);
                }
            });
        }
    }
}
