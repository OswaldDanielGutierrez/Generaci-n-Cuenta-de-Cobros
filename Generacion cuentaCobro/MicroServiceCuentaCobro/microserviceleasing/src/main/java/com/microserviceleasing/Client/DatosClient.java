package com.microserviceleasing.Client;

import com.microserviceleasing.entity.DatosCuentaCobro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@GetMapping("/generarPDF")


@FeignClient(name = "GenerarCuentasCobros", url = "localhost:8090/api/v1/cuentaCobro")
public interface DatosClient {

    @PostMapping("/guardar")
    DatosCuentaCobro enviar(DatosCuentaCobro datosCuentaCobro);

    @GetMapping("/descargarPDF")
    byte[] descargarPDF();

}
