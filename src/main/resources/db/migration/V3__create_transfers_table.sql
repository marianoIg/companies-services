CREATE TABLE IF NOT EXISTS transfers (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    amount DECIMAL(18,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    credit_account VARCHAR(30) NOT NULL,
    debit_account VARCHAR(30) NOT NULL,
    company_id BIGINT NOT NULL
);

ALTER TABLE transfers
ADD CONSTRAINT fk_transfers_company FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE;