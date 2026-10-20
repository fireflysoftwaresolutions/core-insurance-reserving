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


package io.fireflysoftware.core.insurance.reserving.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.reserving.interfaces.dtos.TriangleCellDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for one origin and development cell on a loss-development triangle.
 */
public interface TriangleCellService {

    /**
     * Filters triangleCells with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of TriangleCellDTO
     */
    Mono<PaginationResponse<TriangleCellDTO>> filterTriangleCells(UUID reserveSnapshotId, FilterRequest<TriangleCellDTO> filterRequest);

    /**
     * Creates a TriangleCell.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<TriangleCellDTO> createTriangleCell(UUID reserveSnapshotId, TriangleCellDTO dto);

    /**
     * Updates a TriangleCell.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<TriangleCellDTO> updateTriangleCell(UUID reserveSnapshotId, UUID triangleCellId, TriangleCellDTO dto);

    /**
     * Deletes a TriangleCell.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteTriangleCell(UUID reserveSnapshotId, UUID triangleCellId);

    /**
     * Loads a TriangleCell by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<TriangleCellDTO> getTriangleCellById(UUID reserveSnapshotId, UUID triangleCellId);
}
