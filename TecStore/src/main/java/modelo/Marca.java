package modelo;

import java.util.UUID;
import java.util.function.Supplier;

public class Marca {

    private final String id;
    private final String marca;

    // Se usa al crear una marca nueva
    public Marca(String marca) {
        this.id = generarID.get();
        this.marca = marca;
    }

    // Se usa al recuperar una marca desde la base de datos
    public Marca(String id, String marca) {
        this.id = id;
        this.marca = marca;
    }
    
    
      //SUPPLIER: generar un sku automatico cuando se cree un producto nuevo.
    private static final Supplier<String> generarID=()-> 
            "ID-"+UUID.randomUUID().toString().substring(0,3).toUpperCase();

    public String getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }    
    
    @Override
    public String toString() {
        return """
               ID:       %s
               NOMBRE:   %s
               """.formatted(id, marca);
    }

}
