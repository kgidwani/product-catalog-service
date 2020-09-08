package org.flowkit.product.catalog.domain.model.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jddd.event.annotation.DomainEvent;

@DomainEvent
@Getter
@AllArgsConstructor
public class CatalogCreated {

    private final String id;

}
