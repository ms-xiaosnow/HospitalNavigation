package com.hospitalnavigation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hospitalnavigation.InfoDatas;
import com.hospitalnavigation.R;

import java.util.List;

public class InfoAdapter extends BaseAdapter {
    private List<InfoDatas> mInfoDatas;
    private LayoutInflater mInflater;

    public InfoAdapter(Context context, List<InfoDatas> datas) {
        mInfoDatas = datas;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mInfoDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfoDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {

        TextView tv_service, tv_address, tv_type;

    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.fg_info_item,

                    parent, false);

            holder = new ViewHolder();
            holder.tv_service = (TextView) convertView.findViewById(R.id.tv_service);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tv_service.setText(mInfoDatas.get(position).getService());
        holder.tv_address.setText(mInfoDatas.get(position).getAddress());
        holder.tv_type.setText(mInfoDatas.get(position).getType());

        return convertView;

    }
}
