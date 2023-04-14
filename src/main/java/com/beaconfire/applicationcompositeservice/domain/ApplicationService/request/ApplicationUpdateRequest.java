package com.beaconfire.applicationcompositeservice.domain.ApplicationService.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationUpdateRequest {

    private int id;

    private String status;

    private String comment;
}