package controller;

import view.Menu;
import view.Validaciones;

public class GestionGeneral {

    private static final String CLAVE_ADMINISTRADOR = "123";

    private final Menu menu;
    private final CelularController celularController;
    private final Validaciones validaciones = new Validaciones();
    private final MarcaController marcaController;
    private final EmpleadoController empleadoController;
    private final ClienteController clienteController;
    private final CompraController compraController;
    private final ReporteController reporteController;

    public GestionGeneral(
            Menu menu,
            CelularController celularController,
            MarcaController marcaController,
            EmpleadoController empleadoController,
            ClienteController clienteController,
            CompraController compraController,
            ReporteController reporteController
    ) {
        this.menu = menu;
        this.celularController = celularController;
        this.marcaController = marcaController;
        this.empleadoController = empleadoController;
        this.clienteController = clienteController;
        this.compraController = compraController;
        this.reporteController = reporteController;
    }

    public void iniciar() {
        int opcion;

        do {
            opcion = menu.mostrarMenuPrincipal();

            switch (opcion) {
                case 1 -> {
                    if (autenticarAdministrador()) {
                        mostrarMenuAdministrador();
                    }
                }
                case 2 -> compraController.menu();
                case 3 -> reporteController.menu();
                case 0 -> System.out.println("Saliendo de Tec Store...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private boolean autenticarAdministrador() {
        String clave = validaciones.validarTexto(
                "Ingrese la contraseña de administrador: "
        );

        if (CLAVE_ADMINISTRADOR.equals(clave)) {
            System.out.println("Acceso permitido.");
            return true;
        }

        System.out.println("Contraseña incorrecta.");
        return false;
    }

    private void mostrarMenuAdministrador() {
        int opcion;

        do {
            opcion = menu.mostrarMenuAdministrador();

            switch (opcion) {
                case 1 -> celularController.menu();
                case 2 -> marcaController.gestionarMarcas();
                case 3 -> empleadoController.menu();
                case 4 -> clienteController.menu();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
}