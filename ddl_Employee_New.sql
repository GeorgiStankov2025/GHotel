CREATE TABLE employee
(
    id          UUID                        NOT NULL,
    version     BIGINT,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted     BOOLEAN                     NOT NULL,
    first_name  VARCHAR(30)                 NOT NULL,
    last_name   VARCHAR(30)                 NOT NULL,
    username    VARCHAR(25)                 NOT NULL,
    password    VARCHAR(255)                NOT NULL,
    role        VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD CONSTRAINT uc_employee_username UNIQUE (username);