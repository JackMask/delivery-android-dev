package com.guxingdongli.yizhangguan.util;

/**
 * Created by jackmask on 2018/3/12.
 */

public interface HttpUtileCallBack {
    public abstract void getReturnStr(String returnStr);
    public abstract void getReturnStrFailure(String returnStr);
    public abstract void getErrorStr(String errorStr);
}
