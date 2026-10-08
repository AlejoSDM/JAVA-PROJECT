package view;

import modelo.Empleado;

public class IngresoDatosEmpleado {

    
    Validaciones v = new Validaciones();
    public Empleado ingresoDatos() {
        return new Empleado(
                v.validarTexto("Ingrese el nombre del empleado: "),
                v.validarTexto("Ingrese el telefono del empleado: ")
                );
    }
}
