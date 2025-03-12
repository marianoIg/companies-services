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