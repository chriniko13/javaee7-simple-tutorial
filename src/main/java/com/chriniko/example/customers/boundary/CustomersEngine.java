package com.chriniko.example.customers.boundary;

import com.chriniko.example.customers.control.CustomerNotFound;
import com.chriniko.example.customers.domain.Customer;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

@Stateless
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class CustomersEngine {

    @Inject
    private EntityManager em;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public Customer get(String id) {
        long idL = Long.parseLong(id);
        return em
                .createQuery("select c from Customer c where c.id = :id", Customer.class)
                .setParameter("id", idL)
                .getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Collection<Customer> getAll() {
        return em.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void create(Customer customer) {
        em.persist(customer);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void delete(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) em.remove(customer);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void patch(Customer newCustInfo) {

        Customer oldCustInfo = em.find(Customer.class, newCustInfo.getId());
        if (oldCustInfo == null) {
            throw new CustomerNotFound();
        }

        Customer customer = calculateDiff(newCustInfo, oldCustInfo);
        em.merge(customer);
    }

    private final BiFunction<String, String, String> diffCalc =
            (newInfo, oldInfo) -> Optional.ofNullable(newInfo)
                    .filter(s -> !s.isEmpty())
                    .orElse(oldInfo);

    private Customer calculateDiff(Customer newCustInfo, Customer oldCustInfo) {

        String surname = diffCalc.apply(newCustInfo.getSurname(), oldCustInfo.getSurname());
        String initials = diffCalc.apply(newCustInfo.getInitials(), oldCustInfo.getInitials());
        String firstname = diffCalc.apply(newCustInfo.getFirstname(), oldCustInfo.getFirstname());

        oldCustInfo.setSurname(surname);
        oldCustInfo.setInitials(initials);
        oldCustInfo.setFirstname(firstname);

        return oldCustInfo;
    }

}
