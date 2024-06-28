package com.mdelamo.numbers.infrastructure.rest.controller;

import com.mdelamo.numbers.domain.model.Numbers;
import com.mdelamo.numbers.domain.usecase.NumbersOrdinationUseCase;
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
import org.springframework.http.ResponseEntity;

import static nl.altindag.log.LogCaptor.forClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Instancio.create;
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
    var numbersRQDTO = create(NumbersRQDTO.class);
    var numbersIn = mock(Numbers.class);
    var numbersOut = mock(Numbers.class);
    var numbersRSDTO = create(NumbersRSDTO.class);

    var inOrder = inOrder(restMapper, useCase);
    when(restMapper.toNumbers(numbersRQDTO)).thenReturn(numbersIn);
    when(useCase.order(numbersIn)).thenReturn(numbersOut);
    when(restMapper.toNumbersRSDTO(numbersOut)).thenReturn(numbersRSDTO);

    ResponseEntity<NumbersRSDTO> response;
    try (var logCaptor = forClass(NumbersOrdinationController.class)) {

        // when
        response = controller.order(numbersRQDTO);

        // then
        var messageRequestExpected = String.format("Request  POST /numbers-ordination -> criteria: %s - data: %s",
                numbersRQDTO.getCriteria(), numbersRQDTO.getData());
        var messageResponseExpected = String.format("Response POST /numbers-ordination -> criteria: %s - data: %s",
                numbersRSDTO.getCriteria(), numbersRSDTO.getData());
        assertThat(logCaptor.getInfoLogs()).containsExactly(messageRequestExpected, messageResponseExpected);
    }

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(numbersRSDTO);

    inOrder.verify(restMapper).toNumbers(numbersRQDTO);
    inOrder.verify(useCase).order(numbersIn);
    inOrder.verify(restMapper).toNumbersRSDTO(numbersOut);

    inOrder.verifyNoMoreInteractions();

  }



}
