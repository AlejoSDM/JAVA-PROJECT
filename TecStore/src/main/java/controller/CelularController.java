package controller;

import Dao.CelularCRUD;
import java.sql.SQLException;
import java.util.List;
import modelo.Celular;
import view.Menu;

public class CelularController {

    private final CelularCRUD celularCRUD;
    private final Menu menu;

    public CelularController(CelularCRUD celularCRUD, Menu menu) {
        this.celularCRUD = celularCRUD;
        this.menu = menu;
    }

    // REGISTRAR
    public boolean registrar(Celular celular) throws SQLException {
        validarCelular(celular);
        return celularCRUD.insertar(celular);
    }

    // LISTAR
    public List<Celular> listar() throws SQLException {
        return celularCRUD.listar();
    }

    // ACTUALIZAR
    public boolean actualizar(Celular celular) throws SQLException {
        validarCelular(celular);
        validarId(celular.getId());

        return celularCRUD.actualizar(celular);
    }

    // ELIMINAR
    public boolean eliminar(int id) throws SQLException {
        validarId(id);
        return celularCRUD.eliminar(id);
    }

    // VALIDAR ID
    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "Debes ingresar un ID válido."
            );
        }
    }

    // VALIDAR DATOS DEL CELULAR
    private void validarCelular(Celular celular) {
        if (celular == null) {
            throw new IllegalArgumentException(
                "El celular no puede ser nulo."
            );
        }

        if (celular.getMarca() == null) {
            throw new IllegalArgumentException(
                "Debes seleccionar una marca."
            );
        }

        if (celular.getModelo() == null
                || celular.getModelo().isBlank()) {
            throw new IllegalArgumentException(
                "El modelo no puede estar vacío."
            );
        }

        if (celular.getSistemaop() == null
                || celular.getGama() == null) {
            throw new IllegalArgumentException(
                "Debes seleccionar el sistema operativo y la gama."
            );
        }

        if (!Double.isFinite(celular.getPrecio())
                || celular.getPrecio() < 0) {
            throw new IllegalArgumentException(
                "El precio debe ser válido y no negativo."
            );
        }

        if (celular.getStock() < 0
                || celular.getStock_minimo() < 0) {
            throw new IllegalArgumentException(
                "El stock no puede ser negativo."
            );
        }
    }

    // MENÚ DE GESTIÓN
    public void menu() {
        int op;

        do {
            op = menu.mostrarMenuCelulares();

            try {
                switch (op) {

                    case 1 -> {
                        List<Celular> celulares = listar();

                        System.out.println("\n=== CELULARES ===");

                        if (celulares.isEmpty()) {
                            System.out.println(
                                "No hay celulares registrados."
                            );
                        } else {
                            celulares.forEach(System.out::println);
                        }
                    }

                    case 2 -> {
                        System.out.println(
                            "El registro necesita capturar los datos "
                            + "desde la vista."
                        );
                    }

                    case 3 -> {
                        System.out.println(
                            "La actualización necesita capturar el ID "
                            + "y los nuevos datos desde la vista."
                        );
                    }

                    case 4 -> {
                        System.out.println(
                            "Para eliminar, llama al método eliminar(id) "
                            + "con el ID ingresado en la vista."
                        );
                    }

                    case 0 ->
                        System.out.println("Volviendo al administrador...");

                    default ->
                        System.out.println("Opción no válida.");
                }

            } catch (SQLException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (op != 0);
    }
}
