package com.dbug.checkout.service;

import com.dbug.checkout.exception.ServiceRuntimeException;

public interface Supermarket {

	public int checkOut(String items) throws ServiceRuntimeException;
}
