-- Update all null value to empty string (this will make it easier to create tsvector col)
UPDATE payees
SET payee_email = ''
WHERE payee_email IS NULL;

UPDATE payees
SET payee_name = ''
WHERE payee_name IS NULL;

UPDATE payees
SET payee_phone = ''
WHERE payee_phone IS NULL;

-- Set default value for email, name, and phone. Constraint them to be not null
-- Add new column for full text search
ALTER TABLE IF EXISTS payees
    ADD COLUMN IF NOT EXISTS text_search_vector tsvector,

    ALTER COLUMN payee_email SET DEFAULT '',
    ALTER COLUMN payee_email SET NOT NULL,

    ALTER COLUMN payee_name SET DEFAULT '',
    ALTER COLUMN payee_name SET NOT NULL,

    ALTER COLUMN payee_phone SET DEFAULT '',
    ALTER COLUMN payee_phone SET NOT NULL;

-- Create tsvector for full text search
-- noinspection SqlWithoutWhere
UPDATE payees
SET text_search_vector = to_tsvector(
        REGEXP_REPLACE(payee_email, '[.@]', ' ', 'g') -- fully split email into tokens
            || ' '
            || payees.payee_name
            || ' '
            || payees.payee_phone);

CREATE INDEX IF NOT EXISTS idx_full_text_search ON payees USING gin (text_search_vector);

-- Trigger to update text search vector
CREATE OR REPLACE FUNCTION search_trigger() RETURNS TRIGGER AS
$$
BEGIN
    NEW.text_search_vector := to_tsvector(
            REGEXP_REPLACE(NEW.payee_email, '[.@]', ' ', 'g') -- fully split email into tokens
                || ' '
                || NEW.payee_name
                || ' '
                || NEW.payee_phone);
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER text_search_vector_update
    BEFORE INSERT OR UPDATE
    ON payees
    FOR EACH ROW
EXECUTE FUNCTION search_trigger();
