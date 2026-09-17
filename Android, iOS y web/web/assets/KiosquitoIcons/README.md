# Kiosquito — Paquete completo de iconos

Este paquete trae listo el icono de **Kiosquito** en todos los formatos y tamaños
que necesitas para Android Studio, Xcode/iOS, web, y uso general (carpetas, Sideloadly, AltStore, etc).

## 📁 Estructura

```
KiosquitoIcons/
├── Android/
│   └── app/src/main/res/
│       ├── mipmap-mdpi/xhdpi/xxhdpi/xxxhdpi/hdpi/
│       │     ic_launcher.png, ic_launcher_round.png,
│       │     ic_launcher_foreground.png, ic_launcher_background.png
│       ├── mipmap-anydpi-v26/
│       │     ic_launcher.xml, ic_launcher_round.xml   (adaptive icon)
│       └── play_store_icon_512.png
├── iOS/
│   └── AppIcon.appiconset/   (todos los tamaños + Contents.json listos para Xcode)
├── Web/
│   ├── favicon.ico
│   ├── icon-16x16.png … icon-512x512.png
│   ├── apple-touch-icon.png
│   ├── android-chrome-192x192.png / 512x512.png
│   └── site.webmanifest
└── Master/
    ├── kiosquito_icon_1024.png   (icono maestro, alta resolución)
    ├── kiosquito_icon.ico        (icono para carpetas/exe en Windows)
    └── kiosquito.iconset/        (set base para generar .icns en Mac)
```

## 🤖 Android Studio (Gemini / Android)

1. Copia toda la carpeta `Android/app/src/main/res/` dentro de tu proyecto,
   reemplazando la carpeta `res/` existente (o solo las subcarpetas `mipmap-*`).
2. Ya incluye:
   - Iconos clásicos (`ic_launcher.png`, `ic_launcher_round.png`) para versiones antiguas de Android.
   - **Icono adaptativo** (`ic_launcher_foreground.png` + `ic_launcher_background.png` + los XML en `mipmap-anydpi-v26`) para Android 8+, que es lo que Android Studio/Gemini reconoce automáticamente como "Adaptive Icon".
3. Si prefieres regenerarlo dentro de Android Studio: click derecho en `res` → **New → Image Asset** → elige `Master/kiosquito_icon_1024.png` como Foreground Layer y el color `#F0F9F6` como Background. Android Studio generará automáticamente todo por ti.

## 🍎 iOS (Xcode)

1. Arrastra la carpeta `iOS/AppIcon.appiconset` completa dentro de `Assets.xcassets` en Xcode (reemplaza el AppIcon existente).
2. Ya trae todos los tamaños requeridos: iPhone, iPad, y el de 1024×1024 para App Store.
3. Nota: Apple no permite transparencia en el icono de la app, así que estas versiones están generadas sobre el fondo verde menta original (fondo sólido), tal como pide Apple.

## 🌐 Web

1. Copia todo el contenido de `Web/` a la raíz de tu proyecto (o `/public`).
2. Agrega esto en el `<head>` de tu HTML:
```html
<link rel="icon" type="image/x-icon" href="/favicon.ico">
<link rel="icon" type="image/png" sizes="32x32" href="/icon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="/icon-16x16.png">
<link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
<link rel="manifest" href="/site.webmanifest">
```

## 📲 Sideloadly / AltStore / carpetas / icono genérico

- **Sideloadly** detecta automáticamente el icono embebido dentro del `.ipa`
  (el que va en `AppIcon.appiconset`), así que una vez que compiles el `.ipa`
  con los iconos de iOS ya incluidos, Sideloadly lo mostrará solo.
- Si solo necesitas un archivo suelto para usar como icono de carpeta:
  - **Windows**: usa `Master/kiosquito_icon.ico` (clic derecho en la carpeta → Propiedades → Personalizar → Cambiar icono).
  - **macOS**: abre `Master/kiosquito_icon_1024.png`, cópialo (⌘C), luego clic derecho en la carpeta → Obtener información → clic en el icono de arriba a la izquierda → pegar (⌘V). También incluí `kiosquito.iconset/` por si quieres generar un `.icns` con `iconutil` en una Mac (`iconutil -c icns kiosquito.iconset`).
- Para cualquier otro uso genérico (Discord, apps de terceros, etc.), usa `Master/kiosquito_icon_1024.png` como el icono maestro de máxima calidad.

---
Todos los iconos se generaron a partir de tu logo original, detectando automáticamente
el fondo verde menta para crear también una versión del logo con **fondo transparente**
(usada en el icono adaptativo de Android).
