package win.doyto.ormcamparison.jpa;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * JooqCustomerController
 *
 * @author f0rb on 2025/6/26
 */
@AllArgsConstructor
@RestController
@RequestMapping("/jpa/customer")
public class JpaCustomerController {
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public PagedModel<CustomerEntity> query(CustomerQuery query) {
        Specification<CustomerEntity> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getC_custkey() != null) {
                predicates.add(cb.equal(root.get("c_custkey"), query.getC_custkey()));
            }
            if (query.getC_custkeyLt() != null) {
                predicates.add(cb.lt(root.get("c_custkey"), query.getC_custkeyLt()));
            }
            if (query.getC_nameLike() != null && !query.getC_nameLike().isEmpty()) {
                predicates.add(cb.like(root.get("c_name"), "%" + query.getC_nameLike() + "%"));
            }
            if (query.getC_acctbalGt() != null) {
                predicates.add(cb.gt(root.get("c_acctbal"), query.getC_acctbalGt()));
            }
            if (query.getC_nationkey() != null) {
                predicates.add(cb.equal(root.get("c_nationkey"), query.getC_nationkey()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = Pageable.ofSize(10);
        return new PagedModel<>(customerRepository.findAll(spec, pageable));
    }
}
