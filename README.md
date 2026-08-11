# Sartaroshxona Hisobi — Mobile v1 (boshlang'ich kod)

Ushbu loyiha kelishilgan offline-first arxitekturani boshlaydi:

1. **Xodim Mobile** — xodim mijoz va xizmatlarni kiritadi. Saqlangan yozuv xodim tomonidan tahrir/o'chirilmaydi.
2. **Admin Mobile** — bitta admin telefoni. Sartaroshxonadagi bir xil Wi-Fi orqali xodimlardan yozuvlarni oladi va lokal bazada saqlaydi.
3. **Windows bridge/protocol** — Admin Mobile va Windows master-baza orasidagi keyingi sinxronizatsiya uchun JSON protokol tavsifi.

## Kelishilgan asosiy qoidalar
- Bir xodim = bir telefon.
- Mobil parol Windowsdagi xodim profilida boshqariladi.
- Xizmat va narxlarni faqat Windows belgilaydi.
- Bir mijozga bir nechta xizmat biriktiriladi, lekin TOP uchun 1 mijoz hisoblanadi.
- Choychaqani xodim kiritadi.
- `Saqlash` bosilgach yozuv xodimda immutable (o'zgarmas) bo'ladi.
- Admin/Windows tahriri oxirgi rasmiy qiymat hisoblanadi; tahrir tarixi saqlanadi.
- TOP xodimlar joriy oy mijozlar soni bo'yicha: 1/2/3 = oltin/kumush/bronza.
- Server/bulut yo'q. Sinxronizatsiya lokal Wi-Fi orqali: Xodim -> Admin Mobile -> Windows.

## Hozirgi holat
Bu birinchi manba-kod karkasi. APK bu muhitda Android SDK bo'lmagani sababli kompilyatsiya qilinmadi. Android Studio'da ochib build qilish uchun Gradle loyihalari tayyorlangan.
