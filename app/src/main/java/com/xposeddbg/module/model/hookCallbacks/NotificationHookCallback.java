package com.xposeddbg.module.model.hookCallbacks;

import com.xposeddbg.module.model.HookInfo;

public class NotificationHookCallback extends AbstractHookCallback{
    @Override
    protected void beforeHook(HookInfo hookInfo){
//        Log.d(String.valueOf(R.string.app_name),"Before method: " + hookInfo.getClassName() + "." + hookInfo.getMethodName()); todo
    }

    @Override
    protected void afterHook(HookInfo hookInfo){
//        Log.d(String.valueOf(R.string.app_name),"After method: " + hookInfo.getClassName() + "." + hookInfo.getMethodName()); todo
    }
}
