-- Flyway V3: implicit varchar casts for enum parameters

CREATE CAST (varchar AS line_of_business_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS snapshot_status_enum) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS extract_type_enum) WITH INOUT AS IMPLICIT;
