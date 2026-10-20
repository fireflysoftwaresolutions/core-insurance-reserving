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
import com.firefly.core.insurance.reserving.core.services.TriangleCellService;
import com.firefly.core.insurance.reserving.interfaces.dtos.TriangleCellDTO;
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
 * REST API for one origin and development cell on a loss-development triangle.
 */
@RestController
@RequestMapping("/api/v1/snapshots/{reserveSnapshotId}/cells")
@RequiredArgsConstructor
@Validated
@Tag(name = "Triangle Cells", description = "One origin and development cell on a loss-development triangle")
public class TriangleCellController {

    private final TriangleCellService triangleCellService;

    @PostMapping("/filter")
    @Operation(summary = "Filter triangleCells", description = "Retrieve a page of triangleCells matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<TriangleCellDTO>>> filterTriangleCells(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<TriangleCellDTO> filterRequest) {
        return triangleCellService.filterTriangleCells(reserveSnapshotId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create TriangleCell", description = "Create a TriangleCell")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "TriangleCell created", content = @Content(schema = @Schema(implementation = TriangleCellDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<TriangleCellDTO>> createTriangleCell(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "TriangleCell to create", required = true)
            @Valid @RequestBody TriangleCellDTO dto) {
        return triangleCellService.createTriangleCell(reserveSnapshotId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{triangleCellId}")
    @Operation(summary = "Get TriangleCell by id", description = "Retrieve one TriangleCell")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "TriangleCell found", content = @Content(schema = @Schema(implementation = TriangleCellDTO.class))),
        @ApiResponse(responseCode = "404", description = "TriangleCell not found", content = @Content)
    })
    public Mono<ResponseEntity<TriangleCellDTO>> getTriangleCellById(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Unique identifier of the TriangleCell", required = true)
            @PathVariable UUID triangleCellId) {
        return triangleCellService.getTriangleCellById(reserveSnapshotId, triangleCellId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{triangleCellId}")
    @Operation(summary = "Update TriangleCell", description = "Update an existing TriangleCell")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "TriangleCell updated", content = @Content(schema = @Schema(implementation = TriangleCellDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "TriangleCell not found", content = @Content)
    })
    public Mono<ResponseEntity<TriangleCellDTO>> updateTriangleCell(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Unique identifier of the TriangleCell", required = true)
            @PathVariable UUID triangleCellId,
            @Parameter(description = "Updated TriangleCell", required = true)
            @Valid @RequestBody TriangleCellDTO dto) {
        return triangleCellService.updateTriangleCell(reserveSnapshotId, triangleCellId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{triangleCellId}")
    @Operation(summary = "Delete TriangleCell", description = "Delete a TriangleCell")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "TriangleCell deleted"),
        @ApiResponse(responseCode = "404", description = "TriangleCell not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteTriangleCell(
            @Parameter(description = "Unique identifier of the ReserveSnapshot", required = true)
            @PathVariable UUID reserveSnapshotId,
            @Parameter(description = "Unique identifier of the TriangleCell", required = true)
            @PathVariable UUID triangleCellId) {
        return triangleCellService.deleteTriangleCell(reserveSnapshotId, triangleCellId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
