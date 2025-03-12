CREATE INDEX idx_companies_cuit ON companies(cuit);
CREATE INDEX idx_companies_created_at ON companies(created_at);
CREATE INDEX idx_transfers_created_at ON transfers(created_at);
CREATE INDEX idx_transfers_company_id ON transfers(company_id);

