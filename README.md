# companies-services

Aplicación para la gestión de compañías y consulta de movimientos mensuales.

## Marco del proyecto

- Lenguaje: Java 21 LTS sobre un marco SpringBoot 3.4.2
- Maven: maven versión 3.9.6
- OpenApi: OpenApi versión 3.0.1
- Springboot caching: Sistema de caché

## Tecnologías necesarias

- Java 21
- Docker V27.3.1 
- Lombok 1.18.34
- Maven 3.9.6
- puertos libres:
    - 1986 : Utilizado por la aplicación para recibir peticiones.

## Modo de ejecucion.

- Importe el proyecto a un IDE.
- Inicie la aplicación.

## URL's

- Swagger-ui: http://localhost:1986/swagger-ui/index.html

## Ejemplos

curl -X 'POST' \
'http://localhost:1986/api/companies' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"cuit": "30-12345678-9",
"businessName": "Ejemplo S.A."
}'

{
  "id": 1,
  "cuit": "30-12345678-9",
  "businessName": "Ejemplo SA",
  "createdAt": "2025-03-11T20:22:21.261167"
}


curl -X 'GET' \
  'http://localhost:1986/api/companies/created-last-month?page=0&size=10' \
  -H 'accept: */*'

{
    "content": [
        {
            "id": 8,
            "cuit": "30-12335678-1",
            "businessName": "Empresa Ocho",
            "createdAt": "2025-02-12T00:00:00"
        },
        {
            "id": 9,
            "cuit": "30-12325678-2",
            "businessName": "Empresa Nueve",
            "createdAt": "2025-02-12T00:00:00"
        },
        {
            "id": 10,
            "cuit": "30-12315678-1",
            "businessName": "Empresa Diez",
            "createdAt": "2025-02-12T00:00:00"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 5,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 3,
    "totalPages": 1,
    "first": true,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 3,
    "empty": false
}

curl -X 'GET' \
  'http://localhost:1986/api/companies/transfers/last-month?page=0&size=10' \
  -H 'accept: */*'

{
    "content": [
        {
            "id": 1,
            "cuit": "30-12345678-1",
            "businessName": "Empresa Uno",
            "createdAt": "2025-01-12T00:00:00",
            "transfers": [
                {
                    "id": "3d1d4ad6-62b5-40d0-a994-a76be65e2a53",
                    "amount": 1000.0,
                    "debitAccount": "2345678901234",
                    "creditAccount": "1234567890123",
                    "createdAt": "2025-02-12T00:00:00"
                },
                {
                    "id": "3e4eb8ca-bbec-4738-b8a8-77d1261eabb6",
                    "amount": 1800.0,
                    "debitAccount": "2345678901234",
                    "creditAccount": "6789012345673",
                    "createdAt": "2025-02-12T00:00:00"
                },
                {
                    "id": "4f5eaa85-4b15-4456-ae06-6138c9d7bd19",
                    "amount": 1100.0,
                    "debitAccount": "6789012345679",
                    "creditAccount": "2345678901237",
                    "createdAt": "2025-02-12T00:00:00"
                }
            ]
        },
        {
            "id": 2,
            "cuit": "30-12355679-2",
            "businessName": "Empresa Dos",
            "createdAt": "2025-01-12T00:00:00",
            "transfers": [
                {
                    "id": "6c37e477-14b3-4e5b-a771-22f94ce1411d",
                    "amount": 2000.0,
                    "debitAccount": "3456789012346",
                    "creditAccount": "2345678901235",
                    "createdAt": "2025-02-12T00:00:00"
                },
                {
                    "id": "823253cd-4df8-491a-8931-30fb2b000ca1",
                    "amount": 2200.0,
                    "debitAccount": "8901234567896",
                    "creditAccount": "7890123456785",
                    "createdAt": "2025-02-12T00:00:00"
                },
                {
                    "id": "10ccd7ab-e08e-457a-8c3c-99612d7ad6db",
                    "amount": 1900.0,
                    "debitAccount": "3456789012346",
                    "creditAccount": "3456789012349",
                    "createdAt": "2025-02-12T00:00:00"
                }
            ]
        },
        {
            "id": 3,
            "cuit": "30-12365678-3",
            "businessName": "Empresa Tres",
            "createdAt": "2024-12-12T00:00:00",
            "transfers": [
                {
                    "id": "09fb9423-08d8-4846-9fc7-e77090f064ec",
                    "amount": 1500.0,
                    "debitAccount": "4567890123458",
                    "creditAccount": "3456789012347",
                    "createdAt": "2025-02-12T00:00:00"
                },
                {
                    "id": "7383394e-15b6-4864-8ec2-48d9e873e9a8",
                    "amount": 1700.0,
                    "debitAccount": "4567890123458",
                    "creditAccount": "8901234567897",
                    "createdAt": "2025-02-12T00:00:00"
                }
            ]
        },
        {
            "id": 4,
            "cuit": "30-12375678-4",
            "businessName": "Empresa Cuatro",
            "createdAt": "2024-11-12T00:00:00",
            "transfers": [
                {
                    "id": "d56c580e-202b-4cd5-a319-7e563eac752e",
                    "amount": 1200.0,
                    "debitAccount": "5678901234560",
                    "creditAccount": "4567890123459",
                    "createdAt": "2025-02-12T00:00:00"
                },
                {
                    "id": "f93c12b0-a6ee-4fff-8820-e085e9613724",
                    "amount": 1300.0,
                    "debitAccount": "0123456789010",
                    "creditAccount": "9012345678909",
                    "createdAt": "2025-02-12T00:00:00"
                }
            ]
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 4,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 4,
    "empty": false
}

