package com.nttdata.bootcamp.msyanki.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msyanki.services.YankiService;
import com.nttdata.bootcamp.msyanki.web.mapper.IYankiMapper;
import com.nttdata.bootcamp.msyanki.web.model.YankiModel;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/yanki")
public class YankiController {
    @Value("${spring.application.name}")
    String applicationName;

    @Value("${server.port}")
    String port;

    @Autowired
    private YankiService yankiService;

    @Autowired
    private IYankiMapper yankiMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<YankiModel>>> getAll() {
        log.info("GetAll executed");
        return Mono
                .just(ResponseEntity.ok()
                        .body(yankiService.findAll()
                                .map(yanki -> yankiMapper.entityToModel(yanki))));
    }

    @GetMapping("/{nroPhone}")
    public Mono<ResponseEntity<YankiModel>> findByNroPhone(@PathVariable String nroPhone) {
        log.info("Yanki findByNroPhone: {}", nroPhone);
        return yankiService.findByNroPhone(nroPhone)
                .map(yanki -> yankiMapper.entityToModel(yanki))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<YankiModel>> create(@Valid @RequestBody YankiModel request) {
        return yankiService.create(yankiMapper.modelToEntity(request))
                .map(yanki -> yankiMapper.entityToModel(yanki))
                .flatMap(yanki -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(yanki)));
    }
}
