package view;

import modelo.Cliente;

public class IngresoDatosCliente {

    private final Validaciones v = new Validaciones();

    public Cliente ingresoDatos() {
        return new Cliente(
                v.validarTexto("Ingrese el nombre del cliente: "),
                v.validarTexto("Ingrese la cédula del cliente: "),
                v.validarTexto("Ingrese el correo del cliente: "),
                v.validarTexto("Ingrese el teléfono del cliente: ")
        );
    }

    public Cliente ingresoDatos(String cedula) {
        return new Cliente(
                v.validarTexto("Ingrese el nombre del cliente: "),
                cedula,
                v.validarTexto("Ingrese el correo del cliente: "),
                v.validarTexto("Ingrese el teléfono del cliente: ")
        );
    }
}