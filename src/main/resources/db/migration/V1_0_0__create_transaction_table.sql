DROP TABLE IF EXISTS transactions CASCADE;
CREATE TABLE transactions
(
    transaction_id     uuid             NOT NULL UNIQUE PRIMARY KEY,
    transaction_amount DOUBLE PRECISION NOT NULL DEFAULT 0,
    created_at         TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);
