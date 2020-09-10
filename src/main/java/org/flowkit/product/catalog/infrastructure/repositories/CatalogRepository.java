package org.flowkit.product.catalog.infrastructure.repositories;

import org.flowkit.product.catalog.domain.model.aggregates.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CatalogRepository extends MongoRepository<Catalog, Catalog.CatalogId> {

    List<Catalog> findByNameIgnoreCase(String name);
}