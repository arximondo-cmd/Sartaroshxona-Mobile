package uz.sunnat.sartarosh.admin

import org.json.JSONObject
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class LanSyncServer(private val db:AdminDb, private val onEvent:(String)->Unit){
 private val running=AtomicBoolean(false); private val pool=Executors.newCachedThreadPool(); private var server:ServerSocket?=null
 fun start(port:Int=48721){ if(running.getAndSet(true))return; pool.execute { try{ server=ServerSocket(port); onEvent("Server tayyor: $port"); while(running.get()){ val s=server!!.accept(); pool.execute{handle(s)} } }catch(e:Exception){if(running.get())onEvent("Server xatosi: ${e.message}")} } }
 fun stop(){running.set(false);try{server?.close()}catch(_:Exception){}}
 private fun handle(s:Socket){ s.use { val input=BufferedReader(InputStreamReader(it.getInputStream(),Charsets.UTF_8)); val out=PrintWriter(OutputStreamWriter(it.getOutputStream(),Charsets.UTF_8),true); var count=0; while(true){ val line=input.readLine()?:break; val msg=JSONObject(line); when(msg.optString("type")){"HELLO"->onEvent("${msg.optString("employeeName")} ulandi");"RECORD"->{val p=msg.getJSONObject("payload");db.accept(p);out.println(JSONObject().put("type","ACK").put("recordId",p.getString("recordId")));count++;onEvent("+$count yozuv")};"DONE"->{onEvent("Qabul tugadi: $count ta");break}} } } }
}
