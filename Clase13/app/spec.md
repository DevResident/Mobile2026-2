# Spec: M6-01 — Central de Aviones
> Actividad final · Unidad VI · Desarrollo de Aplicaciones Móviles (Clave 1668)
> SUAYED · FCA · UNAM · Semestre 2026-2
> Asesor: Cristian Cardoso Arellano

---

## 1. Contexto y objetivo

Implementar una simulación de **colisión de aviones en un aeropuerto** usando
**Java puro + Android Studio** (sin Jetpack Compose).

El alumno deberá practicar estructuras de datos en Java dentro del entorno Android,
aplicando colecciones (arreglos, HashMap, conjuntos) vistas en la Unidad VI.

- **Fecha de entrega:** 10 de junio de 2026
- **Valor:** 16 puntos del semestre (+ 5 puntos adicionales sobre el examen parcial si
  el desarrollo es efectivo)
- **Tiempo estimado:** 12 horas
- **Actividad previa sugerida:** M1-01 (Android Studio instalado y funcional)

---

## 2. Recursos del profesor

| Recurso | URL |
|---|---|
| Ejemplo visual del problema | https://xorzy.github.io/coc/airplane/ |
| Proyecto base (descargar) | https://github.com/CristianCardosoA/AndroidFCA24-1/tree/main/Aviones |
| Casos de prueba | Proporcionados por el profesor en clase |

---

## 3. Descripción del problema

Un **grid 2-D** representa el espacio aéreo de un aeropuerto.
Cada celda puede estar vacía o contener un avión con una **dirección de vuelo**.

### 3.1 Avión

| Propiedad | Tipo sugerido | Descripción |
|---|---|---|
| `id` | `int` | Identificador único |
| `x` | `int` | Columna actual en el grid |
| `y` | `int` | Fila actual en el grid |
| `direction` | `enum Direction` | Norte, Sur, Este, Oeste (N/S/E/W) |
| `collided` | `boolean` | Indica si el avión ya colisionó |

### 3.2 Paso de simulación

En cada **paso**:
1. Cada avión avanza **una celda** en su dirección.
2. Si dos o más aviones ocupan la misma celda → **colisión** (ambos se marcan como
   `collided = true`).
3. El contador de colisiones se incrementa por cada par que choca.

### 3.3 Navegación

El usuario puede avanzar o retroceder paso a paso; el estado debe ser reproducible
en ambos sentidos (guarda un historial de estados o recalcula desde el inicio).

---

## 4. Requerimientos funcionales

| ID | Requisito |
|---|---|
| F-01 | Grid visible en pantalla (puede ser un `GridView`, `RecyclerView` o `Canvas`) |
| F-02 | Mostrar cada avión en su posición actual con un ícono o letra que indique dirección |
| F-03 | Mostrar el **número de paso** actual |
| F-04 | Mostrar el **número de colisiones** acumuladas |
| F-05 | Resaltar visualmente las celdas con colisión |
| F-06 | Botón **"Paso siguiente"** — avanza un paso la simulación |
| F-07 | Botón **"Paso anterior"** — retrocede un paso la simulación |
| F-08 | Cargar los casos de prueba del profesor (array hardcodeado o archivo de texto) |

---

## 5. Requerimientos no funcionales

| ID | Requisito |
|---|---|
| NF-01 | Lenguaje: **Java** (no Kotlin, no Jetpack Compose) |
| NF-02 | IDE: **Android Studio** |
| NF-03 | Basarse en el proyecto base del profesor |
| NF-04 | El código debe ser legible (nombres de variables en inglés o español, comentarios) |
| NF-05 | Sin dependencias externas más allá del SDK de Android |

---

## 6. Arquitectura sugerida

```
app/
└── src/main/
    ├── java/.../
    │   ├── model/
    │   │   ├── Airplane.java       # Entidad avión
    │   │   ├── Direction.java      # Enum N/S/E/W
    │   │   └── SimulationState.java # Snapshot de un paso
    │   ├── engine/
    │   │   └── SimulationEngine.java # Lógica de avance, retroceso, colisión
    │   └── ui/
    │       ├── MainActivity.java
    │       └── GridView.java       # Vista personalizada del grid (opcional)
    └── res/
        └── layout/
            └── activity_main.xml
```

---

## 7. Estructura de datos clave

```java
// Snapshot de un paso — para poder retroceder
class SimulationState {
    int step;
    int collisions;
    ArrayList<Airplane> airplanes; // copia profunda
}

// Historial completo
ArrayList<SimulationState> history = new ArrayList<>();
```

---

## 8. Criterios de evaluación

| Criterio | Puntos máximos |
|---|---|
| Funcionalidad (F-01 a F-08 funcionando) | 8 |
| UI (grid legible, botones accesibles, indicadores visibles) | 1 |
| Resolución correcta del algoritmo de colisión en código | 1 |
| **Total** | **10** |

> Los 10 puntos se escalan al valor de 16 pts del semestre.
> El desarrollo efectivo agrega 5 pts sobre el examen parcial.

---

## 9. Entregables

Subir al **repositorio GitHub del alumno** y enlazar en la plataforma Moodle SUAyED:

1. `android_2026_m6_01.pdf` — portada, objetivo, desarrollo, capturas de pantalla,
   conclusión. Citar fuentes en formato **APA 7**.
2. Código fuente completo (proyecto Android Studio).

### Formato del PDF

- Portada (nombre, materia, actividad, fecha)
- Objetivo de la actividad
- Desarrollo (explicación del algoritmo y decisiones de diseño)
- Capturas de pantalla de la app corriendo
- Conclusión
- Referencias en APA 7

---

## 10. Lineamientos generales del curso

- No se acepta entrega fuera de tiempo sin justificación.
- No usar **Jetpack Compose** en ninguna parte del código.
- El uso de IA debe citarse; el copy-paste de código sin interpretación = calificación nula.
- Citar fuentes con **APA 7**.
- Entrega únicamente por la plataforma Moodle oficial (con enlace a GitHub).