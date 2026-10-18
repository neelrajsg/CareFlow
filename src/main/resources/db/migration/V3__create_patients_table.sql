CREATE TABLE patients
(
    id BIGSERIAL PRIMARY KEY,

    patient_code VARCHAR(50) NOT NULL UNIQUE,

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,

    gender VARCHAR(20),
    date_of_birth DATE,
    blood_group VARCHAR(10),

    phone VARCHAR(20),
    email VARCHAR(150),

    address_line_1 VARCHAR(255),
    address_line_2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),

    emergency_contact_name VARCHAR(150),
    emergency_contact_phone VARCHAR(20),

    organization_id BIGINT NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patient_organization
        FOREIGN KEY (organization_id)
            REFERENCES organizations(id)
);

CREATE INDEX idx_patients_organization_id
    ON patients(organization_id);

CREATE INDEX idx_patients_name
    ON patients(first_name, last_name);

CREATE INDEX idx_patients_phone
    ON patients(phone);

CREATE INDEX idx_patients_active
    ON patients(active);