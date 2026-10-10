package controller;

import java.sql.SQLException;
import java.util.List;
import modelo.Celular;
import view.Menu;

public class CompraController {

    private final Menu menu;
    private final CelularController celularController;
    private final GestionVenta gestionVenta;

    public CompraController(
            Menu menu,
            CelularController celularController
    ) {
        this.menu = menu;
        this.celularController = celularController;
        this.gestionVenta = new GestionVenta();
    }

    public void menu() {
        int opcion;

        do {
            opcion = menu.mostrarMenuCompraCliente();

            switch (opcion) {
                case 1 -> mostrarCelulares();
                case 2 -> gestionVenta.realizarCompra();
                case 0 -> System.out.println(
                        "Volviendo al menú principal..."
                );
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void mostrarCelulares() {
        try {
            List<Celular> celulares = celularController.listar();

            if (celulares.isEmpty()) {
                System.out.println("No hay celulares disponibles.");
                return;
            }

            System.out.println("\n=== CELULARES DISPONIBLES ===");
            celulares.forEach(System.out::println);

        } catch (SQLException e) {
            System.out.println(
                    "No se pudieron cargar los celulares: " + e.getMessage()
            );
        }
    }
}