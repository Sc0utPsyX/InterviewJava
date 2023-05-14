-- 1. ошибки в расписании (фильмы накладываются друг на друга),
-- отсортированные по возрастанию времени. Выводить надо колонки «фильм 1»,
-- «время начала», «длительность», «фильм 2», «время начала», «длительность»;
WITH error_table AS (
    SELECT cinema.movie.name as movie,
           cinema.movie.duration as duration,
           cinema.session.start_time as start_time,
           start_time + (interval '00:01' * duration)::interval > lead(start_time) over(order by start_time) AS error_bool,
            start_time + (interval '00:01' * duration)::interval - lead(start_time) over(order by start_time) AS error_time,
            lead(cinema.movie.name)  over(order by start_time) as next_movie_name,
            lead(cinema.movie.duration) over(order by start_time) as next_movie_duration,
            lead(cinema.session.start_time) over(order by start_time) as next_movie_start_time
    FROM cinema.session
             join cinema.movie on cinema.movie.id = cinema.session.movie_id)
SELECT movie,
       duration,
       start_time,
       next_movie_name,
       next_movie_duration,
       next_movie_start_time
FROM error_table
WHERE error_bool = true
ORDER BY error_time DESC;

-- 2. перерывы 30 минут и более между фильмами — выводить по уменьшению длительности
-- перерыва. Колонки «фильм 1», «время начала», «длительность», «время начала
-- второго фильма», «длительность перерыва»;
WITH break_table AS (
    SELECT cinema.movie.name as movie,
           cinema.movie.duration as duration,
           cinema.session.start_time as start_time,
           start_time + (interval '00:01' * duration)::interval + '30 minutes' < lead(start_time) over(order by start_time) AS error_break,
            start_time + (interval '00:01' * duration)::interval - lead(start_time) over(order by start_time) AS break_time,
            lead(start_time) over(order by start_time) as next_movie_start_time
    FROM cinema.session
             join cinema.movie on cinema.movie.id = cinema.session.movie_id)
SELECT movie, start_time, duration, next_movie_start_time, break_time
from break_table
where error_break = true
ORDER by break_time ASC;

-- 3. список фильмов, для каждого — с указанием общего числа посетителей за все
-- время, среднего числа зрителей за сеанс и общей суммы сборов по каждому
-- фильму (отсортировать по убыванию прибыли). Внизу таблицы должна быть строчка
-- «итого», содержащая данные по всем фильмам сразу;
WITH money_table AS(
    SELECT name as movie_name,
           sum(cinema.session.price)as sum,
    count(cinema.tickets.id) as guests
FROM cinema.movie
    JOIN cinema.session on cinema.session.movie_id = cinema.movie.id
    JOIN cinema.tickets on cinema.tickets.session_id = cinema.session.id
GROUP by name
ORDER by sum DESC
    )
SELECT movie_name,
       sum,
       guests
FROM money_table
UNION ALL
SELECT 'Total: ',
       SUM(sum),
       SUM(guests)
FROM money_table;
-- Как использовать ROLLUP при такой сортировке я придумать не смог. Пришлось так.

-- 4.число посетителей и кассовые сборы, сгруппированные по времени
-- начала фильма: с 9 до 15, с 15 до 18, с 18 до 21, с 21 до 00:00
-- (сколько посетителей пришло с 9 до 15 часов и т.д.).
WITH table9to15 AS (
    SELECT
        count(tickets.id) as guests,
        sum(cinema.session.price) as money,
        cinema.session.start_time as start_time
    FROM cinema.tickets
             JOIN cinema.session on cinema.session.id = cinema.tickets.session_id
    WHERE start_time > '09:00' AND start_time < '15:00'
    GROUP by start_time
    ORDER by start_time asc),
     table15to18 AS (
         SELECT
             count(tickets.id) as guests,
             sum(cinema.session.price) as money,
             cinema.session.start_time as start_time
         FROM cinema.tickets
                  JOIN cinema.session on cinema.session.id = cinema.tickets.session_id
         WHERE start_time > '15:00' AND start_time < '18:00'
         GROUP by start_time
         ORDER by start_time asc),
     table18to21 AS (
         SELECT
             count(tickets.id) as guests,
             sum(cinema.session.price) as money,
             cinema.session.start_time as start_time
         FROM cinema.tickets
                  JOIN cinema.session on cinema.session.id = cinema.tickets.session_id
         WHERE start_time > '18:00' AND start_time < '21:00'
         GROUP by start_time
         ORDER by start_time asc),
     table21to0 AS (
         SELECT
             count(tickets.id) as guests,
             sum(cinema.session.price) as money,
             cinema.session.start_time as start_time
         FROM cinema.tickets
                  JOIN cinema.session on cinema.session.id = cinema.tickets.session_id
         WHERE start_time > '21:00' AND start_time < '00:00'
         GROUP by start_time
         ORDER by start_time asc)
SELECT '9 to 15' as time, sum(guests) as Total_Guests, sum(money) as Total_money from table9to15
UNION ALL
SELECT '15 to 18' as time, sum(guests) as Total_Guests, sum(money) as Total_money from table15to18
UNION ALL
SELECT '18 to 21' as time, sum(guests) as Total_Guests, sum(money) as Total_money from table18to21
UNION ALL
SELECT '21 to 0' as time, sum(guests) as Total_Guests, sum(money) as Total_money from table21to0;