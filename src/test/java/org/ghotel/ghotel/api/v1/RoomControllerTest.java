package org.ghotel.ghotel.api.v1;

import org.ghotel.ghotel.dto.request.RoomRequestDTO;
import org.ghotel.ghotel.entity.Customer;
import org.ghotel.ghotel.entity.Reservation;
import org.ghotel.ghotel.entity.Room;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.ghotel.ghotel.repository.CustomerRepository;
import org.ghotel.ghotel.repository.ReservationRepository;
import org.ghotel.ghotel.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class RoomControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    UUID roomId;
    UUID reservationId;
    UUID customerId;

    Room room;
    Reservation reservation;
    Customer customer;

    OffsetDateTime checkIn;
    OffsetDateTime checkOut;

    RoomRequestDTO request;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();

        reservationRepository.deleteAll();
        roomRepository.deleteAll();


        roomId = UUID.fromString
                ("33333333-3333-3333-3333-333333333333");
        reservationId = UUID.fromString
                ("44444444-4444-4444-4444-444444444444");
        customerId = UUID.fromString
                ("55555555-5555-5555-5555-555555555555");

        room = Room.builder()
                .id(roomId)
                .roomNumber(10L)
                .roomCapacity(3)
                .build();

        checkIn = OffsetDateTime.parse("2026-08-25T16:22:19+03:00");
        checkOut = OffsetDateTime.parse("2026-08-29T16:22:19+03:00");

        customer = Customer.builder()
                .firstName("Georgi")
                .lastName("Georgiev")
                .build();

        reservation = Reservation.builder()
                .rooms(new ArrayList<>())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .customer(customer)
                .build();

        request = new RoomRequestDTO(5, 2);

        customerRepository.save(customer);
        reservationRepository.save(reservation);
    }

    @Test
    void addRoom_Successful() throws Exception {

        var jsonRequest = Map.of(

                "roomNumber", room.getRoomNumber(),
                "roomCapacity", room.getRoomCapacity()
        );

        mockMvc.perform(post("/api/v1/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jsonRequest)))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.roomNumber").value(room.getRoomNumber()),
                        jsonPath("$.roomCapacity").value(room.getRoomCapacity())
                );
        assertThat(roomRepository.count()).isEqualTo(1);
    }

    @Test
    void addRoom_Throws_400_roomWithTheNumberAlreadyExists() throws Exception {

        Room savedRoom = new Room(10L, 2);
        roomRepository.save(savedRoom);

        var jsonRequest = Map.of(

                "roomNumber", savedRoom.getRoomNumber(),
                "roomCapacity", savedRoom.getRoomCapacity()
        );

        MvcResult result = mockMvc.perform(post("/api/v1/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jsonRequest)))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.instance").value("/api/v1/room"),
                        jsonPath("$.title").value("Request error.")

                ).andReturn();
        Exception resolvedException = result.getResolvedException();

        assertThat(resolvedException)
                .isNotNull()
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Cannot add room with number: " + savedRoom.getRoomNumber());
        assertThat(roomRepository.count()).isEqualTo(1);
    }

    @Test
    void getRoom_Successful() throws Exception {

        Room newRoom = new Room(10L, 2);
        Room savedRoom = roomRepository.save(newRoom);

        mockMvc.perform(get("/api/v1/room/" + savedRoom.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(savedRoom.getId().toString()),
                        jsonPath("$.roomNumber").value(savedRoom.getRoomNumber()),
                        jsonPath("$.roomCapacity").value(savedRoom.getRoomCapacity())
                );
        assertThat(roomRepository.count()).isEqualTo(1L);
    }

    @Test
    void getRoom_Throws_404_NotFound() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/room/" + roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.instance").value("/api/v1/room/" + roomId),
                        jsonPath("$.title").value("Resource not found.")
                ).andReturn();
        Exception resolvedException = result.getResolvedException();
        assertThat(resolvedException)
                .isNotNull()
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Room not found with id: " + roomId);
        assertThat(roomRepository.count()).isEqualTo(0L);
    }

    @Test
    void editRoom_Successful() throws Exception {

        Room newRoom = new Room(10L, 2);
        Room savedRoom = roomRepository.save(newRoom);

        var jsonRequest = Map.of(
                "roomNumber", request.roomNumber(),
                "roomCapacity", request.roomCapacity()
        );

        mockMvc.perform(put("/api/v1/room/" + savedRoom.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jsonRequest)))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.roomNumber").value(request.roomNumber()),
                        jsonPath("$.roomCapacity").value(request.roomCapacity())
                );
        assertThat(roomRepository.count()).isEqualTo(1);
    }
}
