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
