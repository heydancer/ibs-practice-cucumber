# language: ru

@all
@jdbc
Функция: Тестирование функциональности добавления нового товара в базу данных Spring JDBC

  Предыстория: Проверка таблицы в базе данных
    Дано Выполнить проверку таблицы "FOOD" в базе данных

  Сценарий: Добавление товара в базу данных
    Когда Добавляется товар в базу данных. Наименование: "Дуриан", тип: "FRUIT", экзотик статус: true
    Тогда Товар успешно добавляется в базу данных
    И Товар соответствует ожидаемым значениям
    Затем Товар удаляется из базы данных
    И Товар успешно удален из базы данных