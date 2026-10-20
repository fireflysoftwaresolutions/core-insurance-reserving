-- Flyway V2: tables for core-insurance-reserving

-- Point-in-time reserving snapshot supplied by the actuarial process
CREATE TABLE reserve_snapshot (
    reserve_snapshot_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    snapshot_date DATE NOT NULL,
    line_of_business line_of_business_enum NOT NULL,
    currency VARCHAR(3) NOT NULL,
    snapshot_status snapshot_status_enum NOT NULL,
    source_reference VARCHAR(128) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- One origin and development cell on a loss-development triangle
CREATE TABLE triangle_cell (
    triangle_cell_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reserve_snapshot_id UUID NOT NULL REFERENCES reserve_snapshot (reserve_snapshot_id) ON DELETE CASCADE,
    origin_period VARCHAR(16) NOT NULL,
    development_period INTEGER NOT NULL,
    paid_amount DECIMAL(19, 4) NULL,
    incurred_amount DECIMAL(19, 4) NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Reference to a prepared reporting extract. This record is not a regulatory filing.
CREATE TABLE reporting_extract (
    reporting_extract_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reserve_snapshot_id UUID NOT NULL REFERENCES reserve_snapshot (reserve_snapshot_id) ON DELETE CASCADE,
    extract_type extract_type_enum NOT NULL,
    document_id UUID NULL,
    prepared_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reserve_snapshot_tenant_id ON reserve_snapshot (tenant_id);
CREATE INDEX idx_triangle_cell_reserve_snapshot_id ON triangle_cell (reserve_snapshot_id);
CREATE INDEX idx_reporting_extract_reserve_snapshot_id ON reporting_extract (reserve_snapshot_id);
CREATE INDEX idx_reporting_extract_document_id ON reporting_extract (document_id);
COMMENT ON COLUMN reserve_snapshot.tenant_id IS 'Logical reference: Firefly deployment tenant. No foreign key across services.';
COMMENT ON COLUMN reporting_extract.document_id IS 'Logical reference: core-common-document-mgmt document. No foreign key across services.';
