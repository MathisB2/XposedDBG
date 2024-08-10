package com.xposeddbg.module.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xposeddbg.module.DexClassExplorer;
import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;
import com.xposeddbg.module.model.hooks.HookInfo;

import java.util.ArrayList;

public class AppTreeActivity extends AppCompatActivity {
    private ArrayList<HookInfo> hookableMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_tree);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        AppInfo currentApp = getIntent().getSerializableExtra("appInfo", AppInfo.class);

        this.hookableMethods = (ArrayList<HookInfo>) DexClassExplorer.getMethods(currentApp);

    }
}