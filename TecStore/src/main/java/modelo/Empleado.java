package modelo;

public class Empleado {
    
    private int id;
    private String nombre;
    private String telefono;

    public Empleado(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Empleado(int id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
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
               TELÉFONO: %s
               """.formatted(id, nombre, telefono);
    }
}
