package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventarioImpl extends UnicastRemoteObject implements Inventario {
    private Map<String, Integer> inventario;
    private List<Inventario> otrosServidores;

    protected InventarioImpl() throws RemoteException {
        super();
        inventario = new ConcurrentHashMap<>();
    }

    @Override
    public int consultarStock(String producto) throws RemoteException {
        System.out.println("Consultando stock de: " + producto);
        return inventario.getOrDefault(producto, 0);
    }

    @Override
    public void agregarProducto(String producto, int cantidad) throws RemoteException {
        try {
            System.out.println("Agregando " + cantidad + " unidades de: " + producto);
            inventario.put(producto, inventario.getOrDefault(producto, 0) + cantidad);
            System.out.println("Producto agregado. Notificando a otros servidores...");
            notificarCambio();
        } catch (Exception e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminarProducto(String producto, int cantidad) throws RemoteException {
        try {
            System.out.println("Eliminando " + cantidad + " unidades de: " + producto);
            inventario.put(producto, Math.max(0, inventario.getOrDefault(producto, 0) - cantidad));
            System.out.println("Producto eliminado. Notificando a otros servidores...");
            notificarCambio();
        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    public void notificarCambio() throws RemoteException {
        if (otrosServidores != null) {
            for (Inventario servidor : otrosServidores) {
                try {
                    servidor.actualizarInventario(inventario);
                } catch (Exception e) {
                    System.out.println("Error al notificar a un servidor: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No hay otros servidores configurados para notificar.");
        }
    }

    @Override
    public void actualizarInventario(Map<String, Integer> nuevoInventario) throws RemoteException {
        try {
            this.inventario = nuevoInventario;
            System.out.println("Inventario actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el inventario: " + e.getMessage());
        }
    }

    public void setOtrosServidores(List<Inventario> servidores) {
        this.otrosServidores = servidores;
    }
}
