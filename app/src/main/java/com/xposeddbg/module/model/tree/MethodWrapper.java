package com.xposeddbg.module.model.tree;

import com.xposeddbg.module.model.hooks.HookInfo;

public class MethodWrapper {
    private String displayedValue;
    private HookInfo internalData;

    public MethodWrapper(String displayedValue) {
        this.displayedValue = displayedValue;
        this.internalData = null;
    }

    public MethodWrapper(HookInfo internalData) {
        this.displayedValue = internalData.getMethodName();
        this.internalData = internalData;
    }


    public String getDisplayedValue() {
        return displayedValue;
    }

    public HookInfo getInternalData() {
        return internalData;
    }
}
