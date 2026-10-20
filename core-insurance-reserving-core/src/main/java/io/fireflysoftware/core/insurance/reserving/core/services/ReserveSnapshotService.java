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
import io.fireflysoftware.core.insurance.reserving.interfaces.dtos.ReserveSnapshotDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for point-in-time reserving snapshot supplied by the actuarial process.
 */
public interface ReserveSnapshotService {

    /**
     * Filters reserveSnapshots with the given criteria.
     *
     * @param filterRequest filter criteria
     * @return a page of ReserveSnapshotDTO
     */
    Mono<PaginationResponse<ReserveSnapshotDTO>> filterReserveSnapshots(FilterRequest<ReserveSnapshotDTO> filterRequest);

    /**
     * Creates a ReserveSnapshot.
     *
     * @param dto values to store
     * @return the stored DTO
     */
    Mono<ReserveSnapshotDTO> createReserveSnapshot(ReserveSnapshotDTO dto);

    /**
     * Updates a ReserveSnapshot.
     *
     * @param id and dto
     * @return the updated DTO
     */
    Mono<ReserveSnapshotDTO> updateReserveSnapshot(UUID reserveSnapshotId, ReserveSnapshotDTO dto);

    /**
     * Deletes a ReserveSnapshot.
     *
     * @param id identifier
     * @return completion signal
     */
    Mono<Void> deleteReserveSnapshot(UUID reserveSnapshotId);

    /**
     * Loads a ReserveSnapshot by id.
     *
     * @param id identifier
     * @return the DTO
     */
    Mono<ReserveSnapshotDTO> getReserveSnapshotById(UUID reserveSnapshotId);
}
