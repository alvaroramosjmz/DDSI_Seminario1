/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class PedidoMenu {

     public static void mostrar(Connection connection) {
        Scanner sc = new Scanner(System.in);

        try {
            connection.setAutoCommit(false);

            System.out.print("Introduce el codigo del pedido: ");
            int idPedido = sc.nextInt();
            System.out.print("Introduce el codigo del cliente: ");
            int idCliente = sc.nextInt();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO Pedido VALUES (" + idPedido + ", " + idCliente + ", SYSDATE)");
            Savepoint saveBeforeDetails = connection.setSavepoint("AntesDeDetalles");

            boolean terminado = false;

            while (!terminado) {
                System.out.println("1. Añadir detalle de producto");
                System.out.println("2. Eliminar todos los detalles");
                System.out.println("3. Cancelar pedido (ROLLBACK)");
                System.out.println("4. Finalizar pedido (COMMIT)");
                System.out.print("Opcion: ");
                int opcion = sc.nextInt();

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Codigo de producto: ");
                        int idProd = sc.nextInt();
                        System.out.print("Cantidad: ");
                        int cantidad = sc.nextInt();

                        ResultSet rs = stmt.executeQuery("SELECT Cantidad FROM Stock WHERE Cproducto = " + idProd);
                        if (rs.next() && rs.getInt(1) >= cantidad) {
                            stmt.executeUpdate("INSERT INTO Detalle_Pedido VALUES (" + idPedido + "," + idProd + "," + cantidad + ")");
                            stmt.executeUpdate("UPDATE Stock SET Cantidad = Cantidad - " + cantidad + " WHERE Cproducto = " + idProd);
                            System.out.println("Producto aniadido correctamente.");
                        } else {
                            System.out.println("No hay suficiente stock para este producto.");
                        }
                    }
                    case 2 -> {
                        stmt.executeUpdate("DELETE FROM Detalle_Pedido WHERE Cpedido = " + idPedido);
                        connection.rollback(saveBeforeDetails);
                        System.out.println("Detalles eliminados (rollback al savepoint).");
                    }
                    case 3 -> {
                        connection.rollback();
                        System.out.println("Pedido cancelado (rollback total).");
                        terminado = true;
                    }
                    case 4 -> {
                        connection.commit();
                        System.out.println("Pedido finalizado (commit realizado).");
                        terminado = true;
                    }
                    default -> System.out.println("Opcion no válida.");
                }
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("Error en la transaccion: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
        }
    }
    
}
