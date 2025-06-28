package win.doyto.ormcamparison.jooq.customer;

import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.query.core.PageList;

import java.util.List;

import static org.jooq.impl.DSL.noCondition;
import static win.doyto.ormcamparison.jooq.Tables.CUSTOMER;

/**
 * JooqCustomerController
 *
 * @author f0rb on 2025/6/26
 */
@AllArgsConstructor
@RestController
@RequestMapping("/jooq/customer")
public class JooqCustomerController {
    private DSLContext dslContext;

    @GetMapping("/")
    public PageList<Customer> query(JooqCustomerQuery query) {
        List<Customer> customers = dslContext.selectFrom(CUSTOMER)
                                             .where(buildCondition(query))
                                             .limit((Number) null, 10)
                                             .fetchInto(Customer.class);

        long count = dslContext.fetchCount(CUSTOMER, buildCondition(query));
        return new PageList<>(customers, count);
    }

    public Condition buildCondition(JooqCustomerQuery query) {
        Condition condition = noCondition();

        if (query.getC_custkey() != null) {
            condition = condition.and(CUSTOMER.C_CUSTKEY.eq(query.getC_custkey()));
        }
        if (query.getC_custkeyLt() != null) {
            condition = condition.and(CUSTOMER.C_CUSTKEY.lt(query.getC_custkeyLt()));
        }
        if (query.getC_nameLike() != null && !query.getC_nameLike().isEmpty()) {
            condition = condition.and(CUSTOMER.C_NAME.like("%" + query.getC_nameLike() + "%"));
        }
        if (query.getC_acctbalGt() != null) {
            condition = condition.and(CUSTOMER.C_ACCTBAL.gt(query.getC_acctbalGt()));
        }
        if (query.getC_nationkey() != null) {
            condition = condition.and(CUSTOMER.C_NATIONKEY.eq(query.getC_nationkey()));
        }

        return condition;
    }
}

