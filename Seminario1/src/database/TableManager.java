/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;// conexión activa con la DB
import java.sql.SQLException; //// para lanzar excepciones cuando ocurren errores SQL o de conexion
import java.sql.Statement;


/**
 *
 * @author Usuario
 */
public class TableManager {
    
    public static void eliminarTablas(Connection connection)throws SQLException {
        
        Statement stmt = connection.createStatement();
        
        // Eliminar tablas si existen (para reiniciar la base de datos) y sus restricciones asociadas por orden de dependencia
        try { 
            stmt.executeUpdate("DROP TABLE Detalle_Pedido CASCADE CONSTRAINTS"); 
        } catch (SQLException e) {}
        
        try { 
            stmt.executeUpdate("DROP TABLE Pedido CASCADE CONSTRAINTS"); 
        } catch (SQLException e) {}
        
        try { 
            stmt.executeUpdate("DROP TABLE Stock CASCADE CONSTRAINTS"); 
        } catch (SQLException e) {}
        
        stmt.close();
        connection.commit();
    }
    public static void crearTablas(Connection connection) throws SQLException {
        
        Statement stmt = connection.createStatement();
        
        // Crear tablas según el enunciado
        stmt.executeUpdate(
            "CREATE TABLE Stock (" +
            "Cproducto NUMBER PRIMARY KEY, " +
            "Cantidad NUMBER)"
        );

        stmt.executeUpdate(
            "CREATE TABLE Pedido (" +
            "Cpedido NUMBER PRIMARY KEY, " +
            "Ccliente NUMBER, " +
            "Fecha_pedido DATE)"
        );

        stmt.executeUpdate(
            "CREATE TABLE Detalle_Pedido (" +
            "Cpedido NUMBER, " +
            "Cproducto NUMBER, " +
            "Cantidad NUMBER, " +
            "FOREIGN KEY (Cpedido) REFERENCES Pedido(Cpedido), " +
            "FOREIGN KEY (Cproducto) REFERENCES Stock(Cproducto))"
        );
        
        stmt.close();
        connection.commit();
        System.out.println("Tablas creadas correctamente.");
    }

    public static void insertarDatosIniciales(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();

        // Insertar 10 filas en la tabla Stock
        for (int i = 1; i <= 10; i++) {
            stmt.executeUpdate("INSERT INTO Stock VALUES (" + i + ", " + (10 * i) + ")");
        }

        connection.commit();
        System.out.println("Datos iniciales insertados en Stock.");
    }
}
