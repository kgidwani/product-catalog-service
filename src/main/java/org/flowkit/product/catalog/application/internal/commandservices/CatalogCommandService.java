package org.flowkit.product.catalog.application.internal.commandservices;

import org.flowkit.product.catalog.domain.model.aggregates.Catalog;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommand;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommandResult;
import org.flowkit.product.catalog.infrastructure.repositories.CatalogRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class CatalogCommandService {

    private final CatalogRepository catalogRepository;

    public CatalogCommandService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public CreateCatalogCommandResult createCatalog(@Valid CreateCatalogCommand createCatalogCommand) {
        boolean isNameUnique = validateCatalogNameUniqueness(createCatalogCommand.getName());
        if (!isNameUnique) {
            return new CreateCatalogCommandResult("Catalog with name " + createCatalogCommand.getName() + " already exist");
        }
        return new CreateCatalogCommandResult(catalogRepository.save(new Catalog(createCatalogCommand)));

    }

    private boolean validateCatalogNameUniqueness(String name) {
        List<Catalog> existingCatalogs = catalogRepository.findByNameIgnoreCase(name);
        return existingCatalogs.isEmpty();
    }
}
