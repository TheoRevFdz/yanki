package com.nttdata.bootcamp.msyanki.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nttdata.bootcamp.msyanki.domain.Movement;
import com.nttdata.bootcamp.msyanki.web.model.MovementModel;

@Mapper(componentModel = "spring")
public interface IMovementMapper {
    Movement modelToEntity(MovementModel model);

    MovementModel entityToModel(Movement event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Movement enity, Movement updateEntity);
}
