package com.zhongruan.android.zkfacedemo.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhongruan.android.zkfacedemo.R;
import com.zhongruan.android.zkfacedemo.db.entity.Bk_ks;
import com.zhongruan.android.zkfacedemo.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectKsAdapter extends BaseAdapter {
    private List<Bk_ks> ksList;
    private Context mContext;


    static class ViewHolder {
        TextView tvName, tvKsno;
        ImageView tvSeat;
        LinearLayout ll_kslist;
    }

    public SelectKsAdapter(Context mContext, List<Bk_ks> ksList) {
        this.mContext = mContext;
        this.ksList = new ArrayList();
        this.ksList.addAll(ksList);
    }

    public int getCount() {
        return this.ksList.size();
    }

    public Object getItem(int position) {
        return this.ksList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.pad_adapter_kslist2, null);
            holder = new ViewHolder();
            holder.tvSeat = convertView.findViewById(R.id.tvSeat);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvKsno = convertView.findViewById(R.id.tvKsno);
            holder.ll_kslist = convertView.findViewById(R.id.ll_kslist);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.ksList != null && this.ksList.size() > 0) {
            Picasso.with(mContext).load(new File(FileUtils.getAppSavePath() + "/" + this.ksList.get(position).getKs_xp())).into(holder.tvSeat);
            holder.tvKsno.setText(this.ksList.get(position).getKs_xm());
            holder.ll_kslist.setBackgroundColor(ksList.get(position).getIsRZ().equals("0") ? ContextCompat.getColor(this.mContext, R.color.auth_kslist_hastauth) : ContextCompat.getColor(this.mContext, R.color.auth_kslist_hasauth));
            holder.tvName.setText(ksList.get(position).getKs_zwh());
        }
        return convertView;
    }
}