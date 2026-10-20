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
import io.fireflysoftware.core.insurance.reserving.interfaces.dtos.ReportingExtractDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for reference to a prepared reporting extract. This record is not a regulatory filing..
 */
public interface ReportingExtractService {

    /**
     * Filters reportingExtracts with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of ReportingExtractDTO
     */
    Mono<PaginationResponse<ReportingExtractDTO>> filterReportingExtracts(UUID reserveSnapshotId, FilterRequest<ReportingExtractDTO> filterRequest);

    /**
     * Creates a ReportingExtract.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<ReportingExtractDTO> createReportingExtract(UUID reserveSnapshotId, ReportingExtractDTO dto);

    /**
     * Updates a ReportingExtract.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<ReportingExtractDTO> updateReportingExtract(UUID reserveSnapshotId, UUID reportingExtractId, ReportingExtractDTO dto);

    /**
     * Deletes a ReportingExtract.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteReportingExtract(UUID reserveSnapshotId, UUID reportingExtractId);

    /**
     * Loads a ReportingExtract by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<ReportingExtractDTO> getReportingExtractById(UUID reserveSnapshotId, UUID reportingExtractId);
}
