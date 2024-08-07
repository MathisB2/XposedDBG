package com.xposeddbg.module.model.filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class FilteredList<T> extends ArrayList<T>{
    private ArrayList<T> filteredList;
    public FilterGroup filters;


    private void setupFilterGroup(){
        this.filters = new FilterGroup();
        this.filters.setOnChanged(new filterGroupChangedCallback() {
            @Override
            public void onChanged() {
                applyFilters();
            }
        });
    }

    public FilteredList() {
        this.filteredList = new ArrayList<>();
        this.setupFilterGroup();
    }

    public FilteredList(int initialCapacity) {
        super(initialCapacity);
        this.setupFilterGroup();
    }

    public FilteredList(@NonNull Collection<? extends T> c) {
        super(c);
        this.setupFilterGroup();
    }

    @Override
    public T set(int index, T element) {
        T res = super.set(index, element);
        this.applyFilters();
        return res;
    }

    @Override
    public boolean add(T t) {
        boolean res = super.add(t);
        this.applyFilters();
        return res;
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        this.applyFilters();
    }

    @Override
    public T remove(int index) {
        T res = super.remove(index);
        this.applyFilters();
        return res;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        boolean res = super.remove(o);
        this.applyFilters();
        return res;
    }

    @Override
    public void clear() {
        super.clear();
        this.applyFilters();
    }

    private void applyFilters(){
        this.filteredList.clear();
        this.filteredList.addAll(this);

//        this.filteredList = (ArrayList<T>) this.clone();

        for (Filter f: this.filters) {
            this.filteredList = f.applyTo(this.filteredList);
        }
    }


    public ArrayList<T> getFilteredList(){
        return this.filteredList;
    }

    public int getFilteredSize(){
        return this.filteredList.size();
    }
}
