package servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ServidorInventario {
    private Map<String, Integer> inventario;
    private List<Inventario> otrosServidores;

    public static void main(String[] args) {
        try {
            // Crear la instancia del servidor
            InventarioImpl inventarioImpl = new InventarioImpl();

            // Configurar la lista de otros servidores (vacía en este caso)
            inventarioImpl.setOtrosServidores(Collections.emptyList());

            // Crear el registro RMI en el puerto 2000
            Registry registro = LocateRegistry.createRegistry(2000); // Puerto 2000

            // Registrar el objeto remoto con el nombre "Inventario"
            registro.rebind("Inventario", inventarioImpl); // Registrar el objeto remoto

            System.out.println("Servidor de Inventario listo y esperando conexiones...");
        } catch (RemoteException e) {
            System.out.println("Error al configurar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void notificarCambio() throws RemoteException {
        if (otrosServidores != null) {
            for (Inventario servidor : otrosServidores) {
                try {
                    servidor.actualizarInventario(inventario);  // Notificamos a otros servidores para que actualicen su inventario
                } catch (Exception e) {
                    System.out.println("Error al notificar a un servidor: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No hay otros servidores configurados para notificar.");
        }
    }
}
