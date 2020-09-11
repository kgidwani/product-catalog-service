package org.flowkit.product.catalog.domain.model.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class DeleteCatalogCommand {

    @NotBlank
    private final String id;

}
