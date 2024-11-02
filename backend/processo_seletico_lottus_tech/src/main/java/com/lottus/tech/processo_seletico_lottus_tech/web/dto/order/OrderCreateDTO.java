package com.lottus.tech.processo_seletico_lottus_tech.web.dto.order;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
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
public class OrderCreateDTO {

    @NotBlank
    private String code;
    
    @NotBlank
    private String date;

    @JsonProperty("client_name")
    private String clientName;

    @NumberFormat
    @JsonProperty("total_items")
    private Integer totalItems;

    @NumberFormat
    @JsonProperty("total_value")
    private Double totalValue;

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public Double getTotalValue() {
        return totalValue;
    }
}
