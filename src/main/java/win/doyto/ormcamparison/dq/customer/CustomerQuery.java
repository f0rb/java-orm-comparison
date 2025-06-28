package win.doyto.ormcamparison.dq.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.query.core.PageQuery;

import java.math.BigDecimal;

/**
 * JooqCustomerQuery
 *
 * @author f0rb on 2025/6/27
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerQuery extends PageQuery {
    private Integer c_custkey;
    private Integer c_custkeyLt;
    private String c_nameLike;
    private Integer c_nationkey;
    private BigDecimal c_acctbalGt;
}
