package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionDao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/TecStoreAlejo";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "Isaias";
    

    private ConexionDao() {
        // Constructor privado: no se puede crear con new desde otra clase.
    }

    // Singleton con inicialización para la conexion
    private static class Holder {
        private static final ConexionDao INSTANCIA = new ConexionDao();
    }

    public static ConexionDao getInstance() {
        return Holder.INSTANCIA;
    }

    public Connection conexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}