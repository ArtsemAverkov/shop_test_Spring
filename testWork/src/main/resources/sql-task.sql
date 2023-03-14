--Вывести к каждому самолету класс обслуживания и количество мест этого класса
SELECT model AS Самолет,
       fare_conditions AS Клас_обслуживания,
       count(seat_no) AS Количество_мест
FROM aircrafts_data
LEFT JOIN seats
ON(aircrafts_data.aircraft_code = seats.aircraft_code)
GROUP BY Самолет,
         Клас_обслуживания
ORDER BY model;

--Найти 3 самых вместительных самолета (модель + кол-во мест)
SELECT  model AS Самолет, count(seat_no) AS Количество_мест
FROM aircrafts_data
LEFT JOIN seats
ON(aircrafts_data.aircraft_code = seats.aircraft_code)
GROUP BY Самолет
ORDER BY Количество_мест DESC
LIMIT 3;

--Вывести код,модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам
SELECT aircrafts_data.aircraft_code,
		aircrafts_data.model,
		seats.seat_no,
		seats.fare_conditions
FROM aircrafts_data
LEFT JOIN seats
ON(aircrafts_data.aircraft_code = seats.aircraft_code)
WHERE seats.fare_conditions !='Economy'
AND aircrafts_data.model = '{"en": "Airbus A321-200", "ru": "Аэробус A321-200"}'::jsonb
ORDER BY seats.seat_no ASC;

--Вывести города в которых больше 1 аэропорта ( код аэропорта, аэропорт, город)
SELECT airport_code AS Код_аэропорта,
		airport_name AS Аэропорт,
		city AS Город
FROM airports_data
INNER JOIN (
  SELECT city AS city_duplicate
  FROM airports_data
  GROUP BY city
  HAVING COUNT(*) > 1
) duplicate_city ON (airports_data.city  = duplicate_city.city_duplicate);

-- Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация

SELECT *
FROM flights
INNER JOIN (
  SELECT city AS Отправление, airport_code FROM airports_data
  WHERE city = '{"en": "Yekaterinburg","ru": "Екатеринбург"}'::jsonb) airports_a
  ON (flights.departure_airport =  airports_a.airport_code)
INNER JOIN (
  SELECT city AS Прибытие, airport_code  FROM airports_data
  WHERE city = '{"en": "Moscow","ru": "Москва"}'::jsonb) airports_b
  ON (flights.arrival_airport = airports_b.airport_code)
WHERE flights.status IN ('On Time' )
ORDER BY flights.scheduled_departure DESC
LIMIT 1;

--Вывести самый дешевый и дорогой билет и стоимость ( в одном результирующем ответе)
select distinct * from tickets
left join (select book_ref, total_amount as price
		   from bookings
		   group by  book_ref
		   order by price DESC
		   limit 1) max_sum
on tickets.book_ref = max_sum.book_ref
where max_sum is not null
union all
select * from tickets
left join (select book_ref, total_amount as price
		   from bookings
		   group by  book_ref
		   order by price ASC
		   limit 1) min_sum
on tickets.book_ref = min_sum.book_ref
where  min_sum is not null;

-- Написать DDL таблицы Customers , должны быть поля id , firstName, LastName, email , phone. Добавить ограничения на
--     поля ( constraints).
CREATE TABLE Customers (
                   id SERIAL PRIMARY KEY,
                   firstName CHARACTER VARYING(20) NOT NULL,
                   LastName CHARACTER VARYING(20) NOT NULL,
                   email CHARACTER VARYING(30) CONSTRAINT Customers_email_key UNIQUE,
                   phone CHARACTER VARYING(20) CONSTRAINT customers_phone_key UNIQUE);

-- Написать DDL таблицы Orders , должен быть id, customerId, quantity. Должен быть внешний ключ на таблицу customers +
-- ограничения
CREATE TABLE Orders (
    id SERIAL PRIMARY KEY,
    customerId INTEGER,
    quantity INTEGER,
    FOREIGN KEY (customerId) REFERENCES Customers (id) ON DELETE CASCADE,
	CHECK(quantity>0 AND quantity<100)
);

 -- Написать 5 insert в эти таблицы
INSERT INTO Customers (firstName,  LastName, email, phone)
VALUES
('Artsem', 'Averkov', 'temaaak@mail.ru', +37544505187),
('Ivan', 'Ivanov', 'ivan@mail.ru', +3754411111),
('Pavel', 'Pavlov', 'pavel@mail.ru', +3754422222),
('Petr', 'Petrov', 'petr@mail.ru', +3754433333),
('Semen', 'Semenov', 'semen@mail.ru', +3754444444);

INSERT INTO Orders (customerId,  quantity)
VALUES
(8,1),
(9,2),
(10,3),
(11,4),
(12,5);

-- удалить таблицы
DROP TABLE Orders, Customers;

--Вывести моделеи самолета
--Которые имеют количество мест больше  средняго,  исключить места бизнес класса.

SELECT aircrafts_data.aircraft_code,
       aircrafts_data.model,
       COUNT(seats.seat_no) AS Количество
FROM aircrafts_data
LEFT JOIN seats ON aircrafts_data.aircraft_code = seats.aircraft_code
GROUP BY aircrafts_data.aircraft_code, aircrafts_data.model
HAVING COUNT(seats.seat_no) < ALL (SELECT AVG(cnt)
                                    FROM (SELECT COUNT(seat_no) AS cnt
                                          FROM seats
                                          WHERE seats.fare_conditions != 'Business'
                                          GROUP BY aircraft_code) AS subquery)
ORDER BY Количество;
