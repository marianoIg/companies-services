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



