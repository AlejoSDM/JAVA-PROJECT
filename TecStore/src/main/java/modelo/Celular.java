package modelo;

public class Celular {
    private int id;
    private Marca marca;
    private String modelo;
    private SistemaOperativo sistemaop;
    private CategoriaGama gama;
    private double precio;
    private int stock_minimo;
    private int stock;

    public Celular(Marca marca, String modelo, SistemaOperativo sistemaop, CategoriaGama gama, double precio, int stock_minimo, int stock) {
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaop = sistemaop;
        this.gama = gama;
        this.precio = precio;
        this.stock_minimo = stock_minimo;
        this.stock = stock;
    }

    public Celular(int id, Marca marca, String modelo, SistemaOperativo sistemaop, CategoriaGama gama, double precio, int stock_minimo, int stock) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaop = sistemaop;
        this.gama = gama;
        this.precio = precio;
        this.stock_minimo = stock_minimo;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public SistemaOperativo getSistemaop() {
        return sistemaop;
    }

    public void setSistemaop(SistemaOperativo sistemaop) {
        this.sistemaop = sistemaop;
    }

    public CategoriaGama getGama() {
        return gama;
    }

    public void setGama(CategoriaGama gama) {
        this.gama = gama;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    
    @Override
    public String toString() {
        return """
               ID:               %s
               MARCA:            %s
               MODELO:           %s
               SISTEMAOPERATIVO: %s
               GAMA:             %s
               PRECIO:           %S
               STOCK:            %S
               STOCK MINIMO:     %S
               """.formatted(id, marca, modelo, sistemaop, gama, precio, stock, stock_minimo);
    }
}
