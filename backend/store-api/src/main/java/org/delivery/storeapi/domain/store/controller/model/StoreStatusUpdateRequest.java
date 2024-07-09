package org.delivery.storeapi.domain.store.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.store.enums.StoreStatus;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreStatusUpdateRequest {

    @NotNull
    private Long storeId;

    @NotNull
    private StoreStatus status;
}
