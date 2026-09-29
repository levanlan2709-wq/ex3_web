package vn.iotstar.example3.mapper;

import org.mapstruct.*;
import vn.iotstar.example3.dto.UserDTO;
import vn.iotstar.example3.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    UserDTO toDto(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "products", ignore = true)
    User toEntity(UserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntity(UserDTO dto, @MappingTarget User user);
}
