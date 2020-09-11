package org.flowkit.product.catalog.application.internal.commandservices;

import org.flowkit.product.catalog.domain.model.aggregates.Catalog;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommand;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommandResult;
import org.flowkit.product.catalog.domain.model.commands.UpdateCatalogCommand;
import org.flowkit.product.catalog.infrastructure.repositories.CatalogRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.flowkit.product.catalog.domain.model.aggregates.Catalog.CatalogId;

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

    public Optional<Catalog> updateCatalog(@Valid UpdateCatalogCommand updateCatalogCommand) {

        CatalogId catalogId = getCatalogId(updateCatalogCommand.getId());

        Optional<Catalog> result = catalogRepository.findById(catalogId);
        if (result.isEmpty()) {
            return result;
        }

        Catalog catalog = result.get();
        if (updateCatalogCommand.isNameChanged()) {
            catalog.setName(updateCatalogCommand.getName());
        }
        if (updateCatalogCommand.isDescriptionChanged()) {
            catalog.setDescription(updateCatalogCommand.getDescription());
        }
        return Optional.of(catalogRepository.save(catalog));
    }

    public void deleteCatalog(@NotNull CatalogId id) {
        catalogRepository.deleteById(id);
    }

    public void deleteCatalog(@NotBlank String id) {
        deleteCatalog(getCatalogId(id));
    }

    private CatalogId getCatalogId(String id) {
        UUID uuid = UUID.fromString(id);
        return CatalogId.of(uuid);
    }

    private boolean validateCatalogNameUniqueness(String name) {
        List<Catalog> existingCatalogs = catalogRepository.findByNameIgnoreCase(name);
        return existingCatalogs.isEmpty();
    }
}
