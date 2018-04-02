package com.guxingdongli.yizhangguan.view.home.home_hospital;

import com.guxingdongli.yizhangguan.model.MyDeviceDetailsBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentBasicInfoBase;
import com.guxingdongli.yizhangguan.model.MyRepairOrderDetailsContentOtherBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余先德
 * @data 2018/3/19
 */

public class MyRepairOrderDetailsModel {

    /**
     * 获取设备详情的基本信息
     * @param dataBsen
     * @return
     */
    public static List<MyRepairOrderDetailsContentBasicInfoBase> getDeviceInformation(MyDeviceDetailsBase.DataBean dataBsen){
        if (dataBsen!=null) {
            List<MyRepairOrderDetailsContentBasicInfoBase> basicInfoDataList = new ArrayList<>();
            MyRepairOrderDetailsContentBasicInfoBase b;
            b = new MyRepairOrderDetailsContentBasicInfoBase("名称:", dataBsen.getName());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("编号:", dataBsen.getMaterialNumber());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("规格:", dataBsen.getSpecification());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("型号:", dataBsen.getModel());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("品牌:", dataBsen.getBrand());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("使用科室:", dataBsen.getUseDepartment());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("责任人:", dataBsen.getResponsible());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("责任人电话:", dataBsen.getResponsiblePhone()+"");
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("生成厂家:", dataBsen.getManufacturer());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("使用状态:", dataBsen.getUseStatus());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("供应商:", dataBsen.getSupplierName());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("注册证号:", dataBsen.getRegistrationNumber());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("单位:", dataBsen.getUnit());
            basicInfoDataList.add(b);

            return basicInfoDataList;
        }else{
            return null;
        }
    }



    /**
     * 获取基本信息
     * @param dataBsen
     * @return
     */
    public static List<MyRepairOrderDetailsContentBasicInfoBase> getBasicInformation(MyRepairOrderDetailsBase.DataBean dataBsen){
        if (dataBsen!=null) {
            List<MyRepairOrderDetailsContentBasicInfoBase> basicInfoDataList = new ArrayList<>();
            MyRepairOrderDetailsContentBasicInfoBase b;
            b = new MyRepairOrderDetailsContentBasicInfoBase("维修单号:", dataBsen.getBusinessNumber());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("设备名称:", dataBsen.getName());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("处理阶段:", dataBsen.getMaintenanceStage());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("维修方式:", dataBsen.getMaintenanceType());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("报修时间:", dataBsen.getRepairTime().indexOf("1900")!=-1?"":dataBsen.getRepairTime());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("规格:", dataBsen.getSpecification());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("型号:", dataBsen.getModel());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("设备价值:", dataBsen.getWorth()+"");
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("科室负责人:", dataBsen.getResponsible());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("使用科室:", dataBsen.getDepartmentName());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("生产厂家:", dataBsen.getManufacturer());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("报修人:", dataBsen.getRepairUser());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("报修描述:", dataBsen.getFaultDescription());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("审核人员:", dataBsen.getAcceptanceUser());
            basicInfoDataList.add(b);
            b = new MyRepairOrderDetailsContentBasicInfoBase("验收日期:", (dataBsen.getCompletionDate().indexOf("1900")!=-1)?"":dataBsen.getCompletionDate());
            basicInfoDataList.add(b);
            return basicInfoDataList;
        }else{
            return null;
        }
    }

    /**
     * 院内维修
     * @return
     */
    public static List<MyRepairOrderDetailsContentOtherBase> gethospitalProcessList(List<MyRepairOrderDetailsBase.DataBean.HospitalProcessListBean> dataList){
        if (dataList!=null) {
            List<MyRepairOrderDetailsContentOtherBase> otherDataList = new ArrayList<>();
            for (int i = 0; i < dataList.size();i++) {
                MyRepairOrderDetailsBase.DataBean.HospitalProcessListBean itemData = dataList.get(i);
                MyRepairOrderDetailsContentOtherBase c;
                List<MyRepairOrderDetailsContentOtherBase.DataBean> c_i;
                c_i = new ArrayList<>();
                MyRepairOrderDetailsContentOtherBase.DataBean d;
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修级别", itemData.getMaintenanceLevelName());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("原因分析", itemData.getFaultAnalysisName());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修时间", itemData.getProcessTime().indexOf("1900")!=-1?"":itemData.getProcessTime());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("开始时间", itemData.getMaintenanceStartDate().indexOf("1900")!=-1?"":itemData.getMaintenanceStartDate());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("结束时间", itemData.getMaintenanceEndDate().indexOf("1900")!=-1?"":itemData.getMaintenanceEndDate());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修时长", itemData.getManHour()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("停机时长", itemData.getLongShutdown()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("测试人员", itemData.getTestUser());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("处理情况", itemData.getMaintenanceSituation());
                c_i.add(d);
                c = new MyRepairOrderDetailsContentOtherBase("("+(i+1)+")", c_i);
                otherDataList.add(c);
            }
            return otherDataList;
        }else{
            return null;
        }
    }

    /**
     * 院外维修
     * @param dataList
     * @return
     */
    public static List<MyRepairOrderDetailsContentOtherBase> getOutHospitalProcessList(List<MyRepairOrderDetailsBase.DataBean.OutHospitalProcessListBean> dataList){
        if (dataList!=null) {
            List<MyRepairOrderDetailsContentOtherBase> otherDataList = new ArrayList<>();
            for (int i = 0; i < dataList.size();i++) {
                MyRepairOrderDetailsBase.DataBean.OutHospitalProcessListBean itemData = dataList.get(i);
                MyRepairOrderDetailsContentOtherBase c;
                List<MyRepairOrderDetailsContentOtherBase.DataBean> c_i;
                c_i = new ArrayList<>();
                MyRepairOrderDetailsContentOtherBase.DataBean d;
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修级别", itemData.getMaintenanceLevelName());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("原因分析", itemData.getFaultAnalysisName());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修时间", itemData.getProcessTime().indexOf("1900")!=-1?"":itemData.getProcessTime());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("开始时间", itemData.getMaintenanceStartDate().indexOf("1900")!=-1?"":itemData.getMaintenanceStartDate());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("结束时间", itemData.getMaintenanceEndDate().indexOf("1900")!=-1?"":itemData.getMaintenanceEndDate());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修时长", itemData.getManHour()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("停机时长", itemData.getLongShutdown()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("测试人员", itemData.getTestUser());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("处理情况", itemData.getMaintenanceSituation());
                c_i.add(d);
                c = new MyRepairOrderDetailsContentOtherBase("("+(i+1)+")", c_i);
                otherDataList.add(c);
            }
            return otherDataList;
        }else{
            return null;
        }
    }
    /**
     * 维修费用
     * @param dataList
     * @return
     */
    public static List<MyRepairOrderDetailsContentOtherBase> getRepairFeeList(List<MyRepairOrderDetailsBase.DataBean.RepairFeeListBean> dataList){
        if (dataList!=null) {
            List<MyRepairOrderDetailsContentOtherBase> otherDataList = new ArrayList<>();
            for (int i = 0; i < dataList.size();i++) {
                MyRepairOrderDetailsBase.DataBean.RepairFeeListBean itemData = dataList.get(i);
                MyRepairOrderDetailsContentOtherBase c;
                List<MyRepairOrderDetailsContentOtherBase.DataBean> c_i;
                c_i = new ArrayList<>();
                MyRepairOrderDetailsContentOtherBase.DataBean d;
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("名称", itemData.getFeeName());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修类别", itemData.getFeeType());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("资金来源", itemData.getFundSource());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("计划金额", itemData.getQuotePrice()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("实际金额", itemData.getActualPrice()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("备注", itemData.getRemark()+"");
                c_i.add(d);

                c = new MyRepairOrderDetailsContentOtherBase("("+(i+1)+")", c_i);
                otherDataList.add(c);
            }
            return otherDataList;
        }else{
            return null;
        }
    }

    /**
     * 配件信息
     * @param dataList
     * @return
     */
    public static List<MyRepairOrderDetailsContentOtherBase> getRreplacingFittingList(List<MyRepairOrderDetailsBase.DataBean.ReplacingFittingListBean> dataList){
        if (dataList!=null) {
            List<MyRepairOrderDetailsContentOtherBase> otherDataList = new ArrayList<>();
            for (int i = 0; i < dataList.size();i++) {
                MyRepairOrderDetailsBase.DataBean.ReplacingFittingListBean itemData = dataList.get(i);
                MyRepairOrderDetailsContentOtherBase c;
                List<MyRepairOrderDetailsContentOtherBase.DataBean> c_i;
                c_i = new ArrayList<>();
                MyRepairOrderDetailsContentOtherBase.DataBean d;
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("费用名称", itemData.getName());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("数量", itemData.getQuantity()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("单价", itemData.getPrict()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("维修费用", itemData.getPrice()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("单位", itemData.getUnitName()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("型号", itemData.getModel()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("产地", itemData.getPlaceOriginName()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("厂家", itemData.getSpecification()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("供应商", itemData.getSupplierName()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("序列号", itemData.getSerialNumber()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("出厂时间", itemData.getFactoryTime()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("出厂编号", itemData.getFactoryCode()+"");
                c_i.add(d);

                c = new MyRepairOrderDetailsContentOtherBase("("+(i+1)+")", c_i);
                otherDataList.add(c);
            }
            return otherDataList;
        }else{
            return null;
        }
    }
    /**
     * 验收信息
     * @param dataList
     * @return
     */
    public static List<MyRepairOrderDetailsContentOtherBase> getEvaluationList(List<MyRepairOrderDetailsBase.DataBean.EvaluationListBean> dataList){
        if (dataList!=null) {
            List<MyRepairOrderDetailsContentOtherBase> otherDataList = new ArrayList<>();
            for (int i = 0; i < dataList.size();i++) {
                MyRepairOrderDetailsBase.DataBean.EvaluationListBean itemData = dataList.get(i);
                MyRepairOrderDetailsContentOtherBase c;
                List<MyRepairOrderDetailsContentOtherBase.DataBean> c_i;
                c_i = new ArrayList<>();
                MyRepairOrderDetailsContentOtherBase.DataBean d;
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("工程师", itemData.getEngineerName());
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("分类", itemData.getEngineerName()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("结果", itemData.getServiceEvaluation()+"");
                c_i.add(d);
                d = new MyRepairOrderDetailsContentOtherBase.DataBean("意见", itemData.getEvaluationComment()+"");
                c_i.add(d);

                c = new MyRepairOrderDetailsContentOtherBase("("+(i+1)+")", c_i);
                otherDataList.add(c);
            }
            return otherDataList;
        }else{
            return null;
        }
    }
}
