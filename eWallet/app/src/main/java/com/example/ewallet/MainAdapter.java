package com.example.ewallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

    private ArrayList<Items> ItemsList;
    private Context context;
    private LayoutInflater inflater;
    private TextView textItemCategory,textItemPrice;

    public MainAdapter(Context c, ArrayList<Items> ItemsList){
        context = c;
        this.ItemsList = ItemsList;
    }

    @Override
    public int getCount() {
        return ItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        Items currentItem = ItemsList.get(position);

        textItemCategory = convertView.findViewById(R.id.ItemCategory);
        textItemPrice = convertView.findViewById(R.id.ItemPrice);

        textItemCategory.setText(currentItem.getCategory());
        textItemPrice.setText(""+currentItem.getPrice());
        return convertView;
    }
}
