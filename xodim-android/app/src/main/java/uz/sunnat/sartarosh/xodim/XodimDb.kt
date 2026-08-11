package uz.sunnat.sartarosh.xodim

import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.json.JSONArray
import org.json.JSONObject
import java.time.OffsetDateTime
import java.util.UUID

class XodimDb(ctx: Context): SQLiteOpenHelper(ctx,"xodim_mobile.db",null,1) {
 override fun onCreate(db:SQLiteDatabase){
  db.execSQL("CREATE TABLE profile(employee_id TEXT PRIMARY KEY,name TEXT NOT NULL,device_id TEXT NOT NULL,password_version INTEGER NOT NULL DEFAULT 1)")
  db.execSQL("CREATE TABLE services(service_id TEXT PRIMARY KEY,name TEXT NOT NULL,price INTEGER NOT NULL,active INTEGER NOT NULL DEFAULT 1,revision INTEGER NOT NULL DEFAULT 0)")
  db.execSQL("CREATE TABLE records(record_id TEXT PRIMARY KEY,employee_id TEXT NOT NULL,device_id TEXT NOT NULL,created_at TEXT NOT NULL,services_json TEXT NOT NULL,service_total INTEGER NOT NULL,tip INTEGER NOT NULL,synced_to_admin INTEGER NOT NULL DEFAULT 0,master_revision INTEGER NOT NULL DEFAULT 0)")
  // Demo xizmatlar: keyinchalik faqat Windows -> Admin -> Xodim sinxronizatsiyasidan keladi.
  db.execSQL("INSERT INTO services VALUES('hair','Soch olish',50000,1,0),('beard','Soqol',30000,1,0),('wash','Soch yuvish',15000,1,0)")
 }
 override fun onUpgrade(db:SQLiteDatabase,oldVersion:Int,newVersion:Int){}
 fun ensureDemoProfile(){
  writableDatabase.execSQL("INSERT OR IGNORE INTO profile VALUES('emp-demo','Xodim','device-demo',1)")
 }
 fun services():List<ServiceItem>{
  val out= mutableListOf<ServiceItem>(); readableDatabase.rawQuery("SELECT service_id,name,price FROM services WHERE active=1 ORDER BY name",null).use { c-> while(c.moveToNext()) out+=ServiceItem(c.getString(0),c.getString(1),c.getLong(2)) }; return out
 }
 fun saveImmutable(draft:ClientDraft):String{
  ensureDemoProfile(); val rid=UUID.randomUUID().toString(); val arr=JSONArray(); var total=0L
  draft.services.forEach { s-> total+=s.price; arr.put(JSONObject().put("serviceId",s.id).put("name",s.name).put("price",s.price)) }
  val cv=ContentValues().apply { put("record_id",rid);put("employee_id","emp-demo");put("device_id","device-demo");put("created_at",OffsetDateTime.now().toString());put("services_json",arr.toString());put("service_total",total);put("tip",draft.tip);put("synced_to_admin",0);put("master_revision",0) }
  writableDatabase.insertOrThrow("records",null,cv); return rid
 }
 fun pendingJson():List<JSONObject>{
  val out= mutableListOf<JSONObject>(); readableDatabase.rawQuery("SELECT record_id,employee_id,device_id,created_at,services_json,service_total,tip,master_revision FROM records WHERE synced_to_admin=0 ORDER BY created_at",null).use { c-> while(c.moveToNext()) out += JSONObject().put("recordId",c.getString(0)).put("employeeId",c.getString(1)).put("deviceId",c.getString(2)).put("createdAt",c.getString(3)).put("clientCount",1).put("services",JSONArray(c.getString(4))).put("serviceTotal",c.getLong(5)).put("tip",c.getLong(6)).put("masterRevision",c.getLong(7)) }; return out
 }
 fun markSynced(recordId:String){ writableDatabase.execSQL("UPDATE records SET synced_to_admin=1 WHERE record_id=?", arrayOf(recordId)) }
 fun todayCount():Int { readableDatabase.rawQuery("SELECT COUNT(*) FROM records WHERE substr(created_at,1,10)=date('now','localtime')",null).use { it.moveToFirst(); return it.getInt(0) } }
 fun todayTip():Long { readableDatabase.rawQuery("SELECT COALESCE(SUM(tip),0) FROM records WHERE substr(created_at,1,10)=date('now','localtime')",null).use { it.moveToFirst(); return it.getLong(0) } }
}
