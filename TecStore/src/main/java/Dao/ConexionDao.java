package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDao {
    public Connection conexion() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/TecStoreAlejo",
            "campus2023",
            "campus2023"
        );
    }
}