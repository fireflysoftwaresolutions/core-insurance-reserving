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


package io.fireflysoftware.core.insurance.reserving.models.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * One origin and development cell on a loss-development triangle.
 * Maps to the 'triangle_cell' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("triangle_cell")
public class TriangleCell {

    @Id
    @Column("triangle_cell_id")
    private UUID triangleCellId;

    @Column("reserve_snapshot_id")
    private UUID reserveSnapshotId;

    @Column("origin_period")
    private String originPeriod;

    @Column("development_period")
    private Integer developmentPeriod;

    @Column("paid_amount")
    private BigDecimal paidAmount;

    @Column("incurred_amount")
    private BigDecimal incurredAmount;

    @Column("currency")
    private String currency;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
