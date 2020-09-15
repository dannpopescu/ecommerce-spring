package com.danpopescu.shop.repository;

import com.danpopescu.shop.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends CrudRepository<Address, UUID> {
}
