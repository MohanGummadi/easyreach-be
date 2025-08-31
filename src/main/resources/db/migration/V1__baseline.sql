CREATE TABLE public.companies (
    uuid text NOT NULL,
    company_code character varying(50) NOT NULL,
    company_name character varying(30) NOT NULL,
    company_contact_no character varying(20) NOT NULL,
    company_coordinates character varying(100),
    company_location character varying(50) NOT NULL,
    company_registration_date date NOT NULL,
    owner_name character varying(20) NOT NULL,
    owner_mobile_no character varying(20) NOT NULL,
    owner_email_address character varying(50) NOT NULL,
    owner_dob date NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.companies_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.companies_change_id_seq OWNED BY public.companies.change_id;
CREATE TABLE public.daily_expenses (
    "expenseId" character varying(20) NOT NULL,
    expense_type character varying(30) NOT NULL,
    expense_amount numeric(10,2) NOT NULL,
    expense_date timestamp without time zone NOT NULL,
    expense_note text,
    is_paid boolean DEFAULT false NOT NULL,
    paid_by character varying(50),
    paid_to character varying(50),
    paid_date timestamp without time zone,
    company_uuid character varying(50) NOT NULL,
    user_id character varying(50),
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.daily_expenses_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.daily_expenses_change_id_seq OWNED BY public.daily_expenses.change_id;
CREATE TABLE public.diesel_usage (
    "dieselUsageId" character varying(20) NOT NULL,
    vehicle_name character varying(30) NOT NULL,
    date timestamp without time zone NOT NULL,
    liters numeric(10,2) NOT NULL,
    company_uuid character varying(50) NOT NULL,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.diesel_usage_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.diesel_usage_change_id_seq OWNED BY public.diesel_usage.change_id;
CREATE TABLE public.equipment_usage (
    "equipmentUsageId" character varying(20) NOT NULL,
    equipment_name character varying(30) NOT NULL,
    equipment_type character varying(15) NOT NULL,
    start_km numeric(10,2) NOT NULL,
    end_km numeric(10,2) NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL,
    date timestamp without time zone NOT NULL,
    company_uuid character varying(50) NOT NULL,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.equipment_usage_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.equipment_usage_change_id_seq OWNED BY public.equipment_usage.change_id;
CREATE TABLE public.expense_master (
    id character varying(20) NOT NULL,
    expense_name character varying(20) NOT NULL,
    company_uuid character varying(50) NOT NULL,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.expense_master_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.expense_master_change_id_seq OWNED BY public.expense_master.change_id;
CREATE TABLE public.internal_vehicles (
    "vehicleId" character varying(20) NOT NULL,
    vehicle_name character varying(30) NOT NULL,
    vehicle_type character varying(15) NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    company_uuid character varying(50),
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.internal_vehicles_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.internal_vehicles_change_id_seq OWNED BY public.internal_vehicles.change_id;
CREATE TABLE public.payer_settlements (
    "settlementId" character varying(20) NOT NULL,
    payer_id character varying(20) NOT NULL,
    amount numeric(10,2) NOT NULL,
    date timestamp without time zone NOT NULL,
    company_uuid character varying(50) NOT NULL,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.payer_settlements_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.payer_settlements_change_id_seq OWNED BY public.payer_settlements.change_id;
CREATE TABLE public.payers (
    "payerId" character varying(20) NOT NULL,
    payer_name character varying(30) NOT NULL,
    mobile_no character varying(20) NOT NULL,
    payer_address character varying(100),
    registration_date date,
    credit_limit integer DEFAULT 0,
    company_uuid character varying(50) NOT NULL,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.payers_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.payers_change_id_seq OWNED BY public.payers.change_id;
CREATE TABLE public.refresh_token (
    jti character varying(100) NOT NULL,
    user_id text NOT NULL,
    issued_at timestamp with time zone NOT NULL,
    expires_at timestamp with time zone NOT NULL,
    revoked_at timestamp with time zone,
    rotated_from_jti character varying(100),
    is_synced boolean,
    created_by character varying(50),
    created_at timestamp with time zone,
    updated_by character varying(50),
    updated_at timestamp with time zone
);
CREATE TABLE public.users (
    id text NOT NULL,
    employee_id character varying(50) NOT NULL,
    email character varying(100),
    mobile_no character varying(20),
    password character varying(255),
    role character varying(30),
    name character varying(100),
    company_uuid character varying(50),
    company_code character varying(50),
    created_by character varying(50),
    location character varying(100),
    date_of_birth date,
    joining_date date,
    is_active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.users_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.users_change_id_seq OWNED BY public.users.change_id;
CREATE TABLE public.vehicle_entries (
    "entryId" character varying(20) NOT NULL,
    company_uuid character varying(50) NOT NULL,
    payer_id character varying(20) NOT NULL,
    vehicle_number character varying(15) NOT NULL,
    vehicle_type character varying(15) NOT NULL,
    from_address character varying(50) NOT NULL,
    to_address character varying(50) NOT NULL,
    driver_name character varying(20) NOT NULL,
    driver_contact_no character varying(20) NOT NULL,
    commission numeric(10,2) NOT NULL,
    beta numeric(10,2) NOT NULL,
    referred_by character varying(20),
    amount numeric(10,2) NOT NULL,
    paytype character varying(10) NOT NULL,
    entry_date date NOT NULL,
    entry_time timestamp without time zone NOT NULL,
    exit_time timestamp without time zone,
    notes text,
    payment_received_by character varying(50),
    paid_amount numeric(10,2) NOT NULL,
    pending_amt numeric(10,2) NOT NULL,
    is_settled boolean DEFAULT false NOT NULL,
    settlement_type character varying(15),
    settlement_date timestamp without time zone,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.vehicle_entries_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.vehicle_entries_change_id_seq OWNED BY public.vehicle_entries.change_id;
CREATE TABLE public.vehicle_types (
    id character varying(20) NOT NULL,
    vehicle_type character varying(30) NOT NULL,
    type character varying(10) NOT NULL,
    company_uuid character varying(50) NOT NULL,
    is_synced boolean DEFAULT false NOT NULL,
    created_by character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by character varying(50),
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted boolean DEFAULT false NOT NULL,
    deleted_at timestamp with time zone,
    change_id bigint NOT NULL
);
CREATE SEQUENCE public.vehicle_types_change_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.vehicle_types_change_id_seq OWNED BY public.vehicle_types.change_id;
ALTER TABLE ONLY public.companies ALTER COLUMN change_id SET DEFAULT nextval('public.companies_change_id_seq'::regclass);
ALTER TABLE ONLY public.daily_expenses ALTER COLUMN change_id SET DEFAULT nextval('public.daily_expenses_change_id_seq'::regclass);
ALTER TABLE ONLY public.diesel_usage ALTER COLUMN change_id SET DEFAULT nextval('public.diesel_usage_change_id_seq'::regclass);
ALTER TABLE ONLY public.equipment_usage ALTER COLUMN change_id SET DEFAULT nextval('public.equipment_usage_change_id_seq'::regclass);
ALTER TABLE ONLY public.expense_master ALTER COLUMN change_id SET DEFAULT nextval('public.expense_master_change_id_seq'::regclass);
ALTER TABLE ONLY public.internal_vehicles ALTER COLUMN change_id SET DEFAULT nextval('public.internal_vehicles_change_id_seq'::regclass);
ALTER TABLE ONLY public.payer_settlements ALTER COLUMN change_id SET DEFAULT nextval('public.payer_settlements_change_id_seq'::regclass);
ALTER TABLE ONLY public.payers ALTER COLUMN change_id SET DEFAULT nextval('public.payers_change_id_seq'::regclass);
ALTER TABLE ONLY public.users ALTER COLUMN change_id SET DEFAULT nextval('public.users_change_id_seq'::regclass);
ALTER TABLE ONLY public.vehicle_entries ALTER COLUMN change_id SET DEFAULT nextval('public.vehicle_entries_change_id_seq'::regclass);
ALTER TABLE ONLY public.vehicle_types ALTER COLUMN change_id SET DEFAULT nextval('public.vehicle_types_change_id_seq'::regclass);
ALTER TABLE ONLY public.companies
    ADD CONSTRAINT companies_company_code_key UNIQUE (company_code);
ALTER TABLE ONLY public.companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (uuid);
ALTER TABLE ONLY public.daily_expenses
    ADD CONSTRAINT daily_expenses_pkey PRIMARY KEY ("expenseId");
ALTER TABLE ONLY public.diesel_usage
    ADD CONSTRAINT diesel_usage_pkey PRIMARY KEY ("dieselUsageId");
ALTER TABLE ONLY public.equipment_usage
    ADD CONSTRAINT equipment_usage_pkey PRIMARY KEY ("equipmentUsageId");
ALTER TABLE ONLY public.expense_master
    ADD CONSTRAINT expense_master_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.internal_vehicles
    ADD CONSTRAINT internal_vehicles_pkey PRIMARY KEY ("vehicleId");
ALTER TABLE ONLY public.payer_settlements
    ADD CONSTRAINT payer_settlements_pkey PRIMARY KEY ("settlementId");
ALTER TABLE ONLY public.payers
    ADD CONSTRAINT payers_pkey PRIMARY KEY ("payerId");
ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (jti);
ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_company_uuid_employee_id_key UNIQUE (company_uuid, employee_id);
ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);
ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.vehicle_entries
    ADD CONSTRAINT vehicle_entries_pkey PRIMARY KEY ("entryId");
ALTER TABLE ONLY public.vehicle_types
    ADD CONSTRAINT vehicle_types_pkey PRIMARY KEY (id);
CREATE INDEX idx_companies_uuid_deleted_deleted_at ON public.companies USING btree (uuid, deleted, deleted_at);
CREATE INDEX idx_companies_uuid_updated_at ON public.companies USING btree (uuid, updated_at);
CREATE INDEX idx_daily_expenses_company_uuid ON public.daily_expenses USING btree (company_uuid);
CREATE INDEX idx_daily_expenses_company_uuid_deleted_deleted_at ON public.daily_expenses USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_daily_expenses_company_uuid_updated_at ON public.daily_expenses USING btree (company_uuid, updated_at);
CREATE INDEX idx_diesel_usage_company_uuid ON public.diesel_usage USING btree (company_uuid);
CREATE INDEX idx_diesel_usage_company_uuid_deleted_deleted_at ON public.diesel_usage USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_diesel_usage_company_uuid_updated_at ON public.diesel_usage USING btree (company_uuid, updated_at);
CREATE INDEX idx_equipment_usage_company_uuid ON public.equipment_usage USING btree (company_uuid);
CREATE INDEX idx_equipment_usage_company_uuid_deleted_deleted_at ON public.equipment_usage USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_equipment_usage_company_uuid_updated_at ON public.equipment_usage USING btree (company_uuid, updated_at);
CREATE INDEX idx_expense_master_company_uuid ON public.expense_master USING btree (company_uuid);
CREATE INDEX idx_expense_master_company_uuid_deleted_deleted_at ON public.expense_master USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_expense_master_company_uuid_updated_at ON public.expense_master USING btree (company_uuid, updated_at);
CREATE INDEX idx_internal_vehicles_company_uuid ON public.internal_vehicles USING btree (company_uuid);
CREATE INDEX idx_internal_vehicles_company_uuid_deleted_deleted_at ON public.internal_vehicles USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_internal_vehicles_company_uuid_updated_at ON public.internal_vehicles USING btree (company_uuid, updated_at);
CREATE INDEX idx_payer_settlements_company_uuid ON public.payer_settlements USING btree (company_uuid);
CREATE INDEX idx_payer_settlements_company_uuid_deleted_deleted_at ON public.payer_settlements USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_payer_settlements_company_uuid_updated_at ON public.payer_settlements USING btree (company_uuid, updated_at);
CREATE INDEX idx_payers_company_uuid ON public.payers USING btree (company_uuid);
CREATE INDEX idx_payers_company_uuid_deleted_deleted_at ON public.payers USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_payers_company_uuid_updated_at ON public.payers USING btree (company_uuid, updated_at);
CREATE INDEX idx_users_company_uuid ON public.users USING btree (company_uuid);
CREATE INDEX idx_users_company_uuid_deleted_deleted_at ON public.users USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_vehicle_entries_company_uuid ON public.vehicle_entries USING btree (company_uuid);
CREATE INDEX idx_vehicle_entries_company_uuid_deleted_deleted_at ON public.vehicle_entries USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_vehicle_entries_company_uuid_updated_at ON public.vehicle_entries USING btree (company_uuid, updated_at);
CREATE INDEX idx_vehicle_types_company_uuid ON public.vehicle_types USING btree (company_uuid);
CREATE INDEX idx_vehicle_types_company_uuid_deleted_deleted_at ON public.vehicle_types USING btree (company_uuid, deleted, deleted_at);
CREATE INDEX idx_vehicle_types_company_uuid_updated_at ON public.vehicle_types USING btree (company_uuid, updated_at);
