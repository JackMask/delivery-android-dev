package com.guxingdongli.yizhangguan.controller;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.guxingdongli.yizhangguan.controller.db.PMyInformation;
import com.guxingdongli.yizhangguan.model.FeeNameBase;
import com.guxingdongli.yizhangguan.model.OriginBase;
import com.guxingdongli.yizhangguan.model.ReasonAnalysisBase;
import com.guxingdongli.yizhangguan.model.ServiceLevelBase;
import com.guxingdongli.yizhangguan.model.SourcesOfFundsBase;
import com.guxingdongli.yizhangguan.model.UnitBase;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jackmask on 2018/2/27.
 */

public class YiZhangGuanApplication extends Application {
    private static YiZhangGuanApplication instance;
    private static PMyInformation myInfo;//登陆用户的信息
    private boolean appType = true;//app登陆状态 ,true是医院，false是供应商
    private boolean isEngineer = false;//是否是工程师
    private int storageHospitalPopupW,storageHospitalPopupH;
    private List<ServiceLevelBase.DataBean> serviceLeveList ;//维修等级
    private List<ReasonAnalysisBase.DataBean> ReasonAnalysisList;//原因分析
    private List<SourcesOfFundsBase.DataBean> sourcesOfFundsList;//资金来源
    private List<FeeNameBase.DataBean> FeeNameBaseList;//费用名称
    private List<OriginBase.DataBean> OrigingList;//产地
    private List<UnitBase.DataBean> UnitList;//单位
    public static final int STORAGE_BTN = 1; //验收入库
    public static final int HOME = 2; //扫码报修
    public static final int REPAIR_BTN = 3; //维修管理
    public static final int ACCEPTANCE_BTN = 4; //报修验收

    private String province;
    private String city;
    private String county;

    private int customScan = 1;

    private boolean loginType = true;

    public boolean isLoginType() {
        return loginType;
    }

    public void setLoginType(boolean loginType) {
        this.loginType = loginType;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.myInfo = new PMyInformation(this);
        instance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        Bugtags.start("323e4947da561212b4ea1a5c70af9031", this, Bugtags.BTGInvocationEventBubble);
    }

    public boolean isEngineer() {
        return isEngineer;
    }

    public List<ServiceLevelBase.DataBean> getServiceLeveList() {
        return serviceLeveList;
    }

    public void setServiceLeveList(List<ServiceLevelBase.DataBean> serviceLeveList) {
        this.serviceLeveList = serviceLeveList;
    }

    public List<ReasonAnalysisBase.DataBean> getReasonAnalysisList() {
        return ReasonAnalysisList;
    }

    public void setReasonAnalysisList(List<ReasonAnalysisBase.DataBean> reasonAnalysisList) {
        ReasonAnalysisList = reasonAnalysisList;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setEngineer(boolean engineer) {
        isEngineer = engineer;
    }

    public List<SourcesOfFundsBase.DataBean> getSourcesOfFundsList() {
        return sourcesOfFundsList;
    }

    public void setSourcesOfFundsList(List<SourcesOfFundsBase.DataBean> sourcesOfFundsList) {
        this.sourcesOfFundsList = sourcesOfFundsList;
    }

    public int getCustomScan() {
        return customScan;
    }

    public void setCustomScan(int customScan) {
        this.customScan = customScan;
    }

    public List<FeeNameBase.DataBean> getFeeNameBaseList() {
        return FeeNameBaseList;
    }

    public void setFeeNameBaseList(List<FeeNameBase.DataBean> feeNameBaseList) {
        FeeNameBaseList = feeNameBaseList;
    }

    public List<OriginBase.DataBean> getOrigingList() {
        return OrigingList;
    }

    public void setOrigingList(List<OriginBase.DataBean> origingList) {
        OrigingList = origingList;
    }

    public List<UnitBase.DataBean> getUnitList() {
        return UnitList;
    }

    public void setUnitList(List<UnitBase.DataBean> unitList) {
        UnitList = unitList;
    }

    /**
     * 获取当前登陆用户的信息
     * @return
     */
    public PMyInformation getMyInfo() {
        return myInfo;
    }

    public static YiZhangGuanApplication getInstance() {
        return instance;
    }

    public boolean isAppType() {
        return appType;
    }

    public void setAppType(boolean appType) {
        this.appType = appType;
    }

    public int getStorageHospitalPopupW() {
        return storageHospitalPopupW;
    }

    public void setStorageHospitalPopupW(int storageHospitalPopupW) {
        System.out.println("storageHospitalPopupW = " + storageHospitalPopupW);
        this.storageHospitalPopupW = storageHospitalPopupW;
    }

    public int getStorageHospitalPopupH() {
        return storageHospitalPopupH;
    }

    public void setStorageHospitalPopupH(int storageHospitalPopupH) {
        this.storageHospitalPopupH = storageHospitalPopupH;
    }
}
