package com.basic.android.library.server;

import android.content.Context;
import android.util.Log;

import com.basic.android.library.entity.BaseEntity;
import com.basic.android.library.entity.DataAndTableEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by basic on 2017/3/18.
 */
public class DataAndTableRequestHandler implements AndServerRequestHandler {

    private Context mContext;

    DataAndTableRequestHandler(Context context) {
        mContext = context;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Log.d("plan", "DataAndTableRequestHandler");
        DataAndTableEntity entity = getDatabaseAndTable();
        if (entity == null) {
            entity = new DataAndTableEntity();
            entity.setCode(BaseEntity.FAILURE_CODE);
            entity.setDataList(new ArrayList<Map<String, List<SimpleNameEntity>>>());
        }
        response.setStatusCode(200);
        response.setEntity(new StringEntity(ParserJson.getSafeJsonStr(entity), "utf-8"));
    }

    public DataAndTableEntity getDatabaseAndTable() {
        List<File> databaseList = DatabaseQueryHelper.queryDatabaseList(mContext);
        if (databaseList != null) {
            List<Map<String, List<SimpleNameEntity>>> allData = new ArrayList<>();
            for (File file : databaseList) {
                List<String> tableList = DatabaseQueryHelper.queryTableByName(mContext, file.getName());
                Map<String, List<SimpleNameEntity>> map = new HashMap<>();
                if (databaseList != null) {
                    List<SimpleNameEntity> list = new ArrayList<>();
                    for (String tableName : tableList) {
                        SimpleNameEntity entity = new SimpleNameEntity();
                        entity.setName(tableName);
                        list.add(entity);
                    }
                    map.put(file.getName(), list);
                }
                allData.add(map);
            }
            DataAndTableEntity entity = new DataAndTableEntity();
            entity.setCode(BaseEntity.SUCCESS_CODE);
            entity.setDataList(allData);
            return entity;
        }
        return null;
    }


}
