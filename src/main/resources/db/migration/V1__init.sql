-- Initial database schema

CREATE TABLE IF NOT EXISTS users (
  id TEXT PRIMARY KEY,
  uuid TEXT UNIQUE,
  employeeId TEXT NOT NULL,
  email TEXT UNIQUE,
  mobileNo TEXT,
  password TEXT,
  role TEXT,
  name TEXT,
  companyId INTEGER,
  companyName TEXT,
  createdBy TEXT,
  location TEXT,
  dateOfBirth TEXT,
  joiningDate TEXT,
  isActive INTEGER DEFAULT 1,
  UNIQUE (companyId, employeeId)
);

CREATE TABLE IF NOT EXISTS companies (
  uuid TEXT PRIMARY KEY,
  companyId TEXT UNIQUE,
  companyName TEXT(30) NOT NULL,
  companyContactNo TEXT NOT NULL,
  companyCoordinates TEXT,
  companyLocation TEXT(50) NOT NULL,
  companyRegistrationDate TEXT NOT NULL,
  ownerName TEXT(20) NOT NULL,
  ownerMobileNo TEXT NOT NULL,
  ownerEmailAddress TEXT(50) NOT NULL,
  ownerDOB TEXT NOT NULL,
  isActive INTEGER DEFAULT 1,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS internal_vehicles (
  vehicleId TEXT(20) PRIMARY KEY,
  vehicleName TEXT(30) NOT NULL,
  vehicleType TEXT(15) NOT NULL,
  isActive INTEGER DEFAULT 1,
  companyId TEXT,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vehicle_entries (
  entryId TEXT(20) PRIMARY KEY,
  companyId TEXT NOT NULL,
  payerId TEXT(20) NOT NULL,
  vehicleNumber TEXT(15) NOT NULL,
  vehicleType TEXT(15) NOT NULL,
  fromAddress TEXT(50) NOT NULL,
  toAddress TEXT(50) NOT NULL,
  driverName TEXT(20) NOT NULL,
  driverContactNo TEXT NOT NULL,
  commission REAL NOT NULL,
  beta REAL NOT NULL,
  refferedBy TEXT(20),
  amount REAL NOT NULL,
  paytype TEXT(10) NOT NULL,
  entryDate DATE NOT NULL,
  entryTime DATETIME NOT NULL,
  exitTime DATETIME,
  notes TEXT,
  paymentReceivedBy TEXT,
  paidAmount REAL NOT NULL,
  pendingAmt REAL NOT NULL,
  isSettled INTEGER DEFAULT 0,
  settlementType TEXT(15),
  settlementDate DATETIME,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payer_settlements (
  settlementId TEXT(20) PRIMARY KEY,
  payerId TEXT(20) NOT NULL,
  amount REAL NOT NULL,
  date DATETIME NOT NULL,
  companyId TEXT NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payers (
  payerId TEXT(20) PRIMARY KEY,
  payerName TEXT(30) NOT NULL,
  mobileNo TEXT NOT NULL,
  payerAddress TEXT(100),
  registrationDate DATE,
  creditLimit INTEGER(10) DEFAULT 0,
  companyId TEXT NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS expense_master (
  id TEXT(20) PRIMARY KEY,
  expenseName TEXT(20) NOT NULL,
  companyId TEXT NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS daily_expenses (
  expenseId TEXT(20) PRIMARY KEY,
  expenseType TEXT(30) NOT NULL,
  expenseAmount REAL NOT NULL,
  expenseDate DATETIME NOT NULL,
  expenseNote TEXT,
  companyId TEXT NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS diesel_usage (
  dieselUsageId TEXT(20) PRIMARY KEY,
  vehicleName TEXT(30) NOT NULL,
  date DATETIME NOT NULL,
  liters REAL NOT NULL,
  companyId TEXT NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS equipment_usage (
  equipmentUsageId TEXT(20) PRIMARY KEY,
  equipmentName TEXT(30) NOT NULL,
  equipmentType TEXT(15) NOT NULL,
  startKM REAL NOT NULL,
  endKM REAL NOT NULL,
  startTime DATETIME NOT NULL,
  endTime DATETIME NOT NULL,
  date DATETIME NOT NULL,
  companyId TEXT NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vehicle_types (
  id TEXT(20) PRIMARY KEY,
  vehicleType TEXT(30) NOT NULL,
  type TEXT(10) NOT NULL,
  companyId TEXT NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy TEXT,
  createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
  updatedBy TEXT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for frequent filters
CREATE INDEX idx_users_companyId ON users(companyId);
CREATE INDEX idx_internal_vehicles_companyId ON internal_vehicles(companyId);
CREATE INDEX idx_vehicle_entries_companyId ON vehicle_entries(companyId);
CREATE INDEX idx_payer_settlements_companyId ON payer_settlements(companyId);
CREATE INDEX idx_payers_companyId ON payers(companyId);
CREATE INDEX idx_expense_master_companyId ON expense_master(companyId);
CREATE INDEX idx_daily_expenses_companyId ON daily_expenses(companyId);
CREATE INDEX idx_diesel_usage_companyId ON diesel_usage(companyId);
CREATE INDEX idx_equipment_usage_companyId ON equipment_usage(companyId);
CREATE INDEX idx_vehicle_types_companyId ON vehicle_types(companyId);
