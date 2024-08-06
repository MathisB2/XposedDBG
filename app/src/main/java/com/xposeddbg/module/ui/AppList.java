package com.xposeddbg.module.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppList extends AppCompatActivity {

    private ArrayList<AppInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.loadInstalledApps();
    }




    private void loadInstalledApps() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        appList = new ArrayList<>();

        for (PackageInfo packageInfo : packages) {
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packageInfo.packageName;
            String version = packageInfo.versionName;
            Drawable icon = packageInfo.applicationInfo.loadIcon(pm);

            appList.add(new AppInfo(appName, packageName, version, icon));
        }
    }

    public ArrayList<AppInfo> getInstalledApps(){
        if(appList.isEmpty()) loadInstalledApps();
        return appList;
    }
}