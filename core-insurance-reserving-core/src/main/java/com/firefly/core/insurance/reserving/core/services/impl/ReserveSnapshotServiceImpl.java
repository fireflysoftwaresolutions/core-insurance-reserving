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
import com.firefly.core.insurance.reserving.core.mappers.ReserveSnapshotMapper;
import com.firefly.core.insurance.reserving.core.services.ReserveSnapshotService;
import com.firefly.core.insurance.reserving.interfaces.dtos.ReserveSnapshotDTO;
import com.firefly.core.insurance.reserving.models.entities.ReserveSnapshot;
import com.firefly.core.insurance.reserving.models.repositories.ReserveSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ReserveSnapshotServiceImpl implements ReserveSnapshotService {

    @Autowired
    private ReserveSnapshotRepository repository;

    @Autowired
    private ReserveSnapshotMapper mapper;

    @Override
    public Mono<PaginationResponse<ReserveSnapshotDTO>> filterReserveSnapshots(FilterRequest<ReserveSnapshotDTO> filterRequest) {
        return FilterUtils.createFilter(ReserveSnapshot.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<ReserveSnapshotDTO> createReserveSnapshot(ReserveSnapshotDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ReserveSnapshotDTO> updateReserveSnapshot(UUID reserveSnapshotId, ReserveSnapshotDTO dto) {
        return repository.findById(reserveSnapshotId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReserveSnapshot", reserveSnapshotId.toString())))
                .flatMap(existing -> {
                    mapper.updateEntityFromDto(dto, existing);
                    existing.setReserveSnapshotId(reserveSnapshotId);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteReserveSnapshot(UUID reserveSnapshotId) {
        return repository.findById(reserveSnapshotId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReserveSnapshot", reserveSnapshotId.toString())))
                .flatMap(existing -> repository.deleteById(reserveSnapshotId));
    }

    @Override
    public Mono<ReserveSnapshotDTO> getReserveSnapshotById(UUID reserveSnapshotId) {
        return repository.findById(reserveSnapshotId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("ReserveSnapshot", reserveSnapshotId.toString())))
                .map(mapper::toDTO);
    }
}
