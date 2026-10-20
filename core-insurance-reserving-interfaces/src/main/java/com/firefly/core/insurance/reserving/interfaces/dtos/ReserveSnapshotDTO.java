/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.insurance.reserving.interfaces.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reserving.interfaces.enums.LineOfBusiness;
import com.firefly.core.insurance.reserving.interfaces.enums.SnapshotStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Point-in-time reserving snapshot supplied by the actuarial process.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveSnapshotDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID reserveSnapshotId;

    /** Logical reference: Firefly deployment tenant. This service does not own that record. */
    @FilterableId
    @NotNull(message = "tenantId is required")
    private UUID tenantId;

    @NotNull(message = "snapshotDate is required")
    private LocalDate snapshotDate;

    @NotNull(message = "lineOfBusiness is required")
    private LineOfBusiness lineOfBusiness;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    @NotNull(message = "snapshotStatus is required")
    private SnapshotStatus snapshotStatus;

    @Size(max = 128, message = "sourceReference must not exceed 128 characters")
    private String sourceReference;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
