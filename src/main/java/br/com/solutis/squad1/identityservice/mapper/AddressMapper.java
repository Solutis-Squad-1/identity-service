package br.com.solutis.squad1.identityservice.mapper;

import br.com.solutis.squad1.identityservice.dto.AddressPutDto;
import br.com.solutis.squad1.identityservice.model.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address putDtoToEntity(AddressPutDto addressPutDto);
}