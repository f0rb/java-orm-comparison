package win.doyto.ormcamparison.dq.salary;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.query.web.controller.AbstractEIQController;

@RestController
@RequestMapping("/dq/salary")
public class SalaryController extends AbstractEIQController<SalaryEntity, Long, SalaryQuery> {
}
