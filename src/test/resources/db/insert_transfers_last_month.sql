INSERT INTO companies (id, cuit, business_name, created_at) VALUES
(1, '30-12345678-1', 'Empresa Uno', DATEADD('MONTH', -2, CURRENT_DATE)),
(2, '30-12355679-2', 'Empresa Dos', DATEADD('MONTH', -2, CURRENT_DATE)),
(3, '30-12365678-3', 'Empresa Tres', DATEADD('MONTH', -3, CURRENT_DATE)),
(4, '30-12375678-4', 'Empresa Cuatro', DATEADD('MONTH', -4, CURRENT_DATE)),
(5, '30-12385678-5', 'Empresa Cinco', DATEADD('MONTH', -5, CURRENT_DATE)),
(6, '30-12395678-6', 'Empresa Seis', DATEADD('MONTH', -4, CURRENT_DATE)),
(7, '30-12305678-3', 'Empresa Siete', DATEADD('MONTH', -2, CURRENT_DATE)),
(8, '30-12335678-1', 'Empresa Ocho', DATEADD('MONTH', -1, CURRENT_DATE)),
(9, '30-12325678-2', 'Empresa Nueve', DATEADD('MONTH', -1, CURRENT_DATE)),
(10, '30-12315678-1', 'Empresa Diez', DATEADD('MONTH', -1, CURRENT_DATE));
ALTER SEQUENCE companies_seq RESTART WITH 11;

INSERT INTO transfers (id, amount, created_at, credit_account, debit_account, company_id) VALUES
(UUID(), 1000.00, DATEADD('MONTH', -1, CURRENT_DATE), '1234567890123', '2345678901234', 1),
(UUID(), 2000.00, DATEADD('MONTH', -1, CURRENT_DATE), '2345678901235', '3456789012346', 2),
(UUID(), 1500.00, DATEADD('MONTH', -1, CURRENT_DATE), '3456789012347', '4567890123458', 3),
(UUID(), 1200.00, DATEADD('MONTH', -1, CURRENT_DATE), '4567890123459', '5678901234560', 4),
(UUID(), 1800.00, DATEADD('MONTH', -1, CURRENT_DATE), '6789012345673', '2345678901234', 1),
(UUID(), 2200.00, DATEADD('MONTH', -1, CURRENT_DATE), '7890123456785', '8901234567896', 2),
(UUID(), 1700.00, DATEADD('MONTH', -1, CURRENT_DATE), '8901234567897', '4567890123458', 3),
(UUID(), 1300.00, DATEADD('MONTH', -1, CURRENT_DATE), '9012345678909', '0123456789010', 4),
(UUID(), 1100.00, DATEADD('MONTH', -1, CURRENT_DATE), '2345678901237', '6789012345679', 1),
(UUID(), 1900.00, DATEADD('MONTH', -1, CURRENT_DATE), '3456789012349', '3456789012346', 2);