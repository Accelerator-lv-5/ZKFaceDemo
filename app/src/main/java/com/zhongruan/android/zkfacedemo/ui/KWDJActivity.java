package com.zhongruan.android.zkfacedemo.ui;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongruan.android.zkfacedemo.R;
import com.zhongruan.android.zkfacedemo.adapter.SelectKsAdapter;
import com.zhongruan.android.zkfacedemo.base.BaseActivity;
import com.zhongruan.android.zkfacedemo.db.DbServices;
import com.zhongruan.android.zkfacedemo.db.entity.Bk_ks;
import com.zhongruan.android.zkfacedemo.db.entity.Sfrz_rzfs;
import com.zhongruan.android.zkfacedemo.dialog.KsxxDialog;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class KWDJActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlRzjlBack;
    private GridView mGvKs;
    private TextView mTvInputTip;
    private SelectKsAdapter setAdapter;
    private List<Bk_ks> bk_ks;
    private String[] ITEMS;
    private List<Sfrz_rzfs> rzfsList;
    private String kcmcStr, ccmcStr;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kwdj);
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        kcmcStr = intent.getStringExtra("kcmc");
        ccmcStr = intent.getStringExtra("ccmc");
        mLlRzjlBack = findViewById(R.id.ll_kwdj_back);
        mGvKs = findViewById(R.id.gvKs);
        mTvInputTip = findViewById(R.id.tv_inputTip);
        bk_ks = DbServices.getInstance(getBaseContext()).queryBKKSList(kcmcStr, ccmcStr);
        rzfsList = DbServices.getInstance(getBaseContext()).loadAllrzfs();
        mTvInputTip.setText(kcmcStr + " " + ccmcStr);
        ITEMS = new String[rzfsList.size() - 7];
        for (int i = 7; i < rzfsList.size(); i++) {
            ITEMS[i - 7] = rzfsList.get(i).getRzfs_name();
        }
    }

    @Override
    public void initListeners() {
        mLlRzjlBack.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setAdapter = new SelectKsAdapter(this, bk_ks);
        mGvKs.setAdapter(setAdapter);
        mGvKs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new KsxxDialog(KWDJActivity.this, R.style.dialog, bk_ks.get(i), ITEMS, new KsxxDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_kwdj_back:
                finish();
                break;
        }
    }
}
