package uz.sunnat.sartarosh.xodim

import android.app.*
import android.os.*
import android.text.InputType
import android.view.View
import android.widget.*
import java.util.concurrent.Executors

class MainActivity:Activity(){
 private lateinit var db:XodimDb; private lateinit var root:LinearLayout; private val pool=Executors.newSingleThreadExecutor()
 override fun onCreate(b:Bundle?){ super.onCreate(b); db=XodimDb(this); db.ensureDemoProfile(); showHome() }
 private fun tv(t:String,size:Float=18f)=TextView(this).apply{text=t;textSize=size;setPadding(20,14,20,14)}
 private fun btn(t:String,action:()->Unit)=Button(this).apply{text=t;setOnClickListener{action()}}
 private fun base():LinearLayout=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(24,36,24,24)}
 private fun showHome(){ root=base(); root.addView(tv("SARTAROSHXONA XODIM",24f)); root.addView(tv("BUGUN\n👥 ${db.todayCount()} mijoz\n💵 Choychaqa: ${db.todayTip()} so'm",20f)); root.addView(btn("+ MIJOZ"){showAdd()}); root.addView(btn("ADMIN BILAN SINXRONLASH"){showSync()}); root.addView(tv("Saqlangan yozuvni xodim tahrirlay yoki o'chira olmaydi.",14f)); setContentView(ScrollView(this).apply{addView(root)}) }
 private fun showAdd(){ val lay=base(); lay.addView(tv("Yangi mijoz",24f)); val selected= mutableListOf<ServiceItem>(); db.services().forEach { s-> lay.addView(CheckBox(this).apply{text="${s.name} — ${s.price} so'm";setOnCheckedChangeListener{_,on->if(on)selected+=s else selected.remove(s)}}) }; val tip=EditText(this).apply{hint="Choychaqa, so'm";inputType=InputType.TYPE_CLASS_NUMBER}; lay.addView(tip); lay.addView(btn("SAQLASH"){ if(selected.isEmpty()){Toast.makeText(this,"Kamida bitta xizmat tanlang",Toast.LENGTH_SHORT).show();return@btn}; db.saveImmutable(ClientDraft(selected.toList(),tip.text.toString().toLongOrNull()?:0)); Toast.makeText(this,"Yozuv rasmiy saqlandi",Toast.LENGTH_SHORT).show();showHome()}); lay.addView(btn("Orqaga"){showHome()}); setContentView(ScrollView(this).apply{addView(lay)}) }
 private fun showSync(){ val lay=base(); lay.addView(tv("Admin bilan sinxronlash",24f)); val ip=EditText(this).apply{hint="Admin telefon IP manzili (masalan 192.168.1.20)"}; lay.addView(ip); val status=tv("Kutilmoqda",16f); lay.addView(status); lay.addView(btn("YUBORISH"){ val host=ip.text.toString().trim(); if(host.isEmpty())return@btn; status.text="Ulanmoqda..."; pool.execute { try{val r=LanSyncClient(db).pushToAdmin(host);runOnUiThread{status.text="✓ ${r.second}"}}catch(e:Exception){runOnUiThread{status.text="Xato: ${e.message}"}} } }); lay.addView(btn("Orqaga"){showHome()}); setContentView(ScrollView(this).apply{addView(lay)}) }
}
