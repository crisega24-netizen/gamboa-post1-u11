package com.universidad.refactoringu11.inventario;

/**
 * Tipo de producto con su comportamiento propio (Replace Conditional with Polymorphism).
 * Agregar un tipo nuevo obliga, en tiempo de compilacion, a definir su recargo de envio
 * y los datos de su etiqueta, sin modificar ClasificadorProducto ni EtiquetadoProducto.
 */
public enum TipoProducto {

    LIBRO("[LIBRO]", "Exento de IVA") {
        @Override
        public double calcularRecargoEnvio(double precio) {
            return 0.0;
        }
    },
    ELECTRONICO("[ELECTRO]", "Garantia 1 ano") {
        @Override
        public double calcularRecargoEnvio(double precio) {
            return precio * 0.08;
        }
    },
    ROPA("[ROPA]", "Ver talla") {
        @Override
        public double calcularRecargoEnvio(double precio) {
            return precio * 0.03;
        }
    },
    ALIMENTO("[ALIMENTO]", "Perecedero") {
        @Override
        public double calcularRecargoEnvio(double precio) {
            return 2.50;
        }
    };

    private final String prefijoEtiqueta;
    private final String leyendaEtiqueta;

    TipoProducto(String prefijoEtiqueta, String leyendaEtiqueta) {
        this.prefijoEtiqueta = prefijoEtiqueta;
        this.leyendaEtiqueta = leyendaEtiqueta;
    }

    /** Comportamiento que varia por tipo: cada constante define su propia formula. */
    public abstract double calcularRecargoEnvio(double precio);

    /** Comportamiento comun: solo varian los datos, que cada constante declara. */
    public String generarEtiqueta(String nombreProducto) {
        return prefijoEtiqueta + " " + nombreProducto + " - " + leyendaEtiqueta;
    }
}
