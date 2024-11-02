package com.lottus.tech.processo_seletico_lottus_tech.web.dto.order;

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
public class OrderResponseDTO {
    private Long id;
    private String code;
    private String date;
    private String clientName;
    private Integer totalItems;
    private Double totalValue;
}
