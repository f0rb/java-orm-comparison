package win.doyto.ormcamparison.jpa.salary;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/**
 * SalaryRepository
 *
 * @author f0rb on 2025/6/26
 */
@Repository
public interface SalaryRepository extends JpaRepositoryImplementation<SalaryEntity, Long> {
}
