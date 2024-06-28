package com.mdelamo.numbers.infrastructure.rest.controller;

import com.mdelamo.numbers.domain.usecase.NumbersOrdinationUseCase;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRSDTO;
import com.mdelamo.numbers.infrastructure.rest.mapper.NumbersRestMapper;
import com.mdelamo.numbers.infrastructure.rest.dto.NumbersRQDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("numbers-ordination")
public class NumbersOrdinationController {

    private final NumbersRestMapper restMapper;

    private final NumbersOrdinationUseCase useCase;


    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NumbersRSDTO> order(@RequestBody final NumbersRQDTO numbersRQDTO) {
        log.info("Request  POST /numbers-ordination -> criteria: {} - data: {}", numbersRQDTO.getCriteria(), numbersRQDTO.getData());
        var numbers = restMapper.toNumbers(numbersRQDTO);
        var numbersResult = useCase.order(numbers);
        var numbersRSDTO = restMapper.toNumbersRSDTO(numbersResult);
        log.info("Response POST /numbers-ordination -> criteria: {} - data: {}", numbersRSDTO.getCriteria(), numbersRSDTO.getData());
        return ResponseEntity.ok(numbersRSDTO);
    }
}
