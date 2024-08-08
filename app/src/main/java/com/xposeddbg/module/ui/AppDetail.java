package com.xposeddbg.module.ui;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;

public class AppDetail extends AppCompatActivity {
    private AppInfo app;
    private ImageView appIcon;
    private TextView appTitle;
    private TextView packageName;
    private TextView versionName;
    private TextView apkPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.app = getIntent().getSerializableExtra("appInfo", AppInfo.class);
        this.appTitle = findViewById(R.id.appDetail_title);
        this.appTitle.setText(this.app.getAppName());

        this.appIcon = findViewById(R.id.appDetail_icon);


        try {
            Drawable icon = getPackageManager().getApplicationIcon(this.app.getPackageName());
            this.app.setIcon(icon);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.appIcon.setImageDrawable(this.app.getIcon());



        this.packageName = findViewById(R.id.appDetail_packageName);
        this.packageName.setText(this.app.getPackageName());

        this.versionName = findViewById(R.id.appDetail_versionName);
        this.versionName.setText(this.app.getVersion());

        this.apkPath = findViewById(R.id.appDetail_apkDirectory);
        this.apkPath.setText("test.apk");
    }
}