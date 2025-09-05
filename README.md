# Java ORM Code Size and Performance Comparison: DoytoQuery vs SpringDataJPA/SpringJdbc/jOOQ/MyBatis-plus

## TL;DR

**Code Size**: DoytoQuery << SpringDataJPA/MyBatis-plus < jOOQ << SpringJdbc

**Query Performance**: SpringJdbc > DoytoQuery > jOOQ >>> SpringDataJPA > MyBatis-plus

## Introduction

This paper compares the code size and query performance of the Object Query Mapping (OQM) framework **DoytoQuery**
against common ORM frameworks (Spring Data JPA, Spring JDBC, jOOQ, MyBatis-plus) based on several typical dynamic query
scenarios.

## Experiment Design

The experiment aims to evaluate both the development cost and runtime efficiency of DoytoQuery and other mainstream ORM
frameworks in handling typical dynamic query tasks.

### Dataset

The dataset comes from
Kaggle’s [Data Science Salaries](https://www.kaggle.com/datasets/adilshamim8/salaries-for-data-science-jobs), with a
total of 136,757 records.  
Each record contains fields such as job title, salary, experience, employment type, company size, and location, which
are suitable for constructing various complex dynamic query scenarios.

### Frameworks Compared

| Framework           | Description                                                          |
|---------------------|----------------------------------------------------------------------|
| **DoytoQuery**      | OQM framework, automatically builds SQL via declarative query models |
| **Spring Data JPA** | High-level ORM framework built on Hibernate                          |
| **Spring JDBC**     | Low-level access based on JDBC Template                              |
| **jOOQ**            | DSL framework for type-safe SQL construction                         |
| **MyBatis-plus**    | MyBatis enhancement toolkit with CRUD support                        |

### Query Scenarios

To cover different types of query logic, three query tasks were designed:

| ID     | Description                                                                                                                |
|--------|----------------------------------------------------------------------------------------------------------------------------|
| **T1** | Query records from 2025 with salaries between 20,000 and 100,000                                                           |  
| URL    | `/salary/?workYear=2025&salaryInUsdGt=20000&salaryInUsdLt=100000&pageSize=10`                                              |  
| SQL    | `SELECT * FROM salary WHERE work_year = ? AND salary_in_usd < ? AND salary_in_usd > ? LIMIT 10 OFFSET 0`                   |  
| **T2** | Query records with job title = "Researcher", salary < 30,000 OR > 300,000                                                  |  
| URL    | `/salary/?jobTitle=Researcher&or.salaryInUsdLt=30000&or.salaryInUsdGt=300000&pageSize=10`                                  |  
| SQL    | `SELECT * FROM salary WHERE job_title = ? AND (salary_in_usd < ? OR salary_in_usd > ?)`                                    |  
| **T3** | Query records from 2025 with salary greater than the max salary in 2023                                                    |  
| URL    | `/salary/?workYear=2025&salaryInUsdGt0.workYear=2023`                                                                      |  
| SQL    | `SELECT * FROM salary WHERE work_year = ? AND salary_in_usd > (SELECT max(salary_in_usd) FROM salary WHERE work_year = ?)` |

### Unified Query Object

To ensure fairness, all frameworks used the same query object `SalaryQuery` for parameter handling:

```java
public class SalaryQuery extends PageQuery {
  private Integer workYear;
  private String jobTitle;
  private Double salaryInUsdLt;
  private Double salaryInUsdGt;
  private SalaryQuery or;
  private SalaryQuery salaryInUsdGt0;
}
```

### Evaluation Metrics

* **Code Size**: Counted method LOC and total LOC to assess maintainability.
* **Performance**: Measured throughput (ops/s) with JMH to evaluate runtime efficiency.

### Environment

* **Hardware**:

  * CPU: Intel Core i5-13600KF
  * RAM: 16GB DDR5
  * Storage: 1TB SSD
  * GPU: NVIDIA RTX 2060 Super

* **Software**:

  * OS: Windows 11 Pro
  * Database: MySQL 8.3.0
  * JDK: Amazon Corretto 17.0.5
  * Benchmark Tool: JMH 1.37
  * Framework Versions: Spring Boot 3.5.0, Spring Web 6.2.7, etc.

* **Benchmark Config**:

```java

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms256M", "-Xmx2G", "-XX:+UseG1GC"})
@Threads(8)
@Warmup(iterations = 8, time = 1)
@Measurement(iterations = 3, time = 3)
public class ORMBenchmark { /*...*/
}
```

## Results

Abbreviations:

* **DQ**: DoytoQuery
* **JDBC**: Spring JDBC
* **JPA**: Spring Data JPA
* **MP**: MyBatis-Plus

### Code Size Comparison

![Code size comparison across frameworks](/assets/df4e2628-c710-4fa5-8a87-d52e1dd67d88.png)

**Analysis**:

* DoytoQuery, using declarative query models, **requires no manual dynamic query construction methods**, resulting in
  much lower code size.
* ORM frameworks need imperative SQL construction, leading to similar code size overhead.

If full CRUD APIs are required:

* Spring JDBC needs manual SQL concatenation → largest code size.
* Spring Data JPA, jOOQ, and MyBatis-Plus provide CRUD helpers → moderate size.
* DoytoQuery fully automates SQL building → smallest size.

Overall, **DoytoQuery demonstrates superior code reuse and simplicity**.

### Performance Comparison

![Throughput (ops/s) for T1–T3](/assets/adad0f8b-3d6a-4f6c-bea6-a629523007e3.png)

**Analysis**:

* Spring JDBC achieves the best performance via raw SQL concatenation.
* DoytoQuery and jOOQ follow closely.
* Spring Data JPA and MyBatis-Plus show the weakest performance.

## Conclusion

The experiments show that **the OQM approach, by automatically constructing SQL through declarative query models,
achieves much smaller code size than ORM frameworks**.
Meanwhile, its runtime performance is close to Spring JDBC and outperforms other ORM frameworks, validating OQM’s
practicality and advantages in real-world applications.

========

## About this repo

Table structure: [schema.sql](src/main/resources/schema.sql)

Dataset: [salaries.csv](src/main/resources/salaries.csv)

Database connection: [application-mysql8.yml](src/test/resources/application-mysql8.yml)

Run benchmark: [ORMBenchmark#main()](src/test/java/win/doyto/ormcamparison/benchmark/ORMBenchmark.java).
