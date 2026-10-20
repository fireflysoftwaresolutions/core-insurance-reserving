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


package io.fireflysoftware.core.insurance.reserving.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import io.fireflysoftware.core.insurance.reserving.core.mappers.TriangleCellMapper;
import io.fireflysoftware.core.insurance.reserving.core.services.TriangleCellService;
import io.fireflysoftware.core.insurance.reserving.interfaces.dtos.TriangleCellDTO;
import io.fireflysoftware.core.insurance.reserving.models.entities.TriangleCell;
import io.fireflysoftware.core.insurance.reserving.models.repositories.TriangleCellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class TriangleCellServiceImpl implements TriangleCellService {

    @Autowired
    private TriangleCellRepository repository;

    @Autowired
    private TriangleCellMapper mapper;

    @Override
    public Mono<PaginationResponse<TriangleCellDTO>> filterTriangleCells(UUID reserveSnapshotId, FilterRequest<TriangleCellDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new TriangleCellDTO());
        }
        filterRequest.getFilters().setReserveSnapshotId(reserveSnapshotId);
        return FilterUtils.createFilter(TriangleCell.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<TriangleCellDTO> createTriangleCell(UUID reserveSnapshotId, TriangleCellDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setReserveSnapshotId(reserveSnapshotId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TriangleCellDTO> updateTriangleCell(UUID reserveSnapshotId, UUID triangleCellId, TriangleCellDTO dto) {
        return repository.findById(triangleCellId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("TriangleCell", triangleCellId.toString())))
                .flatMap(existing -> {
                    if (!reserveSnapshotId.equals(existing.getReserveSnapshotId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "TriangleCell with id '" + triangleCellId + "' does not belong to reservesnapshot '" + reserveSnapshotId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteTriangleCell(UUID reserveSnapshotId, UUID triangleCellId) {
        return repository.findById(triangleCellId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("TriangleCell", triangleCellId.toString())))
                .flatMap(existing -> {
                    if (!reserveSnapshotId.equals(existing.getReserveSnapshotId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "TriangleCell with id '" + triangleCellId + "' does not belong to reservesnapshot '" + reserveSnapshotId + "'"));
                    }
                    return repository.deleteById(triangleCellId);
                });
    }

    @Override
    public Mono<TriangleCellDTO> getTriangleCellById(UUID reserveSnapshotId, UUID triangleCellId) {
        return repository.findById(triangleCellId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("TriangleCell", triangleCellId.toString())))
                .flatMap(existing -> {
                    if (!reserveSnapshotId.equals(existing.getReserveSnapshotId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "TriangleCell with id '" + triangleCellId + "' does not belong to reservesnapshot '" + reserveSnapshotId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
