package modelo;

public class Cliente {
    
    private int id;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;

    public Cliente(String nombre, String cedula, String correo, String telefono) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Cliente(int id, String nombre, String cedula, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
    }

    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
 
    @Override
    public String toString() {
        return """
               ID:       %s
               NOMBRE:   %s
               CÉDULA:   %s
               CORREO:   %s
               TELÉFONO: %s
               """.formatted(id, nombre, cedula, correo, telefono);
    }
}
