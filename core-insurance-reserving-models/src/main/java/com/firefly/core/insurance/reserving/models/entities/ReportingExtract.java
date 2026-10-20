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


package com.firefly.core.insurance.reserving.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reserving.interfaces.enums.ExtractType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Reference to a prepared reporting extract. This record is not a regulatory filing..
 * Maps to the 'reporting_extract' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("reporting_extract")
public class ReportingExtract {

    @Id
    @Column("reporting_extract_id")
    private UUID reportingExtractId;

    @Column("reserve_snapshot_id")
    private UUID reserveSnapshotId;

    @Column("extract_type")
    private ExtractType extractType;

    @Column("document_id")
    private UUID documentId;

    @Column("prepared_at")
    private LocalDateTime preparedAt;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
