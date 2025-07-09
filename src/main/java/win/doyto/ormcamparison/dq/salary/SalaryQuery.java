package win.doyto.ormcamparison.dq.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.query.core.PageQuery;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryQuery extends PageQuery {
    private String salaryCurrency;
    private Double salaryGt;
    private Double salaryLt;
    private Double salaryInUsdLt;
    private Double salaryInUsdGt;
    private String companySize;
}
