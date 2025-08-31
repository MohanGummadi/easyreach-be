-- Add sync columns to existing tables

ALTER TABLE companies
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE internal_vehicles
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE vehicle_entries
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE payer_settlements
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE payers
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE expense_master
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE daily_expenses
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE diesel_usage
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE equipment_usage
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

ALTER TABLE vehicle_types
    ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS change_id BIGSERIAL;

-- Indexes to support synchronization queries
CREATE INDEX IF NOT EXISTS idx_companies_uuid_updated_at ON companies(uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_companies_uuid_deleted_deleted_at ON companies(uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_users_company_uuid_updated_at ON users(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_users_company_uuid_deleted_deleted_at ON users(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_internal_vehicles_company_uuid_updated_at ON internal_vehicles(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_internal_vehicles_company_uuid_deleted_deleted_at ON internal_vehicles(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_vehicle_entries_company_uuid_updated_at ON vehicle_entries(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_vehicle_entries_company_uuid_deleted_deleted_at ON vehicle_entries(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_payer_settlements_company_uuid_updated_at ON payer_settlements(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_payer_settlements_company_uuid_deleted_deleted_at ON payer_settlements(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_payers_company_uuid_updated_at ON payers(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_payers_company_uuid_deleted_deleted_at ON payers(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_expense_master_company_uuid_updated_at ON expense_master(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_expense_master_company_uuid_deleted_deleted_at ON expense_master(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_daily_expenses_company_uuid_updated_at ON daily_expenses(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_daily_expenses_company_uuid_deleted_deleted_at ON daily_expenses(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_diesel_usage_company_uuid_updated_at ON diesel_usage(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_diesel_usage_company_uuid_deleted_deleted_at ON diesel_usage(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_equipment_usage_company_uuid_updated_at ON equipment_usage(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_equipment_usage_company_uuid_deleted_deleted_at ON equipment_usage(company_uuid, deleted, deleted_at);

CREATE INDEX IF NOT EXISTS idx_vehicle_types_company_uuid_updated_at ON vehicle_types(company_uuid, updated_at);
CREATE INDEX IF NOT EXISTS idx_vehicle_types_company_uuid_deleted_deleted_at ON vehicle_types(company_uuid, deleted, deleted_at);
