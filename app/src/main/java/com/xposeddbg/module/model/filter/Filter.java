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

    public ArrayList<T> applyTo(ArrayList<T> list) {
        if(!this.enabled) return list;
        return onFilterApplyCallback.applyTo(list);
    }


}
