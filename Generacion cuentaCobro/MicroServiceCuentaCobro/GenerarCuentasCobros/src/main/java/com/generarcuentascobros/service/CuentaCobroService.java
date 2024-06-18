package com.generarcuentascobros.service;

import com.generarcuentascobros.entity.DatosCuentaCobro;
import com.generarcuentascobros.exceptions.CuentaCobroNotFount;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CuentaCobroService {

    private final List<DatosCuentaCobro> listaDatosCuentaCobro = new ArrayList<>();

    public DatosCuentaCobro recibirDatosCuentaCobro ( DatosCuentaCobro datosCuentaCobro){
        listaDatosCuentaCobro.clear();
        listaDatosCuentaCobro.add(datosCuentaCobro);

        return datosCuentaCobro;
    }

    public void export(ByteArrayOutputStream outputStream) throws IOException, CuentaCobroNotFount {

        if (listaDatosCuentaCobro.isEmpty()){
            throw new CuentaCobroNotFount("No hay datos para generar una cuenta de cobro");
        }

        DatosCuentaCobro datosCuentaCobro = listaDatosCuentaCobro.get(0);
        Random random = new Random();
        int numeroCuenta = random.nextInt(900) + 100;

        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        long identificacionEmpresa = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        long cuentaAhorros = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;


        String nombreEmpresa = datosCuentaCobro.getNombreVendedor();
        String nombreComprador = datosCuentaCobro.getNombreComprador();
        String identificacionComprador = datosCuentaCobro.getCelularComprador();
        String celularComprador = datosCuentaCobro.getCelularComprador();
        String celularVendedor = datosCuentaCobro.getCelularVendedor();
        DecimalFormat formato = new DecimalFormat("#,###.##");

        long montoNumero = 0;
        for (DatosCuentaCobro.ProductoComprado producto: datosCuentaCobro.getProductoComprados()){
            montoNumero += producto.getPrecio();
        }

        String monto = formato.format(montoNumero);

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, outputStream);

        Paragraph paragraph = new Paragraph();

        document.open();

        //CUENTA DE COBRO #000
        Font fontNumeroCuentaCobro = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontNumeroCuentaCobro.setSize(28);
        Paragraph pNumeroCuentaCobro = new Paragraph("Cuenta de cobro " + numeroCuenta , fontNumeroCuentaCobro);
        pNumeroCuentaCobro.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pNumeroCuentaCobro);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        //Fecha "14-01-2000"
        Font fontFecha = FontFactory.getFont(FontFactory.HELVETICA);
        fontFecha.setSize(14);
        Paragraph pFecha = new Paragraph(fecha, fontFecha);
        pFecha.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pFecha);
        paragraph.setSpacingAfter(15);
        document.add(paragraph);

        //NOMBRE COMPRADOR
        Font fontNombreComprador = FontFactory.getFont(FontFactory.HELVETICA);
        fontNombreComprador.setSize(16);
        Paragraph pNombreComprador = new Paragraph(nombreComprador  , fontNombreComprador);
        pNombreComprador.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pNombreComprador);
        paragraph.setSpacingAfter(-5);
        document.add(paragraph);


        //IDENTIFICACIÓN COMPRADOR
        Font fontCedulaComprador = FontFactory.getFont(FontFactory.HELVETICA);
        fontCedulaComprador.setSize(16);
        Paragraph pCedulaComprador = new Paragraph("C.C.  " + identificacionComprador  , fontCedulaComprador);
        pCedulaComprador.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pCedulaComprador);
        paragraph.setSpacingAfter(-5);
        document.add(paragraph);

        //CELULAR COMPRADOR
        Font fontCelularComprador = FontFactory.getFont(FontFactory.HELVETICA);
        fontCelularComprador.setSize(16);
        Paragraph pCelularComprador = new Paragraph (celularComprador  , fontCelularComprador);
        pCelularComprador.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pCelularComprador);
        paragraph.setSpacingAfter(25);
        document.add(paragraph);

        //debe a
        Font fontDeber = FontFactory.getFont(FontFactory.HELVETICA);
        fontDeber.setSize(10);
        Paragraph pDeber = new Paragraph("Debe a:"  , fontDeber);
        pDeber.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pDeber);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        //NOMBRE VENDEDOR "OSWALD"
        Font fontNombreVendedor = FontFactory.getFont(FontFactory.HELVETICA);
        fontNombreVendedor.setSize(16);
        Paragraph pNombreVendedor = new Paragraph(nombreEmpresa  , fontNombreVendedor);
        pNombreVendedor.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pNombreVendedor);
        paragraph.setSpacingAfter(-5);
        document.add(paragraph);

        //IDENTIFICACIÓN VENDEDOR
        Font fontIdentificacionVendedor = FontFactory.getFont(FontFactory.HELVETICA);
        fontIdentificacionVendedor.setSize(16);
        Paragraph pIdentificacionVendedor = new Paragraph("NIT  " + identificacionEmpresa  , fontIdentificacionVendedor);
        pIdentificacionVendedor.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pIdentificacionVendedor);
        paragraph.setSpacingAfter(25);
        document.add(paragraph);


        //La suma de
        Font fontSuma = FontFactory.getFont(FontFactory.HELVETICA);
        fontSuma.setSize(10);
        Paragraph pSuma = new Paragraph("La suma de:"  , fontSuma);
        pSuma.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pSuma);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        //Monto
        Font fontMonto = FontFactory.getFont(FontFactory.HELVETICA);
        fontMonto.setSize(16);
        Paragraph pMonto = new Paragraph(monto , fontMonto);
        pMonto.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pMonto);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);

        //Por concento de
        Font fontConceptoDe = FontFactory.getFont(FontFactory.HELVETICA);
        fontConceptoDe.setSize(10);
        Paragraph pConceptoDe = new Paragraph("Por concepto de:"  , fontConceptoDe);
        pConceptoDe.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pConceptoDe);
        paragraph.setSpacingAfter(15);
        document.add(paragraph);

        for (DatosCuentaCobro datos: listaDatosCuentaCobro){

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            PdfPCell cellProducto = new PdfPCell(new Phrase("Producto", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cellProducto.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellProducto.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellProducto.setBackgroundColor(new Color(143, 143, 143));

            PdfPCell cellCantidad = new PdfPCell(new Phrase("Cantidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellCantidad.setBackgroundColor(new Color(143, 143, 143));

            PdfPCell cellPrecio = new PdfPCell(new Phrase("Precio", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cellPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPrecio.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellPrecio.setBackgroundColor(new Color(143, 143, 143));

            table.addCell(cellProducto);
            table.addCell(cellCantidad);
            table.addCell(cellPrecio);


            boolean colorFondo = true;
            for (int rowIndex = 0; rowIndex < datos.getProductoComprados().size(); rowIndex++) {
                DatosCuentaCobro.ProductoComprado producto = datos.getProductoComprados().get(rowIndex);
                PdfPCell cellNombre = new PdfPCell(new Phrase(producto.getNombre()));
                PdfPCell cellCantidadValue = new PdfPCell(new Phrase(String.valueOf(producto.getCantidad())));
                PdfPCell cellPrecioValue = new PdfPCell(new Phrase(String.valueOf(producto.getPrecio())));
                cellCantidadValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellCantidadValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellPrecioValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPrecioValue.setVerticalAlignment(Element.ALIGN_MIDDLE);

                if (colorFondo) {
                    cellNombre.setBackgroundColor(Color.WHITE);
                    cellCantidadValue.setBackgroundColor(Color.WHITE);
                    cellPrecioValue.setBackgroundColor(Color.WHITE);
                } else {
                    cellNombre.setBackgroundColor(new Color(201, 201, 201));
                    cellCantidadValue.setBackgroundColor(new Color(201, 201, 201));
                    cellPrecioValue.setBackgroundColor(new Color(201, 201, 201));

                }
                colorFondo = !colorFondo;

                table.addCell(cellNombre);
                table.addCell(cellCantidadValue);
                table.addCell(cellPrecioValue);
            }

            float cellHeight = 40f;
            for (int i = 0; i < table.getRows().size(); i++) {
                PdfPCell[] cells = table.getRows().get(i).getCells();
                for (PdfPCell cell : cells) {
                    cell.setFixedHeight(cellHeight);
                }
            }

            PdfPCell cellTotal = new PdfPCell(new Phrase("Total:", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellTotal.setColspan(2);
            cellTotal.setFixedHeight(cellHeight);
            table.addCell(cellTotal);

            // Añadir la celda con el monto total
            PdfPCell cellMontoTotal = new PdfPCell(new Phrase(monto));
            cellMontoTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellMontoTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellMontoTotal);


            document.add(table);

            document.add(Chunk.NEWLINE);
        }

        //Atentamente
        Font fontAtentamente = FontFactory.getFont(FontFactory.HELVETICA);
        fontAtentamente.setSize(14);
        Paragraph pAtentamente = new Paragraph("Atentamente, "  , fontAtentamente);
        pAtentamente.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pAtentamente);
        paragraph.setSpacingAfter(10);
        document.add(paragraph);

        //NOMBRE VENDEDOR "OSWALD"
        fontNombreVendedor.setSize(12);
        pNombreVendedor.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pNombreVendedor);
        paragraph.setSpacingAfter(2);
        document.add(paragraph);

        //IDENTIFICACIÓN VENDEDOR
        fontIdentificacionVendedor.setSize(12);
        pIdentificacionVendedor.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pIdentificacionVendedor);
        paragraph.setSpacingAfter(2);
        document.add(paragraph);

        //CELULAR VENDEDOR
        Font fontCelularVendedor = FontFactory.getFont(FontFactory.HELVETICA);
        fontCelularVendedor.setSize(12);
        Paragraph pCelularVendedor = new Paragraph ("TEL:  " + celularVendedor  , fontCelularVendedor);
        pCelularVendedor.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pCelularVendedor);
        paragraph.setSpacingAfter(2);
        document.add(paragraph);

        //CELULAR VENDEDOR
        Font fontConsignacion = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontConsignacion.setSize(12);
        Paragraph pConsignacion = new Paragraph ("Por favor consigrnar a la Cuenta de ahorros No." + cuentaAhorros + " del Banco Bancolombia" , fontConsignacion);
        pConsignacion.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pConsignacion);
        document.add(paragraph);

        document.close();

    }

}
