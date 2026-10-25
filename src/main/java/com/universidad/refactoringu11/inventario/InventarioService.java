package com.universidad.refactoringu11.inventario;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private static final String ENTRADA = "ENTRADA";
    private static final String SALIDA = "SALIDA";

    private final List<Producto> productos = new ArrayList<>();
    private final List<String> movimientos = new ArrayList<>();
    private final CalculadoraReorden calculadoraReorden = new CalculadoraReorden();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public String actualizarInventario(Long productoId, int cantidad, String tipoMovimiento) {
        if (!esMovimientoValido(productoId, cantidad, tipoMovimiento)) return "ERROR_DATOS_INVALIDOS";
        Optional<Producto> encontrado = buscarProducto(productoId);
        if (encontrado.isEmpty()) return "ERROR_PRODUCTO_NO_EXISTE";
        Producto producto = encontrado.get();
        int nuevoStock = calcularNuevoStock(producto.getStock(), cantidad, tipoMovimiento);
        if (tipoMovimiento.equals(SALIDA) && nuevoStock < 0) return "ERROR_STOCK_INSUFICIENTE";
        producto.setStock(nuevoStock);
        registrarMovimiento(tipoMovimiento, cantidad, producto);
        return resultadoDelMovimiento(producto);
    }

    private boolean esMovimientoValido(Long productoId, int cantidad, String tipoMovimiento) {
        return productoId != null && cantidad != 0
                && (tipoMovimiento.equals(ENTRADA) || tipoMovimiento.equals(SALIDA));
    }

    private Optional<Producto> buscarProducto(Long productoId) {
        return productos.stream()
                .filter(p -> p.getId().equals(productoId))
                .findFirst();
    }

    private int calcularNuevoStock(int stockActual, int cantidad, String tipoMovimiento) {
        return tipoMovimiento.equals(ENTRADA) ? stockActual + cantidad : stockActual - cantidad;
    }

    private void registrarMovimiento(String tipoMovimiento, int cantidad, Producto producto) {
        String registro = tipoMovimiento + " de " + cantidad + " unidades de "
                + producto.getNombre() + " -> stock final: " + producto.getStock();
        movimientos.add(registro);
        System.out.println(registro);
    }

    private String resultadoDelMovimiento(Producto producto) {
        if (!calculadoraReorden.necesitaReorden(producto)) return "OK_" + producto.getStock();
        int cantidadReorden = calculadoraReorden.cantidadAReordenar(producto);
        alertarBajoStock(producto, cantidadReorden);
        return "OK_BAJO_STOCK_REORDEN_" + cantidadReorden;
    }

    private void alertarBajoStock(Producto producto, int cantidadReorden) {
        System.out.println("ALERTA: " + producto.getNombre()
                + " bajo stock minimo. Reordenar " + cantidadReorden + " unidades.");
    }
}
