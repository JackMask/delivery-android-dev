package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.PushChildCallBack;
import com.guxingdongli.yizhangguan.model.PushBase;
import com.guxingdongli.yizhangguan.model.RemindBase;
import com.guxingdongli.yizhangguan.view.home.home_hospital.fragment.callback.StorageHospitalOnClickCallBack;
import com.guxingdongli.yizhangguan.view.home.home_merchants.PushActivity;
import com.yuxiaolong.yuxiandelibrary.NoSlideListView;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/24
 */

public class PushAdapter  extends BaseCommAdapter<PushBase.DataBean> {
    private StorageHospitalOnClickCallBack clickCallBack;
    private PushChildCallBack childCallBack;
    private List<PushChildAdapter> adapters;

    public List<PushChildAdapter> getAdapters() {
        return adapters;
    }

    public void setAdapters(List<PushChildAdapter> adapters) {
        this.adapters = adapters;
    }

    public void setChildCallBack(PushChildCallBack childCallBack) {
        this.childCallBack = childCallBack;
    }

    public void setClickCallBack(StorageHospitalOnClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public PushAdapter(List<PushBase.DataBean> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, final int position, final Context context) {
        final PushBase.DataBean data = getItem(position);
        final List<PushBase.DataBean.ListManyBean> manyBeen = new ArrayList<>();

        final  NoSlideListView data_list = holder.getItemView(R.id.data_list);
        ImageView more_iv = holder.getItemView(R.id.more_iv);
        LinearLayout more_btn = holder.getItemView(R.id.more_btn);
        final EditText pirce_unite = holder.getItemView(R.id.pirce_unite);
        pirce_unite.setTag(position);
        TextView name = holder.getItemView(R.id.name);
        name.setText(data.getName());
        for (PushBase.DataBean.ListManyBean item: data.getListMany()){
            manyBeen.add(item);
        }
        final PushChildAdapter adapter = ((PushActivity)(context)).getAdapter(position);
        data_list.setAdapter(((PushActivity)(context)).getAdapter(position));
        pirce_unite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        pirce_unite.setText(s);
                        pirce_unite.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    pirce_unite.setText(s);
                    pirce_unite.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        pirce_unite.setText(s.subSequence(0, 1));
                        pirce_unite.setSelection(1);
                        return;
                    }
                }
                    System.out.println((Integer) pirce_unite.getTag());
                    for (int c = 0 ;c < mDatas.get((Integer) pirce_unite.getTag()).getListMany().size();c++){
                        mDatas.get((Integer) pirce_unite.getTag()).getListMany().get(c).setPrice(s.toString());
                    }
                    mDatas.get((Integer) pirce_unite.getTag()).setPrice(Double.valueOf(!TextUtils.isEmpty(s.toString())?s.toString():"0"));
                    ((PushActivity)(context)).getAdapter((Integer) pirce_unite.getTag()).notifyDataSetChanged();

                    return;

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (data.isType()){
            data_list.setVisibility(View.VISIBLE);
            more_iv.setImageResource(R.mipmap.keepimg);
            if (data.getListMany()!=null&&data.getListMany().size()>0){

            }else{
                data_list.setVisibility(View.GONE);
            }

        }else{
            data_list.setVisibility(View.GONE);
            more_iv.setImageResource(R.mipmap.spreadoutimg);
        }
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean type = false;
                if (data.isType()){
                    getItem(position).setType(false);
                    type = false;
                    data_list.setVisibility(View.GONE);
                }else{
                    getItem(position).setType(true);
                    type = true;
                    data_list.setVisibility(View.VISIBLE);

                }
                if (clickCallBack!=null){
                    clickCallBack.clickNo(position,type);
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_push;
    }
}
