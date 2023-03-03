!!!
commits залиты в один день потому что я писал в основно проекте( есть вся история), потом прочитал внимательно задание и пришлось исправляться
(если нужна история я сделаю пуш на основном проекте)
!!!

# shop_test_Spring
Используемый стек: java-17 Spring-boot Hibernate Grade Posgresql Docker Unit 5 Mockito

Приложение развернул в Docker все работает.
app-1 589d8773e48b65c00c8575177a09a7ce3ca627554199a22833c7a0ad0e169f4e
posgres-1 57b2358e4d4d12317afc84121e85a7d60e27edfdec771cf5d60bd660f1c1bc33


Запроссы в Controller;

Create Discount:

Post Localhost:8080/discount

{ "id": 1, "name": «CARD_1111" }

Return: int

Read Discount:

Get Localhost:8080/discount/{id}

Return: { "id": 1, "name": «CARD_1111" }

Update Discount:

Pach Localhost:8080/discount/{id} { "id": 1, "name": «CARD_1111" }

Return: boolean

Delete Discount:

Delete

Localhost:8080/discount/{id}

Return: boolean

Read All Discount:

Get Localhost:8080/discount

Return: all

Create Product:

Post Localhost:8080/product { "id": 30, "name": "apple", "price": 31.96, "amount": 73, "isDiscount": true, "dateInserting": "2021-05-12" }

Return: int

Get Check:

Get

http://localhost:8080/product/check?id=3&amount=6&id=2&amount=1&id=1&amount=2&id=4&amount=3&id=5&amount=4&id=6&amount=5&discount=CARD_1234&idDiscount=1

Return: возвращаются продукты по условию: -подсчитываться стоимость исходя из количества, -если акционных товаров больше 5 то для них подсчитывает скидка 10 процентов, -возвращается общая сумма и сумма после скидки

{ "id": 30, "name": "apple", "price": 31.96, "amount": 73, "isDiscount": true, "dateInserting": "2021-05-12" }

Update Product:

Pach

Localhost:8080/product/{id} { "id": 30, "name": "apple", "price": 31.96, "amount": 10, "isDiscount": true, "dateInserting": "2021-05-12" } Return: boolean

Delete Product:

Delete

Localhost:8080/product/{id}

Return: boolean

Read All Product:

Get

Localhost:8080/product

Return: all
