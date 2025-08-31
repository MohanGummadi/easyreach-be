CREATE TABLE IF NOT EXISTS refresh_token (
    jti VARCHAR(100) PRIMARY KEY,
    user_id TEXT NOT NULL,
    issued_at TIMESTAMPTZ NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked_at TIMESTAMPTZ,
    rotated_from_jti VARCHAR(100),
    is_synced BOOLEAN,
    created_by VARCHAR(50),
    created_at TIMESTAMPTZ,
    updated_by VARCHAR(50),
    updated_at TIMESTAMPTZ
);
