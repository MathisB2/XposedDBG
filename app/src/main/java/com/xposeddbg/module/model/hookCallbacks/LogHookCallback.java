package com.xposeddbg.module.model.hookCallbacks;

import android.util.Log;

import com.xposeddbg.module.R;
import com.xposeddbg.module.model.HookInfo;

public class LogHookCallback extends AbstractHookCallback{
    @Override
    protected void beforeHook(HookInfo hookInfo){
        Log.d(String.valueOf(R.string.app_name),"Before method: " + hookInfo.getClassName() + "." + hookInfo.getMethodName());
    }

    @Override
    protected void afterHook(HookInfo hookInfo){
        Log.d(String.valueOf(R.string.app_name),"After method: " + hookInfo.getClassName() + "." + hookInfo.getMethodName());
    }
}
