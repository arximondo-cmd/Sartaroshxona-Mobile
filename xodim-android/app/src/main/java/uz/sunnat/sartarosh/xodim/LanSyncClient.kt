package uz.sunnat.sartarosh.xodim

import org.json.JSONObject
import java.io.*
import java.net.InetSocketAddress
import java.net.Socket

class LanSyncClient(private val db:XodimDb){
 fun pushToAdmin(host:String,port:Int=48721):Pair<Int,String>{
  var ok=0
  Socket().use { s->
   s.connect(InetSocketAddress(host,port),3000); s.soTimeout=5000
   val out=PrintWriter(OutputStreamWriter(s.getOutputStream(),Charsets.UTF_8),true)
   val input=BufferedReader(InputStreamReader(s.getInputStream(),Charsets.UTF_8))
   out.println(JSONObject().put("type","HELLO").put("employeeId","emp-demo").put("employeeName","Xodim").put("deviceId","device-demo").put("protocol",1))
   db.pendingJson().forEach { payload->
    out.println(JSONObject().put("type","RECORD").put("payload",payload))
    val reply=JSONObject(input.readLine())
    if(reply.optString("type")=="ACK" && reply.optString("recordId")==payload.getString("recordId")){ db.markSynced(payload.getString("recordId")); ok++ }
   }
   out.println(JSONObject().put("type","DONE").put("count",ok))
  }
  return ok to "$ok ta yozuv Admin telefoniga yuborildi"
 }
}
