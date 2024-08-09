DROP TABLE IF EXISTS transaction_payee CASCADE;
CREATE TABLE transaction_payee
(
    payee_id       uuid             NOT NULL REFERENCES payees (payee_id) ON DELETE CASCADE,
    transaction_id uuid             NOT NULL REFERENCES transactions (transaction_id) ON DELETE CASCADE,
    amount         DOUBLE PRECISION NOT NULL
);
