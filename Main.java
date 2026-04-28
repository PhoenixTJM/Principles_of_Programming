import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.*;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/employee";
        String user = "phoenix";
        String password = "207191";
        ArrayList<employee> employeeList = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM employees";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Function<ResultSet, employee> rsSet = resultSet -> {
                    try{
                       return new employee(resultSet.getDouble("id"),
                               resultSet.getString("name"),
                               resultSet.getDouble("salary"));
                    } catch(SQLException e2){
                        throw new RuntimeException(e2);
                    }
                };
                employeeList.add(rsSet.apply(rs));

            }
            conn.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        System.out.println(employeeList);
        System.out.println();

        /*
        System.out.println("2. print each employee");
        employeeList.stream().forEach(System.out::println);

         */
        System.out.println("3-4. filter by salary above 50000 with predicate");
        Predicate<employee> highEarn = s -> s.salary > 50000;
        List<employee> highEarners = employeeList.stream().filter(highEarn).collect(Collectors.toList());
        System.out.println(highEarners);
        System.out.println();

        System.out.println("5. Write a Function<Employee, Employee> named applyTax to apply a 15% tax reduction");
        Function<employee, employee> applyTax = s -> new employee(s.id, s.name, s.salary *(1 - 0.15));
        List<employee> taxreduce = highEarners.stream().filter(highEarn).map(applyTax).collect(Collectors.toList());
        System.out.println(taxreduce);
        System.out.println();

        System.out.println(("6. Create a Function<Employee, String> named formatSalary to format the salary to 2 decimal places, with $ attached in the front, for example: $52000.30 "));
        Function<employee, String> formatSalary = s -> String.format("%.2f", s.salary); //fix
        List<String> formated = highEarners.stream().filter(highEarn).map(applyTax).map(formatSalary).collect(Collectors.toList());
        System.out.println(formated);






    }
}