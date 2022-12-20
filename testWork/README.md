Используемый стек:
java-17
Spring-boot
Hibernate
Grade
Posgresql
Docker
Unit 5
Mockito

Приложение развернул в Docker все работает.


Запроссы в Controller;

Create Discount:

Post
Localhost:8080/discount

{
"id": 1,
"name": «CARD_1111"
}

Return: int
_____________________
Read Discount:

Get
Localhost:8080/discount/{id}

Return: {
"id": 1,
"name": «CARD_1111"
}
________________________
Update Discount:

Pach
Localhost:8080/discount/{id}
{
"id": 1,
"name": «CARD_1111"
}

Return: boolean

_________________________
Delete Discount:

Delete

Localhost:8080/discount/{id}

Return: boolean
_________________________

Read All Discount:

Get
Localhost:8080/discount

Return: all

______________________________________________________________________

Create Product:

Post
Localhost:8080/product
{
"id": 30,
"name": "apple",
"price": 31.96,
"amount": 73,
"isDiscount": true,
"dateInserting": "2021-05-12"
}

Return: int
_____________________
Get Check:

Get

http://localhost:8080/product/check?id=3&amount=6&id=2&amount=1&id=1&amount=2&id=4&amount=3&id=5&amount=4&id=6&amount=5&discount=CARD_1234&idDiscount=1

Return:
возвращаются продукты по условию:
-подсчитываться стоимость исходя из количества,
-если акционных товаров больше 5 то для них подсчитывает скидка 10 процентов,
-возвращается общая сумма и сумма после скидки

{
"id": 30,
"name": "apple",
"price": 31.96,
"amount": 73,
"isDiscount": true,
"dateInserting": "2021-05-12"
}
________________________
Update Product:

Pach

Localhost:8080/product/{id}
{
"id": 30,
"name": "apple",
"price": 31.96,
"amount": 10,
"isDiscount": true,
"dateInserting": "2021-05-12"
}
Return: boolean

_________________________
Delete Product:

Delete

Localhost:8080/product/{id}

Return: boolean
_________________________

Read All Product:

Get

Localhost:8080/product

Return: all 
