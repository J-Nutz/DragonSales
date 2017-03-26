package database.tables;

/*
 * Created by Jonah on 1/3/2017.
 */

import database.DatabaseExecutor;
import jooq.public_.tables.Employees;
import jooq.public_.tables.records.EmployeesRecord;
import mutual.types.Employee;
import mutual.types.Position;
import org.jooq.*;
import org.jooq.util.h2.H2DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public class EmployeesTable
{
    private static final Employees employees = Employees.EMPLOYEES;

    public static boolean addEmployee(Employee employee)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                InsertValuesStep6<EmployeesRecord, String, String, String, String, BigDecimal, Date> insertEmployee =
                        database.insertInto(employees,
                                employees.IMAGE_PATH,
                                employees.NAME,
                                employees.EMAIL,
                                employees.POSITION,
                                employees.WAGE,
                                employees.HIRED_DATE);

                insertEmployee.values(employee.getImagePath().getAbsolutePath(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getPosition().toString(),
                        employee.getWage(),
                        employee.getDateHired());

                int addEmployeeResult = insertEmployee.execute();

                return addEmployeeResult == 1;
            }
        });
    }

    public static boolean fireEmployee(String name)
    {
        return DatabaseExecutor.submitBoolean(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                int employeeFired = database.deleteFrom(employees)
                                            .where(employees.NAME.eq(name))
                                            .execute();

                return employeeFired == 1;
            }
        });
    }

    public static Employee getEmployee(String name)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<EmployeesRecord> fetchedUser =
                    database.selectFrom(employees)
                            .where(employees.NAME.equal(name))
                            .fetch();

                if(fetchedUser.isNotEmpty())
                {
                    Employee employee = new Employee();

                    for (Record r : fetchedUser)
                    {
                        employee.setImagePath(r.get(employees.IMAGE_PATH));
                        employee.setName(r.get(employees.NAME));
                        employee.setEmail(r.get(employees.EMAIL));
                        employee.setPosition(Position.valueOf(r.get(employees.POSITION)));
                        employee.setWage(r.get(employees.WAGE));
                        employee.setDateHired(r.get(employees.HIRED_DATE));
                    }

                    return employee;
                }
                else
                {
                    return null;
                }
            }
        });
    }

    public static ArrayList<Employee> getEmployees()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record> fetchedEmployees =
                        database.select()
                                .from(employees)
                                .fetch();

                ArrayList<Employee> employeeList = new ArrayList<>();

                for (Record r : fetchedEmployees)
                {
                    Employee employee = new Employee();
                    employee.setImagePath(r.get(employees.IMAGE_PATH));
                    employee.setName(r.get(employees.NAME));
                    employee.setEmail(r.get(employees.EMAIL));
                    employee.setPosition(Position.valueOf(r.get(employees.POSITION)));
                    employee.setWage(r.get(employees.WAGE));
                    employee.setDateHired(r.get(employees.HIRED_DATE));

                    employeeList.add(employee);
                }

                return employeeList;
            }
        });
    }

    public static ArrayList<String> getEmployeeNames()
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record1<String>> employeeNamesResult = database.select(employees.NAME).from(employees).fetch();

                ArrayList<String> employeeNames = new ArrayList<>();

                for(Record1 name : employeeNamesResult)
                {
                    employeeNames.add(name.get(employees.NAME));
                }

                return employeeNames;
            }
        });
    }

    public static Position getEmployeePosition(String name)
    {
        return DatabaseExecutor.submitObject(() ->
        {
            try(Connection connection = DatabaseExecutor.getConnection();
                DSLContext database = H2DSL.using(connection))
            {
                Result<Record1<String>> employeePosition =
                        database.select(employees.POSITION)
                                .from(employees).where(employees.NAME.equal(name))
                                .fetch();

                if(employeePosition.isNotEmpty())
                {
                    return Position.asPosition(employeePosition.get(0).value1());
                }
                else
                {
                    return null;
                }
            }
        });
    }
}