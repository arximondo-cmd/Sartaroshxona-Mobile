package uz.sunnat.sartarosh.xodim

data class ServiceItem(val id:String,val name:String,val price:Long)
data class ClientDraft(val services:List<ServiceItem>, val tip:Long)
