import java.sql.Timestamp;

class Student {
    String name;
    int booksTaken;
    Timestamp firstBookLendDate;
    Student(String name, int booksTaken, Timestamp firstBookLendDate) {
        this.name = name;
        this.booksTaken = booksTaken;
        this.firstBookLendDate = firstBookLendDate;
    }
}