-- Rename companyId to company_uuid and timestamps to snake_case

-- companies table
ALTER TABLE companies RENAME COLUMN companyId TO company_id;
ALTER TABLE companies RENAME COLUMN createdAt TO created_at;
ALTER TABLE companies RENAME COLUMN updatedAt TO updated_at;

-- users table
ALTER TABLE users RENAME COLUMN companyId TO company_uuid;
ALTER TABLE users RENAME COLUMN createdAt TO created_at;
ALTER TABLE users RENAME COLUMN updatedAt TO updated_at;

-- internal_vehicles table
ALTER TABLE internal_vehicles RENAME COLUMN companyId TO company_uuid;
ALTER TABLE internal_vehicles RENAME COLUMN createdAt TO created_at;
ALTER TABLE internal_vehicles RENAME COLUMN updatedAt TO updated_at;

-- vehicle_entries table
ALTER TABLE vehicle_entries RENAME COLUMN companyId TO company_uuid;
ALTER TABLE vehicle_entries RENAME COLUMN createdAt TO created_at;
ALTER TABLE vehicle_entries RENAME COLUMN updatedAt TO updated_at;

-- payer_settlements table
ALTER TABLE payer_settlements RENAME COLUMN companyId TO company_uuid;
ALTER TABLE payer_settlements RENAME COLUMN createdAt TO created_at;
ALTER TABLE payer_settlements RENAME COLUMN updatedAt TO updated_at;

-- payers table
ALTER TABLE payers RENAME COLUMN companyId TO company_uuid;
ALTER TABLE payers RENAME COLUMN createdAt TO created_at;
ALTER TABLE payers RENAME COLUMN updatedAt TO updated_at;

-- expense_master table
ALTER TABLE expense_master RENAME COLUMN companyId TO company_uuid;
ALTER TABLE expense_master RENAME COLUMN createdAt TO created_at;
ALTER TABLE expense_master RENAME COLUMN updatedAt TO updated_at;

-- daily_expenses table
ALTER TABLE daily_expenses RENAME COLUMN companyId TO company_uuid;
ALTER TABLE daily_expenses RENAME COLUMN createdAt TO created_at;
ALTER TABLE daily_expenses RENAME COLUMN updatedAt TO updated_at;

-- diesel_usage table
ALTER TABLE diesel_usage RENAME COLUMN companyId TO company_uuid;
ALTER TABLE diesel_usage RENAME COLUMN createdAt TO created_at;
ALTER TABLE diesel_usage RENAME COLUMN updatedAt TO updated_at;

-- equipment_usage table
ALTER TABLE equipment_usage RENAME COLUMN companyId TO company_uuid;
ALTER TABLE equipment_usage RENAME COLUMN createdAt TO created_at;
ALTER TABLE equipment_usage RENAME COLUMN updatedAt TO updated_at;

-- vehicle_types table
ALTER TABLE vehicle_types RENAME COLUMN companyId TO company_uuid;
ALTER TABLE vehicle_types RENAME COLUMN createdAt TO created_at;
ALTER TABLE vehicle_types RENAME COLUMN updatedAt TO updated_at;

-- Rename existing indexes on companyId
ALTER INDEX idx_users_companyId RENAME TO idx_users_company_uuid;
ALTER INDEX idx_internal_vehicles_companyId RENAME TO idx_internal_vehicles_company_uuid;
ALTER INDEX idx_vehicle_entries_companyId RENAME TO idx_vehicle_entries_company_uuid;
ALTER INDEX idx_payer_settlements_companyId RENAME TO idx_payer_settlements_company_uuid;
ALTER INDEX idx_payers_companyId RENAME TO idx_payers_company_uuid;
ALTER INDEX idx_expense_master_companyId RENAME TO idx_expense_master_company_uuid;
ALTER INDEX idx_daily_expenses_companyId RENAME TO idx_daily_expenses_company_uuid;
ALTER INDEX idx_diesel_usage_companyId RENAME TO idx_diesel_usage_company_uuid;
ALTER INDEX idx_equipment_usage_companyId RENAME TO idx_equipment_usage_company_uuid;
ALTER INDEX idx_vehicle_types_companyId RENAME TO idx_vehicle_types_company_uuid;

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
