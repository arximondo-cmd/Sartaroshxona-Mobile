package uz.sunnat.sartarosh.admin

import android.app.*
import android.os.*
import android.widget.*
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections

class MainActivity:Activity(){
 private lateinit var db:AdminDb; private var server:LanSyncServer?=null; private lateinit var status:TextView
 override fun onCreate(b:Bundle?){super.onCreate(b);db=AdminDb(this);showDashboard()}
 private fun tv(t:String,size:Float=18f)=TextView(this).apply{text=t;textSize=size;setPadding(20,12,20,12)}
 private fun btn(t:String,a:()->Unit)=Button(this).apply{text=t;setOnClickListener{a()}}
 private fun ip():String { for(i in Collections.list(NetworkInterface.getNetworkInterfaces())) for(a in Collections.list(i.inetAddresses)) if(!a.isLoopbackAddress&&a is Inet4Address) return a.hostAddress?:"?"; return "?" }
 private fun showDashboard(){val lay=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(24,34,24,24)};val td=db.today();val mo=db.month();val yr=db.year();lay.addView(tv("SARTAROSHXONA HISOBI — ADMIN",23f));lay.addView(tv("BUGUN\n👥 ${td.first} mijoz\n💰 Tushum: ${td.second} so'm",19f));lay.addView(tv("SHU OY\n👥 ${mo.first} mijoz\n💰 Tushum: ${mo.second} so'm\n📈 Sof foyda: Windows sinxronizatsiyasidan",19f));lay.addView(tv("SHU YIL\n👥 ${yr.first} mijoz\n💰 Tushum: ${yr.second} so'm\n📈 Sof foyda: Windows sinxronizatsiyasidan",19f));val top=StringBuilder("🏆 SHU OY TOP XODIMLAR\n");db.topMonth().forEachIndexed{i,p->top.append(when(i){0->"🥇";1->"🥈";2->"🥉";else->"${i+1}."}).append(" ${p.first} — ${p.second} mijoz\n")};lay.addView(tv(top.toString(),18f));lay.addView(tv("Windows analitikasi: TOP 7 oy haftasi • TOP 7 yil haftasi • TOP 12 oy\n(Windows → Admin sinxronizatsiyasidan keladi)",16f));lay.addView(tv("Windowsga yuborilmagan: ${db.pendingWindows()} yozuv",16f));status=tv("Sinxronizatsiya serveri o'chiq",15f);lay.addView(status);lay.addView(btn("XODIMLARDAN QABUL QILISH"){startReceive()});lay.addView(btn("Yangilash"){server?.stop();showDashboard()});setContentView(ScrollView(this).apply{addView(lay)})}
 private fun startReceive(){server?.stop();server=LanSyncServer(db){runOnUiThread{status.text=it}};server!!.start();status.text="Xodimlar shu Wi-Fi'da Admin IP ${ip()}:48721 ga yuborsin"}
 override fun onDestroy(){server?.stop();super.onDestroy()}
}
