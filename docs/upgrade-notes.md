# TUI-ConsoleLauncher Upgrade Notes

## Contexto
- Objetivo: subir versiones del proyecto al maximo posible sin romper funcionalidades clave.
- Fecha de inicio: 2026-04-22.
- Estrategia actual: saltos grandes de version + validacion rapida para ahorrar tiempo.

## Estado base detectado
- AGP: `8.2.0` (`build.gradle`).
- Gradle wrapper: `8.2` (`gradle/wrapper/gradle-wrapper.properties`).
- `compileSdk`: `34` (`app/build.gradle`).
- `targetSdk`: `34` (`app/build.gradle`).
- `minSdk`: `21` (`app/build.gradle`).
- Dependencias principales:
  - `androidx.appcompat:appcompat:1.6.1`
  - `com.google.android.material:material:1.11.0`
  - `androidx.localbroadcastmanager:localbroadcastmanager:1.1.0`
  - `okhttp:4.12.0`
  - `htmlcleaner:2.29`
  - `json-path:2.9.0`
  - `jsoup:1.17.2`

## Infraestructura local (verificada en esta sesion)
- Se detecta JDK en: `C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot`.
- Con `JAVA_HOME` temporal, `gradlew -q help` funciona.
- Nota: la terminal no tenia `java` en `PATH` al inicio.

## Estado actual (tras actualizacion rapida)
- AGP: `9.1.1` (`build.gradle`).
- Gradle wrapper: `9.3.1` (`gradle/wrapper/gradle-wrapper.properties`).
- `compileSdk`: `35` (`app/build.gradle`).
- `targetSdk`: `35` (`app/build.gradle`).
- `minSdk`: `21` (`app/build.gradle`).
- Java source/target: `17` (`app/build.gradle`).
- Dependencias principales subidas:
  - `androidx.appcompat:appcompat:1.7.0`
  - `com.google.android.material:material:1.12.0`
  - `com.squareup.okhttp3:okhttp:5.3.2`
  - `com.jayway.jsonpath:json-path:2.10.0`
  - `org.jsoup:jsoup:1.18.3`

## Riesgos funcionales que no dependen de versiones
- `wifi` usa `setWifiEnabled` (restringido en Android moderno).
- `bluetooth` usa `enable/disable` directos y faltan permisos modernos runtime.
- Storage depende de `MANAGE_EXTERNAL_STORAGE` y `requestLegacyExternalStorage`.

## Objetivo tecnico de esta iteracion
- Subir toolchain a una combinacion estable y moderna.
- Subir `compileSdk` y `targetSdk`.
- Subir dependencias principales sin cambiar arquitectura.

## Historial de cambios
- 2026-04-22: se crea esta bitacora.
- 2026-04-22: subida de toolchain (AGP/Gradle) y SDK objetivo (`35`).
- 2026-04-22: salto final validado a `AGP 9.1.1` + `Gradle 9.3.1`.
- 2026-04-22: subida de Java source/target a `17`.
- 2026-04-22: `:app:compileFdroidDebugJavaWithJavac` compila correctamente.
- 2026-04-22: `:app:assembleFdroidDebug` falla por keystore faltante (`release.jks`), no por errores de codigo.
- 2026-04-22: `gradlew -q help` funciona con toolchain nuevo.
- 2026-04-22: se desacopla `debug` de `release` signing para permitir compilacion local.
- 2026-04-22: `:app:assembleFdroidDebug` termina OK con AGP `9.1.1` y Gradle `9.3.1`.
- 2026-04-22: `:app:compilePlaystoreReleaseJavaWithJavac` termina OK.

## Pendiente inmediato
- Ejecutar `assembleFdroidDebug` y `assemblePlaystoreRelease` con firma resuelta.
- Limpiar deprecaciones para preparar salto futuro a Gradle 10 (Jetifier y scripts heredados).
- Empezar fase de compatibilidad funcional (wifi/bluetooth/storage) en Android moderno.

## Lo que necesito para seguir mas rapido
- Definir si quieres que elimine la firma `release` del tipo `debug` para compilar sin keystore local.
- Confirmar si el objetivo de distribucion incluye Play Store (afecta permisos y funcionalidades legacy).





