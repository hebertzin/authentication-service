DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_type
        WHERE typname = 'trust_level_enum'
    ) THEN
CREATE TYPE trust_level_enum AS ENUM ('LOW', 'NORMAL', 'HIGH');
END IF;
END $$;

ALTER TABLE devices
    ADD COLUMN IF NOT EXISTS trust_level trust_level_enum;

ALTER TABLE devices
    ALTER COLUMN trust_level SET DEFAULT 'NORMAL';
