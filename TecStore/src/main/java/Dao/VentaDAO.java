package Dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

public class VentaDAO {

    public int registrarVenta(
            int clienteId,
            int empleadoId,
            Map<Integer, Integer> productos
    ) throws SQLException {

        validarId(clienteId, "cliente");
        validarId(empleadoId, "empleado");

        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException(
                    "La venta debe incluir al menos un celular."
            );
        }

        // Copia los productos para trabajar con una lista estable.
        Map<Integer, Integer> productosVenta =
                new LinkedHashMap<>(productos);

        for (Map.Entry<Integer, Integer> producto : productosVenta.entrySet()) {
            validarId(producto.getKey(), "celular");

            if (producto.getValue() == null || producto.getValue() <= 0) {
                throw new IllegalArgumentException(
                        "La cantidad debe ser mayor que cero."
                );
            }
        }

        String sqlObtenerCelular =
                "{CALL sp_venta_obtener_celular(?)}";
        String sqlInsertarVenta =
                "{CALL sp_venta_insertar(?, ?, ?, ?)}";
        String sqlInsertarDetalle =
                "{CALL sp_venta_detalle_insertar(?, ?, ?, ?)}";
        String sqlCalcularTotal =
                "{CALL sp_venta_calcular_total(?)}";

        try (Connection conexion =
                     ConexionDao.getInstance().conexion()) {

            boolean autoCommitAnterior = conexion.getAutoCommit();
            conexion.setAutoCommit(false);

            try {
                // Guarda el precio consultado para cada celular.
                Map<Integer, BigDecimal> precios =
                        new LinkedHashMap<>();

                // Verifica que existan los celulares y que tengan stock.
                try (CallableStatement llamada =
                             conexion.prepareCall(sqlObtenerCelular)) {

                    for (Map.Entry<Integer, Integer> producto
                            : productosVenta.entrySet()) {

                        int celularId = producto.getKey();
                        int cantidad = producto.getValue();

                        llamada.setInt(1, celularId);

                        try (ResultSet rs = llamada.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException(
                                        "No existe el celular con ID "
                                                + celularId + "."
                                );
                            }

                            int stockDisponible = rs.getInt("stock");
                            BigDecimal precio =
                                    rs.getBigDecimal("precio");

                            if (stockDisponible < cantidad) {
                                throw new SQLException(
                                        "Stock insuficiente para el celular "
                                                + "con ID " + celularId
                                                + ". Disponible: "
                                                + stockDisponible
                                );
                            }

                            precios.put(celularId, precio);
                        }
                    }
                }

                // Crea la venta con total inicial cero.
                int ventaId;

                try (CallableStatement llamada =
                             conexion.prepareCall(sqlInsertarVenta)) {

                    llamada.setDouble(1, 0.0);
                    llamada.setInt(2, empleadoId);
                    llamada.setInt(3, clienteId);
                    llamada.registerOutParameter(4, Types.INTEGER);

                    llamada.execute();
                    ventaId = llamada.getInt(4);
                }

                if (ventaId <= 0) {
                    throw new SQLException(
                            "No se pudo obtener el ID de la venta."
                    );
                }

                // Inserta cada detalle.
                // El trigger descontar_stock_al_insertar_detalle
                // descuenta el stock automáticamente.
                try (CallableStatement llamada =
                             conexion.prepareCall(sqlInsertarDetalle)) {

                    for (Map.Entry<Integer, Integer> producto
                            : productosVenta.entrySet()) {

                        int celularId = producto.getKey();
                        int cantidad = producto.getValue();
                        BigDecimal precio = precios.get(celularId);

                        llamada.setInt(1, ventaId);
                        llamada.setInt(2, celularId);
                        llamada.setInt(3, cantidad);
                        llamada.setDouble(4, precio.doubleValue());

                        llamada.executeUpdate();
                    }
                }

                // Calcula el total de los detalles más el 19 % de IVA.
                try (CallableStatement llamada =
                             conexion.prepareCall(sqlCalcularTotal)) {

                    llamada.setInt(1, ventaId);
                    llamada.executeUpdate();
                }

                conexion.commit();
                return ventaId;

            } catch (SQLException | RuntimeException e) {
                try {
                    conexion.rollback();
                } catch (SQLException errorRollback) {
                    e.addSuppressed(errorRollback);
                }

                throw e;

            } finally {
                conexion.setAutoCommit(autoCommitAnterior);
            }
        }
    }

    private void validarId(int id, String entidad) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID del " + entidad + " debe ser válido."
            );
        }
    }
}