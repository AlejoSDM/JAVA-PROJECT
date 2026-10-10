package controller;

import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import Dao.ReporteCRUD;
import Dao.ReporteCRUD.CelularReporte;
import Dao.ReporteCRUD.DetalleReporte;
import Dao.ReporteCRUD.VentaReporte;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import view.Menu;




public class ReporteController {

    private final ReporteCRUD reporteCRUD;
    private final Menu menu;

    public ReporteController(ReporteCRUD reporteCRUD, Menu menu) {
        this.reporteCRUD = reporteCRUD;
        this.menu = menu;
    }

    public void menu() {
        int opcion;

        do {
            opcion = menu.mostrarMenuReportes();

            switch (opcion) {
                case 1 -> mostrarReportes();
                case 2 -> mostrarReporteStockBajo();
                case 3 -> mostrarReporteVentasTotales();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    public void mostrarReporteStockBajo() {
        try {
            List<CelularReporte> celulares = reporteCRUD.listarCelulares();
            mostrarStockBajo(celulares);
        } catch (SQLException e) {
            System.out.println("No se pudo consultar el stock: " + e.getMessage());
        }
    }

    public void mostrarReporteVentasTotales() {
        try {
            List<VentaReporte> ventas = reporteCRUD.listarVentas();
            List<DetalleReporte> detalles = reporteCRUD.listarDetalles();

            mostrarVentasPorMes(ventas);
            generarArchivoVentas(ventas, detalles);
        } catch (SQLException | IOException e) {
            System.out.println("No se pudo generar el reporte de ventas: "
                    + e.getMessage());
        }
    }
    public void mostrarReportes() {
        try {
            List<CelularReporte> celulares = reporteCRUD.listarCelulares();
            List<DetalleReporte> detalles = reporteCRUD.listarDetalles();
            List<VentaReporte> ventas = reporteCRUD.listarVentas();

            mostrarStockBajo(celulares);
            mostrarTopTres(detalles);
            mostrarVentasPorMes(ventas);
            generarArchivoVentas(ventas, detalles);

        } catch (java.sql.SQLException | IOException e) {
            System.out.println("No se pudieron generar los reportes: "
                    + e.getMessage());
        }
    }

    private void mostrarStockBajo(List<CelularReporte> celulares) {
        System.out.println("\n=== CELULARES CON STOCK BAJO (< 5) ===");

        List<CelularReporte> stockBajo = celulares.stream()
                .filter(celular -> celular.stock < 5)
                .sorted(Comparator.comparingInt(celular -> celular.stock))
                .collect(Collectors.toList());

        if (stockBajo.isEmpty()) {
            System.out.println("No hay celulares con stock menor a 5.");
            return;
        }

        stockBajo.forEach(celular ->
                System.out.println(celular.nombreCompleto()
                        + " | Stock: " + celular.stock));
    }

    private void mostrarTopTres(List<DetalleReporte> detalles) {
        System.out.println("\n=== TOP 3 CELULARES MÁS VENDIDOS ===");

        Map<Integer, Integer> unidadesPorCelular = detalles.stream()
                .collect(Collectors.groupingBy(
                        detalle -> detalle.celularId,
                        Collectors.summingInt(detalle -> detalle.cantidad)
                ));

        Map<Integer, String> nombres = detalles.stream()
                .collect(Collectors.toMap(
                        detalle -> detalle.celularId,
                        DetalleReporte::nombreCompleto,
                        (nombreExistente, nombreNuevo) -> nombreExistente
                ));

        List<Map.Entry<Integer, Integer>> topTres =
                unidadesPorCelular.entrySet().stream()
                        .sorted(Map.Entry
                                .<Integer, Integer>comparingByValue()
                                .reversed())
                        .limit(3)
                        .collect(Collectors.toList());

        if (topTres.isEmpty()) {
            System.out.println("Todavía no hay celulares vendidos.");
            return;
        }

        int posicion = 1;
        for (Map.Entry<Integer, Integer> entrada : topTres) {
            System.out.println(posicion++ + ". "
                    + nombres.get(entrada.getKey())
                    + " | Unidades vendidas: " + entrada.getValue());
        }
    }

    private void mostrarVentasPorMes(List<VentaReporte> ventas) {
        System.out.println("\n=== VENTAS TOTALES POR MES ===");

        Map<YearMonth, BigDecimal> totalesPorMes = ventas.stream()
                .collect(Collectors.groupingBy(
                        venta -> YearMonth.from(venta.fecha),
                        Collectors.mapping(
                                venta -> venta.total,
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        BigDecimal::add
                                )
                        )
                ));

        if (totalesPorMes.isEmpty()) {
            System.out.println("Todavía no hay ventas.");
            return;
        }

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("MM/yyyy");

        totalesPorMes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entrada ->
                        System.out.println(
                                entrada.getKey().format(formato)
                                        + " | Total: "
                                        + entrada.getValue()
                        )
                );
    }

    private void generarArchivoVentas(
            List<VentaReporte> ventas,
            List<DetalleReporte> detalles
    ) throws IOException {

        Map<Integer, List<DetalleReporte>> detallesPorVenta =
                detalles.stream()
                        .collect(Collectors.groupingBy(
                                detalle -> detalle.ventaId
                        ));

        Path carpetaDescargas = Paths.get(
                System.getProperty("user.home"),
                "Downloads"
        );

        Files.createDirectories(carpetaDescargas);

        Path archivo = carpetaDescargas.resolve("reporte_ventas.txt");

        DateTimeFormatter formatoFecha =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (BufferedWriter writer = Files.newBufferedWriter(
                archivo,
                StandardCharsets.UTF_8
        )) {
            writer.write("REPORTE DE VENTAS");
            writer.newLine();
            writer.write("=================");
            writer.newLine();
            writer.newLine();

            for (VentaReporte venta : ventas) {
                writer.write("Venta #" + venta.id);
                writer.newLine();
                writer.write("Fecha: " + venta.fecha.format(formatoFecha));
                writer.newLine();
                writer.write("Cliente: " + venta.cliente);
                writer.newLine();
                writer.write("Empleado: " + venta.empleado);
                writer.newLine();
                writer.write("Total: " + venta.total);
                writer.newLine();
                writer.write("Productos:");
                writer.newLine();

                List<DetalleReporte> productos =
                        detallesPorVenta.getOrDefault(
                                venta.id,
                                Collections.emptyList()
                        );

                for (DetalleReporte detalle : productos) {
                    writer.write("  - " + detalle.nombreCompleto()
                            + " | Cantidad: " + detalle.cantidad);
                    writer.newLine();
                }

                writer.newLine();
            }
        }

        System.out.println("\nArchivo generado: "
                + archivo.toAbsolutePath());
    }
}