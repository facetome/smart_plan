package com.basic.android.library.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.basic.android.library.entity.TablePackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于进行数据库查询.
 */
public class DatabaseQueryHelper {

    /**
     * 查询当前应用下的所有的数据库.
     *
     * @param context {@link Context}
     * @return all database
     */
    public static List<File> queryDatabaseList(Context context) {
        File systemFileRoot = context.getFilesDir(); // data/data/package_name/files
        if (systemFileRoot != null) {
            File root = systemFileRoot.getParentFile();
            File database = new File(root, "databases");
            if (database.exists() && database.isDirectory()) {
                File[] files = database.listFiles();
                List<File> all = new ArrayList<>();
                for (File file : files) {
                    if (!TextUtils.isEmpty(file.getName()) && file.canRead()) {
                        if (!file.getName().contains("journal")) { //该数据库是记录查找操作的回滚库.
                            all.add(file);
                        }
                    }
                }
                return all;
            }
        }
        return null;
    }

    /**
     * 查询数据库中所有的表.
     * 不能通过获取data/data/package_name/databases/xxx文件的方式获取数据库表.
     *
     * @param context      {@link Context}
     * @param databaseName 数据库名称
     * @return all table
     */
    public static List<String> queryTableByName(Context context, String databaseName) {
        List<String> list = null;
        List<File> databaseList = queryDatabaseList(context);
        if (databaseList != null && databaseList.size() > 0) {
            if (!isDatabaseExits(databaseList, databaseName)) {
                return list;
            }
            SQLiteDatabase database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
            if (database != null) {
                Cursor cursor = database.rawQuery("select name from sqlite_master where type = 'table'", null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        list = new ArrayList<>();
                        do {
                            String tableName = cursor.getString(0);
                            if (!TextUtils.isEmpty(tableName) && !(tableName.equals("android_metadata")
                                    || tableName.equals("sqlite_sequence"))) {
                                list.add(tableName);
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            }
        }
        return list;
    }

    private static boolean isDatabaseExits(List<File> list, String fileName) {
        for (File file : list) {
            if (file.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTableExits(List<String> list, String tableName) {
        for (String fileName : list) {
            if (fileName.equals(tableName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 查询指定数据库中指定表的数据.
     * 当前只能全部查询，后期可以进行优化
     *
     * @param context {@link Context}
     * @param name    database
     * @param table   table
     * @return list
     */
    public static List<TablePackage> queryDatabaseData(Context context, String name, String table) {
        List<TablePackage> all = null;
        //判断数据库是否存在
        // TODO 重构查询方式.
        List<File> databaseList = queryDatabaseList(context);
        if (!isDatabaseExits(databaseList, name)) {
            return null;
        }
        // TODO 重构查询方式.
        //判断表是否存在
        List<String> tableList = queryTableByName(context, name);
        if (!isTableExits(tableList, table)) {
            return null;
        }
        SQLiteDatabase database = context.openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        if (database != null) {
            Cursor cursor = database.query(table, null, null, null, null, null, null);
            if (cursor != null) {
                String[] columnName = cursor.getColumnNames();
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    all = new ArrayList<>();
                    do {
                        TablePackage tablePackage = new TablePackage();
                        for (int index = 0; index < columnName.length; index++) {
                            //TODO 后面可以修改为根据column的类型来获取相应的值，暂时测试么有问题
                            int columnIndex = cursor.getColumnIndex(columnName[index]);
                            tablePackage.put(columnName[index], columnIndex > -1 ? cursor.getString(columnIndex) : "");
                        }
                        all.add(tablePackage);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            database.close();
        }
        return all;
    }

    /**
     * 获取当前应用下的sharePrefrence文件.
     *
     * @param context {@link Context}
     * @return file[]
     */
    public static File[] querySharePreference(Context context) {
        File rootFileDirectory = context.getFilesDir();
        if (rootFileDirectory != null) {
            File root = rootFileDirectory.getParentFile();
            if (root != null) {
                File shareRoot = new File(root, "shared_prefs");
                if (shareRoot.exists()) {
                    return shareRoot.listFiles();
                }
            }
        }
        return null;
    }
}
