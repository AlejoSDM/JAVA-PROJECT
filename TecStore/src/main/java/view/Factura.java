package view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import modelo.Celular;
import modelo.Cliente;
import modelo.Empleado;

public class Factura {

    public void mostrar(
            int ventaId,
            Cliente cliente,
            Empleado empleado,
            Map<Integer, Integer> productos,
            List<Celular> celulares
    ) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal tasaIva = new BigDecimal("0.19");

        DateTimeFormatter formatoFecha =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("\n============== FACTURA ==============");
        System.out.println("Venta: #" + ventaId);
        System.out.println(
                "Fecha: " + LocalDateTime.now().format(formatoFecha)
        );
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Empleado: " + empleado);
        System.out.println("-------------------------------------");
        System.out.println("Productos:");

        for (Map.Entry<Integer, Integer> producto : productos.entrySet()) {
            int celularId = producto.getKey();
            int cantidad = producto.getValue();

            Celular celular = celulares.stream()
                    .filter(c -> c.getId() == celularId)
                    .findFirst()
                    .orElse(null);

            if (celular == null) {
                System.out.println(
                        "No se encontró el celular con ID " + celularId
                );
                continue;
            }

            BigDecimal precioUnitario =
                    BigDecimal.valueOf(celular.getPrecio());

            BigDecimal subtotalProducto = precioUnitario
                    .multiply(BigDecimal.valueOf(cantidad))
                    .setScale(2, RoundingMode.HALF_UP);

            subtotal = subtotal.add(subtotalProducto);

            System.out.println(
                    celular.getModelo()
                            + " | Cantidad: " + cantidad
                            + " | Precio unitario: $" + precioUnitario
                            + " | Subtotal: $" + subtotalProducto
            );
        }

        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);

        BigDecimal iva = subtotal
                .multiply(tasaIva)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = subtotal.add(iva);

        System.out.println("-------------------------------------");
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("IVA (19 %): $" + iva);
        System.out.println("TOTAL: $" + total);
        System.out.println("=====================================\n");
    }
}