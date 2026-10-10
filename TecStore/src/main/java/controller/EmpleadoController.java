package controller;

import Dao.EmpleadoCRUD;
import java.sql.SQLException;
import java.util.List;
import modelo.Empleado;
import view.IngresoDatosEmpleado;
import view.Menu;
import view.Validaciones;

public class EmpleadoController {

    private final EmpleadoCRUD empleadoCRUD;
    private final Menu menu;
    private final IngresoDatosEmpleado ingresoDatos =
            new IngresoDatosEmpleado();
    private final Validaciones validaciones = new Validaciones();

    public EmpleadoController(EmpleadoCRUD empleadoCRUD, Menu menu) {
        this.empleadoCRUD = empleadoCRUD;
        this.menu = menu;
    }

    public void menu() {
        int opcion;

        do {
            opcion = menu.mostrarMenuEmpleados();

            try {
                switch (opcion) {
                    case 1 -> listarEmpleados();
                    case 2 -> agregarEmpleado();
                    case 3 -> actualizarEmpleado();
                    case 4 -> eliminarEmpleado();
                    case 0 -> System.out.println("Volviendo al administrador...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarEmpleados() throws SQLException {
        List<Empleado> empleados = empleadoCRUD.listar();

        System.out.println("\n=== EMPLEADOS ===");

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        empleados.forEach(System.out::println);
    }

    private void agregarEmpleado() throws SQLException {
        System.out.println("\n=== AGREGAR EMPLEADO ===");

        Empleado empleado = ingresoDatos.ingresoDatos();

        if (empleadoCRUD.insertar(empleado)) {
            System.out.println("Empleado registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el empleado.");
        }
    }

    private void actualizarEmpleado() throws SQLException {
        System.out.println("\n=== ACTUALIZAR EMPLEADO ===");

        int id = validaciones.validarEntero(
                "Ingrese el ID del empleado que desea actualizar: "
        );

        Empleado empleado = ingresoDatos.ingresoDatos();
        empleado = new Empleado(id, empleado.getNombre(), empleado.getTelefono());

        if (empleadoCRUD.actualizar(empleado)) {
            System.out.println("Empleado actualizado correctamente.");
        } else {
            System.out.println("No se encontró el empleado con ese ID.");
        }
    }

    private void eliminarEmpleado() throws SQLException {
        System.out.println("\n=== ELIMINAR EMPLEADO ===");

        int id = validaciones.validarEntero(
                "Ingrese el ID del empleado que desea eliminar: "
        );

        if (empleadoCRUD.eliminar(id)) {
            System.out.println("Empleado eliminado correctamente.");
        } else {
            System.out.println("No se encontró el empleado con ese ID.");
        }
    }
}