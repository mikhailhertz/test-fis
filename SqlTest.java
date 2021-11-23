import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class SqlTest {
    public static void main(String[] args) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "1234");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from student join student_book on student.id = student_book.student_id")
        ) {
            Map<Integer, Student> map = new HashMap<>();
            int maxBooksTaken = 0;
            Timestamp lowestDate = null;
            int worstOffender = -1;
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String studentName = resultSet.getString("name");
                Timestamp dateOut = resultSet.getTimestamp("date_out");
                Timestamp dateIn = resultSet.getTimestamp("date_in");
                if (dateIn == null) {
                    Student student = map.get(studentId);
                    if (student != null) {
                        if (dateOut.before(student.firstBookLendDate)) {
                            student.firstBookLendDate = dateOut;
                        }
                        student.booksTaken += 1;
                        if (student.booksTaken >= maxBooksTaken) {
                            maxBooksTaken = student.booksTaken;
                            if (lowestDate == null || student.firstBookLendDate.before(lowestDate)) {
                                lowestDate = student.firstBookLendDate;
                                worstOffender = studentId;
                            }
                        }
                    } else {
                        map.put(studentId, new Student(studentName, 1, dateOut));
                    }
                }
            }
            Student malicious = map.get(worstOffender);
            System.out.println("The most malicious reader is " + malicious.name + " with " + malicious.booksTaken +
                    " books not returned since " + new Date(malicious.firstBookLendDate.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
