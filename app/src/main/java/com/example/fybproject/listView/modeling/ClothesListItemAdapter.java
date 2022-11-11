package com.example.fybproject.listView.modeling;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.listView.OnItemClick;
import com.example.fybproject.main.fragment.MainModelFragment;
import com.example.fybproject.main.fragment.MainMyclosetFragment;
import com.example.fybproject.mediator.VideoMediator;

import java.util.ArrayList;

public class ClothesListItemAdapter extends RecyclerView.Adapter<ClothesListItemAdapter.ItemViewHolder> {
    View view;

    int position;

    private ArrayList<ClothesListItem> listData;
    private Context context;

    private OnItemClick mCallback;

    public ClothesListItemAdapter(OnItemClick listener) {
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectlist_item, parent, false);

        context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;

        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        if (listData != null) {
            return listData.size();
        }
        return 0;
    }

    public void setList(ArrayList<ClothesListItem> list) {
        this.listData = list;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.clothesImg);
        }

        void onBind(ClothesListItem data) {
            int clothes = data.getClothes();
            String imgName = setImg(clothes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.setBackgroundResource(R.drawable.clothes_item_select);
                    mCallback.onClick(imgName);
                }
            });
        }

        public String setImg(int c) {
            String imageUrl, imgName, png = ".png";

            switch (c) {
                case 1 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/TT4";
                    Log.d(TAG, "경로 확인 - " + imageUrl+png);
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 2 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/BH1";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 3 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/BH2";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 4 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/BJ1";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 5 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/BJ3";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 6 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/TS2";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 7 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WTC1";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 8 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WTC2";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 9 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WTC3";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 10 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WBB1";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 11 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WBB2";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 12 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WBH1";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 13 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WBS1";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 14 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WBS2";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                case 15 :
                    imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/cloth/WBS3";
                    Glide.with(context).load(imageUrl+png).into(img);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + c);
            }

            imgName = imageUrl.substring(57);
            return imgName;
        } // 이미지 set
    }
}
