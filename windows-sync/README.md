# Windows Sync Bridge — keyingi modul

Mavjud `Sartaroshxona hisobi.exe` faqat kompilyatsiya qilingan .NET assembly ko'rinishida mavjud; source code arxivda yo'q. Shu sababli mavjud exe'ni xavfli patch qilish o'rniga yangi Windows versiya/source yoki alohida sync bridge moduliga quyidagi funksiyalar qo'shiladi:

- Admin Mobile bilan bir Wi-Fi'da TCP/JSON sync (`48722` port tavsiya).
- Admin telefonidagi `synced_to_windows=0` yozuvlarni qabul qilish.
- `recordId` bo'yicha idempotent import.
- Windowsda tahrirlangan qiymat + edit history yaratish.
- Windows master ma'lumotlarini (xizmatlar/narxlar, xodim mobil parol versiyasi, jarima, TOP/sof foyda analitikasi) Admin Mobile'ga qaytarish.
- Admin Mobile keyingi tashrifda master update'larni Xodim Mobile'ga tarqatadi.

Windows master-baza eski `%LOCALAPPDATA%\\SartaroshHisob\\malumotlar.xml` bilan migratsiya/backup mosligini saqlaydi.
