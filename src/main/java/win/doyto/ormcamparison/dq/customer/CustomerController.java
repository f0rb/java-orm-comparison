package win.doyto.ormcamparison.dq.customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.query.web.controller.AbstractEIQController;

/**
 * JdbcCustomerController
 *
 * @author f0rb on 2023/2/24
 */
@RestController
@RequestMapping("/dq/customer")
public class CustomerController
        extends AbstractEIQController<CustomerEntity, Integer, CustomerQuery> {

    // @GetMapping("/")
    // public PageList<CustomerEntity> page(CustomerQuery query) {
    //     return new PageList<>(service.query(query), service.count(query));
    // }

    // {
    // JdbcDataAccess<CustomerEntity, Integer, CustomerQuery> customerJdbcDataAccess;
    //
    // public CustomerController(DatabaseOperations databaseOperations) {
    //     customerJdbcDataAccess = new JdbcDataAccess<>(databaseOperations, CustomerEntity.class, new CustomerRowMapper());
    // }
    //
    // @GetMapping("/")
    // public List<CustomerEntity> page(CustomerQuery query) {
    //     return customerJdbcDataAccess.page(query).getList();
    // }
}
