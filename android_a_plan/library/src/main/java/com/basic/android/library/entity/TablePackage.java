package com.basic.android.library.entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by basic on 2017/3/13.
 */

public final class TablePackage {

    private Map<String, String> mClomumMap;

    public TablePackage() {
        mClomumMap = new HashMap<>();
    }

    public void put(String key, String value) {
        mClomumMap.put(key, value);
    }

    public Map<String, String> getClomumMap() {
        return mClomumMap;
    }

}
