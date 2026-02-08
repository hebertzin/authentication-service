CREATE TABLE IF NOT EXISTS login_attempts (
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email     VARCHAR(255) NOT NULL,
    user_id   UUID NULL,
    device_id UUID NULL,
    result    VARCHAR(20) NOT NULL,
    ip        VARCHAR(45),
    created_at TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT fk_login_attempts_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE SET NULL,

    CONSTRAINT fk_login_attempts_device
    FOREIGN KEY (device_id) REFERENCES devices(id)
    ON DELETE SET NULL,

    CONSTRAINT chk_login_attempts_result
    CHECK (result IN ('SUCCESS','FAILURE','BLOCKED'))
    );

CREATE INDEX IF NOT EXISTS idx_login_attempts_email ON login_attempts(email);
CREATE INDEX IF NOT EXISTS idx_login_attempts_user_id ON login_attempts(user_id);
CREATE INDEX IF NOT EXISTS idx_login_attempts_device_id ON login_attempts(device_id);
CREATE INDEX IF NOT EXISTS idx_login_attempts_created_at ON login_attempts(created_at);