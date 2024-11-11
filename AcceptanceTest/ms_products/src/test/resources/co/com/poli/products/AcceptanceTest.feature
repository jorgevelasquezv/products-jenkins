@acceptanceTest
Feature: Acceptance testing scenarios for Product microservice

    Background:
    * url urlBase
    * configure headers = headers
    * def requestProduct = read('json/request.json')

    Scenario: Dado que un un usuario requiere conocer el listado de productos Cuando se realiza una consulta al sistema de productos Entonces el sistema retorna una respuesta exitosa con el listado de productos
    Given path '/products'
    When method GET
    Then status 200

    Scenario Outline: Dado que un usuario requiere conocer la información de un producto Cuando se realiza una consulta al sistema de productos por codigo de producto <ID> Entonces el sistema retorna una respuesta exitosa con la información del producto
    Given path '/products/' + ID
    When method GET
    Then status 200
        Examples:
        | ID |
        | 1  |
        | 2  |
        | 3  |
        | 4  |
        | 5  |

    Scenario: Dado que un usuario requiere conocer la información de un producto Cuando se realiza una consulta al sistema de productos por codigo de producto que no existe Entonces el sistema retorna una respuesta con un mensaje de error
    Given path '/products/999999'
    When method GET
    Then status 404

    Scenario: Dado que un usuario requiere registrar un producto Cuando se realiza una petición al sistema de productos con la información del producto Entonces el sistema retorna una respuesta exitosa con la información del producto registrado
    Given path '/products'
    And request requestProduct
    When method POST
    Then status 200

    Scenario Outline: Dado que un usuario requiere registrar un producto con información incompleta Cuando se realiza una petición al sistema de productos con la información del producto incompleta Entonces el sistema retorna una respuesta con un mensaje de error
    Given path '/products'
    And remove requestProduct.<field>
    And request requestProduct
    When method POST
    Then status 400
        Examples:
        | field |
        | name  |
        | description |
        | price |
        | quantity |

    Scenario Outline: Dado que un usuario requiere actualizar la información de un producto Cuando se realiza una petición al sistema de productos con la información del producto a actualizar <ID> Entonces el sistema retorna una respuesta exitosa con la información del producto actualizado
    Given path '/products/' + ID
    And remove requestProduct.name
    And remove requestProduct.description
    And set requestProduct.price = ProductPrice
    And set requestProduct.stock = ProductStock
    And request requestProduct
    When method PUT
    Then status 200
        Examples:
        | ID | ProductPrice | ProductStock |
        | 1  | 1000         | 10          |
        | 2  | 2000         | 20          |
        | 3  | 3000         | 30          |
        | 4  | 4000         | 40          |
        | 5  | 5000         | 50          |

    Scenario Outline: Dado que un usuario requiere eliminar un producto Cuando se realiza una petición al sistema de productos con el código del producto a eliminar <ID> Entonces el sistema retorna una respuesta exitosa
    Given path '/products/' + ID
    When method DELETE
    Then status 200
        Examples:
        | ID |
        | 1  |
        | 2  |
        | 3  |
        | 4  |
        | 5  |

    Scenario: Dado que un usuario requiere eliminar un producto que no existe Cuando se realiza una petición al sistema de productos con el código del producto a eliminar que no existe Entonces el sistema retorna una respuesta con un mensaje de error
    Given path '/products/999999'
    When method DELETE
    Then status 404