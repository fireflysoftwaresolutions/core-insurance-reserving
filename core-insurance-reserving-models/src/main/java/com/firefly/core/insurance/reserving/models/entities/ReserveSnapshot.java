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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.reserving.interfaces.enums.LineOfBusiness;
import com.firefly.core.insurance.reserving.interfaces.enums.SnapshotStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Point-in-time reserving snapshot supplied by the actuarial process.
 * Maps to the 'reserve_snapshot' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("reserve_snapshot")
public class ReserveSnapshot {

    @Id
    @Column("reserve_snapshot_id")
    private UUID reserveSnapshotId;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("snapshot_date")
    private LocalDate snapshotDate;

    @Column("line_of_business")
    private LineOfBusiness lineOfBusiness;

    @Column("currency")
    private String currency;

    @Column("snapshot_status")
    private SnapshotStatus snapshotStatus;

    @Column("source_reference")
    private String sourceReference;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
