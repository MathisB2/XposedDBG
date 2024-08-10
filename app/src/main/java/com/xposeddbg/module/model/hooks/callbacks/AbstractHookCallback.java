package com.xposeddbg.module.model.hooks.callbacks;

import com.xposeddbg.module.model.hooks.HookInfo;

import de.robv.android.xposed.XC_MethodHook;

public abstract class AbstractHookCallback {
    protected abstract void beforeHook(HookInfo hookInfo);
    protected abstract void afterHook(HookInfo hookInfo);

    public XC_MethodHook getHookCallback(HookInfo hookInfo){
        if(hookInfo.hookBefore && !hookInfo.hookAfter)
            return new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    beforeHook(hookInfo);
                }
            };
        else if(!hookInfo.hookBefore && hookInfo.hookAfter)
            return new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    afterHook(hookInfo);
                }
            };
        else if (hookInfo.hookBefore && hookInfo.hookAfter)
            return new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    beforeHook(hookInfo);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    afterHook(hookInfo);
                }
            };

        return null;
    }
}
