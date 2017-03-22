package com.basic.android.library.entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by basic on 2017/3/14.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class BaseEntity {

    public static final int SUCCESS_CODE = 0;
    public static final int FAILURE_CODE = -1;
    /**
     * 0 success,
     * -1 failure
     */
    @JsonProperty("code")
    private int mCode;

    @JsonProperty("message")
    private String mMessage;

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }
}
