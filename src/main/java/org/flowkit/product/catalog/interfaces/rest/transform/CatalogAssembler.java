package org.flowkit.product.catalog.interfaces.rest.transform;

import org.flowkit.product.catalog.domain.model.aggregates.Catalog;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommand;
import org.flowkit.product.catalog.infrastructure.utils.StringToUUIDMapper;
import org.flowkit.product.catalog.interfaces.rest.dto.CatalogCreate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = StringToUUIDMapper.class)
public interface CatalogAssembler {

    CatalogAssembler INSTANCE = Mappers.getMapper(CatalogAssembler.class);

    CreateCatalogCommand toCommandFromDTO(CatalogCreate catalogCreateDTO);

    @Mapping(source = "id.id", target = "id")
    @Mapping(target = "category", ignore = true)
    org.flowkit.product.catalog.interfaces.rest.dto.Catalog toDTOFromAggregate(Catalog catalog);

    List<org.flowkit.product.catalog.interfaces.rest.dto.Catalog> toDTOFromAggregate(List<Catalog> catalogs);

}