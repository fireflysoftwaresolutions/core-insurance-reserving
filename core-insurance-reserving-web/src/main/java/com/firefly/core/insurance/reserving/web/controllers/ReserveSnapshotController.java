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


package com.firefly.core.insurance.reserving.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.insurance.reserving.core.services.ReserveSnapshotService;
import com.firefly.core.insurance.reserving.interfaces.dtos.ReserveSnapshotDTO;
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
 * REST API for point-in-time reserving snapshot supplied by the actuarial process.
 */
@RestController
@RequestMapping("/api/v1/snapshots")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reserve Snapshots", description = "Point-in-time reserving snapshot supplied by the actuarial process")
public class ReserveSnapshotController {

    private final ReserveSnapshotService reserveSnapshotService;

    @PostMapping("/filter")
    @Operation(summary = "Filter reserveSnapshots", description = "Retrieve a page of reserveSnapshots matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<ReserveSnapshotDTO>>> filterReserveSnapshots(
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<ReserveSnapshotDTO> filterRequest) {
        return reserveSnapshotService.filterReserveSnapshots(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create ReserveSnapshot", description = "Create a ReserveSnapshot")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "ReserveSnapshot created", content = @Content(schema = @Schema(implementation = ReserveSnapshotDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<ReserveSnapshotDTO>> createReserveSnapshot(
            @Parameter(description = "ReserveSnapshot to create", required = true)
            @Valid @RequestBody ReserveSnapshotDTO dto) {
        return reserveSnapshotService.createReserveSnapshot(dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{reserveSnapshotId}")
    @Operation(summary = "Get ReserveSnapshot by id", description = "Retrieve one ReserveSnapshot")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "ReserveSnapshot found", content = @Content(schema = @Schema(implementation = ReserveSnapshotDTO.class))),
        @ApiResponse(responseCode = "404", description = "ReserveSnapshot not found", content = @Content)
    })
    public Mono<ResponseEntity<ReserveSnapshotDTO>> getReserveSnapshotById(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId) {
        return reserveSnapshotService.getReserveSnapshotById(reserveSnapshotId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{reserveSnapshotId}")
    @Operation(summary = "Update ReserveSnapshot", description = "Update an existing ReserveSnapshot")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "ReserveSnapshot updated", content = @Content(schema = @Schema(implementation = ReserveSnapshotDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "ReserveSnapshot not found", content = @Content)
    })
    public Mono<ResponseEntity<ReserveSnapshotDTO>> updateReserveSnapshot(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Updated ReserveSnapshot", required = true)
            @Valid @RequestBody ReserveSnapshotDTO dto) {
        return reserveSnapshotService.updateReserveSnapshot(reserveSnapshotId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{reserveSnapshotId}")
    @Operation(summary = "Delete ReserveSnapshot", description = "Delete a ReserveSnapshot")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "ReserveSnapshot deleted"),
        @ApiResponse(responseCode = "404", description = "ReserveSnapshot not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteReserveSnapshot(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId) {
        return reserveSnapshotService.deleteReserveSnapshot(reserveSnapshotId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
