import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String identificacion;
    private String tipoTransaccion;
    private LocalTime horaLlegada;
    private boolean prioridad;
    
    private static final String[] TRANSACCIONES_VALIDAS = {"Depósito", "Retiro", "Consulta", "Pago"};
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    
    // Constructor completo
    public Cliente(String nombre, String identificacion, String tipoTransaccion, 
                   LocalTime horaLlegada, boolean prioridad) {
        setNombre(nombre);
        setIdentificacion(identificacion);
        setTipoTransaccion(tipoTransaccion);
        setHoraLlegada(horaLlegada);
        this.prioridad = prioridad;
    }
    
    // Constructor sin prioridad
    public Cliente(String nombre, String identificacion, String tipoTransaccion, LocalTime horaLlegada) {
        this(nombre, identificacion, tipoTransaccion, horaLlegada, false);
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
    public String getTipoTransaccion() { return tipoTransaccion; }
    public LocalTime getHoraLlegada() { return horaLlegada; }
    public boolean getPrioridad() { return prioridad; }
    
    // Setters con validaciones
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }
    
    public void setIdentificacion(String identificacion) {
        if (identificacion == null || !identificacion.matches("\\d{7,10}")) {
            throw new IllegalArgumentException("Error: Cédula inválida (debe tener 7-10 dígitos numéricos)");
        }
        this.identificacion = identificacion;
    }
    
    public void setTipoTransaccion(String tipoTransaccion) {
        boolean valido = false;
        for (String tipo : TRANSACCIONES_VALIDAS) {
            if (tipo.equalsIgnoreCase(tipoTransaccion)) {
                valido = true;
                break;
            }
        }
        if (!valido) {
            throw new IllegalArgumentException("Error: Tipo de transacción inválido. Use: Depósito, Retiro, Consulta, Pago");
        }
        // Capitalizar primera letra
        this.tipoTransaccion = tipoTransaccion.substring(0, 1).toUpperCase() + 
                               tipoTransaccion.substring(1).toLowerCase();
    }
    
    public void setHoraLlegada(LocalTime horaLlegada) {
        if (horaLlegada == null) {
            throw new IllegalArgumentException("Error: La hora de llegada no puede ser nula");
        }
        this.horaLlegada = horaLlegada;
    }
    
    // Método estático para parsear hora desde String
    public static LocalTime parseHora(String horaStr) throws DateTimeParseException {
        try {
            return LocalTime.parse(horaStr, FORMATO_HORA);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Error: Formato de hora inválido. Use HH:MM (ejemplo: 14:30)", 
                                            horaStr, e.getErrorIndex());
        }
    }
    
    @Override
    public String toString() {
        String prioridadStr = prioridad ? " [ADULTO MAYOR/DISCAPACITADO]" : "";
        return String.format("Cliente: %s | Cédula: %s | Transacción: %s | Hora: %s%s",
                nombre, identificacion, tipoTransaccion, 
                horaLlegada.format(FORMATO_HORA), prioridadStr);
    }
}