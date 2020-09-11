package org.flowkit.product.catalog.domain.model.commands;

import lombok.Data;
import lombok.ToString;
import org.flowkit.product.catalog.domain.model.entities.Category;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@ToString
public class UpdateCatalogCommand {

    @NotBlank
    private String id;

    private boolean isNameChanged;
    private String name;

    private boolean isDescriptionChanged;
    private String description;

    private boolean isCategoryChanged;
    private Set<Category> categories;

}
