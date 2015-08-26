package example.ericli.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eric Li on 2015/8/26.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_name";
    private static final int VER = 1;

    private static SQLiteDatabase db;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if(db==null || !db.isOpen()){
            db = new DBHelper(context, DB_NAME, null, VER).getWritableDatabase();
        }
        return DBHelper.db;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO 在此建立需使用的資料表
        sqLiteDatabase.execSQL(TableMember.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO 在此加入版本更新時需要移除的舊有資料表
        /*
                 * 此處的寫法是移除舊有 Table 並呼叫 onCreate 建立新版本的 Table
                 * 但此操作會使資料全數移除, 請確認這種寫法適用於你的版本升級, 若有必要需自行處理資料遷移與備份
                 *  */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableMember.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
