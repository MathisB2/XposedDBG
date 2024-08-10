package com.xposeddbg.module.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class AppInfo implements Serializable {
    private String appName;
    private String packageName;
    private String version;
    private String apkDirectory;
    private transient Drawable icon;
    private transient Intent launchIntent;

    public AppInfo(String appName, String packageName, String version, Drawable icon, String apkDirectory) {
        this.appName = appName;
        this.packageName = packageName;
        this.version = version;
        this.icon = icon;
        this.apkDirectory = apkDirectory;
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

    public boolean isLaunchable(){
        return this.launchIntent != null;
    }

    public Intent getLaunchIntent() {
        return launchIntent;
    }

    public void setLaunchIntent(Intent launchIntent) {
        if (launchIntent != null) launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.launchIntent = launchIntent;
    }


    public String getApkDirectory() {
        return apkDirectory;
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
