package com.xposeddbg.module.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;

import java.io.DataOutputStream;
import java.io.IOException;

public class AppDetailActivity extends AppCompatActivity {
    private AppInfo currentApp;
    private ImageView appIcon;
    private TextView appTitle;

    private Button launchButton;
    private Button stopButton;

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


        this.currentApp = getIntent().getSerializableExtra("appInfo", AppInfo.class);
        this.appTitle = findViewById(R.id.appDetail_title);
        this.appTitle.setText(this.currentApp.getAppName());

        this.appIcon = findViewById(R.id.appDetail_icon);



        PackageManager pm = getPackageManager();
        try {
            Drawable icon = pm.getApplicationIcon(this.currentApp.getPackageName());
            this.currentApp.setIcon(icon);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.appIcon.setImageDrawable(this.currentApp.getIcon());



        Intent launchIntent = pm.getLaunchIntentForPackage(this.currentApp.getPackageName());
        this.currentApp.setLaunchIntent(launchIntent);

        this.launchButton = findViewById(R.id.appDetail_startButton);
        this.launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchApp();
            }
        });
        this.launchButton.setEnabled(this.currentApp.isLaunchable());
        this.launchButton.setAllowClickWhenDisabled(true);

        this.stopButton = findViewById(R.id.appDetail_stopButton);
        this.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killApp();
            }
        });


        this.packageName = findViewById(R.id.appDetail_packageName);
        this.packageName.setText(this.currentApp.getPackageName());

        this.versionName = findViewById(R.id.appDetail_versionName);
        this.versionName.setText(this.currentApp.getVersion());

        this.apkPath = findViewById(R.id.appDetail_apkDirectory);
        this.apkPath.setText(this.currentApp.getApkDirectory());




        Button tmpButton = findViewById(R.id.tmp_button);
        tmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(AppDetailActivity.this, AppTreeActivity.class);
                startIntent.putExtra("appInfo",currentApp);
                startActivity(startIntent);
            }
        });
    }




    private void launchApp() {
        if (this.currentApp.isLaunchable()) {
            startActivity(this.currentApp.getLaunchIntent());
        } else {
            Toast.makeText(this, "Unable to launch this app", Toast.LENGTH_SHORT).show();
        }
    }


    private void killApp() {
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(su.getOutputStream());
            os.writeBytes("am force-stop " + this.currentApp.getPackageName() + "\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            su.waitFor();
            Toast.makeText(this, "Process killed successfully", Toast.LENGTH_SHORT).show();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to stop this app", Toast.LENGTH_SHORT).show();
        }
    }
}