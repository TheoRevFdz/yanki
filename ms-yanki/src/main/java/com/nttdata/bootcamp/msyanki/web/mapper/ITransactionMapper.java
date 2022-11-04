package com.nttdata.bootcamp.msyanki.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nttdata.bootcamp.msyanki.domain.Transaction;
import com.nttdata.bootcamp.msyanki.web.model.TransactionModel;

@Mapper(componentModel = "spring")
public interface ITransactionMapper {
    Transaction modelToEntity(TransactionModel model);

    TransactionModel entityToModel(Transaction event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Transaction entity, Transaction updateEntity);
}
