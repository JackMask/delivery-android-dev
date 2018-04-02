package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.controller.adapter.callback.StoraeHospitalDetailsCallBack;
import com.guxingdongli.yizhangguan.model.StoraeHospitalDetailsBase;
import com.guxingdongli.yizhangguan.view.home.home_merchants.MyOrderDetailsActivity;
import com.guxingdongli.yizhangguan.view.home.home_merchants.MyOrderDetailsNewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/22
 */

public class MyOrderDetailsNewAdapter extends BaseAdapter {

    List<StoraeHospitalDetailsBase.DataBean.DetailsListBean> dataList;
    List<StoraeHospitalDetailsBase.DataBean.DetailsListBean> okList = new ArrayList<>();
    private AbActivity abActivity;
    private LayoutInflater inflater;
    boolean editextCon = false;
    private StoraeHospitalDetailsCallBack callBack;
    private boolean type;
    public MyOrderDetailsNewAdapter(AbActivity constant, List<StoraeHospitalDetailsBase.DataBean.DetailsListBean> dataList, boolean type){
        this.abActivity = constant;
        this.dataList  = dataList;
        this.type = type;
        this.okList = dataList;
        this.inflater = ((LayoutInflater) abActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }
    public void setEditextCon(boolean editextCon) {
        this.editextCon = editextCon;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    public StoraeHospitalDetailsCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(StoraeHospitalDetailsCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    public List<StoraeHospitalDetailsBase.DataBean.DetailsListBean> getData(){
        return okList;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final inspectionViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_storae_hospital_details_list, null);
            viewHolder = new inspectionViewHolder(convertView,position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (inspectionViewHolder) convertView.getTag();
        }
        viewHolder.acceptance_num.setTag(position);
        StoraeHospitalDetailsBase.DataBean.DetailsListBean data = dataList.get(position);

        viewHolder.name_tv.setText(data.getName());
        viewHolder.model_tv.setText(data.getSpecification());
        viewHolder.price_tv.setText(data.getPrice());
        viewHolder.batch_number.setText(data.getBatchNumber());
        viewHolder.effective_data.setText(data.getValid());
        viewHolder.delivery_num.setText(data.getQuantity()+"");
        viewHolder.delivery_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                okList.get(position).setQuantity(Integer.valueOf(!TextUtils.isEmpty(charSequence.toString())?charSequence.toString():"0"));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        viewHolder.batch_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                okList.get(position).setBatchNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        viewHolder.acceptance_num.setText(data.getNoAcceptanceQuantity()==0?"":data.getNoAcceptanceQuantity()+"");
        if (type){
            viewHolder.title_name.setText("待验收数量");
            viewHolder.acceptance_num.setFocusable(false);
            viewHolder.acceptance_num.setBackgroundColor(Color.WHITE);
            viewHolder.acceptance_num.setFocusableInTouchMode(false);
            viewHolder.delivery_num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    viewHolder.acceptance_num.setText(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            viewHolder.effective_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MyOrderDetailsNewActivity)abActivity).selectTime(viewHolder.effective_data,okList.get(position));
                }
            });

        }else{
            viewHolder. acceptance_layout.setVisibility(View.GONE);
            viewHolder.delivery_num.setFocusable(false);
            viewHolder.delivery_num.setBackgroundColor(Color.WHITE);
            viewHolder.delivery_num.setFocusableInTouchMode(false);
            viewHolder.effective_data.setFocusable(false);
            viewHolder.effective_data.setBackgroundColor(Color.WHITE);
            viewHolder.effective_data.setFocusableInTouchMode(false);
            viewHolder.batch_number.setFocusable(false);
            viewHolder.batch_number.setBackgroundColor(Color.WHITE);
            viewHolder.batch_number.setFocusableInTouchMode(false);
        }
        return convertView;
    }

    class inspectionViewHolder {
        private TextView name_tv ;
        private TextView model_tv ;
        private TextView price_tv;
        private TextView batch_number ;
        private TextView effective_data ;
        private TextView delivery_num ;
        private TextView title_name;
        private LinearLayout acceptance_layout;
        private EditText acceptance_num;

        public inspectionViewHolder(View view,int pisition){
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            model_tv = (TextView) view.findViewById(R.id.model_tv);
            price_tv = (TextView) view.findViewById(R.id.price_tv);
            acceptance_layout = (LinearLayout) view.findViewById(R.id.acceptance_layout);
            batch_number = (TextView) view.findViewById(R.id.batch_number);
            effective_data = (TextView) view.findViewById(R.id.effective_data);
            delivery_num = (TextView) view.findViewById(R.id.delivery_num);
            title_name = (TextView) view.findViewById(R.id.title_name);
            acceptance_num = (EditText) view.findViewById(R.id.acceptance_num);
            acceptance_num.setTag(pisition);//存tag值
            acceptance_num.setOnFocusChangeListener(new myFocusChange(this));
        }



    }
    class myFocusChange implements View.OnFocusChangeListener{

        private inspectionViewHolder mHolder;

        public myFocusChange(inspectionViewHolder mHolder) {
            this.mHolder = mHolder;
        }
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                int position = (int) mHolder.acceptance_num.getTag();//取tag值
                System.out.println("mHolder.acceptance_num = "+position);
                if (callBack!=null){
                    callBack.getNumNew(position,mHolder.acceptance_num);
                }
                // 此处为得到焦点时的处理内容
            } else {
                System.out.println("fdsfsafdsafdsafdsafdsafdsafdsa");
                // 此处为失去焦点时的处理内容
            }
        }
    }

}