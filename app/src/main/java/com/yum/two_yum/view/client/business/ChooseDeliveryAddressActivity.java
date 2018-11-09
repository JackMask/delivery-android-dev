package com.yum.two_yum.view.client.business;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseReturn;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.base.ProvinceBean;
import com.yum.two_yum.base.SearchNearbyBase;
import com.yum.two_yum.base.SearchNearbyDataBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.controller.adapter.ChooseDeliveryAddressAdapter;
import com.yum.two_yum.controller.adapter.callback.FilterFootCallBack;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.pickerview.OptionsPickerView;
import com.yum.two_yum.view.client.clientorder.AddToAddressActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/11
 */

public class ChooseDeliveryAddressActivity extends BaseActivity {

    @Bind(R.id.address_lv)
    ListView addressLv;

    private ChooseDeliveryAddressAdapter addressAdapter;
    private List<FilterBase> dataList = new ArrayList<>();
    private String lat,lng,distance;
    private String addressId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delivery_address);
        ButterKnife.bind(this);
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        addressId = getIntent().getStringExtra("id");
        distance = getIntent().getStringExtra("distance");
        setView();
    }

    @Override
    protected void setView() {
        super.setView();
        addressAdapter = new ChooseDeliveryAddressAdapter(dataList);
        addressLv.setAdapter(addressAdapter);
        addressAdapter.setLat(lat);
        addressAdapter.setLng(lng);
        addressAdapter.setDistance(distance);
        addressAdapter.setCallBack(new ChooseDeliveryAddressAdapter.ChooseDeliveryAddressCallBack() {
            @Override
            public void selectFoot(int position, boolean selectType,boolean type) {
                for (FilterBase data : dataList){
                    data.setType(false);
                }
                if (type) {
                    dataList.get(position).setType(selectType);
                    Intent intent = new Intent();
                    YumApplication.getInstance().getAddressDB().setAddress(dataList.get(position).getAddressRespResultsBeans().getAddress());
                    YumApplication.getInstance().getAddressDB().setGender(dataList.get(position).getAddressRespResultsBeans().getGender());
                    YumApplication.getInstance().getAddressDB().setId(dataList.get(position).getAddressRespResultsBeans().getId());
                    YumApplication.getInstance().getAddressDB().setLat(dataList.get(position).getAddressRespResultsBeans().getLat());
                    YumApplication.getInstance().getAddressDB().setLng(dataList.get(position).getAddressRespResultsBeans().getLng());
                    YumApplication.getInstance().getAddressDB().setName(dataList.get(position).getAddressRespResultsBeans().getName());
                    YumApplication.getInstance().getAddressDB().setNote(dataList.get(position).getAddressRespResultsBeans().getNote());
                    YumApplication.getInstance().getAddressDB().setPhone(dataList.get(position).getAddressRespResultsBeans().getPhone());
                    YumApplication.getInstance().getAddressDB().setUserId(dataList.get(position).getAddressRespResultsBeans().getUserId());
                    intent.putExtra("address", dataList.get(position).getAddressRespResultsBeans().getAddress());
                    intent.putExtra("name", dataList.get(position).getAddressRespResultsBeans().getName());
                    intent.putExtra("phone", dataList.get(position).getAddressRespResultsBeans().getPhone());
                    intent.putExtra("id", dataList.get(position).getAddressRespResultsBeans().getId());
                    intent.putExtra("note", dataList.get(position).getAddressRespResultsBeans().getNote());
                    addressAdapter.notifyDataSetChanged();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    showMsg(getString(R.string.OUTSIDEDELIVERYAREA));
                }
//                if (type) {
//                    Intent intent = new Intent();
//                    intent.putExtra("address",dataList.get(position).getAddressRespResultsBeans().getAddress());
//                    intent.putExtra("name",dataList.get(position).getAddressRespResultsBeans().getName());
//                    intent.putExtra("phone",dataList.get(position).getAddressRespResultsBeans().getPhone());
//                    intent.putExtra("id",dataList.get(position).getAddressRespResultsBeans().getId());
//                    addressAdapter.notifyDataSetChanged();
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }else{
//                    showMsg(getString(R.string.OUTSIDEDELIVERYAREA));
//                }


            }

            @Override
            public void delclic(final String addressId) {
                Map<String,String> map = new HashMap<>();
                map.put("userId",YumApplication.getInstance().getMyInformation().getUid());
                map.put("addressId",addressId);
                OkHttpUtils
                        .post()
                        .url(Constant.DEL_ADDRESS)
                        .addHeader("Accept-Language", AppUtile.getLanguage())
                        .addHeader("YUM-AUTH-TOKEN",YumApplication.getInstance().getMyInformation().getToken())
                        .params(map)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                if (AppUtile.getNetWork(e.getMessage())){
                                    showMsg(getString(R.string.NETERROR));
                                }
                            }

                            @Override
                            public void onResponse(String s, int i) {
                                if (AppUtile.setCode(s,ChooseDeliveryAddressActivity.this)) {
                                    if (addressId.equals(YumApplication.getInstance().getAddressDB().getId())){
                                        YumApplication.getInstance().getAddressDB().setAddress("");
                                        YumApplication.getInstance().getAddressDB().setGender(-1);
                                        YumApplication.getInstance().getAddressDB().setId("");
                                        YumApplication.getInstance().getAddressDB().setLat("");
                                        YumApplication.getInstance().getAddressDB().setLng("");
                                        YumApplication.getInstance().getAddressDB().setName("");
                                        YumApplication.getInstance().getAddressDB().setNote("");
                                        YumApplication.getInstance().getAddressDB().setPhone("");
                                        YumApplication.getInstance().getAddressDB().setUserId("");
                                    }
                                    getAddress();
                                }
                            }
                        });
            }

            @Override
            public void editClick(SearchNearbyDataBase data) {
                Intent intent = new Intent(ChooseDeliveryAddressActivity.this,AddToAddressActivity.class);
                intent.putExtra("data",data);
                startActivityForResult(intent,1);
            }


        });

        getAddress();
    }
    private boolean conSele = false;
    private boolean idNoSelect = false;
    @OnClick({R.id.del_btn, R.id.address_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.del_btn:
//                if (!TextUtils.isEmpty(addressId)) {
//                    conSele = true;
//                }
//                if (idNoSelect){
//                    FilterBase data = null;
//                    for (int i = 0 ; i < dataList.size();i++){
//                        if (dataList.get(i).isType()){
//                            data = dataList.get(i);
//                        }
//                    }
//                    intent = new Intent();
//                    intent.putExtra("address", data.getAddressRespResultsBeans().getAddress());
//                    intent.putExtra("name", data.getAddressRespResultsBeans().getName());
//                    intent.putExtra("phone", data.getAddressRespResultsBeans().getPhone());
//                    intent.putExtra("id", data.getAddressRespResultsBeans().getId());
//                    intent.putExtra("note", data.getAddressRespResultsBeans().getNote());
//                    setResult(RESULT_OK, intent);
////                    intent = new Intent();
////                    intent.putExtra("addressId",true);
////                    setResult(2,intent);
//                }
                    FilterBase data = null;
                    for (int i = 0; i < dataList.size(); i++) {
                        if (dataList.get(i).isType()) {
                            data = dataList.get(i);
                        }
                    }
                    if (data != null) {
                        intent = new Intent();
                        intent.putExtra("address", data.getAddressRespResultsBeans().getAddress());
                        intent.putExtra("name", data.getAddressRespResultsBeans().getName());
                        intent.putExtra("phone", data.getAddressRespResultsBeans().getPhone());
                        intent.putExtra("id", data.getAddressRespResultsBeans().getId());
                        intent.putExtra("note", data.getAddressRespResultsBeans().getNote());
                        setResult(RESULT_OK, intent);
                    } else {
                        intent = new Intent();
                        intent.putExtra("addressId", true);
                        setResult(2, intent);
                    }
                finish();
                break;
            case R.id.address_btn:
                intent = new Intent(this,AddToAddressActivity.class);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.activity_open, R.anim.out);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1&&resultCode==RESULT_OK){
            getAddress();
        }
    }
    private List<FilterBase> dataListNo = new ArrayList<>();
    private List<FilterBase> dataListYes = new ArrayList<>();
    private void getAddress(){
        OkHttpUtils
                .get()
                .url(Constant.USER_ADDRESS)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId",YumApplication.getInstance().getMyInformation().getUid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {

                            if (AppUtile.setCode(s,ChooseDeliveryAddressActivity.this)){
                                SearchNearbyBase data = JSON.parseObject(s, SearchNearbyBase.class);
                                dataList.clear();
                                dataListYes.clear();
                                dataListNo.clear();
                                if (data.getData().getUserAddressRespResults()!=null&&data.getData().getUserAddressRespResults().size()>0){
                                    for (SearchNearbyDataBase item:data.getData().getUserAddressRespResults()){
                                        FilterBase filterBase = new FilterBase();
                                        if (!TextUtils.isEmpty(addressId)){
                                            if (addressId.equals(item.getId())){
                                                if ((Double.valueOf(distance)*1600)>= AppUtile.GetDistance(Double.valueOf(item.getLat()),Double.valueOf(item.getLng())
                                                        ,Double.valueOf(lat),Double.valueOf(lng))){
                                                    filterBase.setType(true);
                                                    idNoSelect = false;
                                                }else{
                                                    idNoSelect = true;
                                                    filterBase.setType(false);
                                                }

                                            }else{
                                                filterBase.setType(false);
                                            }
                                        }else{
                                            filterBase.setType(false);
                                        }
                                        filterBase.setAddressRespResultsBeans(item);
                                        if ((Double.valueOf(distance)*1600)>= AppUtile.GetDistance(Double.valueOf(item.getLat()),Double.valueOf(item.getLng())
                                                ,Double.valueOf(lat),Double.valueOf(lng))){
                                            dataListYes.add(filterBase);
                                        }else{
                                            dataListNo.add(filterBase);
                                        }

                                        //dataList.add(filterBase);
                                    }
                                    if (dataListYes!=null&&dataListYes.size()>0){
                                        for (FilterBase item:dataListYes){
                                            if (!TextUtils.isEmpty(addressId)) {
                                                if (item.getAddressRespResultsBeans().getId().equals(addressId)) {
                                                    dataList.add(0, item);
                                                }else{
                                                    dataList.add(item);
                                                }
                                            }else{
                                                dataList.add(item);
                                            }
                                        }
                                    }
                                    if (dataListNo!=null&&dataListNo.size()>0){
                                        for (FilterBase item:dataListNo){
                                            dataList.add(item);
                                        }
                                    }
                                }
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}
