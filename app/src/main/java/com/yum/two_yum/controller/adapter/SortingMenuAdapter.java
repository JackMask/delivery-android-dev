package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.GetMerchantMenuListBase;
import com.yum.two_yum.utile.AppUtile;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public class SortingMenuAdapter  extends BaseAdapter {

    private Context context;
    List<GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean> items;// ������������Դ

    public SortingMenuAdapter(Context context, List<GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean> list) {
        this.context = context;
        this.items = list;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void remove(int arg0) {// ɾ��ָ��λ�õ�item
        items.remove(arg0);
        this.notifyDataSetChanged();// ��Ҫ���Ǹ������������������Դ
    }

    public void insert(GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean item, int arg0) {// ��ָ��λ�ò���item
        items.add(arg0, item);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean item = (GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sorting_menu, null);
            viewHolder.ivDragHandle = (LinearLayout) convertView.findViewById(R.id.sorting_layout);
            viewHolder.dishes_img = (ImageView) convertView.findViewById(R.id.dishes_img);
            viewHolder.sort_btn = (ImageView) convertView.findViewById(R.id.sort_btn);
            viewHolder.menu_name_tv = (TextView) convertView.findViewById(R.id.menu_name_tv);
            viewHolder.content_tv = (TextView) convertView.findViewById(R.id.content_tv);
            viewHolder.pice_tv = (TextView) convertView.findViewById(R.id.pice_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.get().load(item.getCover()).placeholder(R.mipmap.timg).into(viewHolder.dishes_img);
        viewHolder.dishes_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.menu_name_tv.setText(item.getName());
        viewHolder.content_tv.setText(item.getDescribe());
        viewHolder.pice_tv.setText("$ "+ AppUtile.getPrice(item.getPrice()+""));
        return convertView;
    }

    class ViewHolder {
        TextView content_tv;
        TextView menu_name_tv;
        TextView pice_tv;
        ImageView sort_btn;
        ImageView dishes_img;
        LinearLayout ivDragHandle;
    }
}
