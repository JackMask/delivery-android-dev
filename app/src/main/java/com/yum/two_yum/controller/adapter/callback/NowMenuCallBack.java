package com.yum.two_yum.controller.adapter.callback;

import com.yum.two_yum.base.GetMerchantMenuListBase;

/**
 * @author 余先德
 * @data 2018/4/14
 */

public interface NowMenuCallBack {
    public abstract void clickSelect(int position,boolean type);
    public abstract void clickDel(int position,String id);
    public abstract void itemClick(GetMerchantMenuListBase.DataBean.MerchantMenuRespResultsBean data);
}
