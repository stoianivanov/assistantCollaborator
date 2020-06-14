package com.fmi.web.rest;

import com.fmi.domain.Discipline;
import com.fmi.domain.dto.LectorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
public class LectorResource {

    @PostMapping("/lectors")
    public ResponseEntity<List<LectorDto>> addLecotrs(@RequestBody List<LectorDto> lectorDtos) {


        return ResponseEntity.ok()
                .body(lectorDtos);

    }
}
