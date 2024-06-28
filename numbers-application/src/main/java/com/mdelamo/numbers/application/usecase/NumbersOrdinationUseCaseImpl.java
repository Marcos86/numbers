package com.mdelamo.numbers.application.usecase;

import com.mdelamo.numbers.application.comparator.BinaryRepresentationComparatorImpl;
import com.mdelamo.numbers.domain.model.Numbers;
import com.mdelamo.numbers.domain.model.type.NumberOrderCriteriaType;
import com.mdelamo.numbers.domain.usecase.NumbersOrdinationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class NumbersOrdinationUseCaseImpl implements NumbersOrdinationUseCase {

    private final BinaryRepresentationComparatorImpl binaryRepresentationComparator;

    @Override
    public Numbers order(final Numbers numbers) {
        var criteria = numbers.getCriteria();

        var numbersOrdered = numbers.getData()
                .stream()
                .sorted(getComparator(criteria))
                .toList();

        return Numbers.builder()
                .criteria(criteria)
                .data(numbersOrdered)
                .build();

    }

    private Comparator<Integer> getComparator(final NumberOrderCriteriaType criteria) {
        return switch (criteria) {
            case BINARY -> binaryRepresentationComparator.getCountSetBitsComparator();
        };
    }

}
