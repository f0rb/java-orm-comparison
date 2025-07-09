package win.doyto.ormcamparison.jpa.salary;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.ormcamparison.jpa.AbstractJpaController;

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
            if (query.getSalaryCurrency() != null) {
                predicates.add(cb.equal(root.get("salaryCurrency"), query.getSalaryCurrency()));
            }
            if (query.getSalaryGt() != null) {
                predicates.add(cb.gt(root.get("salary"), query.getSalaryGt()));
            }
            if (query.getSalaryLt() != null) {
                predicates.add(cb.lt(root.get("salary"), query.getSalaryLt()));
            }
            if (query.getSalaryInUsdGt() != null) {
                predicates.add(cb.gt(root.get("salaryInUsd"), query.getSalaryInUsdGt()));
            }
            if (query.getSalaryInUsdLt() != null) {
                predicates.add(cb.lt(root.get("salaryInUsd"), query.getSalaryInUsdLt()));
            }
            if (query.getCompanySize() != null) {
                predicates.add(cb.equal(root.get("companySize"), query.getCompanySize()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
