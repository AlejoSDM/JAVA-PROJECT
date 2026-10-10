package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReporteCRUD {

    public List<CelularReporte> listarCelulares() throws SQLException {
        List<CelularReporte> resultado = new ArrayList<>();

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada =
                    conexion.prepareCall("{CALL sp_reporte_celulares()}");
            ResultSet rs = llamada.executeQuery()
        ) {
            while (rs.next()) {
                resultado.add(new CelularReporte(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("stock")
                ));
            }
        }

        return resultado;
    }

    public List<DetalleReporte> listarDetalles() throws SQLException {
        List<DetalleReporte> resultado = new ArrayList<>();

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada =
                    conexion.prepareCall("{CALL sp_reporte_detalles()}");
            ResultSet rs = llamada.executeQuery()
        ) {
            while (rs.next()) {
                resultado.add(new DetalleReporte(
                        rs.getInt("id_venta"),
                        rs.getInt("id_celular"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("cantidad")
                ));
            }
        }

        return resultado;
    }

    public List<VentaReporte> listarVentas() throws SQLException {
        List<VentaReporte> resultado = new ArrayList<>();

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada =
                    conexion.prepareCall("{CALL sp_reporte_ventas()}");
            ResultSet rs = llamada.executeQuery()
        ) {
            while (rs.next()) {
                Timestamp fecha = rs.getTimestamp("fecha");

                resultado.add(new VentaReporte(
                        rs.getInt("id"),
                        fecha.toLocalDateTime(),
                        rs.getBigDecimal("total"),
                        rs.getString("cliente"),
                        rs.getString("empleado")
                ));
            }
        }

        return resultado;
    }

    public static class CelularReporte {
        public final int id;
        public final String marca;
        public final String modelo;
        public final int stock;

        public CelularReporte(int id, String marca, String modelo, int stock) {
            this.id = id;
            this.marca = marca;
            this.modelo = modelo;
            this.stock = stock;
        }

        public String nombreCompleto() {
            return marca + " " + modelo;
        }
    }

    public static class DetalleReporte {
        public final int ventaId;
        public final int celularId;
        public final String marca;
        public final String modelo;
        public final int cantidad;

        public DetalleReporte(
                int ventaId,
                int celularId,
                String marca,
                String modelo,
                int cantidad
        ) {
            this.ventaId = ventaId;
            this.celularId = celularId;
            this.marca = marca;
            this.modelo = modelo;
            this.cantidad = cantidad;
        }

        public String nombreCompleto() {
            return marca + " " + modelo;
        }
    }

    public static class VentaReporte {
        public final int id;
        public final java.time.LocalDateTime fecha;
        public final java.math.BigDecimal total;
        public final String cliente;
        public final String empleado;

        public VentaReporte(
                int id,
                java.time.LocalDateTime fecha,
                java.math.BigDecimal total,
                String cliente,
                String empleado
        ) {
            this.id = id;
            this.fecha = fecha;
            this.total = total;
            this.cliente = cliente;
            this.empleado = empleado;
        }
    }
}