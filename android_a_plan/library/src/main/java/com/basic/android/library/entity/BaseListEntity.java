package com.basic.android.library.entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by basic on 2017/3/14.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class BaseListEntity<T> extends BaseEntity {
    @JsonProperty("data")
    private List<T> mDataList;

    public List<T> getDataList() {
        return mDataList == null ? new ArrayList<T>() : mDataList;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
    }
}
