package com.universidad.refactoringu11.inventario;

import java.util.OptionalInt;

/**
 * Politica de reabastecimiento: decide si un producto debe reordenarse y cuantas
 * unidades pedir. Vive en su propia clase porque cambia por razones de compras
 * (factor de stock objetivo, proveedor, tiempos de entrega), no por razones del
 * registro de movimientos de inventario.
 */
public class CalculadoraReorden {

    /** El stock objetivo tras reordenar es el triple del stock minimo. */
    private static final int FACTOR_STOCK_OBJETIVO = 3;

    /**
     * @return la cantidad a reordenar si el stock quedo por debajo del minimo,
     *         o vacio si no hace falta reordenar
     */
    public OptionalInt cantidadAReordenar(Producto producto) {
        if (producto.getStock() >= producto.getStockMinimo()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(producto.getStockMinimo() * FACTOR_STOCK_OBJETIVO - producto.getStock());
    }
}
