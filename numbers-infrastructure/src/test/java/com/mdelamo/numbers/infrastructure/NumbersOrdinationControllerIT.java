package com.mdelamo.numbers.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRSDTO;
import com.mdelamo.numbers.infrastructure.rest.dto.type.NumbersOrderCriteriaType;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRQDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NumbersOrdinationControllerIT {

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private static final String URL_NUMBERS_BY_BINARY = "/numbers-ordination";

	private static final String EXPRESSION_DATA = "$.data";
	private static final String EXPRESSION_TIMESTAMP = "$.timestamp";
	private static final String EXPRESSION_ERROR_CODE = "$.error.code";
	private static final String EXPRESSION_ERROR_MESSAGE = "$.error.message";

	private static final ResultMatcher OK = status().isOk();
	private static final ResultMatcher BAD_REQUEST = status().isBadRequest();
	private static final ResultMatcher MATCHER_APPLICATION_JSON = content().contentType("application/json");

	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@MethodSource("provideListsOfNumbers")
	@DisplayName("Given many numbers list of numbers" +
			"When call order numbers by binary" +
			"Then numbers from each list have been ordered properly")
	@SneakyThrows
	void shouldOrderProperly(final List<Integer> inputNumbers, final List<Integer> outputNumbers) {

		// given
		var numbers = buildNumbers(inputNumbers);

		// when && then
		var response = this.mockMvc.perform(post(URL_NUMBERS_BY_BINARY)
						.contentType(APPLICATION_JSON_UTF8)
						.content(getObjectString(numbers)))
				.andDo(print())
				.andExpect(OK)
				.andExpect(MATCHER_APPLICATION_JSON)
				.andExpect(jsonPath(EXPRESSION_DATA).exists())
				.andReturn()
				.getResponse();

		var numbersOrderByBinaryRSDTO = new ObjectMapper().readValue(response.getContentAsString(), NumbersRSDTO.class);
		assertThat(numbersOrderByBinaryRSDTO.getData()).containsExactlyElementsOf(outputNumbers);
	}




	@Test
	@DisplayName("Given numbers list of numbers (with one negative)" +
			"When call order numbers by binary" +
			"Then response is bad request with error filled")
	@SneakyThrows
	void shouldReturnBadRequestWhenOneNumberIsNegativeThenReturnBadRequest() {

		// given
		var inputNumbers = List.of(1, 28, -4, 6);
		var numbers = buildNumbers(inputNumbers);

		// when && then
		this.mockMvc.perform(post(URL_NUMBERS_BY_BINARY)
						.contentType(APPLICATION_JSON_UTF8)
						.content(getObjectString(numbers)))
				.andDo(print())
				.andExpect(BAD_REQUEST)
				.andExpect(MATCHER_APPLICATION_JSON)
				.andExpect(jsonPath(EXPRESSION_TIMESTAMP).exists())
				.andExpect(jsonPath(EXPRESSION_ERROR_CODE).value("NEGATIVE_NUMBER"))
				.andExpect(jsonPath(EXPRESSION_ERROR_MESSAGE).value("Not possible to order by binary representation. Current number: -4 can't be negative."));

	}

	public static Stream<Arguments> provideListsOfNumbers() {

		return Stream.of(
				Arguments.of(List.of(7, 2, 1, 9, 5, 3, 8, 25, 42),
						List.of (1, 2, 8, 3, 5, 9, 7, 25, 42)),
				Arguments.of(List.of(1, 28, 4, 2, 6, 9, 55, 15, 27, 46, 11, 128, 16),
						List.of (1, 2, 4, 16, 128, 6, 9, 11, 28, 15, 27, 46, 55)));

	}

	private NumbersRQDTO buildNumbers(final List<Integer> inputNumbers) {
		var numbers = new NumbersRQDTO();
		numbers.setData(inputNumbers);
		numbers.setCriteria(NumbersOrderCriteriaType.BINARY);
		return numbers;
	}

	private String getObjectString(Object object) throws JsonProcessingException {
		var mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		var ow = mapper.writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(object);
	}


}
