package view;

import Dao.MarcaCRUD;
import java.sql.SQLException;
import java.util.List;
import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Marca;
import modelo.SistemaOperativo;

public class IngresoDatosCelular {

    private final Validaciones v = new Validaciones();
    private final MarcaCRUD marcaCRUD = new MarcaCRUD();

    public Celular ingresoDatos() throws SQLException {
        Marca marca = seleccionarMarca();

        String modelo = v.validarTexto("Ingrese el modelo del celular: ");
        SistemaOperativo sistema = SistemaOperativo.SeleccionarSistemaOperativo();
        CategoriaGama gama = CategoriaGama.seleccionarGama();
        double precio = v.validarDecimal("Ingrese el precio: ");
        int stock = v.validarEntero("Ingrese el stock: ");
        int stockMinimo = v.validarEntero("Ingrese el stock mínimo: ");

        // El constructor recibe stock_minimo y luego stock.
        return new Celular(
                marca,
                modelo,
                sistema,
                gama,
                precio,
                stockMinimo,
                stock
        );
    }

    private Marca seleccionarMarca() throws SQLException {
        List<Marca> marcas = marcaCRUD.listar();

        if (marcas.isEmpty()) {
            throw new IllegalStateException(
                    "No hay marcas registradas. Registra una marca primero."
            );
        }

        System.out.println("=== MARCAS DISPONIBLES ===");
        marcas.forEach(System.out::println);

        int id = v.validarEntero("Ingrese el ID de la marca: ");

        for (Marca marca : marcas) {
            if (marca.getId() == id) {
                return marca;
            }
        }

        throw new IllegalArgumentException("No existe una marca con ese ID.");
    }
}