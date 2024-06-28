package com.mdelamo.numbers.application.usecase;

import com.mdelamo.numbers.application.comparator.BinaryRepresentationComparatorImpl;
import com.mdelamo.numbers.domain.model.Numbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;

import static com.mdelamo.numbers.domain.model.type.NumberOrderCriteriaType.BINARY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumbersOrdinationUseCaseImplTest {

  @InjectMocks
  private NumbersOrdinationUseCaseImpl useCase;

  @Mock
  private BinaryRepresentationComparatorImpl binaryRepresentationComparator;

  @Test
  @DisplayName("Should order numbers by binary using binary representation comparator")
  void shouldOrderByBinaryUsingBinaryRepresentationComparator() {

    // given
    var numbers = mock(Numbers.class);
    var comparator = mock(Comparator.class);
    var binaryCriteria = BINARY;

    var inOrder = inOrder(numbers, binaryRepresentationComparator);
    when(numbers.getCriteria()).thenReturn(binaryCriteria);
    when(binaryRepresentationComparator.getCountSetBitsComparator()).thenReturn(comparator);

    // when
    var orderResult = useCase.order(numbers);

    // then
    assertThat(orderResult.getCriteria()).isEqualTo(binaryCriteria);
    var numbersExpected = numbers.getData()
            .stream()
            .sorted(comparator)
            .toList();
    assertThat(orderResult.getData()).isEqualTo(numbersExpected);

    inOrder.verify(numbers).getCriteria();
    inOrder.verify(binaryRepresentationComparator).getCountSetBitsComparator();
    inOrder.verify(numbers).getData();
    inOrder.verifyNoMoreInteractions();

  }



}
