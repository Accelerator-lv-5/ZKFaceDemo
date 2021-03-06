package com.zhongruan.android.zkfacedemo.ui;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongruan.android.zkfacedemo.R;
import com.zhongruan.android.zkfacedemo.adapter.SelectCcAdapter;
import com.zhongruan.android.zkfacedemo.adapter.SelectKcAdapter;
import com.zhongruan.android.zkfacedemo.base.BaseActivity;
import com.zhongruan.android.zkfacedemo.db.Bk_ksDao;
import com.zhongruan.android.zkfacedemo.db.Bk_ks_tempDao;
import com.zhongruan.android.zkfacedemo.db.Bk_ksxpDao;
import com.zhongruan.android.zkfacedemo.db.DbServices;
import com.zhongruan.android.zkfacedemo.db.Ks_ccDao;
import com.zhongruan.android.zkfacedemo.db.Ks_kcDao;
import com.zhongruan.android.zkfacedemo.db.entity.Ks_cc;
import com.zhongruan.android.zkfacedemo.db.entity.Ks_kc;
import com.zhongruan.android.zkfacedemo.dialog.HintDialog;
import com.zhongruan.android.zkfacedemo.dialog.HintDialog2;
import com.zhongruan.android.zkfacedemo.utils.ABLSynCallback;
import com.zhongruan.android.zkfacedemo.utils.DateUtil;
import com.zhongruan.android.zkfacedemo.utils.LogUtil;
import com.zhongruan.android.zkfacedemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.android.BuildConfig;

/**
 * Created by Administrator on 2017/9/7.
 */

public class SelectKcCcActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlCcBack;
    private TextView mTvEmpty, mTvSelectkc, rz_title;
    private GridView mGvContent;
    private SelectCcAdapter selectCcAdapter;
    private SelectKcAdapter selectKcAdapter;
    private List<Ks_cc> ksCcList;
    private List<Ks_kc> ksKcList;
    private Button mLlButtons;
    private Ks_cc cc;
    private Ks_kc kc;
    private Intent getIntent;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_select_kccc);
    }

    @Override
    public void initViews() {
        mLlCcBack = findViewById(R.id.ll_cc_back);
        mTvEmpty = findViewById(R.id.tv_empty);
        mTvSelectkc = findViewById(R.id.tv_selectkc);
        mGvContent = findViewById(R.id.gv_content);
        mLlButtons = findViewById(R.id.ll_buttons);
        rz_title = findViewById(R.id.rz_title);
        ksKcList = new ArrayList<>();
        ksCcList = new ArrayList<>();
        MyApplication.getApplication().setShouldStopUploadingData(true);
        getIntent = getIntent();
    }

    @Override
    public void initListeners() {
        List<Ks_kc> ksKcList = DbServices.getInstance(SelectKcCcActivity.this).loadAllkc();
        List<Ks_cc> ksCcList = DbServices.getInstance(SelectKcCcActivity.this).loadAllcc();
        mLlCcBack.setOnClickListener(this);
        if (ksKcList.size() != 0 && ksCcList.size() != 0) {
            if (DbServices.getInstance(SelectKcCcActivity.this).queryKC("1")) {
                selectCC();
            } else {
                selectKC();
            }
        }
        mLlCcBack.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cc_back:
                finish();
                break;
        }
    }

    private void selectKC() {
        rz_title.setText("请选择考场");
        mGvContent.setNumColumns(4);
        mTvEmpty.setVisibility(View.VISIBLE);
        mTvSelectkc.setVisibility(View.GONE);
        Ks_kc ks_kc = new Ks_kc();
        ks_kc.setKc_no("全部考场");
        ks_kc.setKc_name("全部考场");
        ks_kc.setKc_extract("0");
        ksKcList.add(ks_kc);
        ksKcList.addAll(DbServices.getInstance(SelectKcCcActivity.this).loadAllkc());
        selectCcAdapter = new SelectCcAdapter(SelectKcCcActivity.this, ksCcList);
        mGvContent.setAdapter(selectCcAdapter);
        if (Utils.stringIsEmpty(getIntent.getStringExtra("kcmc"))) {
            MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("UPDATE " + Ks_kcDao.TABLENAME + " SET  kc_extract = 1");
        } else {
            DbServices.getInstance(getBaseContext()).saveKsKc(getIntent.getStringExtra("kcmc"));
        }
        selectKcAdapter = new SelectKcAdapter(SelectKcCcActivity.this, ksKcList);
        mGvContent.setAdapter(selectKcAdapter);
        mGvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectKcAdapter.choiceSingleState(i);
                resetKcText();
            }
        });
        mLlButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i(selectKcAdapter.getChosenKcList().size());
                if (selectKcAdapter.getChosenKcList().size() > 0) {
                    if (kc.getKc_name().equals("全部考场")) {
                        MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("UPDATE " + Ks_kcDao.TABLENAME + " SET  kc_extract = 1");
                    } else {
                        MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("UPDATE " + Ks_kcDao.TABLENAME + " SET  kc_extract = 0");
                        DbServices.getInstance(getBaseContext()).saveKsKc(kc.getKc_name());
                    }
                    MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("UPDATE " + Ks_ccDao.TABLENAME + " SET  cc_extract = 0");
                    selectCC();
                }
            }
        });
    }

    private void selectCC() {
        rz_title.setText("请选择场次");
        mGvContent.setNumColumns(2);
        mTvEmpty.setVisibility(View.VISIBLE);
        mTvSelectkc.setVisibility(View.GONE);
        ksCcList.addAll(DbServices.getInstance(SelectKcCcActivity.this).loadAllcc());
        selectCcAdapter = new SelectCcAdapter(SelectKcCcActivity.this, ksCcList);
        mGvContent.setAdapter(selectCcAdapter);
        mGvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectCcAdapter.choiceSingleState(i);
                resetCcText();
            }
        });
        mLlButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!DateUtil.isTime(DateUtil.dateToLong(DateUtil.getNowTime_Millisecond3()), DateUtil.dateToLong(cc.getCc_kssj()), DateUtil.dateToLong(cc.getCc_jssj()))) {
                    new HintDialog2(SelectKcCcActivity.this, R.style.dialog, "当前场次不在当前考试时间", "当前时间：" + DateUtil.getNowTimeChinese(), "所选场次：" + DateUtil.getChineseTime(DateUtil.getStringToDate(cc.getCc_kssj())) + " - " + DateUtil.getChineseTime(DateUtil.getStringToDate(cc.getCc_jssj())), new HintDialog2.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                            } else {
                                startActivity(new Intent(SelectKcCcActivity.this, TimeActivity.class));
                                dialog.dismiss();
                            }
                        }
                    }).setTitle("提示").setBackgroundResource(R.drawable.img_base_icon_question).setNegativeButton("重新选择场次").setPositiveButton("修改当前时间").show();
                } else {
                    showProgressDialog(SelectKcCcActivity.this, "正在提取所选场次数据...", false, 100);
                    if (selectCcAdapter.getChosenCcList().size() > 0) {
                        MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("UPDATE " + Ks_ccDao.TABLENAME + " SET  cc_extract = 0");
                        MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("UPDATE " + Ks_ccDao.TABLENAME + " SET  cc_extract = 1 WHERE cc_no = " + cc.getCc_no());
                        if (DbServices.getInstance(getBaseContext()).selectKC().size() > 1) {
                            DbServices.getInstance(SelectKcCcActivity.this).deleteAllbkks();
                            MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("INSERT INTO " + Bk_ksDao.TABLENAME + " (ks_ksno,ks_xm,ks_xb,ks_zjno,ks_zwh,ks_ccno,ks_ccmc,ks_kcno,ks_kcmc,ks_xp,isRZ,isTG) " + " select a.ksno,a.xm,a.xb,a.zjno,a.zw,a.ccno,a.ccmc,a.kcno,a.kcmc,b.xp_pic,'0','0' from " + Bk_ks_tempDao.TABLENAME + " as a," + Bk_ksxpDao.TABLENAME + " as b where (a.ccno = '" + cc.getCc_no() + "'AND a.zjno=b.zjno)");
                        } else {
                            if (DbServices.getInstance(getBaseContext()).queryBKKSList(DbServices.getInstance(getBaseContext()).selectKC().get(0).getKc_name(), cc.getCc_name()).size() == 0) {
                                MyApplication.getDaoInstant(getBaseContext()).getDatabase().execSQL("INSERT INTO " + Bk_ksDao.TABLENAME + " (ks_ksno,ks_xm,ks_xb,ks_zjno,ks_zwh,ks_ccno,ks_ccmc,ks_kcno,ks_kcmc,ks_xp,isRZ,isTG) " + " select a.ksno,a.xm,a.xb,a.zjno,a.zw,a.ccno,a.ccmc,a.kcno,a.kcmc,b.xp_pic,'0','0' from " + Bk_ks_tempDao.TABLENAME + " as a," + Bk_ksxpDao.TABLENAME + " as b where  (a.kcno = '" + DbServices.getInstance(getBaseContext()).selectKC().get(0).getKc_no() + "' AND a.ccno = '" + cc.getCc_no() + "'AND a.zjno=b.zjno)");
                            }
                        }
                        ABLSynCallback.call(new ABLSynCallback.BackgroundCall() {
                            @Override
                            public Object callback() {
                                if (DbServices.getInstance(getBaseContext()).queryBKKSList(DbServices.getInstance(getBaseContext()).selectKC().get(0).getKc_name(), cc.getCc_name()).size() > 0) {
                                    return Boolean.valueOf(true);
                                } else {
                                    return Boolean.valueOf(false);
                                }
                            }
                        }, new ABLSynCallback.ForegroundCall() {
                            @Override
                            public void callback(Object obj) {
                                if (((Boolean) obj).booleanValue()) {
                                    showProgressDialog(SelectKcCcActivity.this, "正在提取所选场次数据完成", false, 100);
                                    dismissProgressDialog();
                                    if (DbServices.getInstance(getBaseContext()).selectKC().size() > 1) {
                                        new HintDialog(SelectKcCcActivity.this, R.style.dialog, "提取考生完成，共有" + DbServices.getInstance(getBaseContext()).queryBKKSLists(cc.getCc_name()).size() + "个考生", new HintDialog.OnCloseListener() {
                                            @Override
                                            public void onClick(Dialog dialog, boolean confirm) {
                                                if (confirm) {
                                                    dialog.dismiss();
                                                    if (getIntent.getStringExtra("sfrz").equals("2")) {
                                                        startActivity(new Intent(SelectKcCcActivity.this, RZJLActivity.class));
                                                    } else {
                                                        startActivity(new Intent(SelectKcCcActivity.this, RZActivity.class));
                                                    }
                                                    finish();
                                                }
                                            }
                                        }).setBackgroundResource(R.drawable.img_base_check).setNOVisibility(false).setLLButtonVisibility(true).setTitle("提示").setPositiveButton("知道了").show();

                                    } else {
                                        new HintDialog(SelectKcCcActivity.this, R.style.dialog, "提取考生完成，共有" + DbServices.getInstance(getBaseContext()).queryBKKSList(DbServices.getInstance(getBaseContext()).selectKC().get(0).getKc_name(), cc.getCc_name()).size() + "个考生", new HintDialog.OnCloseListener() {
                                            @Override
                                            public void onClick(Dialog dialog, boolean confirm) {
                                                if (confirm) {
                                                    dialog.dismiss();
                                                    if (getIntent.getStringExtra("sfrz").equals("2")) {
                                                        startActivity(new Intent(SelectKcCcActivity.this, RZJLActivity.class));
                                                    } else {
                                                        startActivity(new Intent(SelectKcCcActivity.this, RZActivity.class));
                                                    }
                                                    finish();
                                                }
                                            }
                                        }).setBackgroundResource(R.drawable.img_base_check).setNOVisibility(false).setLLButtonVisibility(true).setTitle("提示").setPositiveButton("知道了").show();
                                    }
                                } else {
                                    ShowHintDialog(SelectKcCcActivity.this, "提取考生指纹失败，请重新选择场次", "提示", R.drawable.img_base_icon_error, "知道了", false);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void resetKcText() {
        if (this.selectKcAdapter == null) {
            return;
        }
        if (this.selectKcAdapter.getChosenKcList().size() > 0) {
            kc = this.selectKcAdapter.getChosenKcList().get(0);
            this.mLlButtons.setEnabled(true);
            this.mTvSelectkc.setText(kc.getKc_name());
            this.mTvSelectkc.setVisibility(View.VISIBLE);
            mTvEmpty.setVisibility(View.GONE);
            return;
        }
        this.kc = null;
        this.mLlButtons.setEnabled(false);
        this.mTvSelectkc.setText(BuildConfig.VERSION_NAME);
        this.mTvSelectkc.setVisibility(View.GONE);
    }

    private void resetCcText() {
        if (this.selectCcAdapter == null) {
            return;
        }
        if (this.selectCcAdapter.getChosenCcList().size() > 0) {
            this.cc = this.selectCcAdapter.getChosenCcList().get(0);
            this.mTvSelectkc.setText(cc.getCc_name());
            this.mLlButtons.setEnabled(true);
            this.mTvSelectkc.setVisibility(View.VISIBLE);
            mTvEmpty.setVisibility(View.GONE);
            return;
        }
        this.cc = null;
        this.mLlButtons.setEnabled(false);
        this.mTvSelectkc.setText(BuildConfig.VERSION_NAME);
        this.mTvSelectkc.setVisibility(View.GONE);
    }
}
