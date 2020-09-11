package org.flowkit.product.catalog.domain.model.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class DeleteCatalogCommandResult {


    private final String error;
    private boolean isSuccess;

    public DeleteCatalogCommandResult() {
        this.isSuccess = true;
        this.error = null;
    }

    public DeleteCatalogCommandResult(String error) {
        this.isSuccess = false;
        this.error = error;
    }
}
