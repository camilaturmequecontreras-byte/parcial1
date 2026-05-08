
    import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class ColaBanco {
    private Queue<Cliente> cola;
    private static final String ARCHIVO_BACKUP = "data/cola_backup.dat";
    
    public ColaBanco() {
        this.cola = new LinkedList<>();
        cargarEstado(); // Cargar clientes guardados al iniciar
    }
    
    // Encolar cliente (al final de la cola - FIFO)
    public void encolar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Error: El cliente no puede ser nulo");
        }
        cola.offer(cliente); // offer() añade al final de la cola
        guardarEstado(); // Guardar automáticamente
        System.out.println(" Cliente agregado exitosamente. Posición: " + cola.size());
    }
    
    // Desencolar cliente (del inicio de la cola - FIFO)
    public Cliente desencolar() {
        Cliente cliente = cola.poll(); // poll() remueve del inicio
        if (cliente == null) {
            throw new RuntimeException("Error: No hay clientes en espera");
        }
        guardarEstado(); // Guardar automáticamente
        return cliente;
    }
    
    // Ver próximo cliente sin eliminar
    public Cliente verProximo() {
        Cliente cliente = cola.peek(); // peek() ve el primero sin remover
        if (cliente == null) {
            throw new RuntimeException("Error: No hay clientes en espera");
        }
        return cliente;
    }
    
    // Verificar si la cola está vacía
    public boolean estaVacia() {
        return cola.isEmpty();
    }
    
    // Obtener el tamaño de la cola
    public int tamaño() {
        return cola.size();
    }
    
    // Vaciar toda la cola
    public void vaciar() {
        cola.clear();
        guardarEstado();
        System.out.println(" Cola vaciada exitosamente");
    }
    
    // Mostrar todos los clientes en la cola
    public void mostrarTodos() {
        if (cola.isEmpty()) {
            System.out.println("📋 No hay clientes en espera");
            return;
        }
        
        System.out.println("\n=== CLIENTES EN ESPERA ===");
        int posicion = 1;
        for (Cliente cliente : cola) {
            System.out.println(posicion + ". " + cliente.toString());
            posicion++;
        }
        System.out.println("Total: " + cola.size() + " cliente(s)");
        System.out.println("=========================\n");
    }
    
    // Guardar estado de la cola en archivo (persistencia)
    private void guardarEstado() {
        try {
            // Crear directorio data si no existe
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Guardar usando serialización de Java
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_BACKUP))) {
                oos.writeObject(new LinkedList<>(cola)); // Guardar una copia
            }
        } catch (IOException e) {
            System.err.println(" Advertencia: No se pudo guardar el estado de la cola: " + e.getMessage());
        }
    }
    
    // Cargar estado de la cola desde archivo (persistencia)
    @SuppressWarnings("unchecked")
    private void cargarEstado() {
        File archivo = new File(ARCHIVO_BACKUP);
        if (!archivo.exists()) {
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_BACKUP))) {
            LinkedList<Cliente> colaCargada = (LinkedList<Cliente>) ois.readObject();
            if (colaCargada != null) {
                this.cola = colaCargada;
                if (!cola.isEmpty()) {
                    System.out.println("📂 Cargados " + cola.size() + " cliente(s) de sesión anterior");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ Advertencia: No se pudo cargar el estado anterior: " + e.getMessage());
        }
    }
}

