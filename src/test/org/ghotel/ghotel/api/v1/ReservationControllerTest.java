package org.ghotel.ghotel.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ghotel.ghotel.application.ReservationFacade;
import org.ghotel.ghotel.dto.request.ReservationRequestDTO;
import org.ghotel.ghotel.dto.response.ReservationResponseDTO;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Reservation;
import org.ghotel.ghotel.mapper.CustomerMapper;
import org.ghotel.ghotel.mapper.ReservationMapper;
import org.ghotel.ghotel.mapper.RoomMapper;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.ghotel.ghotel.repository.ReservationRepository;
import org.ghotel.ghotel.repository.RoomRepository;
import org.ghotel.ghotel.service.customer.CustomerService;
import org.ghotel.ghotel.service.reservation.ReservationService;
import org.ghotel.ghotel.service.room.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    RoomRepository roomRepository;

    @Spy
    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Spy
    ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    @Spy
    RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @InjectMocks
    CustomerService customerService;

    @InjectMocks
    ReservationService reservationService;

    @InjectMocks
    RoomService roomService;

    @InjectMocks
    ReservationFacade reservationFacade;

    @InjectMocks
    ReservationsController reservationController;

    OffsetDateTime nowInSofia = OffsetDateTime.now(ZoneId.of("Europe/Sofia"));


    OffsetDateTime checkIn, checkOut;
    UUID customerId;
    UUID reservationId;
    Customer customer;
    Reservation createdReservation;
    ReservationResponseDTO expectedResponse;
    ReservationRequestDTO reservationRequest;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        customerId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        reservationId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        checkIn = OffsetDateTime.parse("2026-08-19T16:22:19+03:00");
        checkOut = OffsetDateTime.parse("2026-08-20T16:22:19+03:00");

        customer = Customer.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .build();

        createdReservation = Reservation.builder()
                .id(reservationId)
                .details("Some details.")
                .customer(customer)
                .checkIn(checkIn)
                .checkOut(OffsetDateTime.now())
                .build();

        expectedResponse = new ReservationResponseDTO(
                reservationId,
                "Some details.",
                checkIn,
                checkOut
        );

        reservationRequest = new ReservationRequestDTO(
                customerId,
                checkIn,
                checkOut,
                "Some details."
        );
    }

    @Test
    void addReservation_Successful() throws Exception {
        when(customerRepository.getByIdAndDeletedFalse(any(UUID.class)))
                .thenReturn(Optional.of(customer));
        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(createdReservation);

        String jsonRequest = """
                {
                  "customerId": "00000000-0000-0000-0000-000000000000",
                  "checkIn": "2026-08-19T16:22:19+03:00",
                  "checkOut": "2026-08-20T16:22:19+03:00",
                  "details": "Some details."
                }
                """;

        mockMvc.perform(post("/api/v1/reservation")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdReservation.getId()))
                .andExpect(jsonPath("$.details").value(createdReservation.getDetails()))
                .andExpect(jsonPath("$.checkIn").value(createdReservation.getCheckIn()))
                .andExpect(jsonPath("$.checkOut").value(createdReservation.getCheckOut()));

    }
}
