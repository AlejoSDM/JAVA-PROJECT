package view;

import modelo.Marca;

public class IngresoDatosMarca {

    private final Validaciones validaciones = new Validaciones();

    public Marca ingresoDatos() {
        String nombre = validaciones.validarTexto(
                "Ingrese el nombre de la marca: "
        );

        return new Marca(nombre);
    }
}