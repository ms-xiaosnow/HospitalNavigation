package com.hospitalnavigation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hospitalnavigation.Datas;
import com.hospitalnavigation.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<Datas> mDatas;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<Datas> Datas) {
        mDatas = Datas;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {

        TextView text;

        ImageView icon;

    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item,

                    parent, false);

            holder = new ViewHolder();

            holder.text = (TextView) convertView.findViewById(R.id.ic_tv);

            holder.icon = (ImageView) convertView.findViewById(R.id.ic_img);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.text.setText(mDatas.get(position).getName());

        holder.icon.setImageResource(mDatas.get(position).getIcon());

        return convertView;

    }
}
