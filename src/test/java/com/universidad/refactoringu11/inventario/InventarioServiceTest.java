package com.universidad.refactoringu11.inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de caracterizacion: documentan el comportamiento ACTUAL de
 * actualizarInventario() antes de refactorizarlo, incluidos los casos limite.
 */
class InventarioServiceTest {

    private InventarioService service;
    private Producto libro;

    @BeforeEach
    void setUp() {
        service = new InventarioService();
        libro = new Producto(1L, "Clean Code", TipoProducto.LIBRO, 45.0, 20, 5);
        service.agregarProducto(libro);
    }

    @Test
    void entrada_incrementaStockYRetornaOk() {
        assertEquals("OK_30", service.actualizarInventario(1L, 10, "ENTRADA"));
        assertEquals(30, libro.getStock());
    }

    @Test
    void salida_conStockSuficiente_descuentaYRetornaOk() {
        assertEquals("OK_12", service.actualizarInventario(1L, 8, "SALIDA"));
    }

    @Test
    void salida_conStockInsuficiente_retornaErrorYNoModificaStock() {
        assertEquals("ERROR_STOCK_INSUFICIENTE", service.actualizarInventario(1L, 50, "SALIDA"));
        assertEquals(20, libro.getStock());
    }

    @Test
    void salida_dejaStockBajoElMinimo_activaReordenConCantidadCalculada() {
        // stockMinimo=5, stock inicial=20, salida de 17 -> nuevoStock=3 (<5)
        // cantidadReorden = stockMinimo*3 - nuevoStock = 15 - 3 = 12
        assertEquals("OK_BAJO_STOCK_REORDEN_12", service.actualizarInventario(1L, 17, "SALIDA"));
        assertEquals(3, libro.getStock());
    }

    @Test
    void tipoMovimientoInvalido_retornaError() {
        assertEquals("ERROR_DATOS_INVALIDOS", service.actualizarInventario(1L, 5, "TRANSFERENCIA"));
    }

    @Test
    void productoInexistente_retornaError() {
        assertEquals("ERROR_PRODUCTO_NO_EXISTE", service.actualizarInventario(99L, 5, "ENTRADA"));
    }

    // --- Casos limite adicionales de caracterizacion ---

    @Test
    void cantidadCero_retornaErrorDatosInvalidosYNoModificaStock() {
        assertEquals("ERROR_DATOS_INVALIDOS", service.actualizarInventario(1L, 0, "ENTRADA"));
        assertEquals(20, libro.getStock());
    }

    @Test
    void productoIdNulo_retornaErrorDatosInvalidos() {
        assertEquals("ERROR_DATOS_INVALIDOS", service.actualizarInventario(null, 5, "ENTRADA"));
    }

    @Test
    void tipoMovimiento_distingueMayusculas() {
        assertEquals("ERROR_DATOS_INVALIDOS", service.actualizarInventario(1L, 5, "entrada"));
    }

    @Test
    void salida_dejaStockIgualAlMinimo_noActivaReorden() {
        // 20 - 15 = 5 y la condicion de reorden es estricta (nuevoStock < stockMinimo)
        assertEquals("OK_5", service.actualizarInventario(1L, 15, "SALIDA"));
    }

    @Test
    void salida_dejaStockEnCero_esValidaYReordenaElTripleDelMinimo() {
        // 20 - 20 = 0 (no es negativo) -> cantidadReorden = 5*3 - 0 = 15
        assertEquals("OK_BAJO_STOCK_REORDEN_15", service.actualizarInventario(1L, 20, "SALIDA"));
        assertEquals(0, libro.getStock());
    }

    @Test
    void entrada_queNoAlcanzaElMinimo_tambienActivaReorden() {
        Producto cafe = new Producto(2L, "Cafe", TipoProducto.ALIMENTO, 15.0, 2, 5);
        service.agregarProducto(cafe);
        // 2 + 1 = 3 (<5) -> cantidadReorden = 15 - 3 = 12
        assertEquals("OK_BAJO_STOCK_REORDEN_12", service.actualizarInventario(2L, 1, "ENTRADA"));
        assertEquals(3, cafe.getStock());
    }

    @Test
    void entrada_conCantidadNegativa_restaStock_comportamientoActualDocumentado() {
        // Caracterizacion: el codigo actual solo rechaza cantidad == 0, asi que una
        // ENTRADA negativa resta stock. Se documenta tal cual, aunque sea discutible.
        assertEquals("OK_15", service.actualizarInventario(1L, -5, "ENTRADA"));
        assertEquals(15, libro.getStock());
    }
}
