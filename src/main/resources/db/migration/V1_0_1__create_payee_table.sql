DROP TABLE IF EXISTS payees CASCADE;
CREATE TABLE payees
(
    payee_id    uuid NOT NULL UNIQUE PRIMARY KEY,
    payee_email VARCHAR(320),
    payee_name  VARCHAR(58),
    payee_phone VARCHAR(15),
    CONSTRAINT chk_payee_at_least_one_not_null
        CHECK (payee_name IS NOT NULL OR payee_email IS NOT NULL OR payee_phone IS NOT NULL)
);
