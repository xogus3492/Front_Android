package com.example.fybproject.listView.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;

import java.util.ArrayList;

public class RecommendShopListItemAdapter extends BaseAdapter {
    ArrayList<RecommendShopListItem> items = new ArrayList<RecommendShopListItem>();
    Context context;

    MainActivity mainActivity;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        RecommendShopListItem listItem = items.get(i);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.recommenlist_item, viewGroup, false);
        }

        TextView rank = view.findViewById(R.id.rank);
        ImageView img = view.findViewById(R.id.recommenImg);
        TextView krName = view.findViewById(R.id.recommendKrName);
        LinearLayout recommendItem = view.findViewById(R.id.recommendItem);
        //TextView EngName = view.findViewById(R.id.recommendEngName);

        rank.setText(i + 1);
        krName.setText(listItem.getName());

        recommendItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity = new MainActivity();
                mainActivity.visitUrl(listItem.getUrl());
            }
        }); // 아이템 클릭시 경로 이동


        return view;
    }

    public void addItem(RecommendShopListItem listItem) {
        items.add(listItem);
    }

    public void clearItem() {
        items.clear();
    }
}
