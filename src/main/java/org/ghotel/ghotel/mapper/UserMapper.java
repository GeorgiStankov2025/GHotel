package org.ghotel.ghotel.mapper;

import org.ghotel.ghotel.dto.request.UserRequestDTO;
import org.ghotel.ghotel.dto.response.UserResponseDTO;
import org.ghotel.ghotel.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface UserMapper {
    User toUserEntity(UserRequestDTO request);

    UserResponseDTO toUserResponseDTO(User user);

//    User updateUser(UserRequestDTO request, @MappingTarget User employee);
}