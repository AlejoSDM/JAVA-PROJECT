package controller;

import Dao.ClienteCRUD;
import java.sql.SQLException;
import java.util.List;
import modelo.Cliente;
import view.IngresoDatosCliente;
import view.Menu;
import view.Validaciones;

public class ClienteController {

    private final ClienteCRUD clienteCRUD;
    private final Menu menu;
    private final IngresoDatosCliente ingresoDatos =
            new IngresoDatosCliente();
    private final Validaciones validaciones = new Validaciones();

    public ClienteController(ClienteCRUD clienteCRUD, Menu menu) {
        this.clienteCRUD = clienteCRUD;
        this.menu = menu;
    }

    public void menu() {
        int opcion;

        do {
            opcion = menu.mostrarMenuClientes();

            try {
                switch (opcion) {
                    case 1 -> listarClientes();
                    case 2 -> agregarCliente();
                    case 3 -> actualizarCliente();
                    case 4 -> eliminarCliente();
                    case 0 -> System.out.println("Volviendo al administrador...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarClientes() throws SQLException {
        List<Cliente> clientes = clienteCRUD.listar();

        System.out.println("\n=== CLIENTES ===");

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        clientes.forEach(System.out::println);
    }

    private void agregarCliente() throws SQLException {
        System.out.println("\n=== AGREGAR CLIENTE ===");

        Cliente cliente = ingresoDatos.ingresoDatos();

        if (clienteCRUD.insertar(cliente)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    private void actualizarCliente() throws SQLException {
        System.out.println("\n=== ACTUALIZAR CLIENTE ===");

        int id = validaciones.validarEntero(
                "Ingrese el ID del cliente que desea actualizar: "
        );

        Cliente datos = ingresoDatos.ingresoDatos();
        Cliente cliente = new Cliente(
                id,
                datos.getNombre(),
                datos.getCedula(),
                datos.getCorreo(),
                datos.getTelefono()
        );

        if (clienteCRUD.actualizar(cliente)) {
            System.out.println("Cliente actualizado correctamente.");
        } else {
            System.out.println("No se encontró el cliente con ese ID.");
        }
    }

    private void eliminarCliente() throws SQLException {
        System.out.println("\n=== ELIMINAR CLIENTE ===");

        int id = validaciones.validarEntero(
                "Ingrese el ID del cliente que desea eliminar: "
        );

        if (clienteCRUD.eliminar(id)) {
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("No se encontró el cliente con ese ID.");
        }
    }
}