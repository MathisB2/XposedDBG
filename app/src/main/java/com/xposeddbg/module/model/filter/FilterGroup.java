package com.xposeddbg.module.model.filter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class FilterGroup extends ArrayList<Filter>{
    private filterGroupChangedCallback onFilterGroupChanged;

    public FilterGroup() {
      super();
    }

    public FilterGroup(int initialCapacity) {
        super(initialCapacity);
    }

    public FilterGroup(@NonNull Collection<? extends Filter> c) {
        super(c);
    }

    @Override
    public Filter set(int index, Filter element) {
        Filter res = super.set(index, element);
        this.onChanged();
        return res;
    }

    @Override
    public boolean add(Filter t) {
        boolean res = super.add(t);
        this.onChanged();
        return res;
    }

    @Override
    public void add(int index, Filter element) {
        super.add(index, element);
        this.onChanged();
    }

    @Override
    public Filter remove(int index) {
        Filter res = super.remove(index);
        this.onChanged();
        return res;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        boolean res = super.remove(o);
        this.onChanged();
        return res;
    }

    @Override
    public void clear() {
        super.clear();
        this.onChanged();
    }

    private void onChanged(){
        this.onFilterGroupChanged.onChanged();
    }

    public void setOnChanged(filterGroupChangedCallback callback){
        this.onFilterGroupChanged = callback;
    }
}
