package win.doyto.ormcamparison.jdbc.salary;

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
 * SalaryJdbcController
 *
 * @author f0rb on 2025/6/26
 */
@RestController
@RequestMapping("/jdbc/salary")
public class SalaryJdbcController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<SalaryEntity> rowMapper = new BeanPropertyRowMapper<>(SalaryEntity.class);

    @GetMapping("/")
    public PageList<SalaryEntity> query(SalaryQuery query) {
        List<Object> argList = new ArrayList<>();
        String where = buildWhere(query, argList);
        String page = " LIMIT " + query.getPageSize() + " OFFSET " + query.getPageNumber() * query.getPageSize();
        List<SalaryEntity> entities = jdbcTemplate.query("SELECT id, work_year, experience_level, employment_type, job_title, salary, salary_currency, salary_in_usd, employee_residence, remote_ratio, company_location, company_size FROM salary" + where + page, rowMapper, argList.toArray());
        long count = jdbcTemplate.queryForObject("SELECT count(0) FROM salary" + where, argList.toArray(), long.class);
        return new PageList<>(entities, count);
    }

    public String buildWhere(SalaryQuery query, List<Object> argList) {
        StringJoiner where = new StringJoiner(" AND ", " WHERE ", "");
        where.setEmptyValue("");
        if (query.getSalaryCurrency() != null) {
            where.add("salary_currency = ?");
            argList.add(query.getSalaryCurrency());
        }
        if (query.getSalaryLt() != null) {
            where.add("salary < ?");
            argList.add(query.getSalaryLt());
        }
        if (query.getSalaryGt() != null) {
            where.add("salary > ?");
            argList.add(query.getSalaryGt());
        }
        if (query.getSalaryInUsdLt() != null) {
            where.add("salary_in_usd < ?");
            argList.add(query.getSalaryInUsdLt());
        }
        if (query.getSalaryInUsdGt() != null) {
            where.add("salary_in_usd > ?");
            argList.add(query.getSalaryInUsdGt());
        }
        if (query.getCompanySize() != null) {
            where.add("company_size > ?");
            argList.add(query.getCompanySize());
        }
        return where.toString();
    }
}
