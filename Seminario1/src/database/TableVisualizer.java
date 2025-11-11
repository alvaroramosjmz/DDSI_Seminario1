package database;

import java.sql.Connection;
import java.sql.ResultSet; //para devolver los resultados de un consulta SQL
import java.sql.SQLException;
import java.sql.Statement;

public class TableVisualizer {

    public static void mostrarTablas(Connection connection) {
        try (Statement stmt = connection.createStatement()) {

            // ==== TABLA STOCK ====
            System.out.println("\n===== TABLA STOCK =====");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Stock ORDER BY Cproducto");
            
            while (rs.next()) {
                int idProd = rs.getInt("Cproducto");
                int cantidad = rs.getInt("Cantidad");
                if (rs.wasNull()) cantidad = 0;
                System.out.printf("Producto: %-5d | Cantidad: %-5d%n", idProd, cantidad);
            }
            
            rs.close();

            // ==== TABLA PEDIDO ====
            System.out.println("\n===== TABLA PEDIDO =====");
            rs = stmt.executeQuery("SELECT * FROM Pedido ORDER BY Cpedido");
            
            while (rs.next()) {
                int idPedido = rs.getInt("Cpedido");
                int idCliente = rs.getInt("Ccliente");
                java.sql.Date fecha = rs.getDate("Fecha_pedido");
                System.out.printf("Pedido: %-5d | Cliente: %-5d | Fecha: %s%n", idPedido, idCliente, fecha);
            }
            
            rs.close();

            // ==== TABLA DETALLE_PEDIDO ====
            System.out.println("\n===== TABLA DETALLE_PEDIDO =====");
            rs = stmt.executeQuery("SELECT * FROM Detalle_Pedido ORDER BY Cpedido, Cproducto");
            
            while (rs.next()) {
                int idPedido = rs.getInt("Cpedido");
                int idProducto = rs.getInt("Cproducto");
                int cantidad = rs.getInt("Cantidad");
                if (rs.wasNull()) cantidad = 0;
                System.out.printf("Pedido: %-5d | Producto: %-5d | Cantidad: %-5d", idPedido, idProducto, cantidad);
            }
            
            rs.close();

            System.out.println("\n=================================\n");

        } catch (SQLException e) {
            System.err.println("Error al visualizar tablas: " + e.getMessage());
        }
    }
}
