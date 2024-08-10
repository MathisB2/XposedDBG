package com.xposeddbg.module.model.hooks;

import com.xposeddbg.module.model.hooks.callbacks.LogHookCallback;
import com.xposeddbg.module.model.hooks.callbacks.NotificationHookCallback;
import com.xposeddbg.module.model.hooks.callbacks.ToastHookCallback;

import java.io.Serializable;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;

public class HookInfo implements Serializable {
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private HookType hookType;
    public boolean hookBefore;
    public boolean hookAfter;


    public HookInfo(String className, String methodName, Class<?>[] parameterTypes) {
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.hookType = HookType.Log;
        this.hookBefore = false;
        this.hookAfter = false;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }


    public Object[] getParameterTypesAndCallback(){
        Object[] parameterTypesAndCallback = new Object[this.getParameterTypes().length + 1];
        System.arraycopy(this.getParameterTypes(), 0, parameterTypesAndCallback, 0, this.getParameterTypes().length);
        parameterTypesAndCallback[parameterTypesAndCallback.length - 1] = this.getHookCallback();
        return parameterTypesAndCallback;
    }


    private XC_MethodHook getHookCallback(){
        if(this.hookType == HookType.Notification) return (new NotificationHookCallback()).getHookCallback(this);
        else if (this.hookType == HookType.Toast) return (new ToastHookCallback()).getHookCallback(this);
        return (new LogHookCallback().getHookCallback(this));
    }


    public void setHookType(HookType hookType) {
        this.hookType = hookType;
    }

    @Override
    public String toString() {
        return  "+ HookInfo :" + "\n" +
                "| className : " + className + "\n" +
                "| methodName : " + methodName + "\n" +
                "| parameterTypes : " + Arrays.toString(parameterTypes) + "\n" +
                "+-\n";
    }
}
