package com.example.birthday_manager;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;


public class MyListViewAdapter extends BaseAdapter implements ListAdapter {
    private List<person> data;
    private int layout;
    private Context context;

    private Button item_face = null;
    private TextView item_name = null;
    private TextView item_date = null;
    private TextView item_age = null;
    private TextView item_restdays = null;
    private LinearLayout item = null;

    public MyListViewAdapter(List<person> data, int layout, Context context) {
        this.data = data;
        this.layout = layout;
        this.context = context;
        //this.itemListener=itemListener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //装载view
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_style, null);
        }
        //获取控件
        item_face = (Button) view.findViewById(R.id.item_face);
        item_name = (TextView) view.findViewById(R.id.item_name);
        item_date = (TextView) view.findViewById(R.id.item_date);
        item_age = (TextView) view.findViewById(R.id.item_age);
        item_restdays = (TextView) view.findViewById(R.id.item_restdays);
        item = (LinearLayout) view.findViewById(R.id.item);

        //对控件赋值
        person person_data = (person) getItem(position);
        if (person_data != null) {
            item_face.setBackground(new BitmapDrawable(person_data.getFace()));
            item_name.setText(person_data.getName());
            item_date.setText(person_data.getMonth() + "月" + person_data.getDay() + "日");
            item_age.setText(person_data.getAge() + "岁");
            item_restdays.setText(person_data.getRestdays() + "天");
        }
        return view;
    }
}
