package org.flowkit.product.catalog.domain.model.commands;

import lombok.Getter;
import org.flowkit.product.catalog.domain.model.aggregates.Catalog;

@Getter
public class CreateCatalogCommandResult {

    private final boolean isSuccess;
    private final Catalog catalog;
    private final String error;

    public CreateCatalogCommandResult(Catalog catalog) {
        this.isSuccess = true;
        this.catalog = catalog;
        this.error = null;
    }

    public CreateCatalogCommandResult(String error) {
        this.isSuccess = false;
        this.error = error;
        this.catalog = null;
    }

}
