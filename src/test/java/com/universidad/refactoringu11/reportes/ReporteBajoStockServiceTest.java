package com.universidad.refactoringu11.reportes;

import com.universidad.refactoringu11.inventario.Producto;
import com.universidad.refactoringu11.inventario.TipoProducto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReporteBajoStockServiceTest {

    private final ReporteBajoStockService reporte = new ReporteBajoStockService();

    @Test
    void sinAlertas_indicaQueNoHayProductosBajoElMinimo() {
        assertEquals("REPORTE BAJO STOCK: sin productos bajo el minimo", reporte.generarReporte());
    }

    @Test
    void conservaLaUltimaAlertaDeCadaProductoEnOrdenDeLlegada() {
        Producto libro = new Producto(1L, "Clean Code", TipoProducto.LIBRO, 45.0, 3, 5);
        Producto cafe = new Producto(2L, "Cafe", TipoProducto.ALIMENTO, 15.0, 1, 4);
        reporte.notificar(libro, 12);
        reporte.notificar(cafe, 11);
        libro.setStock(2);
        reporte.notificar(libro, 13);   // segunda alerta del mismo producto: reemplaza la anterior

        assertEquals("REPORTE BAJO STOCK (2 productos):"
                + "\n - Clean Code: stock 2 / minimo 5 -> reordenar 13 unidades"
                + "\n - Cafe: stock 1 / minimo 4 -> reordenar 11 unidades", reporte.generarReporte());
    }
}
