package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ventas {

    private int id;
    private Cliente cliente;
    private int idEmpleado;
    private LocalDateTime fecha;
    private double total;
    private List<DetalleVentas> detalles;

    // Para registrar una venta nueva
    public Ventas(Cliente cliente, int idEmpleado) {
        this.cliente = cliente;
        this.idEmpleado = idEmpleado;
        this.fecha = LocalDateTime.now();
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }

    // Para cargar una venta desde la base de datos
    public Ventas(int id, Cliente cliente, int idEmpleado,
                 LocalDateTime fecha, double total) {
        this.id = id;
        this.cliente = cliente;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.total = total;
        this.detalles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVentas> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentas> detalles) {
        this.detalles = detalles;
    }

    public void agregarDetalle(DetalleVentas detalle) {
        detalles.add(detalle);
    }

    @Override
    public String toString() {
        return """
               ID:        %s
               CLIENTE:   %s
               EMPLEADO:  %s
               FECHA:     %s
               TOTAL:     %s
               """.formatted(
                id,
                cliente.getNombre(),
                idEmpleado,
                fecha,
                total
        );
    }
}