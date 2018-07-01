package com.chriniko.example.customers.control;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class CustomerNotFound extends RuntimeException {
}
