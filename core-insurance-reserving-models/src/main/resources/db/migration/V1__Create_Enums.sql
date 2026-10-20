-- Flyway V1: enum types for core-insurance-reserving

CREATE TYPE line_of_business_enum AS ENUM (
    'MOTOR',
    'PROPERTY',
    'LIABILITY',
    'LIFE',
    'HEALTH',
    'MARINE',
    'AVIATION',
    'ACCIDENT',
    'OTHER'
);

CREATE TYPE snapshot_status_enum AS ENUM (
    'DRAFT',
    'SUBMITTED',
    'ACCEPTED'
);

CREATE TYPE extract_type_enum AS ENUM (
    'TRIANGLE',
    'IFRS17_DATA',
    'SOLVENCY_II_DATA'
);

