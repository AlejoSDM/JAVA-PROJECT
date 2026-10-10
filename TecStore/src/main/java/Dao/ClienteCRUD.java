package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;

public class ClienteCRUD {

    // INSERTAR cliente
    public boolean insertar(Cliente cliente) throws SQLException {
        validarCliente(cliente);

        String sql = "{CALL sp_cliente_insertar(?, ?, ?, ?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setString(1, cliente.getNombre().trim());
            llamada.setString(2, cliente.getCedula().trim());
            llamada.setString(3, cliente.getCorreo().trim());
            llamada.setString(4, cliente.getTelefono().trim());

            llamada.executeUpdate();
            return true;
        }
    }

    // LISTAR clientes
    public List<Cliente> listar() throws SQLException {
        String sql = "{CALL sp_cliente_listar()}";
        List<Cliente> clientes = new ArrayList<>();

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql);
            ResultSet rs = llamada.executeQuery()
        ) {
            while (rs.next()) {
                clientes.add(convertirCliente(rs));
            }
        }

        return clientes;
    }

    // ACTUALIZAR cliente
    public boolean actualizar(Cliente cliente) throws SQLException {
        validarCliente(cliente);
        validarId(cliente.getId());

        String sql = "{CALL sp_cliente_actualizar(?, ?, ?, ?, ?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, cliente.getId());
            llamada.setString(2, cliente.getNombre().trim());
            llamada.setString(3, cliente.getCedula().trim());
            llamada.setString(4, cliente.getCorreo().trim());
            llamada.setString(5, cliente.getTelefono().trim());

            return llamada.executeUpdate() > 0;
        }
    }

    // ELIMINAR cliente por ID
    public boolean eliminar(int id) throws SQLException {
        validarId(id);

        String sql = "{CALL sp_cliente_eliminar(?)}";

        try (
            Connection conexion = ConexionDao.getInstance().conexion();
            CallableStatement llamada = conexion.prepareCall(sql)
        ) {
            llamada.setInt(1, id);
            return llamada.executeUpdate() > 0;
        }
    }
    
    public Cliente buscarPorCedula(String cedula) throws SQLException {
    if (cedula == null || cedula.isBlank()) {
        throw new IllegalArgumentException("La cédula no puede estar vacía.");
    }

    String sql = "{CALL sp_cliente_buscar_por_cedula(?)}";

    try (
        Connection conexion = ConexionDao.getInstance().conexion();
        CallableStatement llamada = conexion.prepareCall(sql)
    ) {
        llamada.setString(1, cedula.trim());

        try (ResultSet rs = llamada.executeQuery()) {
            if (rs.next()) {
                return convertirCliente(rs);
            }
        }
    }

    return null; // No se encontró un cliente con esa cédula
    }

    // Convertir la fila del ResultSet en un objeto Cliente
    private Cliente convertirCliente(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("cedula"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                "El cliente no puede ser nulo."
            );
        }

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                "El nombre del cliente no puede estar vacío."
            );
        }

        if (cliente.getCedula() == null || cliente.getCedula().isBlank()) {
            throw new IllegalArgumentException(
                "La cédula no puede estar vacía."
            );
        }

        if (cliente.getCorreo() == null || cliente.getCorreo().isBlank()) {
            throw new IllegalArgumentException(
                "El correo no puede estar vacío."
            );
        }

        if (cliente.getTelefono() == null || cliente.getTelefono().isBlank()) {
            throw new IllegalArgumentException(
                "El teléfono no puede estar vacío."
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