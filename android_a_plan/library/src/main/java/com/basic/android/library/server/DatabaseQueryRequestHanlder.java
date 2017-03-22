package com.basic.android.library.server;

import android.content.Context;
import android.util.Log;

import com.basic.android.library.entity.BaseEntity;
import com.basic.android.library.entity.DatabaseEntity;
import com.basic.android.library.entity.SimpleNameEntity;
import com.basic.android.library.util.DatabaseQueryHelper;
import com.basic.android.library.util.ParserJson;
import com.yanzhenjie.andserver.AndServerRequestHandler;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于查询数据库数据接口.
 */
public class DatabaseQueryRequestHanlder implements AndServerRequestHandler {

    private Context mContext;

    DatabaseQueryRequestHanlder(Context context) {
        mContext = context;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        //处于线程之中.
        Log.d("plan", "DatabaseQueryRequestHanlder");
        response.setEntity(new StringEntity(getDataResponse(), "utf-8"));
        response.setStatusCode(200);
    }

    public String getDataResponse() {
        DatabaseEntity entity = new DatabaseEntity();
        int code = BaseEntity.FAILURE_CODE; //
        List<File> list = DatabaseQueryHelper.queryDatabaseList(mContext);
        List<SimpleNameEntity> databaseList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (File file : list) {
                String name = file.getName();
                SimpleNameEntity simple = new SimpleNameEntity();
                simple.setName(name);
                databaseList.add(simple);
            }
            code = BaseEntity.SUCCESS_CODE;
        }
        entity.setCode(code);
        entity.setDataList(databaseList);
        String result = ParserJson.getSafeJsonStr(entity);
        return result;
    }
}
