package modelo;

public class Empleado extends Persona {
    
    private int id;

    public Empleado(String nombre, String telefono) {
        super(nombre, telefono);
    }

    public Empleado(int id, String nombre, String telefono) {
        super(nombre, telefono);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return """
               ID:       %s
               NOMBRE:   %s
               TELÉFONO: %s
               """.formatted(id, getNombre(), getTelefono());
    }
}
