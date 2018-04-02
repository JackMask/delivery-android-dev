package com.guxingdongli.yizhangguan.util;

/**
 * Created by jackmask on 2018/3/12.
 */

public class Constant  {
    //域名
    public static  final String DOMAIN_NAME = "http://www.yizhangguan.cn/services/v1/";
    //登录
    public static  final String LOGIN = "login";
    //注册
    public static  final String REGISTER = "register";

    //根据用户所在地区获取医院信息，医院、供应商端都可调用（已实现）
    public static  final String GETHOSPITALLIST = "GetHospitalList";
    //获取App主界面功能模块信息，医院、供应商端都可调用（已实现）
    public static  final String HOME = "home";
    //获取验证码接口，医院、供应商端都可调用（已实现）
    public static  final String GETVERCODE = "getvercode";
    //找回密码，医院、供应商端都可调用（已实现）
    public static  final String FINDPWD = "findpwd";
    //工作提醒
    public static  final String GETWORKTIP = "GetWorkTip";
    //站内信息
    public static  final String SEARCHMESSAGE = "SearchMessage";
    //退出系统，医院、供应商端都可调用（已实现）
    public static  final String SIGNOUT = "signout";
    //修改密码，医院、供应商端都可调用（已实现）
    public static  final String CHANGEPWD = "changepwd";
    //修改密码，医院、供应商端都可调用（已实现）
    public static  final String UPDATEUSER = "updateuser";
    //验收入库-列表（医院版）
    public static  final String SEARCHPURCHASEORDERLIST = "SearchPurchaseOrderList";
    //验收入库-订单详情（医院版）
    public static  final String GETPURCHASEORDER = "GetPurchaseOrder";
    //验收入库（医院版）
    public static  final String HOSPITALACCEPURCHASEORDER = "HospitalAccePurchaseOrder";
    //维修管理-列表（医院版）
    public static  final String SEARCHREPAIRORDERLIST= "SearchRepairOrderList";
    //选择设备报修 进入到选择报修设备列表（资产报修）&&设备动态-列表页（医院版）
    public static  final String SEARCHMATERIAL = "SearchMaterial";
    //手动报修（添加报修单）
    public static  final String GETREPAIRNUMBER = "GetRepairNumber";
    //上传文件接口，可用于拍照上传，医院、供应商端都可调用（已实现）
    public static  final String UPLOADFILE = "UploadFile";
    //医院通用报修申请接口，可以报修医疗设备、非医疗设备、其它物资等 医院端调用（已实现）
    public static  final String REPAIRAPPLY = "RepairApply";
    //扫码计时接口，院内或院外工程师处理时调用此接口，扫码成功后，app界面按钮需要置灰，不能再次点击（已实现）
    public static  final String SCANCODETIMING = "ScanCodeTiming";
    //维修管理-维修单详情（通用）
    public static  final String GETREPAIRALLDETAILS = "GetRepairAllDetails";
    //获取字典信息，医院、供应商端都可调用（已实现)
    public static  final String GETDICTIONARYLIST = "GetDictionaryList";
    //新增字典值接口,Other值里面返回了新增字典的ID和guid，医院、供应商端都可调用（已实现
    public static  final String ADDDICTIONARY = "AddDictionary";
    //列表页点击查看
    public static  final String GETGUIDCHECKIN = "GetGuidCheckIn";
    //扫码登记（维修处理）接口，点击按钮或扫码直接调用此接口，前提是已经维修计时了
    public static  final String SCANCODECHECKIN = "ScanCodeCheckIn";
    //维修验收列表点击进入
    public static  final String GETGUIDREAPIRACCEPTANCE = "GetGuidReapirAcceptance";
    //维修单验收
    public static  final String SCANCODEREAPIRACCEPTANCE = "ScanCodeReapirAcceptance";
    //查看维修工程师处理信息 页面上处理情况
    public static  final String GETREPAIRPROCESSLIST = "GetRepairProcessList";
    //查看配件明细 页面上更换零部件
    public static  final String GETREPLACINGFITTINGLIST = "GetReplacingFittingList";
    //维修费用明细 页面上明细
    public static  final String GETGYREPAIRFEELIST = "GetGyRepairfeeList";
    //设备动态-修改设备状态（医院版）
    public static  final String UPDQTEMATERIAL = "UpdqteMaterial";
    //设备动态-设备详情（医院版）
    public static  final String GETMATERIAL = "GetMaterial";
    //录入订单
    public static  final String ADDPURCHASEORDERQUANTITY = "AddPurchaseOrderQuantity";
    //基础数据-我的基础数据（供应商版）
    public static  final String SEARCHBASICDATALIST = "SearchBasicDataList";
    //基础数据-医院基础数据（供应商版）
    public static  final String SEARCHBASICHOSPITDATA = "SearchBasicHospitData";
    //基础数据-推送础数据（供应商版）
    public static  final String GETPUSHBASICDATA = "GetPushBasicData";
    //  基础数据-推送时选择的医院（供应商版）
    public static  final String SEARCHSUPPLIERHOSPITALRELATION = "SearchSupplierHospitalRelation";
    // 添加推送数据到医院基础数据（已实现）
    public static  final String GETPUSHADD = "GetPushAdd";
    // 对账单管理-对账单请求汇总（供应商版）
    public static  final String SEARCHRECONCILIATION = "SearchReconciliation";
    // 对账单管理-已请求账单汇总（供应商版）
    public static  final String SEARCHRECONCILIATIONRECORD = "SearchReconciliationRecord";
    // 对账单管理-添加账单（供应商版)
    public static  final String ADDRECONCILIATION = "AddReconciliation";
    // 供应商确认对账记录 仅供应商端程序调用 （已实现）
    public static  final String BILLCONFIRM = "BillConfirm";
    // 供应商确认对账记录 仅供应商端程序调用 （已实现）
    public static  final String GETSPAREORDER = "GetSpareOrder";
    // 备货单
    public static  final String SEARCHSPAREORDERLIST = "SearchSpareOrderList";
    // 备货单
    public static  final String ADDSPAREORDER = "AddSpareOrder";
    // 扫码进入 报修验收（医院端）
    public static  final String GETQRPURCHASEORDER = "GetQrPurchaseOrder";
    // 扫码进入 扫码报修（医院端）
    public static  final String SCANCODEREPAIR = "ScanCodeRepair";
    // 扫码进入  维修管理-扫码功能（医院版）
    public static  final String SCANCODEACTION = "ScanCodeAction";
    // 扫码进入  报修验收-验收维修单（医院版）
    public static  final String GETNUMBERREAPIRACCEPTANCE = "GetNumberReapirAcceptance";

    // 扫码进入  维修管理-工程师维修登记（医院版）
    public static  final String GETNUMBERCHECKIN = "GetNumberCheckIn";
    // 生成时供应商获取默认开始时间 仅供应商端程序调用（已实现）
    public static  final String GETLASTTIME = "GetLastTime";
    // 获取注册协议地址
    public static  final String GETREGISTAGREEURL = "GetRegistAgreeUrl";
    // 供应商取消订单 供应商端调用（已实现）
    public static  final String CANCELPURCHASEORDER = "CancelPurchaseOrder";
}
