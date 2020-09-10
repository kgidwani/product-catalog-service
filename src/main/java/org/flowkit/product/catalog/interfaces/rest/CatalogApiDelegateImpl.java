package org.flowkit.product.catalog.interfaces.rest;

import org.flowkit.product.catalog.application.internal.commandservices.CatalogCommandService;
import org.flowkit.product.catalog.application.internal.queryservices.CatalogQueryService;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommand;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommandResult;
import org.flowkit.product.catalog.interfaces.rest.dto.Catalog;
import org.flowkit.product.catalog.interfaces.rest.dto.CatalogCreate;
import org.flowkit.product.catalog.interfaces.rest.transform.CatalogAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Service
public class CatalogApiDelegateImpl implements CatalogApiDelegate {

    private final CatalogCommandService catalogCommandService;
    private final CatalogQueryService catalogQueryService;

    public CatalogApiDelegateImpl(CatalogCommandService catalogCommandService, CatalogQueryService catalogQueryService) {
        this.catalogCommandService = catalogCommandService;
        this.catalogQueryService = catalogQueryService;
    }

    @Override
    public ResponseEntity<Catalog> createCatalog(CatalogCreate catalogCreateDTO) {

        CreateCatalogCommand command = getCreateCatalogCommand(catalogCreateDTO);
        CreateCatalogCommandResult result = catalogCommandService.createCatalog(command);

        if (!result.isSuccess()) {
            throw Problem.valueOf(Status.CONFLICT, result.getError());
        }
        Catalog catalogDTO = getCatalogDTO(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogDTO);
    }


    private Catalog getCatalogDTO(CreateCatalogCommandResult createCatalogCommandResult) {
        return CatalogAssembler.INSTANCE.toDTOFromAggregate(createCatalogCommandResult.getCatalog());
    }

    private CreateCatalogCommand getCreateCatalogCommand(CatalogCreate catalogCreateDTO) {
        return CatalogAssembler.INSTANCE.toCommandFromDTO(catalogCreateDTO);
    }
}