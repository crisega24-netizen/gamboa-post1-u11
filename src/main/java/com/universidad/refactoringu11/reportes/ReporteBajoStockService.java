package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.InventarioService;
import com.universidad.refactoringu11.inventario.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteBajoStockService {

    private final InventarioService inventarioService;

    public ReporteBajoStockService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public String generarReporte() {
        List<Producto> bajoStock = inventarioService.productosBajoStockMinimo();
        if (bajoStock.isEmpty()) {
            return "REPORTE BAJO STOCK: sin productos bajo el minimo";
        }
        StringBuilder reporte = new StringBuilder("REPORTE BAJO STOCK (" + bajoStock.size() + " productos):");
        for (Producto producto : bajoStock) {
            reporte.append("\n - ").append(producto.getNombre())
                    .append(": stock ").append(producto.getStock())
                    .append(" / minimo ").append(producto.getStockMinimo());
        }
        return reporte.toString();
    }
}
