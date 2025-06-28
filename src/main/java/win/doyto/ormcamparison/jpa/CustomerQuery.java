package win.doyto.ormcamparison.jpa;

import lombok.Data;
import win.doyto.query.core.PageQuery;

import java.math.BigDecimal;

/**
 * JooqCustomerQuery
 *
 * @author f0rb on 2025/6/26
 */
@Data
public class CustomerQuery extends PageQuery {
    private Integer c_custkey;
    private Integer c_custkeyLt;
    private String c_nameLike;
    private Integer c_nationkey;
    private BigDecimal c_acctbalGt;
}
