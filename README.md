# 🥾 SenderismoApp

Aplicación Android de senderismo gamificada orientada a la **navegación por rutas, exploración de la naturaleza y gamificación**.

El objetivo del proyecto es crear una aplicación que permita descubrir y realizar rutas de senderismo mediante GPS y, progresivamente, incorporar elementos de exploración, identificación de flora y fauna, una Pokédex de la naturaleza, desafíos, experiencia, niveles y otras funciones sociales.

Actualmente el proyecto se encuentra en desarrollo de la **Alpha 0.1**.

---

## 🎯 Visión del proyecto

SenderismoApp busca combinar:

* 🥾 Rutas de senderismo.
* 🗺️ Navegación GPS.
* 📍 Seguimiento de la posición durante una ruta.
* 🌿 Exploración de la naturaleza.
* 📸 Fotografías e identificación de especies.
* 📖 Pokédex personal de flora y fauna.
* 🎮 Experiencia, niveles y logros.
* 🏆 Desafíos.
* 👤 Perfil y estadísticas.
* 🌎 Mapa personal de exploración.
* 👥 Funciones sociales y cooperativas.

La idea es comenzar con una aplicación sencilla y funcional para realizar rutas y evolucionarla progresivamente hasta convertirla en una plataforma de exploración y gamificación.

---

# 🚀 Estado actual

## Alpha 0.1

El objetivo de la Alpha 0.1 es conseguir una primera versión funcional capaz de permitir al usuario **seleccionar una ruta, desplazarse hasta su inicio y comenzar una navegación GPS**.

Actualmente se han completado los puntos **1–9** del flujo principal.

### 🟢 Completado

1. **Abrir aplicación** ✅
2. **Seleccionar zona** ✅
3. **Ver rutas** ✅
4. **Seleccionar ruta** ✅
5. **Consultar información básica de la ruta** ✅
6. **Pulsar EMPEZAR** ✅
7. **Obtener posición GPS** ✅
8. **Guiar al usuario hasta el inicio mediante Google Maps** ✅
9. **Detectar llegada al inicio de la ruta** ✅

### Funcionalidades implementadas

* 🗺️ Integración con Google Maps.
* 📍 Obtención de ubicación mediante GPS.
* 🥾 Representación de una ruta mediante coordenadas.
* 🟢 Marcador de inicio.
* 🔴 Marcador de final.
* 🔵 Marcador de posición actual.
* 📏 Cálculo de distancia hasta el inicio.
* 📐 Conversión de distancia entre metros y kilómetros.
* 📍 Detección de llegada al inicio mediante un radio de proximidad.
* 🧭 Apertura de Google Maps para guiar al usuario hasta el inicio.
* ▶️ Inicio/finalización del estado de navegación.
* 🎨 Primera versión de la interfaz visual.

---

# 🟡 Pendiente para completar Alpha 0.1

Los siguientes puntos quedan pendientes:

10. **Comenzar ruta.**
11. **Seguimiento de la posición sobre el trazado.**
12. **Calcular distancia recorrida y distancia restante.**
13. **Calcular porcentaje de progreso.**
14. **Mostrar tiempo y altitud.**
15. **Detectar llegada al final de la ruta.**
16. **Mostrar pantalla de “¡Ruta completada!” y resumen de la actividad.**

### ⚠️ Prueba física pendiente

Los puntos 10–16 quedan temporalmente aparcados hasta poder realizar una **prueba física de la ruta en el Parque del Alamillo**.

La intención es validar primero que el sistema funciona correctamente en condiciones reales antes de continuar desarrollando la lógica completa de seguimiento.

---

# 🗺️ Próximo bloque de trabajo

Antes de continuar con los puntos 10–16, se quiere completar la funcionalidad básica de las pestañas **MAPA** y **RUTAS**.

## MAPA

La pantalla deberá mostrar:

* 📍 La ubicación del usuario cuando exista una posición GPS disponible.
* La ausencia del marcador del usuario cuando no exista ubicación.
* 📌 La zona donde existe una ruta disponible.
* 🥾 La ruta disponible del Parque del Alamillo.

## RUTAS

La pantalla deberá mostrar:

**Ruta del Parque del Alamillo**

mediante una tarjeta similar a la utilizada actualmente en **Rutas destacadas** de la pantalla de inicio.

Al seleccionar la ruta:

**Rutas → Detalle de ruta → Empezar → Navegación**

---

# 🏗️ Arquitectura actual

Actualmente el proyecto todavía se encuentra en una fase de prototipo.

La ruta utilizada para las pruebas está definida directamente dentro del código de la aplicación.

### Situación actual

```text
Android App
     │
     ├── Ruta de prueba
     │      └── Coordenadas definidas en Kotlin
     │
     ├── GPS
     │      └── Ubicación del dispositivo
     │
     └── Google Maps
            └── Visualización del mapa
```

Actualmente **no existe todavía una API/backend propio conectado a la aplicación**.

Tampoco se está utilizando todavía Oracle desde la aplicación.

---

# 🗄️ Base de datos

El proyecto contempla utilizar una base de datos Oracle para almacenar la información de las rutas.

La estructura inicial prevista incluye:

### ZONAS

Información de las zonas disponibles para realizar rutas.

### RUTAS

Información general de cada ruta.

### PUNTOS_RUTA

Puntos que forman el trazado de cada ruta.

La tabla `PUNTOS_RUTA` contempla actualmente información como:

* `ID`
* `RUTA_ID`
* `ORDEN`
* `LATITUD`
* `LONGITUD`
* `ALTITUD`

La conexión entre la aplicación y Oracle se realizará posteriormente mediante una API/backend.

---

# 🔌 Arquitectura futura

La arquitectura evolucionará progresivamente desde el prototipo actual:

```text
ACTUAL

App
 │
 └── Ruta hardcodeada
```

hacia:

```text
FASE FUTURA

Android App
     │
     ↓
   API
     │
     ↓
  Oracle
     │
     ├── ZONAS
     ├── RUTAS
     └── PUNTOS_RUTA
```

Y posteriormente se incorporarán mecanismos de almacenamiento local y sincronización para permitir funcionalidades offline.

---

# 🛠️ Tecnologías

## Android

* Kotlin
* Jetpack Compose
* Android Studio

## Mapas

* Google Maps SDK
* Google Maps Compose

## Localización

* Android Location Services
* Google Play Services Location
* GPS del dispositivo

## Base de datos

* Oracle — prevista para fases posteriores.

## Backend

* API propia — prevista para fases posteriores.

---

# 📱 Ejecución del proyecto

El proyecto puede abrirse desde **Android Studio**.

Actualmente, al tratarse de un prototipo, no es necesario disponer de la base de datos Oracle ni de una API/backend para ejecutar las funcionalidades que ya están implementadas.

La aplicación utiliza una ruta de prueba incluida en el propio código.

Para utilizar correctamente el mapa será necesario disponer de una configuración válida de **Google Maps API**.

---

# 📋 Roadmap

## 🟢 Alpha 0.1 — Ruta + navegación básica

**Hitos 1–4**

* [x] Base mínima de rutas.
* [x] Mapa.
* [x] GPS.
* [x] Localización del inicio.
* [ ] Seguimiento completo de la ruta.
* [ ] Distancia recorrida/restante.
* [ ] Progreso.
* [ ] Tiempo y altitud.
* [ ] Llegada al final.
* [ ] Resumen de ruta.

---

## 🔵 Alpha 0.2 — Información avanzada

**Hitos 5–6**

* [ ] Información avanzada de rutas.
* [ ] Dificultad.
* [ ] Distancia y duración.
* [ ] Desnivel.
* [ ] Altitud mínima/máxima.
* [ ] Tipo de terreno.
* [ ] Recomendaciones.
* [ ] Perfil de elevación.
* [ ] Imagen de la ruta.
* [ ] Puntos de interés.

---

## 🟣 Alpha 0.3 — Offline

**Hito 7**

Investigar e implementar:

* [ ] Descarga de rutas.
* [ ] Datos de navegación offline.
* [ ] GPS sin conexión.
* [ ] Mapas offline.
* [ ] Waypoints offline.
* [ ] Almacenamiento local.
* [ ] Sincronización posterior.

Tecnologías a estudiar:

* Mapbox
* OpenStreetMap
* osmdroid
* Mapsforge

---

## 🟢 Alpha 0.4 — Naturaleza y Pokédex

**Hitos 8–11**

* [ ] Base de datos de flora.
* [ ] Base de datos de fauna.
* [ ] Asociar especies a rutas.
* [ ] Descubrimientos.
* [ ] Pokédex personal.

---

## 🟠 Alpha 0.5 — IA y gamificación

**Hitos 12–14**

* [ ] Cámara.
* [ ] Identificación de especies mediante IA.
* [ ] Desafíos.
* [ ] Experiencia.
* [ ] Niveles.
* [ ] Insignias.

---

## 🔵 Beta 0.9

**Hitos 15–23**

* [ ] Perfil y estadísticas.
* [ ] Mapa personal.
* [ ] Información contextual.
* [ ] Asistente de exploración.
* [ ] Funciones sociales.
* [ ] Expediciones cooperativas.
* [ ] Conservación y educación.
* [ ] Funciones avanzadas.
* [ ] Calidad y pruebas beta.

---

## 🏁 V1.0

**Hito 24**

Primera versión completa del producto.

---

# 🗺️ Prioridades del proyecto

Las prioridades principales son:

1. 🥇 GPS.
2. 🥈 Mapa.
3. 🥉 Rutas.
4. 🥾 Inicio y final de ruta.
5. 📍 Seguimiento.
6. 📏 Distancia.
7. ⏱️ Tiempo.
8. 📊 Progreso.
9. 📴 Funcionamiento offline.

---

# 🧪 Filosofía de desarrollo

El proyecto se desarrolla de forma incremental.

La metodología actual consiste en:

> **Un cambio pequeño → compilar → probar → continuar.**

Se prioriza tener primero una funcionalidad real y comprobable antes de añadir nuevas capas de complejidad.

Especialmente en la navegación GPS, se pretende realizar una **prueba física real** antes de continuar desarrollando las funcionalidades posteriores.

---

# 📌 Estado del proyecto

**Versión:** Alpha 0.1
**Estado:** 🟡 En desarrollo
**Ruta de prueba:** Parque del Alamillo, Sevilla
**Navegación GPS:** 🟢 Funcional en prototipo
**Base de datos:** 🔴 Pendiente de integración
**API/backend:** 🔴 Pendiente
**Offline:** 🔴 Pendiente
**Gamificación:** 🔴 Pendiente

### Próximo objetivo

> **Completar las pestañas MAPA y RUTAS y preparar el proyecto para continuar posteriormente con el seguimiento real de la ruta.**

---

## 👨‍💻 Desarrollo

Proyecto en desarrollo.

La arquitectura, funcionalidades y roadmap irán evolucionando progresivamente a medida que se validen las distintas fases del proyecto.
