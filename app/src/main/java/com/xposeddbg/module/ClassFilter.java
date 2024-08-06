package com.xposeddbg.module;

public class ClassFilter {

    public ClassFilter() {
    }

    public boolean passFilter(String className){
        if(className.contains("$")) return false;

        return true;
    }
}
