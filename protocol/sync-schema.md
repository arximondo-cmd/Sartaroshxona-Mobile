# LAN Sync Protocol v1

Port: `48721` TCP, UTF-8 JSON lines.

Har bir mijoz yozuvi `recordId` (UUID) bilan keladi. Qabul qiluvchi `recordId` mavjud bo'lsa qayta qo'shmaydi.

## Xodim -> Admin
```json
{"type":"HELLO","employeeId":"...","employeeName":"Ismoil","deviceId":"...","protocol":1}
{"type":"RECORD","payload":{...}}
{"type":"DONE","count":18}
```

Admin har qabul qilingan yozuvga:
```json
{"type":"ACK","recordId":"..."}
```
qaytaradi. Xodim faqat ACK olgandan keyin yozuvni `synced_to_admin=1` deb belgilaydi.

## Windows -> Admin -> Xodim
`MASTER_UPDATE` paketlari xizmatlar, narxlar, mobil ruxsat/parol versiyasi, jarimalar va Windows tahrirlarini olib keladi. Xodim lokal tarixini o'zgartira olmaydi; master update ko'rsatiladigan amaldagi natijani yangilaydi.
