package com.lottus.tech.processo_seletico_lottus_tech.web.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("total_items")
    private Integer totalItems;

    @JsonProperty("total_value")
    private Double totalValue;
}
