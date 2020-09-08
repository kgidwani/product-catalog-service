package org.flowkit.product.catalog.domain.model.entities;

import lombok.*;
import org.jddd.core.annotation.Entity;
import org.jddd.core.types.Identifier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {

    @EqualsAndHashCode.Include
    private Category.CategoryId id;

    @NotBlank
    private String name;
    private String description;

    @NotNull
    private Boolean isRoot;
    private String parentId;

    @Value(staticConstructor = "of")
    public static class CategoryId implements Identifier {
        UUID id;
    }

}
