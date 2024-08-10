package com.xposeddbg.module;


import android.util.Log;

import com.xposeddbg.module.model.AppInfo;
import com.xposeddbg.module.model.hooks.HookInfo;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DexClassExplorer {


    public static List<HookInfo> getMethods(AppInfo appInfo) {
        List<HookInfo> hookableMethods = new ArrayList<>();

        try {
            DexFile dexFile = new DexFile(new File(appInfo.getApkDirectory()));
            Enumeration<String> classNames = dexFile.entries();

            PathClassLoader classLoader = new PathClassLoader(appInfo.getApkDirectory(), ClassLoader.getSystemClassLoader());
            ClassFilter filter = new ClassFilter();

            while (classNames.hasMoreElements()) {
                String className = classNames.nextElement();
                if (className.startsWith(appInfo.getPackageName()) && filter.passFilter(className)) {
                    try {
                        Log.d("xposed", "Class : " + className);

                        Class<?> clazz = classLoader.loadClass(className);  // Charger la classe avec PathClassLoader

                        for (Method method : clazz.getDeclaredMethods()) {
                            if(filter.passFilter(method.getName())){

                                HookInfo hookInfo = new HookInfo(
                                        clazz.getName(),
                                        method.getName(),
                                        method.getParameterTypes()
                                );
                                hookableMethods.add(hookInfo);
                            }

                        }
                    } catch (NoClassDefFoundError | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hookableMethods;
    }

}
