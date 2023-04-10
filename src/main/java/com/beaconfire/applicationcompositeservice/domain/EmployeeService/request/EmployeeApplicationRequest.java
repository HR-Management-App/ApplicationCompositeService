package com.beaconfire.applicationcompositeservice.domain.EmployeeService.request;

import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.Address;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.Contact;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.PersonalDocument;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.VisaStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EmployeeApplicationRequest {

    long userID;
    String firstName;
    String lastName;
    String middleName;
    String preferredName;
    //todo: replace this url
    String profilePictureUrl;
//    Address currentAddress;
    String cellPhoneNumber;
    String workPhoneNumber;
    String email;
    String ssn;
    Date dob;
    String gender;
//    VisaStatus visaStatus;
    String driverLicense;
    Date driverLicenseExpiration;
    List<Contact> contactList;
    List<Address> addressList;
    List<VisaStatus> visaStatusList;
    List<PersonalDocument> personalDocumentList;

}
