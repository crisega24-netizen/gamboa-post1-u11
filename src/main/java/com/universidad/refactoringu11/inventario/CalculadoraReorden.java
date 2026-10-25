package com.universidad.refactoringu11.inventario;

/**
 * Politica de reabastecimiento: decide si un producto debe reordenarse y cuantas
 * unidades pedir. Vive en su propia clase porque cambia por razones de compras
 * (factor de stock objetivo, proveedor, tiempos de entrega), no por razones del
 * registro de movimientos de inventario.
 */
public class CalculadoraReorden {

    /** El stock objetivo tras reordenar es el triple del stock minimo. */
    private static final int FACTOR_STOCK_OBJETIVO = 3;

    public boolean necesitaReorden(Producto producto) {
        return producto.getStock() < producto.getStockMinimo();
    }

    public int cantidadAReordenar(Producto producto) {
        if (!necesitaReorden(producto)) {
            return 0;
        }
        return producto.getStockMinimo() * FACTOR_STOCK_OBJETIVO - producto.getStock();
    }
}
