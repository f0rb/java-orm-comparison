package win.doyto.ormcamparison.jdbc.salary;

import jakarta.annotation.Resource;
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
    @Resource
    private JdbcTemplate jdbcTemplate;
    private RowMapper<SalaryEntity> rowMapper = new BeanPropertyRowMapper<>(SalaryEntity.class);
    @GetMapping("/")
    public PageList<SalaryEntity> page(SalaryQuery query) {
        List<Object> argList = new ArrayList<>();
        String where = buildWhere(query, argList);
        String page = " LIMIT " + query.getPageSize() + " OFFSET " + query.getPageNumber() * query.getPageSize();
        String sql = "SELECT id, work_year, experience_level, employment_type, job_title, salary, salary_currency, salary_in_usd, employee_residence, remote_ratio, company_location, company_size FROM salary" + where + page;
        List<SalaryEntity> entities = jdbcTemplate.query(sql, rowMapper, argList.toArray());
        long count = jdbcTemplate.queryForObject("SELECT count(0) FROM salary" + where, long.class, argList.toArray());
        return new PageList<>(entities, count);
    }
    public String buildWhere(SalaryQuery query, List<Object> argList) {
        return buildWhere(query, argList, " AND ", " WHERE ", "");
    }
    public String buildWhere(SalaryQuery query, List<Object> argList, String delimiter, String prefix, String suffix) {
        StringJoiner where = new StringJoiner(delimiter, prefix, suffix);
        where.setEmptyValue("");
        if (query.getWorkYear() != null) {
            where.add("work_year = ?");
            argList.add(query.getWorkYear());
        }
        if (query.getJobTitle() != null) {
            where.add("job_title = ?");
            argList.add(query.getJobTitle());
        }
        if (query.getSalaryInUsdLt() != null) {
            where.add("salary_in_usd < ?");
            argList.add(query.getSalaryInUsdLt());
        }
        if (query.getSalaryInUsdGt() != null) {
            where.add("salary_in_usd > ?");
            argList.add(query.getSalaryInUsdGt());
        }
        if (query.getSalaryOr() != null) {
            where.add(buildWhere(query.getSalaryOr(), argList, " OR ", "(", ")"));
        }
        if (query.getSalaryInUsdGt0() != null) {
            String condition = "salary_in_usd > (SELECT max(salary_in_usd) FROM salary";
            where.add(condition + buildWhere(query.getSalaryInUsdGt0(), argList, " AND ", " WHERE ", ")"));
        }
        return where.toString();
    }
}
