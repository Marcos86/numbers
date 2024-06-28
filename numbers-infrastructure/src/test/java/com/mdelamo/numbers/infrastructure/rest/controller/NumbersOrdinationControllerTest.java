package com.mdelamo.numbers.infrastructure.rest.controller;

import com.mdelamo.numbers.domain.model.Numbers;
import com.mdelamo.numbers.domain.usecase.NumbersOrdinationUseCase;
import com.mdelamo.numbers.infrastructure.rest.controller.NumbersOrdinationController;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRQDTO;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRSDTO;
import com.mdelamo.numbers.infrastructure.rest.mapper.NumbersRestMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumbersOrdinationControllerTest {

  @InjectMocks
  private NumbersOrdinationController controller;

  @Mock
  private NumbersRestMapper restMapper;

  @Mock
  private NumbersOrdinationUseCase useCase;


  @Test
  @DisplayName("Should return response entity ok with filled response when ordinate with criteria")
  void shouldReturnResponseEntityOkWithFilledResponseWhenOrdinateWithCriteria() {

    // given
    var numbersRQDTO = mock(NumbersRQDTO.class);
    var numbersIn = mock(Numbers.class);
    var numbersOut = mock(Numbers.class);
    var numbersRSDTO = mock(NumbersRSDTO.class);

    var inOrder = inOrder(restMapper, useCase);
    when(restMapper.toNumbers(numbersRQDTO)).thenReturn(numbersIn);
    when(useCase.order(numbersIn)).thenReturn(numbersOut);
    when(restMapper.toNumbersRSDTO(numbersOut)).thenReturn(numbersRSDTO);

    // when
    var response = controller.order(numbersRQDTO);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(numbersRSDTO);

    inOrder.verify(restMapper).toNumbers(numbersRQDTO);
    inOrder.verify(useCase).order(numbersIn);
    inOrder.verify(restMapper).toNumbersRSDTO(numbersOut);

    inOrder.verifyNoMoreInteractions();

  }



}
