package vn.iotstar.example3.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.iotstar.example3.dto.UserDTO;
import vn.iotstar.example3.entity.Role;
import vn.iotstar.example3.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-28T11:57:54+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260826-1225, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setRoleId( userRoleId( user ) );
        userDTO.setRoleName( userRoleName( user ) );
        userDTO.setEmail( user.getEmail() );
        userDTO.setEnabled( user.isEnabled() );
        userDTO.setFullName( user.getFullName() );
        userDTO.setId( user.getId() );
        userDTO.setImages( user.getImages() );
        userDTO.setUsername( user.getUsername() );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( dto.getEmail() );
        user.setEnabled( dto.isEnabled() );
        user.setFullName( dto.getFullName() );
        user.setId( dto.getId() );
        user.setImages( dto.getImages() );
        user.setUsername( dto.getUsername() );

        return user;
    }

    @Override
    public void updateEntity(UserDTO dto, User user) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getEmail() != null ) {
            user.setEmail( dto.getEmail() );
        }
        user.setEnabled( dto.isEnabled() );
        if ( dto.getFullName() != null ) {
            user.setFullName( dto.getFullName() );
        }
        if ( dto.getId() != null ) {
            user.setId( dto.getId() );
        }
        if ( dto.getImages() != null ) {
            user.setImages( dto.getImages() );
        }
        if ( dto.getUsername() != null ) {
            user.setUsername( dto.getUsername() );
        }
    }

    private Long userRoleId(User user) {
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getId();
    }

    private String userRoleName(User user) {
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getName();
    }
}
