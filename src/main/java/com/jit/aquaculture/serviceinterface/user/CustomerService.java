package com.jit.aquaculture.serviceinterface.user;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.user.Customer;
import com.jit.aquaculture.dto.CustomerDto;

public interface CustomerService {
    Customer insertCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Boolean deleteCustomer(String ids);
    CustomerDto getOne(String username);
    PageVO<CustomerDto> getAll(PageQO pageQO);
}
