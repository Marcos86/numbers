package com.mdelamo.numbers.infrastructure;

import com.mdelamo.numbers.infrastructure.rest.controller.NumbersOrdinationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {NumbersApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ApplicationIT {

    @Autowired
    private NumbersOrdinationController numbersOrderByBinaryController;

    @Test
    void loadsUp() {
        assertNotNull(this.numbersOrderByBinaryController);
    }
}