package example.ericli.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.internal.widget.ActionBarContextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Eric Li on 2015/8/26.
 */
public class TableMember {

    private SQLiteDatabase db;

    //資料表基本結構
    public static final String TABLE_NAME = "member";
    public static final String C_ID = "_id";
    public static final String C_NAME = "name";
    public static final String C_LEVEL = "level";

    //資料表建立SQL命令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            C_NAME + " TEXT NOT NULL, " +
            C_LEVEL + " INTEGER NOT NULL)";

    public TableMember(Context context){
        this.db = DBHelper.getDatabase(context);
    }

    /* 關閉資料庫連線 */
    public void close(){
        this.db.close();
    }

    /* 新增 */
    public boolean insert(String name, int level){
        //亦可改用 db.execSQL() 執行串接完成的 SQL 命令

        ContentValues cv = new ContentValues();

        cv.put(C_NAME, name); //將實際資料填入ContentValues準備
        cv.put(C_LEVEL, level);

        long id = db.insert(TABLE_NAME, null, cv);
        //此處id為db執行insert命令後傳回的row id, 若該值為-1表示新增失敗
        return id!=-1;
    }

    /* 刪除 */
    public boolean delete(long id){
        //亦可改用 db.execSQL() 執行串接完成的 SQL 命令

        int deletedRows = db.delete(TABLE_NAME, C_ID + "=" + id, null);
        //delete將傳回一int值, 其表示成功移除的列數
        return deletedRows > 0;
    }

    /* 查詢 */
    public ArrayList<Member> query(){
        //本 method 故意採用 db.rawQuery() 執行 SQL 命令, 同學可以衡量一下何者較合意

        ArrayList<Member> queryResult = new ArrayList<>();

        String sqlQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sqlQuery, null);

        while(cursor.moveToNext()){ //還存在著下一筆資料時
            Member member = new Member();
            member.id = cursor.getInt(0); //第1欄位(index=0)
            member.name = cursor.getString(1); //第2欄位
            member.level = cursor.getInt(2); //第3欄位

            queryResult.add(member);
        }

        return queryResult;
    }

    /* 更新 */
    public boolean update(long id, String name, int level){
        //亦可改用 db.execSQL() 執行串接完成的 SQL 命令

        //準備欲更新的資料
        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_LEVEL, level);

        //組合 where 條件
        String where = C_ID + "=" + id;

        int updatedRows = db.update(TABLE_NAME, cv, where, null);
        return updatedRows > 0;
    }

}
