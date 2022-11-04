package com.nttdata.bootcamp.msyanki.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nttdata.bootcamp.msyanki.domain.Yanki;
import com.nttdata.bootcamp.msyanki.web.model.YankiModel;

@Mapper(componentModel = "spring")
public interface IYankiMapper {
    Yanki modelToEntity(YankiModel model);

    YankiModel entityToModel(Yanki event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Yanki entity, Yanki updateEntity);
}
