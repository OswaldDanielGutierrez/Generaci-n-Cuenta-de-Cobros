package com.microserviceleasing.controller;

import com.microserviceleasing.entity.DatosCuentaCobro;
import com.microserviceleasing.service.DatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leasing")
public class DatosController {

    @Autowired
    private DatosService datosService;

    @PostMapping("/guardar")
    public ResponseEntity<DatosCuentaCobro> guardarCuenta(@RequestBody DatosCuentaCobro datosCuentaCobro) {
        return new ResponseEntity<>(datosService.guardarDatosCuentaCobro(datosCuentaCobro), HttpStatus.CREATED);
    }

    @PostMapping("/enviar")
    public ResponseEntity<DatosCuentaCobro> enviarDatos(@RequestBody DatosCuentaCobro datosCuentaCobro) {
        return new ResponseEntity<>(datosService.enviarListaCuentaCobro(datosCuentaCobro), HttpStatus.OK);
    }

    @GetMapping("/PDF")
    public ResponseEntity<byte[]> descargarPDF() {
        try {
            byte[] pdfBytes = datosService.obtenerPDFDesdeGenerarCuentaCobro();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "cuenta_cobro.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Manejar excepciones
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
