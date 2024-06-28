package com.mdelamo.numbers.infrastructure.rest.handler;

import com.mdelamo.numbers.domain.exception.InvalidNumberOrderByBinaryException;
import com.mdelamo.numbers.infrastructure.rest.handler.NumbersExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NumbersExceptionHandlerTest {

    private static final String KEY_TIMESTAMP = "timestamp";

    private static final String KEY_ERROR = "error";

    private static final String KEY_CODE = "code";

    private static final String KEY_MESSAGE = "message";

    private static final int DELTA = 750;

    @InjectMocks
    private NumbersExceptionHandler numbersExceptionHandler;

    @Mock
    private WebRequest webRequest;


    @Test
    @DisplayName("should handle InvalidNumberOrderByBinaryException properly")
    void shouldHandleInvalidNumberOrderByBinaryExceptionProperly() {

        // given
        var codeExpected = "TEST_CODE";
        var messageExpected = "TEST_MESSAGE";
        var invalidNumberOrderByBinaryException = new InvalidNumberOrderByBinaryException(codeExpected, messageExpected);

        // when
        var responseEntityResult = numbersExceptionHandler.handleInvalidNumberOrderByBinaryException(invalidNumberOrderByBinaryException, webRequest);

        // Then
        var bodyResult = (Map<String, Object>) responseEntityResult.getBody();
        var localDateValue = (LocalDateTime) bodyResult.get(KEY_TIMESTAMP);
        assertThat(localDateValue).isCloseTo(now(), within(DELTA, ChronoUnit.MILLIS));
        var errorResult = (Map<String, String>) bodyResult.get(KEY_ERROR);
        assertThat(errorResult).containsEntry(KEY_CODE, codeExpected).containsEntry(KEY_MESSAGE, messageExpected);
        assertThat(responseEntityResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void shouldHandleRuntimeException() {

        // given
        var npeMessage = "npeMessage";
        var nullPointerException = new NullPointerException(npeMessage);

        // when
        var responseEntityResult = numbersExceptionHandler.handleRuntimeException(nullPointerException, webRequest);

        // then
        var bodyResult = (Map<String, Object>) responseEntityResult.getBody();
        var localDateValue = (LocalDateTime) bodyResult.get(KEY_TIMESTAMP);
        assertThat(localDateValue).isCloseTo(now(), within(DELTA, ChronoUnit.MILLIS));
        var errorResult = (Map<String, String>) bodyResult.get(KEY_ERROR);
        assertThat(errorResult).containsEntry(KEY_CODE, "UCE").containsEntry(KEY_MESSAGE,npeMessage);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityResult.getStatusCode());

    }

}
