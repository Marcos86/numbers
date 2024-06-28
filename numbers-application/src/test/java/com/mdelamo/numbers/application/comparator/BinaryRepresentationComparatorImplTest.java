package com.mdelamo.numbers.application.comparator;

import com.mdelamo.numbers.domain.exception.InvalidNumberOrderByBinaryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BinaryRepresentationComparatorImplTest {

  @InjectMocks
  private BinaryRepresentationComparatorImpl comparator;


  @ParameterizedTest
  @MethodSource("provideListsOfNumbers") // given
  @DisplayName("Should compare numbers using binary representation comparator properly")
  void shouldCompareNumbersUsingBinaryRepresentationComparatorProperly(final List<Integer> inputNumbers, final List<Integer> outputNumbers) {

    // when
    var result = inputNumbers
            .stream()
            .sorted(comparator.getCountSetBitsComparator())
            .toList();

    // then
    assertThat(result).containsExactlyElementsOf(outputNumbers);

  }


  @Test
  @DisplayName("Should throw InvalidNumberOrderByBinaryException when a numbers is negative")
  void shouldThrowInvalidNumberOrderByBinaryExceptionWhenANumberIsNegative() {

    // given
    var inputNumbers = List.of(1, 28, -4, 6);

    @SuppressWarnings("java:S5778") // Reason: False issue this change not apply in test.
    var invalidNumberOrderByBinaryException = assertThrows(InvalidNumberOrderByBinaryException.class, () ->
                    inputNumbers
                            .stream()
                            .sorted(comparator.getCountSetBitsComparator())
                            .toList());

    assertThat(invalidNumberOrderByBinaryException.getCode()).isEqualTo("NEGATIVE_NUMBER");
    var messageExpected = "Not possible to order by binary representation. Current number: -4 can't be negative.";
    assertThat(invalidNumberOrderByBinaryException.getMessage()).isEqualTo(messageExpected);


  }

  public static Stream<Arguments> provideListsOfNumbers() {

    return Stream.of(
            Arguments.of(List.of(7, 2, 1, 9, 5, 3, 8, 25, 42),
                    List.of (1, 2, 8, 3, 5, 9, 7, 25, 42)),
            Arguments.of(List.of(1, 28, 4, 2, 6, 9, 55, 15, 27, 46, 11, 128, 16),
                    List.of (1, 2, 4, 16, 128, 6, 9, 11, 28, 15, 27, 46, 55)));

  }

}
