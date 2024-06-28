package com.mdelamo.numbers.application.comparator;

import com.mdelamo.numbers.domain.exception.InvalidNumberOrderByBinaryException;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class BinaryRepresentationComparatorImpl implements OrderNumbersComparator{

    @Override
    public Comparator<Integer> getCountSetBitsComparator() {
        return (number1, number2) -> {
            validateNegativeNumber(number1);
            validateNegativeNumber(number2);
            var compareSetBits = Integer.compare(countSetBits(number1), countSetBits(number2));
            if (compareSetBits == 0) {
                return number1.compareTo(number2);
            } else {
                return compareSetBits;
            }
        };
    }


    private int countSetBits(int number) {
        var count = 0;
        while (number > 0) {
            number &= number - 1;
            count++;
        }
        return count;
    }

    private void validateNegativeNumber(int number) {
        if (number < 0) {
            throw new InvalidNumberOrderByBinaryException("NEGATIVE_NUMBER",
                    "Not possible to order by binary representation. Current number: " + number + " can't be negative.");
        }
    }
}
