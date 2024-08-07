package com.xposeddbg.module.model.filter;

import java.util.ArrayList;

public interface filterApplyCallback<T> {
    default void applyTo(ArrayList<T> list){};
}
