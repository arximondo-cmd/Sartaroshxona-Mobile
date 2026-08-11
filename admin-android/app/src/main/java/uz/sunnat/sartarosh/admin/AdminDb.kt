package uz.sunnat.sartarosh.admin

import android.content.*
import android.database.sqlite.*
import org.json.JSONObject

class AdminDb(ctx:Context):SQLiteOpenHelper(ctx,"admin_mobile.db",null,1){
 override fun onCreate(db:SQLiteDatabase){
  db.execSQL("CREATE TABLE records(record_id TEXT PRIMARY KEY,employee_id TEXT NOT NULL,device_id TEXT NOT NULL,created_at TEXT NOT NULL,payload_json TEXT NOT NULL,received_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,synced_to_windows INTEGER NOT NULL DEFAULT 0)")
  db.execSQL("CREATE TABLE master_metrics(k TEXT PRIMARY KEY,v INTEGER NOT NULL,updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP)")
 }
 override fun onUpgrade(db:SQLiteDatabase,o:Int,n:Int){}
 fun accept(payload:JSONObject):Boolean { val cv=ContentValues().apply{put("record_id",payload.getString("recordId"));put("employee_id",payload.getString("employeeId"));put("device_id",payload.getString("deviceId"));put("created_at",payload.getString("createdAt"));put("payload_json",payload.toString())}; return writableDatabase.insertWithOnConflict("records",null,cv,SQLiteDatabase.CONFLICT_IGNORE)!=-1L }
 fun countAll():Int=readableDatabase.rawQuery("SELECT COUNT(*) FROM records",null).use{it.moveToFirst();it.getInt(0)}
 fun pendingWindows():Int=readableDatabase.rawQuery("SELECT COUNT(*) FROM records WHERE synced_to_windows=0",null).use{it.moveToFirst();it.getInt(0)}
 fun today():Pair<Int,Long>{ readableDatabase.rawQuery("SELECT COUNT(*),COALESCE(SUM(CAST(json_extract(payload_json,'$.serviceTotal') AS INTEGER)+CAST(json_extract(payload_json,'$.tip') AS INTEGER)),0) FROM records WHERE substr(created_at,1,10)=date('now','localtime')",null).use{it.moveToFirst();return it.getInt(0) to it.getLong(1)} }
 fun month():Pair<Int,Long>{ readableDatabase.rawQuery("SELECT COUNT(*),COALESCE(SUM(CAST(json_extract(payload_json,'$.serviceTotal') AS INTEGER)+CAST(json_extract(payload_json,'$.tip') AS INTEGER)),0) FROM records WHERE substr(created_at,1,7)=strftime('%Y-%m','now','localtime')",null).use{it.moveToFirst();return it.getInt(0) to it.getLong(1)} }
 fun year():Pair<Int,Long>{ readableDatabase.rawQuery("SELECT COUNT(*),COALESCE(SUM(CAST(json_extract(payload_json,'$.serviceTotal') AS INTEGER)+CAST(json_extract(payload_json,'$.tip') AS INTEGER)),0) FROM records WHERE substr(created_at,1,4)=strftime('%Y','now','localtime')",null).use{it.moveToFirst();return it.getInt(0) to it.getLong(1)} }
 fun topMonth():List<Pair<String,Int>>{ val out=mutableListOf<Pair<String,Int>>();readableDatabase.rawQuery("SELECT employee_id,COUNT(*) n FROM records WHERE substr(created_at,1,7)=strftime('%Y-%m','now','localtime') GROUP BY employee_id ORDER BY n DESC LIMIT 10",null).use{c->while(c.moveToNext())out+=c.getString(0) to c.getInt(1)};return out }
}
