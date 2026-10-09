package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Marca;

public class MarcaCRUD {

    public boolean insertar(Marca marca) throws SQLException {
        validarMarca(marca);

        String sql = "{CALL sp_marca_insertar(?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setString(1, marca.getMarca().trim());
            llamada.executeUpdate();
            return true;
        }
    }

    public List<Marca> listar() throws SQLException {
        String sql = "{CALL sp_marca_listar()}";
        List<Marca> marcas = new ArrayList<>();

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql);
            ResultSet rs = llamada.executeQuery()
        ) {
            while (rs.next()) {
                marcas.add(convertirMarca(rs));
            }
        }

        return marcas;
    }

    public Marca buscarPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre de la marca no puede estar vacío."
            );
        }

        String sql = "{CALL sp_marca_buscar_por_nombre(?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setString(1, nombre.trim());

            try (ResultSet rs = llamada.executeQuery()) {
                if (rs.next()) {
                    return convertirMarca(rs);
                }
            }
        }

        return null;
    }

    public boolean actualizar(Marca marca) throws SQLException {
        validarMarca(marca);
        validarId(marca.getId());

        String sql = "{CALL sp_marca_actualizar(?, ?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, marca.getId());
            llamada.setString(2, marca.getMarca().trim());

            return llamada.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        validarId(id);

        String sql = "{CALL sp_marca_eliminar(?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, id);
            return llamada.executeUpdate() > 0;
        }
    }

    private Marca convertirMarca(ResultSet rs) throws SQLException {
        return new Marca(
            rs.getInt("id"),
            rs.getString("nombre")
        );
    }

    private void validarMarca(Marca marca) {
        if (marca == null) {
            throw new IllegalArgumentException(
                "La marca no puede ser nula."
            );
        }

        if (marca.getMarca() == null || marca.getMarca().isBlank()) {
            throw new IllegalArgumentException(
                "El nombre de la marca no puede estar vacío."
            );
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "Debes ingresar un ID válido."
            );
        }
    }
}