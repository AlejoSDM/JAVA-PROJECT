package modelo;

public class Marca {

    private int id;
    private String marca;

    // Se usa al crear una marca nueva
    public Marca(String marca) {
        this.marca = marca;
    }

    public Marca(int id, String marca) {
        this.id = id;
        this.marca = marca;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
    @Override
    public String toString() {
        return """
               ID:       %s
               NOMBRE:   %s
               """.formatted(id, marca);
    }

}
