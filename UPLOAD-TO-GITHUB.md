# GitHub repo uchun tayyor paket

Repo: `arximondo-cmd/Sartaroshxona-Mobile`

1. Ushbu ZIP ichidagi **barcha fayl va papkalarni** repo ildiziga yuklang.
2. `.github/workflows/build-apks.yml` ham yuklanganiga ishonch hosil qiling.
3. GitHub `Actions` bo'limida **Build Android APKs** workflow ishga tushadi.
4. Build muvaffaqiyatli tugagach `Artifacts` bo'limida quyidagilar chiqadi:
   - `Sartaroshxona-Xodim`
   - `Sartaroshxona-Admin`
5. Har bir artifact ZIP ichida mos APK bo'ladi:
   - `Sartaroshxona-Xodim.apk`
   - `Sartaroshxona-Admin.apk`

Bu APKlar debug imzo bilan imzolangan va telefonga test uchun o'rnatiladi.
