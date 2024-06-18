package com.microserviceleasing.service;

import com.microserviceleasing.Client.DatosClient;
import com.microserviceleasing.entity.DatosCuentaCobro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DatosService {

    @Autowired
    private DatosClient datosClient;

    public DatosCuentaCobro guardarDatosCuentaCobro(DatosCuentaCobro datosCuentaCobro) {
        return datosCuentaCobro;
    }

    public DatosCuentaCobro enviarListaCuentaCobro(DatosCuentaCobro datosCuentaCobro){
        datosCuentaCobro.setNombreVendedor("Leasing - Grupo Bancolombia");
        datosCuentaCobro.setCelularVendedor("588 15 68");
        return datosClient.enviar(datosCuentaCobro);
    }


    public byte[] obtenerPDFDesdeGenerarCuentaCobro() {
        return datosClient.descargarPDF();
    }


}
