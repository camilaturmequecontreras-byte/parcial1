# parcial1
 Nombre del Proyecto
Sistema de Gestión de Cola Bancaria - Implementación FIFO

 Descripción del Proyecto
Sistema desarrollado en Java para gestionar la cola de clientes en una sucursal bancaria. Implementa una estructura FIFO (First In, First Out) que garantiza que los clientes sean atendidos en estricto orden de llegada.

Características principales:
Gestión completa de cola (encolar, desencolar, consultar)

- Persistencia de datos (guarda automáticamente el estado)
- Validación de datos de clientes (cédula, nombre, transacciones)
- Manejo robusto de errores y excepciones
- Interfaz de consola amigable e intuitiva
- Pruebas unitarias para verificar el comportamiento FIFO

Tecnologías utilizadas:
- Java JDK 11+
- Estructuras de datos nativas (Queue, LinkedList)
- Serialización de Java para persistencia


CAPTURAS DE PRUEBA
<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/70dd2ed3-77db-4395-ac04-6def1b57dc2d" />
<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/16dd2887-17f2-4fd8-94ac-c024363164c8" />

EXPLICACION DE LA (FIFO)

ENTRADA (llegada de clientes)          SALIDA (atención)
      ↓                                    ↓
   [Cliente 1] ← Llega primero      Sale → [Cliente 1]
   [Cliente 2]                                     ↓
   [Cliente 3]                                 [Cliente 2] ← Sale segundo
                                                     ↓
                                                 [Cliente 3] ← Sale tercero

Orden de llegada: 1 → 2 → 3
Orden de atención: 1 → 2 → 3 (MISMO orden)


DIAGRAMA

┌─────────────────────────────────────────┐
│                     MAIN                │
├─────────────────────────────────────────┤
│ - colaBanco: ColaBanco                  │
│ - scanner: Scanner                      │
├─────────────────────────────────────────┤
│ + main(args: String[]): void            │
│ - mostrarMenu(): void                   │
│ - procesarOpcion(opcion: int): void     │
│ - agregarCliente(): void                │
│ - atenderCliente(): void                │
│ - verProximoCliente(): void             │
│ - consultarCantidad(): void             │
│ - vaciarCola(): void                    │
└───────────────────┬─────────────────────┘
                    │
                    │ usa
                    ↓
┌─────────────────────────────────────┐
│             COLABANCO               │
├─────────────────────────────────────┤
│ - cola: Queue<Cliente>              │
│ - ARCHIVO_BACKUP: String            │
├─────────────────────────────────────┤
│ + ColaBanco()                       │
│ + encolar(cliente: Cliente): void   │
│ + desencolar(): Cliente             │
│ + verProximo(): Cliente             │
│ + estaVacia(): boolean              │
│ + tamaño(): int                     │
│ + vaciar(): void                    │
│ + mostrarTodos(): void              │
│ - guardarEstado(): void             │
│ - cargarEstado(): void              │
└─────────────────────────────────────┘
                    │
                    │ contiene
                    ↓
┌───────────────────────────────────┐
│                 CLIENTE           │
├───────────────────────────────────┤
│ - nombre: String                  │
│ - identificacion: String          │
│ - tipoTransaccion: String         │
│ - horaLlegada: LocalTime          │
│ - prioridad: boolean              │
├───────────────────────────────────┤
│ + Cliente                         │
│  (nombre, id, tipo,               │
│hora, prioridad)                   │
│ + getters() / setters()           │
│ + parseHora(horaStr: String):     │
│LocalTime                          │
│ + toString(): String              │
└───────────────────────────────────┘

                    <<interface>>
                    ┌─────────────┐
                    │   Queue<T>  │
                    ├─────────────┤
                    │ + offer(e)  │
                    │ + poll()    │
                    │ + peek()    │
                    │ + clear()   │
                    │ + size()    │
                    └──────┬──────┘
                           │
                           △
                           │
                    ┌──────┴──────┐
                    │ LinkedList  │
                    └─────────────┘
