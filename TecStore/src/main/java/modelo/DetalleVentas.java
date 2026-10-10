package modelo;

public class DetalleVentas {

    private int id;
    private Celular celular;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // Para crear un detalle nuevo.
    // Toma una copia del precio actual del celular.
    public DetalleVentas(Celular celular, int cantidad) {
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = celular.getPrecio();
        calcularSubtotal();
    }

    // Para cargar un detalle desde la base de datos.
    public DetalleVentas(int id, Celular celular, int cantidad,
                        double precioUnitario, double subtotal) {
        this.id = id;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
        this.precioUnitario = celular.getPrecio();
        calcularSubtotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    private void calcularSubtotal() {
        this.subtotal = cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return """
               CELULAR:         %s
               CANTIDAD:        %s
               PRECIO UNITARIO: %s
               SUBTOTAL:        %s
               """.formatted(
                celular.getMarca() + " " + celular.getModelo(),
                cantidad,
                precioUnitario,
                subtotal
        );
    }
}