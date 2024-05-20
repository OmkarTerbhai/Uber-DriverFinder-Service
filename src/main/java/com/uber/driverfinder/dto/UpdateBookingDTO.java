package com.uber.driverfinder.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingDTO {
    private String bookingId;
    private String driverId;
}
