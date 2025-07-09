CREATE SCHEMA IF NOT EXISTS demo;
SET SCHEMA demo;

DROP TABLE IF EXISTS salary;
CREATE TABLE salary (
    id INT AUTO_INCREMENT PRIMARY KEY,
    work_year INT NOT NULL,
    experience_level CHAR(2) NOT NULL COMMENT 'MI=Mid, SE=Senior, EN=Entry, EX=Executive',
    employment_type CHAR(2) NOT NULL COMMENT 'FT=Full-time, PT=Part-time, CT=Contract, FL=Freelance',
    job_title VARCHAR(100) NOT NULL,
    salary DECIMAL(12, 2) NOT NULL,
    salary_currency CHAR(3) NOT NULL,
    salary_in_usd DECIMAL(12, 2) NOT NULL,
    employee_residence CHAR(2) NOT NULL,
    remote_ratio INT NOT NULL COMMENT '0=No remote, 50=Hybrid, 100=Fully remote',
    company_location CHAR(2) NOT NULL,
    company_size CHAR(1) NOT NULL COMMENT 'S=Small, M=Medium, L=Large',
    INDEX (job_title),
    INDEX (salary_in_usd),
    INDEX (salary_currency,salary)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO salary(work_year, experience_level, employment_type, job_title, salary, salary_currency, salary_in_usd, employee_residence, remote_ratio, company_location, company_size)(
    SELECT * FROM CSVREAD('classpath:/salaries.csv', 'work_year,experience_level,employment_type,job_title,salary,salary_currency,salary_in_usd,employee_residence,remote_ratio,company_location,company_size', 'charset=UTF-8 fieldSeparator=,')
) OFFSET 1 ROWS;

