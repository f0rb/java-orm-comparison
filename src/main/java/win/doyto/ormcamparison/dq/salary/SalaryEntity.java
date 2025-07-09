package win.doyto.ormcamparison.dq.salary;

import lombok.Data;
import win.doyto.query.entity.AbstractPersistable;

@Data
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
