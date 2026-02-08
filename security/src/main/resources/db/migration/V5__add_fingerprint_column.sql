ALTER TABLE devices
    ADD COLUMN IF NOT EXISTS fingerprint varchar(64);

ALTER TABLE devices
    ALTER COLUMN fingerprint SET NOT NULL;

CREATE INDEX IF NOT EXISTS idx_devices_user_fingerprint
    ON devices (user_id, fingerprint);
