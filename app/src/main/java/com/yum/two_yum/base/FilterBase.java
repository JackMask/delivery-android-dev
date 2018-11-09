package com.yum.two_yum.base;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/8
 */

public class FilterBase extends BaseReturn implements Comparable<FilterBase>,Serializable {
    private String title;
    private String comparison;
    private boolean type;
    private String currencySymbol;
    private String flag;
    private String id;
    private boolean selectType;

    private SearchNearbyDataBase addressRespResultsBeans;//用户地址
    private GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean menuRespResultsBean;//我的餐馆菜品

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean getMenuRespResultsBean() {
        return menuRespResultsBean;
    }

    public void setMenuRespResultsBean(GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean menuRespResultsBean) {
        this.menuRespResultsBean = menuRespResultsBean;
    }

    public SearchNearbyDataBase getAddressRespResultsBeans() {
        return addressRespResultsBeans;
    }

    public void setAddressRespResultsBeans(SearchNearbyDataBase addressRespResultsBeans) {
        this.addressRespResultsBeans = addressRespResultsBeans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isSelectType() {
        return selectType;
    }

    public void setSelectType(boolean selectType) {
        this.selectType = selectType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {

        this.comparison = comparison.toLowerCase();
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull FilterBase o) {
        return this.getComparison().compareTo(o.getComparison());
    }
}
