# UC-1-create payroll service database.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sakila             |
| sys                |
| world              |
+--------------------+
6 rows in set (0.01 sec)

mysql> USE payroll_service;
mysql> CREATE DATABASE payroll_service;
Query OK, 1 row affected (0.02 sec)

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| payroll_service    |
| performance_schema |
| sakila             |
| sys                |
| world              |
+--------------------+
7 rows in set (0.00 sec)

mysql> USE payroll_service;
Database changed
mysql> SELECT DATABASE();
+-----------------+
| DATABASE()      |
+-----------------+
| payroll_service |
+-----------------+
1 row in set (0.00 sec)


# UC-2-CreateEmployeePayrollDatabase

mysql> CREATE TABLE employee_payroll
    -> (
    -> id               INT unsigned NOT NULL AUTO_INCREMENT,
    -> name             VARCHAR(150) NOT NULL,
    -> salary           Double NOT NULL,
    -> start            DATE NOT NULL,
    -> PRIMARY KEY      (id)
    -> );
Query OK, 0 rows affected (0.05 sec)

mysql> DESCRIBE employee_payroll;
+--------+--------------+------+-----+---------+----------------+
| Field  | Type         | Null | Key | Default | Extra          |
+--------+--------------+------+-----+---------+----------------+
| id     | int unsigned | NO   | PRI | NULL    | auto_increment |
| name   | varchar(150) | NO   |     | NULL    |                |
| salary | double       | NO   |     | NULL    |                |
| start  | date         | NO   |     | NULL    |                |
+--------+--------------+------+-----+---------+----------------+
4 rows in set (0.01 sec)

# UC-3-Insert data into employee_payroll database

mysql> INSERT INTO employee_payroll ( name, salary, start) VALUES
    -> ( 'Bill', 1000000.00, '2018-01-03' ),
    -> ( 'Terisa', 2000000.00, '2019-11-13' ),
    -> ( 'Charlie', 3000000.00, '2020-05-21' );
Query OK, 3 rows affected (0.03 sec)
Records: 3  Duplicates: 0  Warnings: 0

# UC-4-Retrieve employee_payroll data

mysql> SELECT * FROM employee_payroll;
+----+---------+---------+------------+
| id | name    | salary  | start      |
+----+---------+---------+------------+
|  1 | Bill    | 1000000 | 2018-01-03 |
|  2 | Terisa  | 2000000 | 2019-11-13 |
|  3 | Charlie | 3000000 | 2020-05-21 |
+----+---------+---------+------------+
3 rows in set (0.01 sec)

# UC-5-Retrieve salary data for perticular name as well as the given date range


mysql> SELECT * FROM employee_payroll
    -> WHERE start BETWEEN CAST('2019-01-01' AS DATE) AND DATE(NOW());
+----+---------+---------+------------+
| id | name    | salary  | start      |
+----+---------+---------+------------+
|  2 | Terisa  | 2000000 | 2019-11-13 |
|  3 | Charlie | 3000000 | 2020-05-21 |
+----+---------+---------+------------+
2 rows in set (0.01 sec)

mysql> SELECT salary FROM employee_payroll WHERE name = "Bill";
+---------+
| salary  |
+---------+
| 1000000 |
+---------+
1 row in set (0.01 sec)

# UC-6-update gender of the employee_payroll data.

mysql> pdate employee_payroll set gender = 'M' WHERE name = 'Bill' or name = 'Charlie';
Query OK, 2 rows affected (0.03 sec)
Rows matched: 2  Changed: 2  Warnings: 0

mysql> SELECT *FROM employee_payroll;
+----+---------+--------+---------+------------+
| id | name    | gender | salary  | start      |
+----+---------+--------+---------+------------+
|  1 | Bill    | M      | 1000000 | 2018-01-03 |
|  2 | Terisa  | F      | 2000000 | 2019-11-13 |
|  3 | Charlie | M      | 3000000 | 2020-05-21 |
+----+---------+--------+---------+------------+
3 rows in set (0.01 sec)

# UC-7-find min max avg and number of male and female employee.

mysql> SELECT AVG(basic_pay) FROM employee_payroll WHERE gender = 'M' GROUP BY gender;
+----------------+
| AVG(basic_pay) |
+----------------+
|        2000000 |
+----------------+
1 row in set (0.01 sec)

mysql> SELECT AVG(basic_pay) FROM employee_payroll GROUP BY gender;
+----------------+
| AVG(basic_pay) |
+----------------+
|        2000000 |
|        3000000 |
+----------------+
2 rows in set (0.00 sec)

mysql> SELECT gender, AVG(basic_pay) FROM employee_payroll GROUP BY gender;
+--------+----------------+
| gender | AVG(basic_pay) |
+--------+----------------+
| M      |        2000000 |
| F      |        3000000 |
+--------+----------------+
2 rows in set (0.00 sec)

mysql> SELECT gender, COUNT(name) FROM employee_payroll GROUP BY gender;
+--------+-------------+
| gender | COUNT(name) |
+--------+-------------+
| M      |           2 |
| F      |           1 |
+--------+-------------+
2 rows in set (0.00 sec)

mysql> SELECT gender, SUM(basic_pay) FROM employee_payroll GROUP BY gender;
+--------+----------------+
| gender | SUM(basic_pay) |
+--------+----------------+
| M      |        4000000 |
| F      |        3000000 |
+--------+----------------+
2 rows in set (0.00 sec)

mysql> SELECT gender, MAX(basic_pay) FROM employee_payroll GROUP BY gender;
+--------+----------------+
| gender | MAX(basic_pay) |
+--------+----------------+
| M      |        3000000 |
| F      |        3000000 |
+--------+----------------+
2 rows in set (0.01 sec)

mysql> SELECT gender, MIN(basic_pay) FROM employee_payroll GROUP BY gender;
+--------+----------------+
| gender | MIN(basic_pay) |
+--------+----------------+
| M      |        1000000 |
| F      |        3000000 |
+--------+----------------+
2 rows in set (0.00 sec)

# UC-8-add phone number, address, department.

mysql> DESCRIBE employee_payroll;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| id           | int unsigned | NO   | PRI | NULL    | auto_increment |
| name         | varchar(150) | NO   |     | NULL    |                |
| department   | varchar(250) | YES  |     | NULL    |                |
| address      | varchar(250) | YES  |     | PUNE    |                |
| phone_number | varchar(250) | YES  |     | NULL    |                |
| gender       | char(1)      | YES  |     | NULL    |                |
| basic_pay    | double       | NO   |     | NULL    |                |
| deductions   | double       | NO   |     | NULL    |                |
| taxable_pay  | double       | NO   |     | NULL    |                |
| tax          | double       | NO   |     | NULL    |                |
| net_pay      | double       | NO   |     | NULL    |                |
| start        | date         | NO   |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+
12 rows in set (0.01 sec)

# UC-10-Ability to make Terissa as part of Sales and Marketing Department

mysql> SELECT * FROM employee_payroll;
+----+---------+------------+---------+--------------+--------+-----------+------------+-------------+--------+---------+------------+
| id | name    | department | address | phone_number | gender | basic_pay | deductions | taxable_pay | tax    | net_pay | start      |
+----+---------+------------+---------+--------------+--------+-----------+------------+-------------+--------+---------+------------+
|  1 | Bill    | NULL       | NULL    | NULL         | M      |   1000000 |          0 |           0 |      0 |       0 | 2018-01-03 |
|  2 | Terisa  | Sales      | NULL    | NULL         | F      |   3000000 |          0 |           0 |      0 |       0 | 2019-11-13 |
|  3 | Charlie | NULL       | NULL    | NULL         | M      |   3000000 |          0 |           0 |      0 |       0 | 2020-05-21 |
|  4 | Mark    | TBD        | PUNE    | NULL         | M      |   1000000 |          0 |           0 |      0 |       0 | 2018-01-03 |
|  5 | Terisa  | Marketting | PUNE    | NULL         | F      |   3000000 |     100000 |      200000 | 500000 | 1500000 | 2018-01-02 |
+----+---------+------------+---------+--------------+--------+-----------+------------+-------------+--------+---------+------------+
5 rows in set (0.00 sec)

mysql> UPDATE employee_payroll
    -> SET address = 'Mumbai'
    -> WHERE department = 'Marketting';
Query OK, 1 row affected (0.03 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> SELECT * FROM employee_payroll;
+----+---------+------------+---------+--------------+--------+-----------+------------+-------------+--------+---------+------------+
| id | name    | department | address | phone_number | gender | basic_pay | deductions | taxable_pay | tax    | net_pay | start      |
+----+---------+------------+---------+--------------+--------+-----------+------------+-------------+--------+---------+------------+
|  1 | Bill    | NULL       | NULL    | NULL         | M      |   1000000 |          0 |           0 |      0 |       0 | 2018-01-03 |
|  2 | Terisa  | Sales      | NULL    | NULL         | F      |   3000000 |          0 |           0 |      0 |       0 | 2019-11-13 |
|  3 | Charlie | NULL       | NULL    | NULL         | M      |   3000000 |          0 |           0 |      0 |       0 | 2020-05-21 |
|  4 | Mark    | TBD        | PUNE    | NULL         | M      |   1000000 |          0 |           0 |      0 |       0 | 2018-01-03 |
|  5 | Terisa  | Marketting | Mumbai  | NULL         | F      |   3000000 |     100000 |      200000 | 500000 | 1500000 | 2018-01-02 |
+----+---------+------------+---------+--------------+--------+-----------+------------+-------------+--------+---------+------------+
5 rows in set (0.01 sec)

# UC-11-Emplement ER diagram into payroll_servicr DB.

mysql> CREATE TABLE company(
    -> company_id INT NOT NULL,
    -> company_name VARCHAR(30) NOT NULL,
    -> PRIMARY KEY(company_id));
Query OK, 0 rows affected (0.07 sec)

 INSERT INTO company VALUES(1,'Facebook'),(2,'Google'),(3,'Apple');
Query OK, 3 rows affected (0.02 sec)
Records: 3  Duplicates: 0  Warnings: 0


mysql> CREATE TABLE department(
    -> dept_id INT NOT NULL,
    -> dept_name VARCHAR(50) NOT NULL,
    -> PRIMARY KEY(dept_id));
Query OK, 0 rows affected (0.08 sec)

mysql> INSERT INTO department VALUES (201,'Sales'),(202,'Marketing'),(203,'Logistics'),(204,'Management');
Query OK, 4 rows affected (0.02 sec)
Records: 4  Duplicates: 0  Warnings: 0


mysql> CREATE TABLE employee(
    -> id INT NOT NULL,
    -> company_id INT NOT NULL,
    -> employee_name VARCHAR(30) NOT NULL,
    -> phone_number VARCHAR(30) NOT NULL,
    -> address VARCHAR(250) NOT NULL DEFAULT 'TBD',
    -> gender VARCHAR(1) NOT NULL,
    -> PRIMARY KEY(id),
    -> CONSTRAINT FK FOREIGN KEY(company_id) REFERENCES company(company_id));
Query OK, 0 rows affected (0.07 sec)

mysql> INSERT INTO employee VALUES 
(101,1,'Bill','9876543210','California','M'),
(102,1,'Terisa','8876543211','San Francisco','F'),
(103,2,'Charlie','7876543212','New York','M'),
(104, 3, 'Mark', '8811123568', 'GoldenCity', 'M');

mysql> CREATE TABLE employee_department(
    ->  emp_id INT NOT NULL,
    -> dept_id INT NOT NULL,
    -> PRIMARY KEY (emp_id,dept_id),
    -> FOREIGN KEY (dept_id) REFERENCES department (dept_id),
    -> FOREIGN KEY (emp_id) REFERENCES employee (id),
    -> FOREIGN KEY (dept_id) REFERENCES department (dept_id)
    -> );
Query OK, 0 rows affected (0.12 sec)


mysql> CREATE TABLE payroll (
    ->  emp_id INT DEFAULT NULL,
    -> basic_pay DOUBLE NOT NULL,
    -> deductions DOUBLE NOT NULL,
    -> taxable_pay DOUBLE NOT NULL,
    -> tax DOUBLE NOT NULL,
    -> net_pay DOUBLE NOT NULL
    -> );
Query OK, 0 rows affected (0.06 sec)

mysql> INSERT INTO payroll VALUES (101,50000,5000,45000,5000,40000),(102,20000,2000,18000,3000,15000),(103,60000,6000,54000,4000,50000);
Query OK, 3 rows affected (0.02 sec)
Records: 3  Duplicates: 0  Warnings: 0

#UC-12-Ensure all retrieve queries done especially in UC 4, UC 5 and UC 7 are working with new table structure.

mysql> SELECT * FROM company;
+------------+--------------+
| company_id | company_name |
+------------+--------------+
|          1 | Facebook     |
|          2 | Google       |
|          3 | Apple        |
+------------+--------------+
3 rows in set (0.00 sec)

mysql> DESCRIBE employee;
+---------------+--------------+------+-----+---------+-------+
| Field         | Type         | Null | Key | Default | Extra |
+---------------+--------------+------+-----+---------+-------+
| id            | int          | NO   | PRI | NULL    |       |
| company_id    | int          | NO   | MUL | NULL    |       |
| employee_name | varchar(30)  | NO   |     | NULL    |       |
| phone_number  | varchar(30)  | NO   |     | NULL    |       |
| address       | varchar(250) | NO   |     | TBD     |       |
| gender        | varchar(1)   | NO   |     | NULL    |       |
+---------------+--------------+------+-----+---------+-------+
6 rows in set (0.02 sec)

mysql> SELECT * FROM employee;
+-----+------------+---------------+--------------+---------------+--------+
| id  | company_id | employee_name | phone_number | address       | gender |
+-----+------------+---------------+--------------+---------------+--------+
| 101 |          1 | Bill          | 9876543210   | California    | M      |
| 102 |          1 | Terisa        | 8876543211   | San Francisco | F      |
| 103 |          2 | Charlie       | 7876543212   | New York      | M      |
+-----+------------+---------------+--------------+---------------+--------+
3 rows in set (0.01 sec)

mysql> SELECT * FROM department;
+---------+------------+
| dept_id | dept_name  |
+---------+------------+
|     201 | Sales      |
|     202 | Marketing  |
|     203 | Logistics  |
|     204 | Management |
+---------+------------+
4 rows in set (0.00 sec)

mysql> SELECT MIN(p.net_pay) FROM employee e left join payroll p on  p.emp_id = e.id GROUP BY e.gender;
+----------------+
| MIN(p.net_pay) |
+----------------+
|          40000 |
|          15000 |
+----------------+
2 rows in set (0.00 sec)
