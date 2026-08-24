package org.ghotel.ghotel.api.v1;

import org.ghotel.ghotel.application.ReservationFacade;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Reservation;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.mapper.CustomerMapper;
import org.ghotel.ghotel.mapper.ReservationMapper;
import org.ghotel.ghotel.mapper.RoomMapper;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.ghotel.ghotel.repository.ReservationRepository;
import org.ghotel.ghotel.repository.RoomRepository;
import org.ghotel.ghotel.service.customer.CustomerService;
import org.ghotel.ghotel.service.reservation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:GHotelTest;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.driver-class-name=org.h2.Driver"
})
public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CustomerRepository customerRepository;

    @MockitoBean
    private ReservationRepository reservationRepository;

    @MockitoBean
    private org.springframework.transaction.PlatformTransactionManager transactionManager;

    @MockitoBean
    private RoomRepository roomRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID customerId;
    private UUID reservationId;
    private UUID roomId;

    private OffsetDateTime checkIn;
    private OffsetDateTime checkOut;
    private Customer customer;
    private Reservation savedReservation;
    private Reservation reservationWithDetails;
    private Room room;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();

        customerId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        reservationId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        roomId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        checkIn = OffsetDateTime.parse("2026-08-25T16:22:19+03:00");
        checkOut = OffsetDateTime.parse("2026-08-29T16:22:19+03:00");

        customer = Customer.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .version(0L)
                .deleted(false)
                .build();

        savedReservation = Reservation.builder()
                .id(reservationId)
                .details("Some details.")
                .customer(customer)
                .version(1L)
                .deleted(false)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .rooms(new ArrayList<>())
                .build();

        room = Room.builder()
                .id(roomId)
                .roomNumber(13)
                .roomCapacity(3)
                .build();

        reservationWithDetails = Reservation.builder()
                .id(reservationId)
                .details("Some details also including room.")
                .checkIn(checkIn)
                .checkOut(checkOut)
                .customer(customer)
                .rooms(new ArrayList<>())
                .build();
        reservationWithDetails.addRoom(room);

    }

    @Test
    void addReservation_Successful() throws Exception {
        doReturn(Optional.of(customer))
                .when(customerRepository)
                .getByIdAndDeletedFalse(any());
        doReturn(Optional.of(customer))
                .when(customerRepository)
                .findById(any());
        doReturn(savedReservation).when(reservationRepository).save(any(Reservation.class));

        var jsonRequest = Map.of(
                "customerId", customerId,
                "checkIn", checkIn,
                "checkOut", checkOut,
                "details", "Some details."
        );

        MvcResult result = mockMvc.perform(post("/api/v1/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jsonRequest)))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value(savedReservation.getId().toString()),
                        jsonPath("$.details").value(savedReservation.getDetails()),
                        jsonPath("$.checkIn").exists(),
                        jsonPath("$.checkOut").exists()
                ).andReturn();
    }

    @Test
    void addReservation_Throws_404_Customer_NotFound() throws Exception {
        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());


        var jsonRequest = Map.of(
                "customerId", customerId,
                "checkIn", checkIn,
                "checkOut", checkOut,
                "details", "Some details."
        );

        var mvcResult = mockMvc.perform(post("/api/v1/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jsonRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instance").value("/api/v1/reservation"))
                .andExpect(jsonPath("$.title").value("Resource not found."))
                .andReturn();

        Exception resolvedException = mvcResult.getResolvedException();

        assertThat(resolvedException)
                .isNotNull()
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found with id: %s".formatted(customerId));
    }

    @Test
    void getReservationWithDetails_Successful() throws Exception {

        when(reservationRepository.getReservationAndRoomsAndCustomerByIdAndDeletedFalse(any(UUID.class)))
                .thenReturn(Optional.of(reservationWithDetails));

        mockMvc.perform(get("/api/v1/reservation/" + reservationId + "/details")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.reservation.id").value(
                                reservationWithDetails.getId().toString()),
                        jsonPath("$.reservation.details").value(
                                reservationWithDetails.getDetails()),
                        jsonPath("$.reservation.checkIn").value(
                                reservationWithDetails.getCheckIn().toString()),
                        jsonPath("$.reservation.checkOut").value(
                                reservationWithDetails.getCheckOut().toString()),
                        jsonPath("$.customer.id").value(customer.getId().toString()),
                        jsonPath("$.customer.firstName").value(customer.getFirstName()),
                        jsonPath("$.customer.lastName").value(customer.getLastName())

                );
    }

    @Test
    void getReservationById_Throws_404_NotFound() throws Exception {
        when(reservationRepository.getReservationByIdAndDeletedFalse(any(UUID.class)))
                .thenReturn(Optional.empty());

        var mvcResult = mockMvc.perform(get("/api/v1/reservation/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instance").value("/api/v1/reservation/" + reservationId))
                .andExpect(jsonPath("$.title").value("Resource not found."))
                .andReturn();

        Exception resolvedException = mvcResult.getResolvedException();

        assertThat(resolvedException)
                .isNotNull()
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Reservation not found with id: %s".formatted(reservationId));
    }
}