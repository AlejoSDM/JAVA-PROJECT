package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.CategoriaGama;
import modelo.Celular;
import modelo.Marca;
import modelo.SistemaOperativo;

public class CelularCRUD {

    public boolean insertar(Celular celular) throws SQLException {
        String sql = "{CALL sp_celular_insertar(?, ?, ?, ?, ?, ?, ?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, celular.getMarca().getId());
            llamada.setString(2, celular.getModelo());
            llamada.setString(3, celular.getSistemaop().name());
            llamada.setString(4, celular.getGama().name());
            llamada.setDouble(5, celular.getPrecio());
            llamada.setInt(6, celular.getStock());
            llamada.setInt(7, celular.getStock_minimo());

            llamada.executeUpdate();
            return true;
        }
    }

    public List<Celular> listar() throws SQLException {
        String sql = "{CALL sp_celular_listar()}";
        List<Celular> celulares = new ArrayList<>();

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql);
            ResultSet rs = llamada.executeQuery()
        ) {
            while (rs.next()) {
                celulares.add(convertirCelular(rs));
            }
        }

        return celulares;
    }

    public boolean actualizar(Celular celular) throws SQLException {
        String sql = "{CALL sp_celular_actualizar(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, celular.getId());
            llamada.setInt(2, celular.getMarca().getId());
            llamada.setString(3, celular.getModelo());
            llamada.setString(4, celular.getSistemaop().name());
            llamada.setString(5, celular.getGama().name());
            llamada.setDouble(6, celular.getPrecio());
            llamada.setInt(7, celular.getStock());
            llamada.setInt(8, celular.getStock_minimo());

            return llamada.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "{CALL sp_celular_eliminar(?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, id);
            return llamada.executeUpdate() > 0;
        }
    }

    private Celular convertirCelular(ResultSet rs) throws SQLException {
        Marca marca = new Marca(
            rs.getInt("marca_id"),
            rs.getString("marca_nombre")
        );

        return new Celular(
            rs.getInt("id"),
            marca,
            rs.getString("modelo"),
            SistemaOperativo.valueOf(rs.getString("sistema_operativo")),
            CategoriaGama.valueOf(rs.getString("gama")),
            rs.getDouble("precio"),
            rs.getInt("stock_minimo"), // El constructor recibe primero el stock mínimo
            rs.getInt("stock")
        );
    }
}