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


package com.firefly.core.insurance.reserving.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import com.firefly.core.insurance.reserving.core.mappers.ReportingExtractMapper;
import com.firefly.core.insurance.reserving.core.services.ReportingExtractService;
import com.firefly.core.insurance.reserving.interfaces.dtos.ReportingExtractDTO;
import com.firefly.core.insurance.reserving.models.entities.ReportingExtract;
import com.firefly.core.insurance.reserving.models.repositories.ReportingExtractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ReportingExtractServiceImpl implements ReportingExtractService {

    @Autowired
    private ReportingExtractRepository repository;

    @Autowired
    private ReportingExtractMapper mapper;

    @Override
    public Mono<PaginationResponse<ReportingExtractDTO>> filterReportingExtracts(UUID reserveSnapshotId, FilterRequest<ReportingExtractDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new ReportingExtractDTO());
        }
        filterRequest.getFilters().setReserveSnapshotId(reserveSnapshotId);
        return FilterUtils.createFilter(ReportingExtract.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<ReportingExtractDTO> createReportingExtract(UUID reserveSnapshotId, ReportingExtractDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setReserveSnapshotId(reserveSnapshotId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ReportingExtractDTO> updateReportingExtract(UUID reserveSnapshotId, UUID reportingExtractId, ReportingExtractDTO dto) {
        return repository.findById(reportingExtractId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReportingExtract", reportingExtractId.toString())))
                .flatMap(existing -> {
                    if (!reserveSnapshotId.equals(existing.getReserveSnapshotId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "ReportingExtract with id '" + reportingExtractId + "' does not belong to reservesnapshot '" + reserveSnapshotId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteReportingExtract(UUID reserveSnapshotId, UUID reportingExtractId) {
        return repository.findById(reportingExtractId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReportingExtract", reportingExtractId.toString())))
                .flatMap(existing -> {
                    if (!reserveSnapshotId.equals(existing.getReserveSnapshotId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "ReportingExtract with id '" + reportingExtractId + "' does not belong to reservesnapshot '" + reserveSnapshotId + "'"));
                    }
                    return repository.deleteById(reportingExtractId);
                });
    }

    @Override
    public Mono<ReportingExtractDTO> getReportingExtractById(UUID reserveSnapshotId, UUID reportingExtractId) {
        return repository.findById(reportingExtractId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReportingExtract", reportingExtractId.toString())))
                .flatMap(existing -> {
                    if (!reserveSnapshotId.equals(existing.getReserveSnapshotId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "ReportingExtract with id '" + reportingExtractId + "' does not belong to reservesnapshot '" + reserveSnapshotId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}
