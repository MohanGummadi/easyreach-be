CREATE TABLE IF NOT EXISTS users (
  id TEXT PRIMARY KEY,
  uuid TEXT UNIQUE,
  employeeId VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE,
  mobileNo VARCHAR(20),
  password VARCHAR(255),
  role VARCHAR(30),
  name VARCHAR(100),
  companyId VARCHAR(50),
  companyName VARCHAR(100),
  createdBy VARCHAR(50),
  location VARCHAR(100),
  dateOfBirth DATE,
  joiningDate DATE,
  isActive INTEGER DEFAULT 1,
  UNIQUE (companyId, employeeId)
);

CREATE TABLE IF NOT EXISTS companies (
  uuid TEXT PRIMARY KEY,
  companyId VARCHAR(50) UNIQUE,
  companyName VARCHAR(30) NOT NULL,
  companyContactNo VARCHAR(20) NOT NULL,
  companyCoordinates VARCHAR(100),
  companyLocation VARCHAR(50) NOT NULL,
  companyRegistrationDate DATE NOT NULL,
  ownerName VARCHAR(20) NOT NULL,
  ownerMobileNo VARCHAR(20) NOT NULL,
  ownerEmailAddress VARCHAR(50) NOT NULL,
  ownerDOB DATE NOT NULL,
  isActive INTEGER DEFAULT 1,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS internal_vehicles (
  vehicleId VARCHAR(20) PRIMARY KEY,
  vehicleName VARCHAR(30) NOT NULL,
  vehicleType VARCHAR(15) NOT NULL,
  isActive INTEGER DEFAULT 1,
  companyId VARCHAR(50),
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vehicle_entries (
  entryId VARCHAR(20) PRIMARY KEY,
  companyId VARCHAR(50) NOT NULL,
  payerId VARCHAR(20) NOT NULL,
  vehicleNumber VARCHAR(15) NOT NULL,
  vehicleType VARCHAR(15) NOT NULL,
  fromAddress VARCHAR(50) NOT NULL,
  toAddress VARCHAR(50) NOT NULL,
  driverName VARCHAR(20) NOT NULL,
  driverContactNo VARCHAR(20) NOT NULL,
  commission NUMERIC(10,2) NOT NULL,
  beta NUMERIC(10,2) NOT NULL,
  refferedBy VARCHAR(20),
  amount NUMERIC(10,2) NOT NULL,
  paytype VARCHAR(10) NOT NULL,
  entryDate DATE NOT NULL,
  entryTime TIMESTAMP NOT NULL,
  exitTime TIMESTAMP,
  notes TEXT,
  paymentReceivedBy VARCHAR(50),
  paidAmount NUMERIC(10,2) NOT NULL,
  pendingAmt NUMERIC(10,2) NOT NULL,
  isSettled INTEGER DEFAULT 0,
  settlementType VARCHAR(15),
  settlementDate TIMESTAMP,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payer_settlements (
  settlementId VARCHAR(20) PRIMARY KEY,
  payerId VARCHAR(20) NOT NULL,
  amount NUMERIC(10,2) NOT NULL,
  date TIMESTAMP NOT NULL,
  companyId VARCHAR(50) NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payers (
  payerId VARCHAR(20) PRIMARY KEY,
  payerName VARCHAR(30) NOT NULL,
  mobileNo VARCHAR(20) NOT NULL,
  payerAddress VARCHAR(100),
  registrationDate DATE,
  creditLimit INTEGER DEFAULT 0,
  companyId VARCHAR(50) NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS expense_master (
  id VARCHAR(20) PRIMARY KEY,
  expenseName VARCHAR(20) NOT NULL,
  companyId VARCHAR(50) NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS daily_expenses (
  expenseId VARCHAR(20) PRIMARY KEY,
  expenseType VARCHAR(30) NOT NULL,
  expenseAmount NUMERIC(10,2) NOT NULL,
  expenseDate TIMESTAMP NOT NULL,
  expenseNote TEXT,
  companyId VARCHAR(50) NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS diesel_usage (
  dieselUsageId VARCHAR(20) PRIMARY KEY,
  vehicleName VARCHAR(30) NOT NULL,
  date TIMESTAMP NOT NULL,
  liters NUMERIC(10,2) NOT NULL,
  companyId VARCHAR(50) NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS equipment_usage (
  equipmentUsageId VARCHAR(20) PRIMARY KEY,
  equipmentName VARCHAR(30) NOT NULL,
  equipmentType VARCHAR(15) NOT NULL,
  startKM NUMERIC(10,2) NOT NULL,
  endKM NUMERIC(10,2) NOT NULL,
  startTime TIMESTAMP NOT NULL,
  endTime TIMESTAMP NOT NULL,
  date TIMESTAMP NOT NULL,
  companyId VARCHAR(50) NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vehicle_types (
  id VARCHAR(20) PRIMARY KEY,
  vehicleType VARCHAR(30) NOT NULL,
  type VARCHAR(10) NOT NULL,
  companyId VARCHAR(50) NOT NULL,
  isSynced INTEGER DEFAULT 0,
  createdBy VARCHAR(50),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updatedBy VARCHAR(50),
  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
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
