package org.example.factory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConnectionManagerMySQL {
    private Connection conex;
    private static final String URL = "jdbc:mysql://localhost:3306/Entregable1";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Creo una instancia de mi Manejador de Conexiones
    private static volatile ConnectionManagerMySQL instance = new ConnectionManagerMySQL();


    // Creo un constructor privado para evitar que de afuera se instancie la clase
    // Funcion: crea la conexión a MySQL y la guarda en el atributo conex
    private ConnectionManagerMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conex = java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con MySQL!");
            conex.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver no encontrado", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }


    // Implementacion del patron SINGLENTON
    // (asegura que solo exista una unica instancia de esta clase en toda la app).
    public static ConnectionManagerMySQL getInstance() {
        if (instance == null){
            synchronized (ConnectionManagerMySQL.class){
                if(instance == null){
                    instance = new ConnectionManagerMySQL();
                }
            }
        }
        return instance;
    }

    public Connection getConex() {
        return this.conex;
    }

    public void closeConn() {
        if (this.conex != null) {
            try {
                this.conex.close();
                this.conex = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}



/* USO DE VOLATILE:
volatile se usa para indicar que la variable puede ser modificada por multiples
threads simultaneamente y que su valor siempre debe leerse desde la memoria principal,
no desde la cache del thread.
*/


/* IMPORTANCIA del uso de synchronized y volatile:
Si dos threads diferentes llaman al mismo tiempo a ConnectionManagerMySQL.getInstance():
Sin el synchronized, podrían crear dos instancias distintas del Singleton,
rompiendo la idea de "solo una instancia".
Con synchronized y volatile, asegurás que solo un thread crea la instancia y los demás
esperan su turno.
*/