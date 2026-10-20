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


package io.fireflysoftware.core.insurance.reserving.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.reserving.core.services.ReportingExtractService;
import io.fireflysoftware.core.insurance.reserving.interfaces.dtos.ReportingExtractDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST API for reference to a prepared reporting extract. This record is not a regulatory filing..
 */
@RestController
@RequestMapping("/api/v1/snapshots/{reserveSnapshotId}/extracts")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reporting Extracts", description = "Reference to a prepared reporting extract. This record is not a regulatory filing.")
public class ReportingExtractController {

    private final ReportingExtractService reportingExtractService;

    @PostMapping("/filter")
    @Operation(summary = "Filter reportingExtracts", description = "Retrieve a page of reportingExtracts matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<ReportingExtractDTO>>> filterReportingExtracts(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<ReportingExtractDTO> filterRequest) {
        return reportingExtractService.filterReportingExtracts(reserveSnapshotId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create ReportingExtract", description = "Create a ReportingExtract")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "ReportingExtract created", content = @Content(schema = @Schema(implementation = ReportingExtractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<ReportingExtractDTO>> createReportingExtract(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "ReportingExtract to create", required = true)
            @Valid @RequestBody ReportingExtractDTO dto) {
        return reportingExtractService.createReportingExtract(reserveSnapshotId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{reportingExtractId}")
    @Operation(summary = "Get ReportingExtract by id", description = "Retrieve one ReportingExtract")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "ReportingExtract found", content = @Content(schema = @Schema(implementation = ReportingExtractDTO.class))),
        @ApiResponse(responseCode = "404", description = "ReportingExtract not found", content = @Content)
    })
    public Mono<ResponseEntity<ReportingExtractDTO>> getReportingExtractById(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Unique identifier of the ReportingExtract", required = true)
            @PathVariable UUID reportingExtractId) {
        return reportingExtractService.getReportingExtractById(reserveSnapshotId, reportingExtractId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{reportingExtractId}")
    @Operation(summary = "Update ReportingExtract", description = "Update an existing ReportingExtract")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "ReportingExtract updated", content = @Content(schema = @Schema(implementation = ReportingExtractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "ReportingExtract not found", content = @Content)
    })
    public Mono<ResponseEntity<ReportingExtractDTO>> updateReportingExtract(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Unique identifier of the ReportingExtract", required = true)
            @PathVariable UUID reportingExtractId,
            @Parameter(description = "Updated ReportingExtract", required = true)
            @Valid @RequestBody ReportingExtractDTO dto) {
        return reportingExtractService.updateReportingExtract(reserveSnapshotId, reportingExtractId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{reportingExtractId}")
    @Operation(summary = "Delete ReportingExtract", description = "Delete a ReportingExtract")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "ReportingExtract deleted"),
        @ApiResponse(responseCode = "404", description = "ReportingExtract not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteReportingExtract(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Unique identifier of the ReportingExtract", required = true)
            @PathVariable UUID reportingExtractId) {
        return reportingExtractService.deleteReportingExtract(reserveSnapshotId, reportingExtractId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
