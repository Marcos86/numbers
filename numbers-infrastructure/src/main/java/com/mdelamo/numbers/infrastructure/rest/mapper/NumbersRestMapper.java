package com.mdelamo.numbers.infrastructure.rest.mapper;

import com.mdelamo.numbers.domain.model.Numbers;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRSDTO;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRQDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface NumbersRestMapper {

  Numbers toNumbers(NumbersRQDTO numbersRQDTO);

  NumbersRSDTO toNumbersRSDTO(Numbers numbers);

}
