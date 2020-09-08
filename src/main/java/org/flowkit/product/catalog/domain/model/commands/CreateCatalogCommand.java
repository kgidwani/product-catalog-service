package org.flowkit.product.catalog.domain.model.commands;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCatalogCommand {

    @NotBlank
    private String name;
    private String description;

}