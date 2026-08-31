package org.ghotel.ghotel.service.auth;

import org.ghotel.ghotel.dto.request.UserRequestDTO;
import org.ghotel.ghotel.dto.request.LoginRequestDTO;
import org.ghotel.ghotel.dto.request.TokenRequestDTO;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);

    UserResponseDTO register(UserRequestDTO request);

    AuthResponseDTO refresh(TokenRequestDTO request);
}
