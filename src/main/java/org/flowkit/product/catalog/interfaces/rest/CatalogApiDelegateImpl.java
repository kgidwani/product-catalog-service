package org.flowkit.product.catalog.interfaces.rest;

import org.flowkit.product.catalog.application.internal.commandservices.CatalogCommandService;
import org.flowkit.product.catalog.application.internal.queryservices.CatalogQueryService;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommand;
import org.flowkit.product.catalog.domain.model.commands.CreateCatalogCommandResult;
import org.flowkit.product.catalog.domain.model.commands.UpdateCatalogCommand;
import org.flowkit.product.catalog.domain.model.queries.FindCatalogsResult;
import org.flowkit.product.catalog.interfaces.rest.dto.Catalog;
import org.flowkit.product.catalog.interfaces.rest.dto.CatalogCreate;
import org.flowkit.product.catalog.interfaces.rest.dto.CatalogUpdate;
import org.flowkit.product.catalog.interfaces.rest.transform.CatalogAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;

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
        Catalog catalogDTO = getCatalogDTO(result.getCatalog());
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogDTO);
    }

    @Override
    public ResponseEntity<Catalog> findCatalog(String id) {
        var result = catalogQueryService.findCatalog(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .body(getCatalogDTO(result.get()));
    }

    @Override
    public ResponseEntity<List<Catalog>> findCatalogs(Integer offset, Integer limit) {
        var result = catalogQueryService.findCatalogs(offset, limit);

        List<Catalog> catalogs = getCatalogDTOList(result.getCatalogs());
        HttpHeaders responseHeaders = getHttpHeaders(result);

        return ResponseEntity.ok().headers(responseHeaders).body(catalogs);
    }

    @Override
    public ResponseEntity<Catalog> patchCatalog(String id, CatalogUpdate catalogDTO) {
        UpdateCatalogCommand updateCatalogCommand = getUpdateCatalogCommand(id, catalogDTO);

        var result = catalogCommandService.updateCatalog(updateCatalogCommand);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(getCatalogDTO(result.get()));
    }

    @Override
    public ResponseEntity<Void> deleteCatalog(String id) {
        catalogCommandService.deleteCatalog(id);
        return ResponseEntity.ok().build();
    }

    private UpdateCatalogCommand getUpdateCatalogCommand(String id, CatalogUpdate catalog) {
        UpdateCatalogCommand updateCatalogCommand = new UpdateCatalogCommand();
        updateCatalogCommand.setId(id);
        if (catalog.getName().isPresent()) {
            updateCatalogCommand.setNameChanged(true);
            updateCatalogCommand.setName(catalog.getName().get());
        }

        if (catalog.getDescription().isPresent()) {
            updateCatalogCommand.setDescriptionChanged(true);
            updateCatalogCommand.setDescription(catalog.getDescription().get());
        }
        return updateCatalogCommand;
    }

    private HttpHeaders getHttpHeaders(FindCatalogsResult result) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Total-Count", result.getTotalCount().toString());
        responseHeaders.set("X-Result-Count", result.getResultCount().toString());
        return responseHeaders;
    }

    private Catalog getCatalogDTO(org.flowkit.product.catalog.domain.model.aggregates.Catalog catalog) {
        return CatalogAssembler.INSTANCE.toDTOFromAggregate(catalog);
    }

    private List<Catalog> getCatalogDTOList(List<org.flowkit.product.catalog.domain.model.aggregates.Catalog> catalogs) {
        return CatalogAssembler.INSTANCE.toDTOFromAggregate(catalogs);
    }

    private CreateCatalogCommand getCreateCatalogCommand(CatalogCreate catalogCreateDTO) {
        return CatalogAssembler.INSTANCE.toCommandFromDTO(catalogCreateDTO);
    }
}