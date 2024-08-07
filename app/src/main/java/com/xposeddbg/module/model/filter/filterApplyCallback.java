package com.xposeddbg.module.model.filter;

import java.util.ArrayList;

public interface filterApplyCallback<T> {
    default ArrayList<T> applyTo(ArrayList<T> list){
        return list;
    };
}
