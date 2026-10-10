package controller;

import Dao.CelularCRUD;
import java.sql.SQLException;
import java.util.List;
import modelo.Celular;
import view.IngresoDatosCelular;
import view.Menu;

public class CelularController {

    private final CelularCRUD celularCRUD;
    private final Menu menu;
    private final IngresoDatosCelular ingresoDatos = new IngresoDatosCelular();

    public CelularController(CelularCRUD celularCRUD, Menu menu) {
        this.celularCRUD = celularCRUD;
        this.menu = menu;
    }

    public boolean registrar(Celular celular) throws SQLException {
        validarCelular(celular);
        return celularCRUD.insertar(celular);
    }

    public List<Celular> listar() throws SQLException {
        return celularCRUD.listar();
    }

    public boolean actualizar(Celular celular) throws SQLException {
        validarCelular(celular);
        validarId(celular.getId());
        return celularCRUD.actualizar(celular);
    }

    public boolean eliminar(int id) throws SQLException {
        validarId(id);
        return celularCRUD.eliminar(id);
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Debes ingresar un ID válido.");
        }
    }

    private void validarCelular(Celular celular) {
        if (celular == null) {
            throw new IllegalArgumentException("El celular no puede ser nulo.");
        }

        if (celular.getMarca() == null || celular.getMarca().getId() <= 0) {
            throw new IllegalArgumentException("Debes seleccionar una marca válida.");
        }

        if (celular.getModelo() == null || celular.getModelo().isBlank()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }

        if (celular.getSistemaop() == null || celular.getGama() == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar el sistema operativo y la gama."
            );
        }

        if (!Double.isFinite(celular.getPrecio()) || celular.getPrecio() <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser válido y mayor que cero."
            );
        }

        if (celular.getStock() <= 0) {
            throw new IllegalArgumentException(
                    "El stock inicial debe ser mayor que cero."
            );
        }

        if (celular.getStock_minimo() < 0) {
            throw new IllegalArgumentException(
                    "El stock mínimo no puede ser negativo."
            );
        }
    }

    public void menu() {
        int opcion;

        do {
            opcion = menu.mostrarMenuCelulares();

            try {
                switch (opcion) {
                    case 1 -> mostrarCelulares();
                    case 2 -> agregarCelular();
                    case 3 -> actualizarCelular();
                    case 4 -> eliminarCelular();
                    case 0 -> System.out.println("Volviendo al administrador...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException | RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarCelulares() throws SQLException {
        List<Celular> celulares = listar();

        System.out.println("\n=== CELULARES ===");

        if (celulares.isEmpty()) {
            System.out.println("No hay celulares registrados.");
            return;
        }

        celulares.forEach(System.out::println);
    }

    private void agregarCelular() throws SQLException {
        System.out.println("\n=== AGREGAR CELULAR ===");

        Celular celular = ingresoDatos.ingresoDatos();

        if (registrar(celular)) {
            System.out.println("Celular registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el celular.");
        }
    }

    private void actualizarCelular() throws SQLException {
        System.out.println("\n=== ACTUALIZAR CELULAR ===");

        int id = ingresoId("ID del celular que deseas actualizar: ");
        Celular celular = ingresoDatos.ingresoDatos();
        celular.setId(id);

        if (actualizar(celular)) {
            System.out.println("Celular actualizado correctamente.");
        } else {
            System.out.println("No se encontró el celular o no se pudo actualizar.");
        }
    }

    private void eliminarCelular() throws SQLException {
        System.out.println("\n=== ELIMINAR CELULAR ===");

        int id = ingresoId("ID del celular que deseas eliminar: ");

        if (eliminar(id)) {
            System.out.println("Celular eliminado correctamente.");
        } else {
            System.out.println("No se encontró el celular o no se pudo eliminar.");
        }
    }
    
    /*ingresoId solo sirve para pedir y validar el ID del celular antes
    de actualizarlo o eliminarlo. Evita repetir la llamada a validarEntero.*/
    private int ingresoId(String mensaje) {
        // Se usa la validación del menú para leer un entero.
        return new view.Validaciones().validarEntero(mensaje);
    }
}