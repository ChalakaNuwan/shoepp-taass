package taass.shoepp.userservice.service;

import taass.shoepp.userservice.entity.Address;
import taass.shoepp.userservice.model.AddressForm;

public interface AddressService {
    Address getAddress(Long userId);

    void deleteAddress(Long userId);

    void updateAddress(Long userId, AddressForm addressForm);
}
