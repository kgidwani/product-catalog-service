package org.flowkit.product.catalog.application.internal.queryservices;

import org.flowkit.product.catalog.domain.model.aggregates.Catalog;
import org.flowkit.product.catalog.domain.model.queries.FindCatalogsResult;
import org.flowkit.product.catalog.infrastructure.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.flowkit.product.catalog.domain.model.aggregates.Catalog.CatalogId;

@Service
public class CatalogQueryService {

    private final CatalogRepository catalogRepository;
    @Value("${default.pagination.offset.value}")
    private int defaultOffset;
    @Value("${default.pagination.limit.value}")
    private int defaultLimit;

    public CatalogQueryService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Optional<Catalog> findCatalog(@NotNull CatalogId catalogId) {
        return catalogRepository.findById(catalogId);
    }

    public Optional<Catalog> findCatalog(@NotBlank String id) {
        CatalogId catalogId = CatalogId.of(UUID.fromString(id));
        return findCatalog(catalogId);
    }

    public FindCatalogsResult findCatalogs(Integer offset, Integer limit) {

        Pageable pageRequest = createPageRequest(offset, limit);
        Page<Catalog> page = catalogRepository.findAll(pageRequest);

        List<Catalog> catalogs = page.get().collect(Collectors.toList());

        return new FindCatalogsResult(catalogs, page.getTotalElements(), catalogs.size());
    }

    private Pageable createPageRequest(Integer offset, Integer limit) {
        return PageRequest.of(null != offset ? offset : defaultOffset,
                null != limit ? limit : defaultLimit);
    }

}