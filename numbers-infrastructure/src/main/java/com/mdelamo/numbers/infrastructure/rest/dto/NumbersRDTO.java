package com.mdelamo.numbers.infrastructure.rest.dto;

import com.mdelamo.numbers.infrastructure.rest.dto.type.NumbersOrderCriteriaType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class NumbersRDTO {


    @NotNull
    private NumbersOrderCriteriaType criteria;

    @Size(min = 1)
    private List<Integer> data;

}
