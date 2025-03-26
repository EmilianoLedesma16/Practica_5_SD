package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Inventario extends Remote {
    int consultarStock(String producto) throws RemoteException;
    void agregarProducto(String producto, int cantidad) throws RemoteException;
    void eliminarProducto(String producto, int cantidad) throws RemoteException;
    void actualizarInventario(Map<String, Integer> nuevoInventario) throws RemoteException;
}
