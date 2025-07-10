package win.doyto.ormcamparison.dq.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.query.annotation.Subquery;
import win.doyto.query.core.PageQuery;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryQuery extends PageQuery {
    private Integer workYear;
    private String jobTitle;
    private Double salaryInUsdLt;
    private Double salaryInUsdGt;
    private SalaryQuery salaryOr;
    @Subquery(select = "max(salary_in_usd)", from = SalaryEntity.class)
    private SalaryQuery salaryInUsdGt0;
}
