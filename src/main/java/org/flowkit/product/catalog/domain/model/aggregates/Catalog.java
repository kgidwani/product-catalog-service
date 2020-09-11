package org.flowkit.product.catalog.domain.model.aggregates;

import lombok.*;
import org.apache.commons.text.WordUtils;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommand;
import org.flowkit.product.catalog.domain.model.entities.Category;
import org.flowkit.product.catalog.domain.model.events.CatalogCreated;
import org.jddd.core.annotation.AggregateRoot;
import org.jddd.core.types.Identifier;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@AggregateRoot
@Getter
@Setter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Catalog extends AbstractAggregateRoot<Catalog> {

    @NotNull
    @EqualsAndHashCode.Include
    private CatalogId id;

    @NotBlank
    private String name;
    private String description;

    @Valid
    private Set<Category> categories;

    public Catalog(@NotNull CreateCatalogCommand createCatalogCommand) {
        this.id = CatalogId.create();
        this.name = getTitleCase(createCatalogCommand.getName());
        this.description = createCatalogCommand.getDescription();
        addDomainEvent(new CatalogCreated(id.toString()));
    }

    private String getTitleCase(String input) {
        return WordUtils.capitalize(input);
    }

    private void addDomainEvent(Object event) {
        registerEvent(event);
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    public static class CatalogId implements Identifier {

        UUID id;

        public static CatalogId create() {
            return CatalogId.of(UUID.randomUUID());
        }
    }

}