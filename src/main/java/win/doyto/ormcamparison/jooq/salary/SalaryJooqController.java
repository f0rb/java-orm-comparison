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
        if (query.getSalaryCurrency() != null) {
            condition = condition.and(SALARY.SALARY_CURRENCY.eq(query.getSalaryCurrency()));
        }
        if (query.getSalaryLt() != null) {
            condition = condition.and(SALARY.SALARY_.lt(query.getSalaryLt()));
        }
        if (query.getSalaryGt() != null) {
            condition = condition.and(SALARY.SALARY_.gt(query.getSalaryGt()));
        }
        if (query.getSalaryInUsdLt() != null) {
            condition = condition.and(SALARY.SALARY_.lt(query.getSalaryInUsdLt()));
        }
        if (query.getSalaryInUsdGt() != null) {
            condition = condition.and(SALARY.SALARY_.gt(query.getSalaryInUsdGt()));
        }
        if (query.getCompanySize() != null) {
            condition = condition.and(SALARY.COMPANY_SIZE.gt(query.getCompanySize()));
        }
        return condition;
    }
}

