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

import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reserving.interfaces.enums.ExtractType;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Reference to a prepared reporting extract. This record is not a regulatory filing..
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportingExtractDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID reportingExtractId;

    @FilterableId
    @NotNull(message = "ReserveSnapshot id is required")
    private UUID reserveSnapshotId;

    @NotNull(message = "extractType is required")
    private ExtractType extractType;

    /** Logical reference: core-common-document-mgmt document. This service does not own that record. */
    @FilterableId
    private UUID documentId;

    @NotNull(message = "preparedAt is required")
    private LocalDateTime preparedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
