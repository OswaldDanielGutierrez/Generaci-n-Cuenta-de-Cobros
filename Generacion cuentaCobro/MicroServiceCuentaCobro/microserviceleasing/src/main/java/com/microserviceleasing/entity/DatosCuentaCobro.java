package com.microserviceleasing.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class DatosCuentaCobro {


    @NotEmpty
    private String nombreComprador;

    @NotEmpty
    private String celularComprador;

    @NotEmpty
    private String identificacionComprador;

    private String nombreVendedor;

    private String celularVendedor;

    @NotEmpty
    @Size(min = 1)
    private List<ProductoComprado> productoComprados;

    @Setter
    @Getter
    @NotEmpty
    public static class ProductoComprado{

        @NotEmpty
        private String nombre;

        @NotEmpty
        @Size(min = 1)
        private int cantidad;

        @NotEmpty
        @Positive
        private long precio;

    }

}