package cliente;

import servidor.Inventario;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteInventario {
    public static void main(String[] args) {
        try {
            // Conectar al servidor RMI
            Registry registro = LocateRegistry.getRegistry("localhost", 2000);
            Inventario stub = (Inventario) registro.lookup("Inventario");

            // Leer los comandos del usuario
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.println("Ingrese un comando (consultar, agregar, eliminar, salir):");
                    String comando = scanner.nextLine().trim().toLowerCase();  // Asegurarse de eliminar espacios y pasar a minúsculas

                if (comando.equals("consultar")) {
                    System.out.println("Ingrese el producto:");
                    String producto = scanner.nextLine().trim();
                    int stock = stub.consultarStock(producto);
                    System.out.println("Stock de " + producto + ": " + stock);
                } else if (comando.equals("agregar")) {
                    System.out.println("Ingrese el producto:");
                    String producto = scanner.nextLine().trim();
                    System.out.println("Ingrese la cantidad a agregar:");
                    int cantidad = Integer.parseInt(scanner.nextLine().trim());
                    stub.agregarProducto(producto, cantidad);
                    System.out.println("Producto agregado: " + producto + " - Cantidad: " + cantidad);
                } else if (comando.equals("eliminar")) {
                    System.out.println("Ingrese el producto:");
                    String producto = scanner.nextLine().trim();
                    System.out.println("Ingrese la cantidad a eliminar:");
                    int cantidad = Integer.parseInt(scanner.nextLine().trim());
                    stub.eliminarProducto(producto, cantidad);
                    System.out.println("Producto eliminado: " + producto + " - Cantidad: " + cantidad);
                } else if (comando.equals("salir")) {
                    System.out.println("Saliendo del programa...");
                    break;  // Salir del ciclo while
                } else {
                    System.out.println("Comando no reconocido.");
                }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
