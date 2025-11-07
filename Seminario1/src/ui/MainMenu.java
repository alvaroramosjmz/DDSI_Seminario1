/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import database.DBConnection;
import database.TableManager;
import java.sql.Connection;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class MainMenu {
    
    public static void mostrar() throws Exception {
        Connection connection = DBConnection.getConnection();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENU PRINCIPAL =====");
            System.out.println("1. Crear tablas e insertar datos iniciales");
            System.out.println("2. Dar de alta nuevo pedido");
            System.out.println("3. Mostrar contenido de las tablas");
            System.out.println("4. Salir");
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> {
                    TableManager.crearTablas(connection);
                    TableManager.insertarDatosIniciales(connection);
                }
                case 2 -> PedidoMenu.mostrar(connection);
                case 3 -> {
                    System.out.println("Visualizacion de tablas no implementada todavia.");
                }
                case 4 -> DBConnection.closeConnection();
                default -> System.out.println("Opcion no valida.");
            }

        } while (opcion != 4);
    }
}
