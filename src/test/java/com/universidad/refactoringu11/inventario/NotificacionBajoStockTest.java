package com.universidad.refactoringu11.inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifica el nuevo contrato de la Parte 2: InventarioService avisa por el puerto
 * NotificadorBajoStock, sin conocer quien lo implementa. Usa un doble de prueba
 * escrito como lambda, sin Spring ni el paquete reportes.
 */
class NotificacionBajoStockTest {

    private final List<String> notificaciones = new ArrayList<>();
    private InventarioService service;

    @BeforeEach
    void setUp() {
        service = new InventarioService();
        service.agregarProducto(new Producto(1L, "Clean Code", TipoProducto.LIBRO, 45.0, 20, 5));
        service.setNotificador((producto, cantidad) ->
                notificaciones.add(producto.getNombre() + ":" + cantidad));
    }

    @Test
    void notificaUnaVezConLaCantidadDeReordenCuandoElStockQuedaBajoElMinimo() {
        service.actualizarInventario(1L, 17, "SALIDA");   // 20 - 17 = 3 -> reordenar 12
        assertEquals(List.of("Clean Code:12"), notificaciones);
    }

    @Test
    void noNotificaSiElStockQuedaEnElMinimoOEncima() {
        service.actualizarInventario(1L, 15, "SALIDA");   // 20 - 15 = 5 -> no reordena
        assertTrue(notificaciones.isEmpty());
    }
}
