package com.guxingdongli.yizhangguan.controller.db;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/3 0003.
 */
 
public class PMyInformation {

    private static final String CODE_UID = "uid";
    private static final String CODE_HEADIMG = "headImg";
    private static final String CODE_NAME = "name";
    private static final String CODE_REALNAME = "realName";
    private static final String CODE_LOGINNAME = "loginName";
    private static final String CODE_EMAIL = "email";
    private static final String CODE_MOBILEPHONE = "mobilePhone";
    private static final String CODE_USERTYPE = "userType";
    private static final String CODE_ISADMIN = "isAdmin";
    private static final String CODE_ISENGINEER = "isEngineer";
    private static final String CODE_DEPARTMENTNAME = "departmentName";
    private static final String CODE_TOKENDATA = "tokenData";
    private static final String CODE_ONAPP = "onApp";
    private static final String CODE_HOSPITAL = "CODE_HOSPITAL";
    private static final String CODE_TOKEN_TYPE = "token_type";
    private static final String CODE_ISAPP_TYPE = "isAppType";
    private static final String CODE_HOSPITAL_LOGIN_NAME = "hospitalLoginName";
    private static final String CODE_SUPPLIER_LOGIN_NAME = "supplierLoginName";




    private String uid;
    private String headImg;
    private String name;
    private String realName;
    private String loginName;
    private String email;
    private String mobilePhone;
    private String userType;
    private boolean isAdmin;
    private boolean isEngineer;
    private String departmentName;
    private String tokenData;
    private String token_hospital;
    private String token_type;
    private String hospitalLoginName;
    private String supplierLoginName;
    private boolean onApp;
    private boolean isAppType;



        private Context context;
        private SharedPreferences cookiePrefs;


        public PMyInformation(Context paramContext) {
            this.context = paramContext;
            load();
        }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SharedPreferences getCookiePrefs() {
        return cookiePrefs;
    }

    public void setCookiePrefs(SharedPreferences cookiePrefs) {
        this.cookiePrefs = cookiePrefs;
    }
        public void load() {
            this.cookiePrefs = this.context.getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
            if (this.cookiePrefs != null) {
                this.uid = this.cookiePrefs.getString(CODE_UID,null);
                this.headImg = this.cookiePrefs.getString(CODE_HEADIMG,null);
                this.name = this.cookiePrefs.getString(CODE_NAME,null);
                this.realName = this.cookiePrefs.getString(CODE_REALNAME,null);
                this.loginName = this.cookiePrefs.getString(CODE_LOGINNAME,null);
                this.email = this.cookiePrefs.getString(CODE_EMAIL,null);
                this.mobilePhone = this.cookiePrefs.getString(CODE_MOBILEPHONE,null);
                this.userType = this.cookiePrefs.getString(CODE_USERTYPE,null);
                this.isAdmin = this.cookiePrefs.getBoolean(CODE_ISADMIN,false);
                this.isEngineer = this.cookiePrefs.getBoolean(CODE_ISENGINEER,false);
                this.departmentName = this.cookiePrefs.getString(CODE_DEPARTMENTNAME,null);
                this.tokenData = this.cookiePrefs.getString(CODE_TOKENDATA,null);
                this.token_type = this.cookiePrefs.getString(CODE_TOKEN_TYPE,null);
                this.token_hospital = this.cookiePrefs.getString(CODE_HOSPITAL,null);
                this.onApp = this.cookiePrefs.getBoolean(CODE_ONAPP,false);
                this.isAppType = this.cookiePrefs.getBoolean(CODE_ISAPP_TYPE,true);
                this.hospitalLoginName = this.cookiePrefs.getString(CODE_HOSPITAL_LOGIN_NAME,"");
                this.supplierLoginName = this.cookiePrefs.getString(CODE_SUPPLIER_LOGIN_NAME,"");
            }
        }
        public void clear(){
            if (this.cookiePrefs != null) {
                this.cookiePrefs.edit().clear();
                this.cookiePrefs.edit().commit();
            }
        }

    public String getHospitalLoginName() {
        return hospitalLoginName;
    }

    public void setHospitalLoginName(String hospitalLoginName) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.hospitalLoginName = hospitalLoginName;
        this.cookiePrefs.edit()
                .putString(CODE_HOSPITAL_LOGIN_NAME, this.hospitalLoginName).commit();
    }

    public String getSupplierLoginName() {
        return supplierLoginName;
    }

    public void setSupplierLoginName(String supplierLoginName) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.supplierLoginName = supplierLoginName;
        this.cookiePrefs.edit()
                .putString(CODE_SUPPLIER_LOGIN_NAME, this.supplierLoginName).commit();
    }

    public boolean isAppType() {
        return isAppType;
    }

    public void setAppType(boolean appType) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        isAppType = appType;
        this.cookiePrefs.edit()
                .putBoolean(CODE_ISAPP_TYPE, this.isAppType).commit();
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.token_type = token_type;
        this.cookiePrefs.edit()
                .putString(CODE_TOKEN_TYPE, this.token_type).commit();

    }

    public String getToken_hospital() {
        return token_hospital;
    }

    public void setToken_hospital(String token_hospital) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.token_hospital = token_hospital;
        this.cookiePrefs.edit()
                .putString(CODE_HOSPITAL, this.token_hospital).commit();

    }

    public void setUid(String uid) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.uid = uid;
        this.cookiePrefs.edit()
                .putString(CODE_UID, this.uid).commit();
    }

    public void setHeadImg(String headImg) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.headImg = headImg;
        this.cookiePrefs.edit()
                .putString(CODE_HEADIMG, this.headImg).commit();

    }

    public void setName(String name) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.name = name;
        this.cookiePrefs.edit()
                .putString(CODE_NAME, this.name).commit();

    }

    public void setRealName(String realName) {
        this.cookiePrefs = this.context
            .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.realName = realName;
        this.cookiePrefs.edit()
                .putString(CODE_REALNAME, this.realName).commit();
    }

    public void setLoginName(String loginName) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.loginName = loginName;
        this.cookiePrefs.edit()
                .putString(CODE_LOGINNAME, this.loginName).commit();

    }

    public void setEmail(String email) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.email = email;
        this.cookiePrefs.edit()
                .putString(CODE_EMAIL, this.email).commit();

    }

    public void setMobilePhone(String mobilePhone) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.mobilePhone = mobilePhone;
        this.cookiePrefs.edit()
                .putString(CODE_MOBILEPHONE, this.mobilePhone).commit();

    }

    public void setUserType(String userType) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.userType = userType;
        this.cookiePrefs.edit()
                .putString(CODE_USERTYPE, this.userType).commit();

    }

    public void setAdmin(boolean admin) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.isAdmin = admin;
        this.cookiePrefs.edit()
                .putBoolean(CODE_ISADMIN, this.isAdmin).commit();

    }

    public void setEngineer(boolean engineer) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.isEngineer = engineer;
        this.cookiePrefs.edit()
                .putBoolean(CODE_ISENGINEER, this.isEngineer).commit();

    }

    public void setDepartmentName(String departmentName) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.departmentName = departmentName;
        this.cookiePrefs.edit()
                .putString(CODE_DEPARTMENTNAME, this.departmentName).commit();

    }

    public void setTokenData(String tokenData) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.tokenData = tokenData;
        this.cookiePrefs.edit()
                .putString(CODE_TOKENDATA, this.tokenData).commit();

    }

    public void setOnApp(boolean onApp) {
        this.cookiePrefs = this.context
                .getSharedPreferences("MyInformation", Context.MODE_PRIVATE);
        this.onApp = onApp;
        this.cookiePrefs.edit()
                .putBoolean(CODE_ONAPP, this.onApp).commit();

    }

    public String getUid() {
        return uid;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getName() {
        return name;
    }

    public String getRealName() {
        return realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getUserType() {
        return userType;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isEngineer() {
        return isEngineer;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getTokenData() {
        return tokenData;
    }

    public boolean isOnApp() {
        return onApp;
    }
}
