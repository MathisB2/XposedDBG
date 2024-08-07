package com.xposeddbg.module.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;
import com.xposeddbg.module.model.filter.Filter;
import com.xposeddbg.module.model.filter.FilterGroup;
import com.xposeddbg.module.model.filter.FilteredList;
import com.xposeddbg.module.model.filter.filterApplyCallback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppList extends AppCompatActivity {

    private FilteredList<AppInfo> appList;
    private ListView appListView;
    private AppListAdapter adapter;

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


        this.appList = new FilteredList<>();

        this.setupListView();
        this.setupFilters();
    }

    private void setupFilters(){
        Filter<AppInfo> f = new Filter<>();
        f.setOnApply(new filterApplyCallback<AppInfo>() {
            @Override
            public ArrayList<AppInfo> applyTo(ArrayList<AppInfo> list) {
                Comparator<AppInfo> comparator;
                comparator = Comparator.comparing(AppInfo::getAppName);
                list.sort(comparator);

                adapter.notifyDataSetChanged();
                return list;
            }


        });
        this.appList.filters.add(f);
//        this.filterSet.addFilter(new SortFilter())
//        this.filterSet.addFilter(new SearchFilter())
    }

    private void setupListView(){
        this.appListView = findViewById(R.id.app_listView);

        this.adapter = new AppListAdapter(this,this.getInstalledApps().getFilteredList());
        this.appListView.setAdapter(this.adapter);


        this.appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo selectedApp = appList.getFilteredList().get(position);
                Toast.makeText(AppList.this, selectedApp.getAppName(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadInstalledApps() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        appList = new FilteredList<>();

        for (PackageInfo packageInfo : packages) {
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packageInfo.packageName;
            String version = packageInfo.versionName;
            Drawable icon = packageInfo.applicationInfo.loadIcon(pm);

            appList.add(new AppInfo(appName, packageName, version, icon));
        }
    }

    public FilteredList<AppInfo> getInstalledApps(){
        if(appList.isEmpty()) loadInstalledApps();
        return appList;
    }
}