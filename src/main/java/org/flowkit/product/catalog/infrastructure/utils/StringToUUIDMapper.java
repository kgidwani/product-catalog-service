package org.flowkit.product.catalog.infrastructure.utils;

import java.util.UUID;

public class StringToUUIDMapper {

    public String asString(UUID data) {
        return data != null ? data.toString() : null;
    }

    public UUID asUUID(String data) {
        return data != null ? UUID.fromString(data) : null;
    }
}
