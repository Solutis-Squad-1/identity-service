package br.com.solutis.squad1.identityservice.model.repository;

import br.com.solutis.squad1.identityservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
