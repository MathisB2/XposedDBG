package com.xposeddbg.module.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;
import com.xposeddbg.module.model.filter.Filter;
import com.xposeddbg.module.model.filter.FilteredList;
import com.xposeddbg.module.model.filter.filterApplyCallback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private FilteredList<AppInfo> appList;
    private ListView appListView;
    private AppListAdapter adapter;
    private Filter<AppInfo> searchFilter;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchBar = (SearchView) searchItem.getActionView();
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchFilter.setOnApply(new filterApplyCallback<AppInfo>() {
                    @Override
                    public void applyTo(ArrayList<AppInfo> list) {
                        onSearchFilterChanged(list, newText);
                    }

                });
                appList.applyFilters();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setupFilters(){
        Filter<AppInfo> sortFilter = new Filter<>();
        sortFilter.setOnApply(new filterApplyCallback<AppInfo>() {
            @Override
            public void applyTo(ArrayList<AppInfo> list) {
                Comparator<AppInfo> comparator;
                comparator = Comparator.comparing(AppInfo::getAppName);
                list.sort(comparator);

                adapter.notifyDataSetChanged();
            }


        });
        this.appList.filters.add(sortFilter);

        this.searchFilter = new Filter<>();
        searchFilter.setOnApply(new filterApplyCallback<AppInfo>() {
            @Override
            public void applyTo(ArrayList<AppInfo> list) {
                onSearchFilterChanged(list, "");
            }
        });
        this.appList.filters.add(this.searchFilter);
    }

    private void onSearchFilterChanged(ArrayList<AppInfo> list, String query){
        for (int i = 0; i < list.size(); i++) {
            AppInfo currentApp = list.get(i);
            if (!currentApp.getAppName().toLowerCase().contains(query.toLowerCase()) &&
                    !currentApp.getPackageName().toLowerCase().contains(query.toLowerCase())) {
                list.remove(i);
                i--;
            }
        }
    }

    private void setupListView(){
        this.appListView = findViewById(R.id.app_listView);

        this.adapter = new AppListAdapter(this,this.getInstalledApps().getFilteredList());
        this.appListView.setAdapter(this.adapter);


        this.appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo selectedApp = appList.getFilteredList().get(position);

                Intent startIntent = new Intent(AppListActivity.this, AppDetailActivity.class);
                startIntent.putExtra("appInfo",selectedApp);
                startActivity(startIntent);
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
            String apkDirectory = packageInfo.applicationInfo.sourceDir;

            appList.add(new AppInfo(appName, packageName, version, icon, apkDirectory));
        }
    }

    public FilteredList<AppInfo> getInstalledApps(){
        if(appList.isEmpty()) loadInstalledApps();
        return appList;
    }
}