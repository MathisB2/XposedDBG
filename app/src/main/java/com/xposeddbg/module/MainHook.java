package com.xposeddbg.module;


import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import dalvik.system.DexFile;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class MainHook implements IXposedHookLoadPackage {

    private final String debugTag = "XposedDBG";
    private ClassFilter filter;

    public MainHook() {
        this.filter = new ClassFilter();
    }

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Log.d(debugTag, "Loaded app: "+lpparam.packageName);
        Log.d(debugTag, lpparam.appInfo.sourceDir);
        Log.d(debugTag, "");
        try {
            ClassLoader classLoader = lpparam.classLoader;
            hookAllMethodsInPackage(lpparam, classLoader, lpparam.packageName);
        } catch (Exception e) {
            Log.e(debugTag,e.getMessage());
        }
    }


    private void hookAllMethodsInPackage(XC_LoadPackage.LoadPackageParam lpparam,ClassLoader classLoader, String packageName) throws Exception {
        DexFile dexFile = new DexFile(lpparam.appInfo.sourceDir);
        Enumeration<String> entries = dexFile.entries();

        while (entries.hasMoreElements()) {
            String className = entries.nextElement();
            if (className.startsWith(packageName)) {
                try {
                    if(filter.passFilter(className)){
                        Class<?> clazz = Class.forName(className, false, classLoader);
                        hookAllMethodsInClass(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    Log.e(debugTag,"Class not found: " + className);
                }
            }
        }
    }

    private void hookAllMethodsInClass(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        Log.d(debugTag, "+ Class : "+clazz);

        for (final Method method : methods) {

            Class<?>[] parameters = method.getParameterTypes();
            StringBuilder sb = new StringBuilder();
            for(Class<?> parameter: parameters){
                sb.append(parameter);
            }

            Log.d(debugTag, "| + "+method.getName()+"("+sb.toString()+")");

/*
            try {
                Object[] parameterTypesAndCallback = new Object[method.getParameterTypes().length + 1];
                System.arraycopy(method.getParameterTypes(), 0, parameterTypesAndCallback, 0, method.getParameterTypes().length);
                parameterTypesAndCallback[parameterTypesAndCallback.length - 1] = new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d(debugTag,"Method called: " + method.getDeclaringClass().getName() + "." + method.getName());
                    }
                };
                XposedHelpers.findAndHookMethod(clazz, method.getName(), parameterTypesAndCallback);
            } catch (Throwable t) {
                Log.e(debugTag, "Failed to hook method: " + method.getName() + " in class " + clazz.getName() + " with error: " + t.getMessage());
            }*/
        }
        Log.d(debugTag, "");

    }
}