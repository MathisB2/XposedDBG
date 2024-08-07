package com.xposeddbg.module.model.filter;

import java.util.ArrayList;

public class Filter<T> {
    public boolean enabled;
    private filterApplyCallback<T> onFilterApplyCallback;


    public Filter() {
        this.enabled = true;
    }


    public void setOnApply(filterApplyCallback<T> callback){
        this.onFilterApplyCallback = callback;
    }

    public void applyTo(ArrayList<T> list) {
        if(this.enabled) onFilterApplyCallback.applyTo(list);
    }


}
