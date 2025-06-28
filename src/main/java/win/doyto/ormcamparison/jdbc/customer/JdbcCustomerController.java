package win.doyto.ormcamparison.jdbc.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.query.core.PageList;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * JooqCustomerController
 *
 * @author f0rb on 2025/6/26
 */
@RestController
@RequestMapping("/jdbc/customer")
public class JdbcCustomerController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<CustomerEntity> rowMapper = new BeanPropertyRowMapper<>(CustomerEntity.class);

    @GetMapping("/")
    public PageList<CustomerEntity> query(CustomerQuery query) {
        List<Object> argList = new ArrayList<>();
        String where = buildWhere(query, argList);
        List<CustomerEntity> entities = jdbcTemplate.query("SELECT * FROM customer" + where, rowMapper, argList.toArray());
        int count = jdbcTemplate.queryForObject("SELECT count(0) FROM customer" + where, argList.toArray(), int.class);
        return new PageList<>(entities, count);
    }

    public static String buildWhere(CustomerQuery query, List<Object> argList) {
        StringJoiner where = new StringJoiner(" AND ", " WHERE ", "");
        where.setEmptyValue("");
        if (query.getC_custkey() != null) {
            where.add("c_custkey = ?");
            argList.add(query.getC_custkey());
        }
        if (query.getC_custkeyLt() != null) {
            where.add("c_custkey < ?");
            argList.add(query.getC_custkeyLt());
        }
        if (query.getC_nameLike() != null && !query.getC_nameLike().isBlank()) {
            where.add("c_name LIKE CONCAT('%', ?, '%')");
            argList.add(query.getC_nameLike());
        }
        if (query.getC_nationkey() != null) {
            where.add("c_nationkey = ?");
            argList.add(query.getC_nationkey());
        }
        return where.toString();
    }
}
