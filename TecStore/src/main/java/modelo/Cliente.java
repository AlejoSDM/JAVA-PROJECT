package modelo;

public class Cliente extends Persona {
    
    private int id;
    private String cedula;
    private String correo;

    public Cliente(String nombre, String cedula, String correo, String telefono) {
        super(nombre, telefono);
        this.cedula = cedula;
        this.correo = correo;
    }

    public Cliente(int id, String nombre, String cedula, String correo, String telefono) {
        super(nombre, telefono);
        this.id = id;
        this.cedula = cedula;
        this.correo = correo;
    }

    
    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return """
               ID:       %s
               NOMBRE:   %s
               CÉDULA:   %s
               CORREO:   %s
               TELÉFONO: %s
                   """.formatted(
                id,
                getNombre(),
                cedula,
                correo,
                getTelefono()
            );
    }
}
