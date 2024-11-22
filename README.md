# Proyecto Prueba Jenkins 

[![codecov](https://codecov.io/gh/jorgevelasquezv/products-jenkins/branch/master/graph/badge.svg?token=0EO7RRUA97)](https://codecov.io/gh/jorgevelasquezv/products-jenkins)

## Depliegue en local

1. Requisitos: Docker y docker-compose 
2. Abrir el proyecto en una consola o terminal navegar a la ruta ```/ms_products/deployment```
3. Ejecutar el comando
   ```
   docker-compose up -d 
   ```
4. Validar que los contenedores ms-products y db-products esten levantados con el comando 
   ```
   docker ps 
   ```
5. Cuando los contenedores de ms-products y db-products esten levantados se puede realizar peticones al API usando la coleción de postman proporcionada en la carpeta collections. [Enlace a la colección de Postman](./collections/MS_Products_IC.postman_collection) 
   
