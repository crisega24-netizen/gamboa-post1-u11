package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.NotificadorBajoStock;
import com.universidad.refactoringu11.inventario.Producto;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Adaptador del puerto NotificadorBajoStock: registra cada alerta de bajo stock que
 * emite el inventario y arma con ellas el reporte. Depende de inventario (Producto y
 * NotificadorBajoStock) en la unica direccion permitida: reportes -> inventario.
 */
@Service
public class ReporteBajoStockService implements NotificadorBajoStock {

    /** Ultima alerta por producto, en el orden en que se recibieron. */
    private final Map<Long, String> alertasPorProducto = new LinkedHashMap<>();

    @Override
    public void notificar(Producto producto, int cantidadReorden) {
        alertasPorProducto.put(producto.getId(), producto.getNombre()
                + ": stock " + producto.getStock()
                + " / minimo " + producto.getStockMinimo()
                + " -> reordenar " + cantidadReorden + " unidades");
    }

    public String generarReporte() {
        if (alertasPorProducto.isEmpty()) {
            return "REPORTE BAJO STOCK: sin productos bajo el minimo";
        }
        StringBuilder reporte = new StringBuilder(
                "REPORTE BAJO STOCK (" + alertasPorProducto.size() + " productos):");
        alertasPorProducto.values().forEach(alerta -> reporte.append("\n - ").append(alerta));
        return reporte.toString();
    }
}
