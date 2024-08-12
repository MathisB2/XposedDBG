package com.xposeddbg.module.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xposeddbg.module.DexClassExplorer;
import com.xposeddbg.module.R;
import com.xposeddbg.module.model.AppInfo;
import com.xposeddbg.module.model.hooks.HookInfo;
import com.xposeddbg.module.model.tree.DexTreeAdapter;
import com.xposeddbg.module.model.tree.DexTreeItem;
import com.xposeddbg.module.model.tree.DexTreeItemHelper;

import java.util.ArrayList;
import java.util.List;

import io.github.ikws4.treeview.TreeView;

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
        setTitle(currentApp.getAppName());







        this.hookableMethods = (ArrayList<HookInfo>) DexClassExplorer.getMethods(currentApp);


        TreeView treeView = findViewById(R.id.appTree_treeView);
        DexTreeAdapter adapter = new DexTreeAdapter();
        treeView.setAdapter(adapter);

        DexTreeItem packageRoot = new DexTreeItem("root",true);

        DexTreeItemHelper helper = new DexTreeItemHelper();

        for (HookInfo method:this.hookableMethods) {

            DexTreeItem child = helper.createItemHierarchy(method);
            if (child != null)
                helper.addChildIfNotExists(packageRoot,child,true);
        }

        packageRoot.setExpanded(true);
        adapter.setRoot(packageRoot, false);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}