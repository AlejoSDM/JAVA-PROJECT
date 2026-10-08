package modelo;

import java.util.UUID;
import java.util.function.Supplier;

public class Cliente {
    
    private final String id;
    private final String nombre;
    private final String cedula;
    private final String correo;
    private final String telefono;

    public Cliente(String nombre, String cedula, String correo, String telefono) {
        this.id = generarID.get();
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
    }
    
      //SUPPLIER: generar un sku automatico cuando se cree un producto nuevo.
    private static final Supplier<String> generarID=()-> 
            "ID-"+UUID.randomUUID().toString().substring(0,4).toUpperCase();
    
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
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
