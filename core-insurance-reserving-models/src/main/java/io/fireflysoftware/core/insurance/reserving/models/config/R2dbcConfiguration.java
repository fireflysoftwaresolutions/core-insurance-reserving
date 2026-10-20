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


package io.fireflysoftware.core.insurance.reserving.models.config;

import io.fireflysoftware.core.insurance.reserving.interfaces.enums.LineOfBusiness;
import io.fireflysoftware.core.insurance.reserving.interfaces.enums.SnapshotStatus;
import io.fireflysoftware.core.insurance.reserving.interfaces.enums.ExtractType;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers every PostgreSQL enum used by this service with the R2DBC codec.
 * Without this, the driver binds enum parameters as varchar and Postgres rejects the cast.
 */
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Value("${DB_HOST:localhost}")
    private String host;

    @Value("${DB_PORT:5432}")
    private int port;

    @Value("${DB_NAME:postgres}")
    private String database;

    @Value("${DB_USERNAME:postgres}")
    private String username;

    @Value("${DB_PASSWORD:postgres}")
    private String password;

    @Value("${DB_SSL_MODE:disable}")
    private String sslMode;

    @WritingConverter
    static class LineOfBusinessConverter implements Converter<LineOfBusiness, LineOfBusiness> {
        @Override
        public LineOfBusiness convert(LineOfBusiness source) {
            return source;
        }
    }

    @WritingConverter
    static class SnapshotStatusConverter implements Converter<SnapshotStatus, SnapshotStatus> {
        @Override
        public SnapshotStatus convert(SnapshotStatus source) {
            return source;
        }
    }

    @WritingConverter
    static class ExtractTypeConverter implements Converter<ExtractType, ExtractType> {
        @Override
        public ExtractType convert(ExtractType source) {
            return source;
        }
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new LineOfBusinessConverter());
        converters.add(new SnapshotStatusConverter());
        converters.add(new ExtractTypeConverter());
        return converters;
    }

    @Bean
    @Primary
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .username(username)
                        .password(password)
                        .database(database)
                        .sslMode(SSLMode.valueOf(sslMode.toUpperCase()))
                        .codecRegistrar(EnumCodec.builder()
                                .withEnum("line_of_business_enum", LineOfBusiness.class)
                                .withEnum("snapshot_status_enum", SnapshotStatus.class)
                                .withEnum("extract_type_enum", ExtractType.class)
                                .build())
                        .build()
        );
    }
}
