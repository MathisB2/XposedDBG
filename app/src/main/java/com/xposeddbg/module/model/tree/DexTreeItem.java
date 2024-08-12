package com.xposeddbg.module.model.tree;

import androidx.annotation.NonNull;

import com.xposeddbg.module.model.hooks.HookInfo;

import java.util.List;

import io.github.ikws4.treeview.TreeItem;

public class DexTreeItem extends TreeItem<MethodWrapper> {
    public DexTreeItem(@NonNull String displayedValue) {
        this(displayedValue, false);
    }

    public DexTreeItem(@NonNull String displayedValue, boolean expandable) {
        super(new MethodWrapper(displayedValue)  , expandable);
    }

    public DexTreeItem(@NonNull HookInfo hookInfo, boolean expandable) {
        super(new MethodWrapper(hookInfo)  , expandable);
    }

    public DexTreeItem findChildByValue(String value){
        for (TreeItem<MethodWrapper> child:this.getChildren()) {
            if(child.getValue().getDisplayedValue().equals(value)) return (DexTreeItem) child;
        }

        return null;
    }
}
