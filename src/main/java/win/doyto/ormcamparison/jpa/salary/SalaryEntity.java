package win.doyto.ormcamparison.jpa.salary;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@Entity
@Table(name = "salary")
public class SalaryEntity extends AbstractPersistable<Long> {
    private Integer workYear;
    private String experienceLevel;
    private String employmentType;
    private String jobTitle;
    private Double salary;
    private String salaryCurrency;
    private Double salaryInUsd;
    private String employeeResidence;
    private Integer remoteRatio;
    private String companyLocation;
    private String companySize;
}
