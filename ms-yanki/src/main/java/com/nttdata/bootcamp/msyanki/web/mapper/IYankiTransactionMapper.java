package com.nttdata.bootcamp.msyanki.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nttdata.bootcamp.msyanki.domain.YankiTransaction;
import com.nttdata.bootcamp.msyanki.web.model.YankiTransactionModel;

@Mapper(componentModel = "spring")
public interface IYankiTransactionMapper {
    YankiTransaction modelToEntity(YankiTransactionModel model);

    YankiTransactionModel entityToModel(YankiTransaction event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget YankiTransaction entity, YankiTransaction updateEntity);
}
