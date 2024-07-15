package coding.dojo.Pfe.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequest {

    private String userId;
    private long carId;

    LocalDate startDate;

    LocalDate endDate;

    boolean withChouffer;
}
