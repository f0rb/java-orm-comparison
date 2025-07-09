package win.doyto.ormcamparison.jdbc.salary;

import lombok.Data;

@Data
public class SalaryEntity {
    private Long id;
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
