package com.mdelamo.numbers.infrastructure.rest.mapper;

import com.mdelamo.numbers.domain.model.Numbers;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRQDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Instancio.create;

@ExtendWith(MockitoExtension.class)
class NumbersRestMapperTest {

  @Spy
  private final NumbersRestMapper numbersRestMapper = Mappers.getMapper(NumbersRestMapper.class);

  @Spy
  private final NumbersRestMapperHelper numbersRestMapperHelper = Mappers.getMapper(NumbersRestMapperHelper.class);

  @Test
  @DisplayName("should map properly toNumbers")
  void mapProperlyToNumbers() {

    // given
    var numbersRQDTO = create(NumbersRQDTO.class);

    // when
    var spacecraft = this.numbersRestMapper.toNumbers(numbersRQDTO);

    // then
    var spacecraftRQDTOResult = this.numbersRestMapperHelper.toNumbersRQDTO(spacecraft);
    assertThat(spacecraftRQDTOResult).isEqualTo(numbersRQDTO);
  }


  @Test
  @DisplayName("should map properly toNumbersRSDTO")
  void mapProperlyToNumbersRSDTO() {

    // given
    var numbers = create(Numbers.class);

    // when
    var numbersRSDTO = this.numbersRestMapper.toNumbersRSDTO(numbers);

    // then
    var numbersRestMapper = this.numbersRestMapperHelper.toNumbers(numbersRSDTO);
    assertThat(numbersRestMapper).isEqualTo(numbers);
  }



}
