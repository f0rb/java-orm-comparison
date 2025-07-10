package win.doyto.ormcamparison.jpa.salary;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.ormcamparison.jpa.AbstractJpaController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jpa/salary")
public class SalaryJpaController extends AbstractJpaController<SalaryEntity, Long, SalaryQuery> {
    public SalaryJpaController(SalaryRepository repository) {
        super(repository);
    }
    protected Specification<SalaryEntity> toSpecification(SalaryQuery query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getWorkYear() != null) {
                predicates.add(cb.equal(root.get("workYear"), query.getWorkYear()));
            }
            if (query.getJobTitle() != null) {
                predicates.add(cb.equal(root.get("jobTitle"), query.getJobTitle()));
            }
            if (query.getSalaryInUsdGt() != null) {
                predicates.add(cb.gt(root.get("salaryInUsd"), query.getSalaryInUsdGt()));
            }
            if (query.getSalaryInUsdLt() != null) {
                predicates.add(cb.lt(root.get("salaryInUsd"), query.getSalaryInUsdLt()));
            }
            if (query.getSalaryOr() != null) {
                List<Predicate> orPredicates = new ArrayList<>();
                if (query.getSalaryInUsdGt() != null) {
                    orPredicates.add(cb.gt(root.get("salaryInUsd"), query.getSalaryInUsdGt()));
                }
                if (query.getSalaryInUsdLt() != null) {
                    orPredicates.add(cb.lt(root.get("salaryInUsd"), query.getSalaryInUsdLt()));
                }
                cb.and(cb.or(orPredicates.toArray(new Predicate[0])));
            }
            if (query.getSalaryInUsdGt0() != null) {
                SalaryQuery subqueryData = query.getSalaryInUsdGt0();
                Subquery<BigDecimal> subquery = cq.subquery(BigDecimal.class);
                Root<SalaryEntity> salaryRoot = subquery.from(SalaryEntity.class);
                subquery.select(cb.max(salaryRoot.get("salaryInUsd")))
                        .where(cb.equal(salaryRoot.get("workYear"), subqueryData.getWorkYear()));
                return cb.greaterThan(root.get("salaryInUsd"), subquery);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
