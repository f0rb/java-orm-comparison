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

import static org.jooq.impl.DSL.noCondition;
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
        List<Salary> Salaries = dslContext.select().from(SALARY)
                .where(buildCondition(query))
                .limit(query.getPageNumber(), query.getPageSize())
                .fetchInto(Salary.class);

        long count = dslContext.fetchCount(SALARY, buildCondition(query));
        return new PageList<>(Salaries, count);
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
        return condition;
    }
}

