package modelo;

import java.util.UUID;
import java.util.function.Supplier;

public class Empleado {
    
    private final String id;
    private final String nombre;
    private final String telefono;

    public Empleado(String nombre, String telefono) {
        this.id = generarID.get();
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
      //SUPPLIER: generar un sku automatico cuando se cree un producto nuevo.
    private static final Supplier<String> generarID=()-> 
            "ID-"+UUID.randomUUID().toString().substring(0,6).toUpperCase();
    
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
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
