package win.doyto.ormcamparison.jooq.salary;

import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.ormcamparison.jooq.tables.pojos.Salary;
import win.doyto.query.core.PageList;

import java.util.List;

import static org.jooq.impl.DSL.*;
import static win.doyto.ormcamparison.jooq.Tables.SALARY;

/**
 * SalaryJooqController
 *
 * @author f0rb on 2025/6/26
 */
@AllArgsConstructor
@RestController
@RequestMapping("/jooq/salary")
public class SalaryJooqController {
    private DSLContext dslContext;
    @GetMapping("/")
    public PageList<Salary> query(SalaryQuery query) {
        List<Salary> salaries = dslContext.select().from(SALARY)
                .where(buildCondition(query))
                .limit(query.getPageNumber(), query.getPageSize())
                .fetchInto(Salary.class);
        long count = dslContext.fetchCount(SALARY, buildCondition(query));
        return new PageList<>(salaries, count);
    }
    public Condition buildCondition(SalaryQuery query) {
        Condition condition = noCondition();
        if (query.getWorkYear() != null) {
            condition = condition.and(SALARY.WORK_YEAR.eq(query.getWorkYear()));
        }
        if (query.getJobTitle() != null) {
            condition = condition.and(SALARY.JOB_TITLE.eq(query.getJobTitle()));
        }
        if (query.getSalaryInUsdLt() != null) {
            condition = condition.and(SALARY.SALARY_IN_USD.lt(query.getSalaryInUsdLt()));
        }
        if (query.getSalaryInUsdGt() != null) {
            condition = condition.and(SALARY.SALARY_IN_USD.gt(query.getSalaryInUsdGt()));
        }
        if (query.getSalaryOr() != null) {
            Condition orCondition = noCondition();
            if (query.getSalaryInUsdLt() != null) {
                orCondition = orCondition.or(SALARY.SALARY_IN_USD.lt(query.getSalaryInUsdLt()));
            }
            if (query.getSalaryInUsdGt() != null) {
                orCondition = orCondition.or(SALARY.SALARY_IN_USD.gt(query.getSalaryInUsdGt()));
            }
            condition = condition.and(orCondition);
        }
        if (query.getSalaryInUsdGt0() != null) {
            Condition subCondition = buildCondition(query.getSalaryInUsdGt0());
            condition = condition.and(SALARY.SALARY_IN_USD.gt(select(max(SALARY.SALARY_IN_USD)).from(SALARY).where(subCondition)));
        }
        return condition;
    }
}

