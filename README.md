# 1. ER диаграмма
![erd](https://user-images.githubusercontent.com/92732643/143015118-ddabf051-233f-4271-a43f-a45af859730d.png)
**Сущность: книга**
- varchar name - название книги
- int count - количество экземпляров книги на учёте библиотеки  

**Сущность: автор**
- varchar name - имя автора
- Связь "многие-ко-многим" с книгой  

**Сущность: издательство**
- varchar name - название издательства
- Связь "многие-ко-многим" с книгой  

**Сущность: студент**
- varchar name - имя студента
- Связь "многие-ко-многим" с книгой, так как одни и те же книги хранятся в одной строке, а не в нескольких  

Промежуточная таблица студент-книга хранит в себе дату выдачи и возврата книги.  
# 2. SQL-запрос, возвращающий самого популярного автора за год
Схема базы данных находится в файле sql.txt.  
Запрос из задания приведен ниже:  
```sql
-- most popular author in a year
select author.name from author
	join author_book
		on author.id = author_book.author_id
	join student_book
		on author_book.book_id = student_book.book_id
		and localtimestamp - student_book.date_out < '1 year'
	group by author.name
	order by count(author.name) desc 
	limit 1;
  ```
# 3. Злостный читатель
Злостный читатель - читатель, который не возвращает взятые им книги.  
Алгоритм поиска самого злостного читателя: находим читателя с самым большим числом несданных книг. Если таких читателей несколько - выбираем того, за кем долг числится дольше всего.  
Функция реализована в файле SqlTest.java (к сожалению, пока что не на js).  
