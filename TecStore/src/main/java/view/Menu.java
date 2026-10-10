package view;

public class Menu {

    private final Validaciones validaciones = new Validaciones();

    public int mostrarMenuPrincipal() {
        System.out.println("""
                === TEC STORE ===
                1. Administrador
                2. Comprar como cliente
                3. Reportes
                0. Salir
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }

    public int mostrarMenuAdministrador() {
        System.out.println("""
                === ADMINISTRADOR ===
                1. Gestión de celulares
                2. Gestión de marcas
                3. Gestión de empleados
                4. Gestión de clientes
                0. Volver
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }

    public int mostrarMenuCelulares() {
        System.out.println("""
                === GESTIÓN DE CELULARES ===
                1. Listar celulares
                2. Agregar celular
                3. Actualizar celular
                4. Eliminar celular
                0. Volver
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }

    public int mostrarMenuMarcas() {
        System.out.println("""
                === GESTIÓN DE MARCAS ===
                1. Listar marcas
                2. Agregar marca
                3. Actualizar marca
                4. Eliminar marca
                0. Volver
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }

    public int mostrarMenuEmpleados() {
        System.out.println("""
                === GESTIÓN DE EMPLEADOS ===
                1. Listar empleados
                2. Agregar empleado
                3. Actualizar empleado
                4. Eliminar empleado
                0. Volver
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }

    public int mostrarMenuClientes() {
        System.out.println("""
                === GESTIÓN DE CLIENTES ===
                1. Listar clientes
                2. Agregar cliente
                3. Actualizar cliente
                4. Eliminar cliente
                0. Volver
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }

    public int mostrarMenuCompraCliente() {
        System.out.println("""
                === COMPRA COMO CLIENTE ===
                1. Ver celulares
                2. Comprar
                0. Volver
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }



    public int mostrarMenuReportes() {
        System.out.println("""
                === COMPRA COMO CLIENTE ===
                1. Ver reportes
                2. Stock bajo
                3. Ventas totales
                0. Volver
                """);

        return validaciones.validarEntero("Seleccione una opción: ");
    }
}