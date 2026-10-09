package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Empleado;

public class EmpleadoCRUD {

    // INSERTAR empleado
    public boolean insertar(Empleado empleado) throws SQLException {
        validarEmpleado(empleado);

        String sql = "{CALL sp_empleado_insertar(?, ?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setString(1, empleado.getNombre().trim());
            llamada.setString(2, empleado.getTelefono().trim());

            llamada.executeUpdate();
            return true;
        }
    }

    // LISTAR empleados
    public List<Empleado> listar() throws SQLException {
        String sql = "{CALL sp_empleado_listar()}";
        List<Empleado> empleados = new ArrayList<>();

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql);
            ResultSet rs = llamada.executeQuery()
        ) {
            while (rs.next()) {
                empleados.add(convertirEmpleado(rs));
            }
        }

        return empleados;
    }

    // ACTUALIZAR empleado
    public boolean actualizar(Empleado empleado) throws SQLException {
        validarEmpleado(empleado);
        validarId(empleado.getId());

        String sql = "{CALL sp_empleado_actualizar(?, ?, ?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, empleado.getId());
            llamada.setString(2, empleado.getNombre().trim());
            llamada.setString(3, empleado.getTelefono().trim());

            return llamada.executeUpdate() > 0;
        }
    }

    // ELIMINAR empleado por ID
    public boolean eliminar(int id) throws SQLException {
        validarId(id);

        String sql = "{CALL sp_empleado_eliminar(?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, id);
            return llamada.executeUpdate() > 0;
        }
    }

    // Convierte una fila del ResultSet en un objeto Empleado
    private Empleado convertirEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("telefono")
        );
    }

    private void validarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException(
                "El empleado no puede ser nulo."
            );
        }

        if (empleado.getNombre() == null
                || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                "El nombre del empleado no puede estar vacío."
            );
        }

        if (empleado.getTelefono() == null
                || empleado.getTelefono().isBlank()) {
            throw new IllegalArgumentException(
                "El teléfono del empleado no puede estar vacío."
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