package controller;

import Dao.CelularCRUD;
import Dao.ClienteCRUD;
import Dao.EmpleadoCRUD;
import Dao.VentaDAO;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelo.Celular;
import modelo.Cliente;
import modelo.Empleado;
import view.Factura;
import view.IngresoDatosCliente;
import view.Validaciones;

public class GestionVenta {

    private final Validaciones validaciones = new Validaciones();
    private final IngresoDatosCliente ingresoDatosCliente =
            new IngresoDatosCliente();

    private final ClienteCRUD clienteCRUD = new ClienteCRUD();
    private final EmpleadoCRUD empleadoCRUD = new EmpleadoCRUD();
    private final CelularCRUD celularCRUD = new CelularCRUD();
    private final VentaDAO ventaDAO = new VentaDAO();
    private final Factura factura = new Factura();

    public void realizarCompra() {
        try {
            Cliente cliente = buscarORegistrarCliente();
            Empleado empleado = seleccionarEmpleado();

            if (empleado == null) {
                return;
            }

            Map<Integer, Integer> productos = seleccionarProductos();

            if (productos.isEmpty()) {
                System.out.println(
                        "No se agregaron productos. Compra cancelada."
                );
                return;
            }

            int ventaId = ventaDAO.registrarVenta(
                    cliente.getId(),
                    empleado.getId(),
                    productos
            );

            System.out.println(
                    "Compra registrada correctamente. Número de venta: "
                            + ventaId
            );

            mostrarFactura(ventaId, cliente, empleado, productos);

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(
                    "No se pudo completar la compra: " + e.getMessage()
            );
        }
    }

    private Cliente buscarORegistrarCliente() throws SQLException {
        String cedula = validaciones.validarTexto(
                "Ingrese la cédula del cliente: "
        );

        Cliente cliente = clienteCRUD.buscarPorCedula(cedula);

        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente.getNombre());
            return cliente;
        }

        System.out.println(
                "No se encontró un cliente con esa cédula. "
                        + "Vamos a registrarlo."
        );

        Cliente nuevoCliente = ingresoDatosCliente.ingresoDatos(cedula);
        clienteCRUD.insertar(nuevoCliente);

        // Recupera el cliente con el ID generado por la base de datos.
        cliente = clienteCRUD.buscarPorCedula(cedula);

        if (cliente == null) {
            throw new SQLException(
                    "El cliente se registró, pero no se pudo recuperar."
            );
        }

        System.out.println("Cliente registrado: " + cliente.getNombre());
        return cliente;
    }

    private Empleado seleccionarEmpleado() throws SQLException {
        List<Empleado> empleados = empleadoCRUD.listar();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return null;
        }

        System.out.println("\n=== EMPLEADOS ===");
        empleados.forEach(System.out::println);

        while (true) {
            int idEmpleado = validaciones.validarEntero(
                    "Ingrese el ID del empleado que atiende la venta: "
            );

            for (Empleado empleado : empleados) {
                if (empleado.getId() == idEmpleado) {
                    return empleado;
                }
            }

            System.out.println("No existe un empleado con ese ID.");
        }
    }

    private Map<Integer, Integer> seleccionarProductos()
            throws SQLException {

        List<Celular> celulares = celularCRUD.listar();
        Map<Integer, Integer> productos = new LinkedHashMap<>();
        Map<Integer, Integer> stockDisponible = new LinkedHashMap<>();

        for (Celular celular : celulares) {
            if (celular.getStock() > 0) {
                stockDisponible.put(celular.getId(), celular.getStock());
            }
        }

        if (stockDisponible.isEmpty()) {
            System.out.println("No hay celulares disponibles en stock.");
            return productos;
        }

        System.out.println("\n=== CELULARES DISPONIBLES ===");

        for (Celular celular : celulares) {
            if (celular.getStock() > 0) {
                System.out.println(celular);
            }
        }

        System.out.println(
                "Ingrese 0 como ID cuando termine de agregar productos."
        );

        while (true) {
            int idCelular = validaciones.validarEntero(
                    "ID del celular que desea agregar: "
            );

            if (idCelular == 0) {
                break;
            }

            Integer stock = stockDisponible.get(idCelular);

            if (stock == null) {
                System.out.println(
                        "Ese celular no existe en la lista o no tiene stock."
                );
                continue;
            }

            int cantidad = pedirCantidad();
            int cantidadAnterior = productos.getOrDefault(idCelular, 0);
            int cantidadTotal = cantidadAnterior + cantidad;

            if (cantidadTotal > stock) {
                System.out.println(
                        "Stock insuficiente. Disponible: " + stock
                                + "; ya agregado: " + cantidadAnterior + "."
                );
                continue;
            }

            productos.put(idCelular, cantidadTotal);
            System.out.println("Producto agregado a la compra.");
        }

        return productos;
    }

    private int pedirCantidad() {
        while (true) {
            int cantidad = validaciones.validarEntero("Cantidad: ");

            if (cantidad > 0) {
                return cantidad;
            }

            System.out.println("La cantidad debe ser mayor que cero.");
        }
    }

    private void mostrarFactura(
            int ventaId,
            Cliente cliente,
            Empleado empleado,
            Map<Integer, Integer> productos
    ) {
        try {
            List<Celular> celulares = celularCRUD.listar();

            factura.mostrar(
                    ventaId,
                    cliente,
                    empleado,
                    productos,
                    celulares
            );
        } catch (SQLException e) {
            System.out.println(
                    "La venta se registró, pero no se pudo mostrar "
                            + "la factura: " + e.getMessage()
            );
        }
    }
}