package controller; // Cambia el paquete si en tu proyecto tiene otro nombre

import Dao.MarcaCRUD;
import java.sql.SQLException;
import java.util.List;
import modelo.Marca;
import view.IngresoDatosMarca;
import view.Menu;
import view.Validaciones;

public class MarcaController {

    private final MarcaCRUD marcaCRUD = new MarcaCRUD();
    private final Menu menu = new Menu();
    private final Validaciones validaciones = new Validaciones();
    private final IngresoDatosMarca ingresoDatosMarca =
            new IngresoDatosMarca();

    public void gestionarMarcas() {
        int opcion;

        do {
            opcion = menu.mostrarMenuMarcas();

            switch (opcion) {
                case 1 -> listarMarcas();
                case 2 -> agregarMarca();
                case 3 -> actualizarMarca();
                case 4 -> eliminarMarca();
                case 0 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void listarMarcas() {
        try {
            List<Marca> marcas = marcaCRUD.listar();

            if (marcas.isEmpty()) {
                System.out.println("No hay marcas registradas.");
                return;
            }

            System.out.println("=== MARCAS REGISTRADAS ===");
            for (Marca marca : marcas) {
                System.out.println(marca);
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron listar las marcas: " + e.getMessage());
        }
    }

    private void agregarMarca() {
        try {
            Marca marca = ingresoDatosMarca.ingresoDatos();
            boolean insertada = marcaCRUD.insertar(marca);

            if (insertada) {
                System.out.println("Marca agregada correctamente.");
            } else {
                System.out.println("No se pudo agregar la marca.");
            }
        } catch (SQLException e) {
            System.out.println("No se pudo agregar la marca: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos: " + e.getMessage());
        }
    }

    private void actualizarMarca() {
        int id = validaciones.validarEntero("Ingrese el ID de la marca: ");
        String nombre = validaciones.validarTexto(
                "Ingrese el nuevo nombre de la marca: "
        );

        Marca marca = new Marca(id, nombre);

        try {
            boolean actualizada = marcaCRUD.actualizar(marca);

            if (actualizada) {
                System.out.println("Marca actualizada correctamente.");
            } else {
                System.out.println("No se encontró una marca con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("No se pudo actualizar la marca: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos: " + e.getMessage());
        }
    }

    private void eliminarMarca() {
        int id = validaciones.validarEntero("Ingrese el ID de la marca a eliminar: ");

        try {
            boolean eliminada = marcaCRUD.eliminar(id);

            if (eliminada) {
                System.out.println("Marca eliminada correctamente.");
            } else {
                System.out.println("No se encontró una marca con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar la marca: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido: " + e.getMessage());
        }
    }
}