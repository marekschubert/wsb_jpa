package com.jpacourse.service;

import com.jpacourse.dto.address.AddressTO;

public interface AddressService
{
    public AddressTO findById(final Long id);
}
