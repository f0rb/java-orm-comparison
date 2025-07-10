package win.doyto.ormcamparison.jooq.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.query.core.PageQuery;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryQuery extends PageQuery {
    private Integer workYear;
    private String jobTitle;
    private BigDecimal salaryInUsdLt;
    private BigDecimal salaryInUsdGt;
    private SalaryQuery salaryOr;
    private SalaryQuery salaryInUsdGtAll;
}
