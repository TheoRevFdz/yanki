package com.nttdata.bootcamp.msyanki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.bootcamp.msyanki.domain.Yanki;
import com.nttdata.bootcamp.msyanki.repositories.IYankiRepository;
import com.nttdata.bootcamp.msyanki.web.mapper.IYankiMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class YankiService {
    @Autowired
    private IYankiRepository yankiRepository;

    @Autowired
    private IYankiMapper yankiMapper;

    public Flux<Yanki> findAll() {
        log.debug("Yanki findAll executed");
        return yankiRepository.findAll();
    }

    public Mono<Yanki> findByNroPhone(String nroPhone) {
        log.debug("Yanki findByNroPhone executed");
        return yankiRepository.findByNroPhone(nroPhone);
    }

    public Mono<Yanki> create(Yanki yanki) {
        log.debug("Yanki create executed");
        return yankiRepository.findByNroPhone(yanki.getNroPhone())
                .flatMap(y -> {
                    if (y == null) {
                        return yankiRepository.save(yanki);
                    }
                    throw new RuntimeException(String.format(
                            "Ya existe una cuenta Yanki registrada con el número de celular: %s", yanki.getNroPhone()));
                });
    }

    public Mono<Yanki> update(String id, Yanki yanki) {
        log.debug("Yanki update {}: {}", id, yanki);
        return yankiRepository.findById(id)
                .flatMap(y -> {
                    yankiMapper.update(y, yanki);
                    return yankiRepository.save(y);
                });
    }
}
