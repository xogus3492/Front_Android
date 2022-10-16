package com.example.fybproject.listView.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class SearchListItemAdapter extends BaseAdapter {
    ArrayList<SearchListItem> items = new ArrayList<SearchListItem>();
    Context context;

    MainActivity mainActivity;

    @Override
    public int getCount() {
        return items.size();
    } // 리스트 크기 반환

    @Override
    public Object getItem(int i) {
        return items.get(i);
    } // 해당 위치에 있는 아이템 반환

    @Override
    public long getItemId(int i) {
        return i;
    } // 해당 하이템의 위치 반환

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        SearchListItem listItem = items.get(i);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.searchlist_item, viewGroup, false);
        }

        LinearLayout secondItem = view.findViewById(R.id.secondItem);

        TextView name1 = view.findViewById(R.id.shopName1);
        TextView name2 = view.findViewById(R.id.shopName2);

        ImageView img1 = view.findViewById(R.id.firstImg);
        ImageView img2 = view.findViewById(R.id.secondImg);

        if(listItem.getName2() == null && listItem.getUrl2() == null) {
            secondItem.setVisibility(View.INVISIBLE);
        } // 두번째 아이템 없을 때

        name1.setText(listItem.getName1());
        name2.setText(listItem.getName2());

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity = new MainActivity();
                mainActivity.visitUrl(listItem.getUrl1());
            }
        }); // 첫번째 이미지

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity = new MainActivity();
                mainActivity.visitUrl(listItem.getUrl2());
            }
        }); // 두번째 이미지

        return view;
    }

    public void addItem(SearchListItem listItem) {
        items.add(listItem);
    }

    public void clearItem() {
        items.clear();
    }
}
