package com.generarcuentascobros.controller;

import com.generarcuentascobros.entity.DatosCuentaCobro;
import com.generarcuentascobros.exceptions.CuentaCobroNotFount;
import com.generarcuentascobros.service.CuentaCobroService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1/cuentaCobro")
public class CuentaCobroController {

    @Autowired
    private CuentaCobroService cuentaCobroService;

    @PostMapping("/guardar")
    public ResponseEntity<DatosCuentaCobro> guardarCuenta(@RequestBody DatosCuentaCobro datosCuentaCobro){
        return new ResponseEntity<>(cuentaCobroService.recibirDatosCuentaCobro(datosCuentaCobro), HttpStatus.CREATED);
    }


    @GetMapping("/descargarPDF")
    public ResponseEntity<byte[]> descargarPDF(HttpServletRequest request) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            cuentaCobroService.export(baos); // Exporta el PDF al OutputStream

            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "cuenta_cobro.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException | CuentaCobroNotFount e) {
            // Manejar excepciones
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
