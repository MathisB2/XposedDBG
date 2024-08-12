package com.xposeddbg.module.model.tree;

import com.xposeddbg.module.model.hooks.HookInfo;

import java.util.ArrayList;
import java.util.List;

import io.github.ikws4.treeview.TreeItem;

public class DexTreeItemHelper {

    public DexTreeItem createItemHierarchy(HookInfo hookInfo){
        return this.createItemHierarchy(hookInfo.getClasspathAsList());
    }

    public DexTreeItem createItemHierarchy(List<String> packages){
        if(packages.isEmpty()) return null;

        if(packages.size() == 1) return new DexTreeItem(packages.get(0), true);

        DexTreeItem root = new DexTreeItem(packages.get(0),true);

        List<String> sublist = new ArrayList<>();
        if(packages.size() == 2)
            sublist.add(packages.get(1));
        else
            sublist.addAll(packages.subList(1,packages.size()));

        DexTreeItem child = this.createItemHierarchy(sublist);

        root.getChildren().add(child);
        return root;
    }



    public void addChildIfNotExists(DexTreeItem root, DexTreeItem child, boolean recursive){
        DexTreeItem existingChild = root.findChildByValue(child.getValue().getDisplayedValue());
        if(existingChild == null)
            root.getChildren().add(child);
        else if (recursive){
            for (TreeItem<MethodWrapper> newChild:child.getChildren()) {
                this.addChildIfNotExists(existingChild, (DexTreeItem) newChild, true);
            }
        }
    }
}
