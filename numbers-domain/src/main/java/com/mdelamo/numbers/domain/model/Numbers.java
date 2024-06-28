package com.mdelamo.numbers.domain.model;

import com.mdelamo.numbers.domain.model.type.NumberOrderCriteriaType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Numbers {

    private NumberOrderCriteriaType criteria;

    private List<Integer> data;
}
