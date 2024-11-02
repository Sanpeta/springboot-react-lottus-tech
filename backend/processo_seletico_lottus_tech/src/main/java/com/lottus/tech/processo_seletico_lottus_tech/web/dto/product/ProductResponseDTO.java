package com.lottus.tech.processo_seletico_lottus_tech.web.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Double price;
}
