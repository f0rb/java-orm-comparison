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
    private String salaryCurrency;
    private BigDecimal salaryGt;
    private BigDecimal salaryLt;
    private BigDecimal salaryInUsdLt;
    private BigDecimal salaryInUsdGt;
    private String companySize;
}
