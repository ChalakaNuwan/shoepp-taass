package taass.shoepp.userservice.model;

import javax.validation.constraints.NotBlank;

public class AddressForm {
    @NotBlank
    private String streetAddress;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String zipCode;

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }
}
