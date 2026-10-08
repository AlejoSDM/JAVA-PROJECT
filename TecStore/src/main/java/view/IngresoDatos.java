package view;

import modelo.Celular;
import modelo.Marca;
import modelo.SistemaOperativo;
import modelo.CategoriaGama;


public class IngresoDatos {
/*No se hace void porque retorna su mismo producto*/
    
    Validaciones v = new Validaciones();
    public Celular ingresoDatos() {
        /*ACA SE INGRESA EL NOMBRE DE LA MARCA Y SE LO MANDAMOS AL CONSTRUCTOR*/
        String nombre_marca = v.validarTexto("Ingrese el nombre de la marca: ");
        Marca marca = new Marca(nombre_marca);
        return new Celular(
                marca,
                v.validarTexto("Ingrese el nombre del modelo: "),
                SistemaOperativo.SeleccionarSistemaOperativo(),
                CategoriaGama.seleccionarGama(),
                v.validarDecimal("Ingrese el precio del celular: "),
                v.validarEntero("Ingrese el stock del celular: "),
                v.validarEntero("Ingrese el stock minimo del celular: ")
                );
    }
}
