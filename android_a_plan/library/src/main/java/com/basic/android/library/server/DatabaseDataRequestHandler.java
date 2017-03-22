package com.basic.android.library.server;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.basic.android.library.entity.BaseEntity;
import com.basic.android.library.entity.DatabaseDataEntity;
import com.basic.android.library.entity.TablePackage;
import com.basic.android.library.util.DatabaseQueryHelper;
import com.basic.android.library.util.ParserJson;
import com.yanzhenjie.andserver.AndServerRequestHandler;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by basic on 2017/3/15.
 */
public class DatabaseDataRequestHandler implements AndServerRequestHandler {

    private Context mContext;

    public DatabaseDataRequestHandler(Context context) {
        mContext = context;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        RequestLine line = request.getRequestLine();
        Uri uri = Uri.parse(line.getUri());
        DatabaseDataEntity entity;
        if (uri != null) {
            String database = uri.getQueryParameter("database");
            String tableName = uri.getQueryParameter("table");
            entity = getDataResponse(database, tableName);
            if (entity != null) {
                response.setStatusCode(200);
                response.setEntity(new StringEntity(ParserJson.getSafeJsonStr(entity), "utf-8"));
                return;
            }
        }
        entity = new DatabaseDataEntity();
        entity.setDataList(new ArrayList<Map<String, String>>());
        entity.setCode(BaseEntity.FAILURE_CODE);
        response.setStatusCode(200);
        response.setEntity(new StringEntity(ParserJson.getSafeJsonStr(entity), "utf-8"));
    }

    public DatabaseDataEntity getDataResponse(String database, String tableName) {
        if (!TextUtils.isEmpty(database) && !TextUtils.isEmpty(tableName)) {
            List<TablePackage> list = DatabaseQueryHelper.queryDatabaseData(mContext, database, tableName);
            List<Map<String, String>> dataList = new ArrayList<>();
            if (list != null) {
                for (TablePackage tablePackage : list) {
                    dataList.add(tablePackage.getClomumMap());
                }
            }
            DatabaseDataEntity entity = new DatabaseDataEntity();
            entity.setCode(BaseEntity.SUCCESS_CODE);
            entity.setDataList(dataList);
            return entity;
        }
        return null;
    }
}
