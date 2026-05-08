import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ColaBanco colaBanco = new ColaBanco();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println(" ====================================");
        System.out.println("   SISTEMA DE COLA DEL BANCO POPULAR");
        System.out.println("   Implementación FIFO (Primero en llegar)");
        System.out.println("==================================== 🏦");
        
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 7);
        
        System.out.println("\n ¡Gracias por usar el sistema! Hasta luego.");
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Agregar cliente (tomar turno)");
        System.out.println("2.  Atender siguiente cliente (FIFO)");
        System.out.println("3.  Ver próximo cliente en espera");
        System.out.println("4.  Mostrar todos los clientes en cola");
        System.out.println("5.  Consultar cantidad de clientes en espera");
        System.out.println("6.  Vaciar la cola (emergencia)");
        System.out.println("7.  Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next(); // Limpiar buffer
            return -1;
        }
    }
    
    private static void procesarOpcion(int opcion) {
        scanner.nextLine(); // Limpiar buffer
        
        switch (opcion) {
            case 1:
                agregarCliente();
                break;
            case 2:
                atenderCliente();
                break;
            case 3:
                verProximoCliente();
                break;
            case 4:
                colaBanco.mostrarTodos();
                break;
            case 5:
                consultarCantidad();
                break;
            case 6:
                vaciarCola();
                break;
            case 7:
                // Salir
                break;
            default:
                System.out.println(" Opción inválida. Por favor, seleccione una opción del 1 al 7.");
        }
    }
    
    private static void agregarCliente() {
        System.out.println("\n=== NUEVO CLIENTE ===");
        
        try {
            System.out.print("Ingrese nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Ingrese identificación (cédula - solo números): ");
            String identificacion = scanner.nextLine();
            
            System.out.print("Ingrese tipo de transacción (Depósito/Retiro/Consulta/Pago): ");
            String tipo = scanner.nextLine();
            
            System.out.print("Ingrese hora de llegada (HH:MM): ");
            String horaStr = scanner.nextLine();
            
            System.out.print("¿Cliente prioritario? (adulto mayor/discapacitado) (s/n): ");
            String prioridadStr = scanner.nextLine();
            boolean prioridad = prioridadStr.equalsIgnoreCase("s");
            
            LocalTime hora = Cliente.parseHora(horaStr);
            Cliente nuevoCliente = new Cliente(nombre, identificacion, tipo, hora, prioridad);
            
            colaBanco.encolar(nuevoCliente);
            
        } catch (IllegalArgumentException | java.time.format.DateTimeParseException e) {
            System.out.println(" " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Error inesperado: " + e.getMessage());
        }
    }
    
    private static void atenderCliente() {
        System.out.println("\n=== ATENDIENDO CLIENTE ===");
        try {
            Cliente cliente = colaBanco.desencolar();
            System.out.println(" ATENDIENDO A: " + cliente);
            System.out.println(" Clientes restantes en cola: " + colaBanco.tamaño());
        } catch (RuntimeException e) {
            System.out.println(" " + e.getMessage());
        }
    }
    
    private static void verProximoCliente() {
        System.out.println("\n=== PRÓXIMO CLIENTE ===");
        try {
            Cliente cliente = colaBanco.verProximo();
            System.out.println(" Próximo en ser atendido: " + cliente);
        } catch (RuntimeException e) {
            System.out.println(" " + e.getMessage());
        }
    }
    
    private static void consultarCantidad() {
        int cantidad = colaBanco.tamaño();
        System.out.println("\n Clientes en espera: " + cantidad);
        if (cantidad > 0) {
            System.out.println("⏱ Tiempo estimado de espera: ~" + (cantidad * 3) + " minutos");
        }
    }
    
    private static void vaciarCola() {
        System.out.print("\n ¿Está seguro de vaciar toda la cola? (s/n): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("s")) {
            colaBanco.vaciar();
        } else {
            System.out.println("❌ Operación cancelada.");
        }
    }
}
