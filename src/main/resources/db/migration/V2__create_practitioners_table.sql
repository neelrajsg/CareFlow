CREATE TABLE  practitioners
(
id BIGSERIAL PRIMARY KEY,
practitioner_code   VARCHAR(50) NOT NULL UNIQUE,
first_name          VARCHAR(100) NOT NULL,
last_name           VARCHAR(100) NOT NULL,
gender              VARCHAR(20),
date_of_birth       DATE,
specialization      VARCHAR(100),
qualification       VARCHAR(150),
license_number      VARCHAR(100) NOT NULL UNIQUE,
phone               VARCHAR(20),
email               VARCHAR(150),
organization_id     BIGINT NOT NULL,
active              BOOLEAN NOT NULL DEFAULT TRUE,
created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

constraint fk_practitioner_organization foreign key (organization_id) references organizations(id)

);
CREATE INDEX idx_practitioners_organization_id
    ON practitioners(organization_id);

CREATE INDEX idx_practitioners_specialization
    ON practitioners(specialization);

CREATE INDEX idx_practitioners_active
    ON practitioners(active);