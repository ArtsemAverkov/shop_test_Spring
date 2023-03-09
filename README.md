# Привет, я ![Артем](https://www.linkedin.com/in/артем-аверков-aa7663239/) 
Введение
Этот проект представляет собой Java-приложение, котороевыводит чек купленных товаров у. Приложение построено с использованием 17 Java и использует [Spring-boot Hibernate Grade Posgresql Docker Unit 5 Mockito] для достижения своей функциональности.

Начиная
Чтобы начать работу с этим проектом, на вашем компьютере должна быть установлена ​​17 Java. Вы можете загрузить и установить последнюю версию Java с сайта https://www.oracle.com/java/.

Далее вам нужно будет скачать исходный код проекта с ![GitHub](https://github.com/ArtsemAverkov/shop_test_Spring.git)  Получив исходный код, вы можете импортировать проект в  среду IDE IntelliJ и запустить его оттуда.

Применение
Чтобы использовать приложение, нужно использовать например Postman, по методу ![GET](Localhost:8080/product) чтобы получить все товары потом по методу Get в таком формате:
http://localhost:8080/product/check?id=3&amount=6&id=2&amount=1&id=1&amount=2&id=4&amount=3&id=5&amount=4&id=6&amount=5&discount=CARD_1234&idDiscount=1 где Id это Id товара, amount количество и CARD это скиданная карта, возвращаеться чек по условию: -подсчитываться стоимость исходя из количества, -если акционных товаров больше 5 то для них подсчитывает скидка 10 процентов, -возвращается общая сумма и сумма после скидки.


Функции
Приложение имеет следующие возможности:
Вывод чека,
Получение, запись, удаление, обновление и получения всех продуктов
Получение, запись, удаление, обновление и получения всех дискретных карт,
Получить и сохранить аватар продукта.


Заключение
Благодарим вас за интерес к этому Java-проекту. Если у вас есть какие-либо вопросы или отзывы, пожалуйста, не стесняйтесь 
![linkedin](https://www.linkedin.com/in/артем-аверков-aa7663239/).
