package com.universidad.refactoringu11.inventario;

/**
 * Puerto de salida del inventario: lo invoca InventarioService cada vez que un
 * movimiento deja un producto por debajo de su stock minimo.
 *
 * Vive en el paquete inventario (dominio, el de mayor nivel) para que la dependencia
 * apunte hacia el: los paquetes que reaccionan a la alerta, como reportes, lo
 * implementan, e inventario no conoce a ninguno de ellos (Inversion de Dependencias).
 */
@FunctionalInterface
public interface NotificadorBajoStock {

    void notificar(Producto producto, int cantidadReorden);
}
