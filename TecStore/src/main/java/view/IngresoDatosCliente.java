
package view;

import modelo.Cliente;

public class IngresoDatosCliente {
    Validaciones v = new Validaciones();
    public Cliente ingresoDatos() {
        return new Cliente(
                v.validarTexto("Ingrese el nombre del cliente: "),
                v.validarTexto("Ingrese la cedula del cliente: "),
                v.validarTexto("Ingrese el correo del cliente: "),
                v.validarTexto("Ingrese el telefono del cliente: ")       
                );
    }
}
