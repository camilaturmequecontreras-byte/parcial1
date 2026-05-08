import org.junit.jupiter.api.*;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ColaBancoTest {
    private static ColaBanco colaBanco;
    private static Cliente cliente1;
    private static Cliente cliente2;
    
    @BeforeAll
    static void setUp() {
        colaBanco = new ColaBanco();
        cliente1 = new Cliente("Juan Pérez", "12345678", "Depósito", LocalTime.of(10, 30), false);
        cliente2 = new Cliente("María López", "87654321", "Retiro", LocalTime.of(10, 35), true);
    }
    
    @BeforeEach
    void limpiarCola() {
        // Vaciar la cola antes de cada prueba
        while (!colaBanco.estaVacia()) {
            try {
                colaBanco.desencolar();
            } catch (RuntimeException e) {
                break;
            }
        }
    }
    
    @Test
    @Order(1)
    void testEncolarCliente() {
        colaBanco.encolar(cliente1);
        assertEquals(1, colaBanco.tamaño());
        assertFalse(colaBanco.estaVacia());
    }
    
    @Test
    @Order(2)
    void testDesencolarCliente() {
        colaBanco.encolar(cliente1);
        colaBanco.encolar(cliente2);
        
        Cliente atendido = colaBanco.desencolar();
        assertNotNull(atendido);
        assertEquals(cliente1.getNombre(), atendido.getNombre());
        assertEquals(1, colaBanco.tamaño());
    }
    
    @Test
    @Order(3)
    void testVerProximoCliente() {
        colaBanco.encolar(cliente1);
        colaBanco.encolar(cliente2);
        
        Cliente proximo = colaBanco.verProximo();
        assertNotNull(proximo);
        assertEquals(cliente1.getNombre(), proximo.getNombre());
        assertEquals(2, colaBanco.tamaño()); // Verificar que no se elimina
    }
    
    @Test
    @Order(4)
    void testColaVacia() {
        assertTrue(colaBanco.estaVacia());
        assertEquals(0, colaBanco.tamaño());
    }
    
    @Test
    @Order(5)
    void testDesencolarColaVacia() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            colaBanco.desencolar();
        });
        assertTrue(exception.getMessage().contains("No hay clientes en espera"));
    }
    
    @Test
    @Order(6)
    void testVaciarCola() {
        colaBanco.encolar(cliente1);
        colaBanco.encolar(cliente2);
        assertEquals(2, colaBanco.tamaño());
        
        colaBanco.vaciar();
        assertTrue(colaBanco.estaVacia());
        assertEquals(0, colaBanco.tamaño());
    }
    
    @Test
    @Order(7)
    void testOrdenFIFO() {
        // Prueba estricta del orden FIFO (First In, First Out)
        Cliente primero = new Cliente("Primero", "11111111", "Consulta", LocalTime.of(9, 0), false);
        Cliente segundo = new Cliente("Segundo", "22222222", "Pago", LocalTime.of(9, 5), false);
        Cliente tercero = new Cliente("Tercero", "33333333", "Depósito", LocalTime.of(9, 10), false);
        
        colaBanco.encolar(primero);
        colaBanco.encolar(segundo);
        colaBanco.encolar(tercero);
        
        // Deben salir en el mismo orden que entraron
        Cliente salida1 = colaBanco.desencolar();
        Cliente salida2 = colaBanco.desencolar();
        Cliente salida3 = colaBanco.desencolar();
        
        assertEquals(primero.getNombre(), salida1.getNombre());
        assertEquals(segundo.getNombre(), salida2.getNombre());
        assertEquals(tercero.getNombre(), salida3.getNombre());
    }
    
    @Test
    @Order(8)
    void testMultiplesOperaciones() {
        // Probar múltiples operaciones consecutivas
        colaBanco.encolar(cliente1);
        colaBanco.encolar(cliente2);
        assertEquals(2, colaBanco.tamaño());
        
        Cliente atendido = colaBanco.desencolar();
        assertEquals(cliente1.getNombre(), atendido.getNombre());
        assertEquals(1, colaBanco.tamaño());
        
        Cliente proximo = colaBanco.verProximo();
        assertEquals(cliente2.getNombre(), proximo.getNombre());
        
        colaBanco.vaciar();
        assertTrue(colaBanco.estaVacia());
    }
}
