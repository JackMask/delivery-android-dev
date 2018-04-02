package com.yuxiaolong.yuxiande;


import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/2/17.
 */

public class AdapterTest extends BaseCommAdapter<AboutUsVo.ResultBean> {

    public AdapterTest(List<AboutUsVo.ResultBean> datas)
    {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context)
    {
        AboutUsVo.ResultBean item = getItem(position);

        TextView tv_name = holder.getItemView(R.id.content);
        tv_name.setText(item.getContent());

        TextView tv_sex = holder.getItemView(R.id.id);
        tv_sex.setText(item.getId());

        TextView iv_head = holder.getItemView(R.id.title);
        iv_head.setText(item.getTitle());
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.item_content;
    }
}
