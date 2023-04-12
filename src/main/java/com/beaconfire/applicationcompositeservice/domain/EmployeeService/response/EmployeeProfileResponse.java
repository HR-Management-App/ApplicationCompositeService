package com.beaconfire.applicationcompositeservice.domain.EmployeeService.response;

import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.Address;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.Contact;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.PersonalDocument;
import com.beaconfire.applicationcompositeservice.domain.EmployeeService.domain.VisaStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileResponse {

    // to get response from core service
    String firstName;
    String lastName;
    String middleName;
    String preferredName;
    String profilePictureUrl;
    String email;
    String ssn;
    Date dob;
    String gender;
    Address currentAddress;
    //contact info
    String cellPhoneNumber;
    String workPhoneNumber;
    VisaStatus visaStatus;
    String driverLicense;
    Date driverLicenseExpiration;
    List<Contact> contactList;
    //todo: update list of docuemnts url
    List<PersonalDocument> personalDocumentList;
}
