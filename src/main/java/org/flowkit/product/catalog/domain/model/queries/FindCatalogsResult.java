package org.flowkit.product.catalog.domain.model.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.flowkit.product.catalog.domain.model.aggregates.Catalog;

import java.util.List;

@Getter
@AllArgsConstructor()
public class FindCatalogsResult {

    private final List<Catalog> catalogs;
    private final Long totalCount;
    private final Integer resultCount;

}