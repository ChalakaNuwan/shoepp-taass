package taass.shoepp.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taass.shoepp.userservice.entity.Address;
import taass.shoepp.userservice.entity.User;
import taass.shoepp.userservice.model.AddressForm;
import taass.shoepp.userservice.repository.AddressRepository;
import taass.shoepp.userservice.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address getAddress(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesnt exist"));
        return user.getAddress();
    }

    @Override
    public void updateAddress(Long userId, AddressForm addressForm) {
        User user = userRepository.findById(userId).get();

        if (user.getAddress() == null) {
            user.setAddress(new Address());
        }

        user.getAddress().setStreetAddress(addressForm.getStreetAddress());
        user.getAddress().setCity(addressForm.getCity());
        user.getAddress().setCountry(addressForm.getCountry());
        user.getAddress().setZipCode(addressForm.getZipCode());
        addressRepository.save(user.getAddress());
        userRepository.save(user);
    }

    @Override
    public void deleteAddress(Long userId) {
        Address address = getAddress(userId);
        User user = userRepository.findById(userId).get();
        user.setAddress(null);
        userRepository.save(user);
       // addressRepository.deleteById(address.getId());
    }
}
