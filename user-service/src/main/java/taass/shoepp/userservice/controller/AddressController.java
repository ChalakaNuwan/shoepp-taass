package taass.shoepp.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.shoepp.userservice.entity.Address;
import taass.shoepp.userservice.entity.User;
import taass.shoepp.userservice.model.AddressForm;
import taass.shoepp.userservice.service.AddressService;
import taass.shoepp.userservice.utility.RestUtility;

import static taass.shoepp.userservice.utility.RestUtility.HEADER_AUTH;

@CrossOrigin
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;


   @GetMapping("/user/address")
    public ResponseEntity<Address> getAddress(@RequestHeader(HEADER_AUTH) String tokenHeader) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        Address address = addressService.getAddress(accountId);
        return ResponseEntity.ok(address);
    }

    @PostMapping("/user/address")
    public ResponseEntity<String> addAddress(@RequestHeader(HEADER_AUTH) String tokenHeader, @RequestBody AddressForm addressForm) throws Exception {
       Long accountId = RestUtility.getUserId(tokenHeader);
       addressService.updateAddress(accountId, addressForm);
       return ResponseEntity.ok("Address updated");
    }

    @DeleteMapping("/user/address")
    public ResponseEntity<String> deleteAddress(@RequestHeader(HEADER_AUTH) String tokenHeader) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        addressService.deleteAddress(accountId);
        return ResponseEntity.ok("Address deleted");
    }
}
