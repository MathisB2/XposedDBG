package com.xposeddbg.module.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class AppInfo implements Serializable {
    private String appName;
    private String packageName;
    private String version;
    private transient Drawable icon;

    public AppInfo(String appName, String packageName, String version, Drawable icon) {
        this.appName = appName;
        this.packageName = packageName;
        this.version = version;
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersion(){
        return version;
    }

    public Drawable getIcon() {
        return icon;
    }


    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name:   \t"+this.getAppName()+"\n");
        sb.append("Package:\t"+this.getPackageName()+"\n");
        sb.append("Version:\t"+this.getVersion()+"\n");
        return sb.toString();
    }
}
