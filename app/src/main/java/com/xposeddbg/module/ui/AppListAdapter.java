package com.xposeddbg.module.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;

import java.util.ArrayList;

public class AppListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AppInfo> appList;

    public AppListAdapter(Context context, ArrayList<AppInfo> appList) {
        this.context = context;
        this.appList = appList;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.app_list_item, parent, false);
        }

        AppInfo appInfo = appList.get(position);

        ImageView appIcon = convertView.findViewById(R.id.app_list_icon);
        TextView appName = convertView.findViewById(R.id.app_listItem_title);
        TextView packageName = convertView.findViewById(R.id.app_listItem_package);

        appIcon.setImageDrawable(appInfo.getIcon());
        appName.setText(appInfo.getAppName());
        packageName.setText(appInfo.getPackageName());

        return convertView;
    }
}
