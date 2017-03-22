package com.basic.android.library.server;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于出去指定数据库中表信息.
 */
public class DatabaseTableRequestHandler implements AndServerRequestHandler {

    private Context mContext;

    public DatabaseTableRequestHandler(Context context) {
        mContext = context;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Log.d("plan", "DatabaseTableRequestHandler");
        DatabaseEntity entity;
        String uri = request.getRequestLine().getUri();
        String key =  Uri.parse(uri).getQueryParameter("database_name");
        if (!TextUtils.isEmpty(key)) {
            entity = getTableResponse(key.trim());
        }  else {
            entity = new DatabaseEntity();
            entity.setCode(BaseEntity.FAILURE_CODE);
            entity.setDataList(new ArrayList<SimpleNameEntity>());
        }
        String result = ParserJson.getSafeJsonStr(entity);
        response.setStatusCode(200);
        response.setEntity(new StringEntity(result, "utf-8"));
    }

    public DatabaseEntity getTableResponse(String param) {
        DatabaseEntity entity = new DatabaseEntity();
        List<SimpleNameEntity> allTable = new ArrayList<>();
        int statusCode = BaseEntity.FAILURE_CODE;
        if (!TextUtils.isEmpty(param)) {
            statusCode = BaseEntity.SUCCESS_CODE;
            String database = param;
            if (!TextUtils.isEmpty(database)) {
                List<String> tableList = DatabaseQueryHelper.queryTableByName(mContext, database.trim());
                if (tableList != null) {
                    for (String table : tableList) {
                        SimpleNameEntity simple = new SimpleNameEntity();
                        simple.setName(table);
                        allTable.add(simple);
                    }
                }
            }
        }
        entity.setCode(statusCode);
        entity.setDataList(allTable);
        return entity;
    }
}
