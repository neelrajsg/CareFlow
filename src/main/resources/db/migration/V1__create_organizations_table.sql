CREATE TABLE organizations
(
    id BIGSERIAL PRIMARY KEY,

    organization_code VARCHAR(50) NOT NULL UNIQUE,

    name VARCHAR(150) NOT NULL,

    type VARCHAR(50) NOT NULL,

    phone VARCHAR(20),

    email VARCHAR(150),

    address_line_1 VARCHAR(255),

    address_line_2 VARCHAR(255),

    city VARCHAR(100),

    state VARCHAR(100),

    postal_code VARCHAR(20),

    country VARCHAR(100),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_organizations_name
    ON organizations(name);

CREATE INDEX idx_organizations_city
    ON organizations(city);

CREATE INDEX idx_organizations_active
    ON organizations(active);