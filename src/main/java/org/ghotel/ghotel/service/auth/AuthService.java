package org.ghotel.ghotel.service.auth;

import org.ghotel.ghotel.dto.request.EmployeeRequestDTO;
import org.ghotel.ghotel.dto.request.LoginRequestDTO;
import org.ghotel.ghotel.dto.request.TokenRequestDTO;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.dto.response.EmployeeResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);

    EmployeeResponseDTO register(EmployeeRequestDTO request);

    AuthResponseDTO refresh(TokenRequestDTO request);
}
