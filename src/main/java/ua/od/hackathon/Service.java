package ua.od.hackathon;

import ua.od.hackathon.dto.UserDto;
import ua.od.hackathon.entities.UserEntity;


public class Service {

    private static Dao userDao = new Dao();

    public void addNewUser(final UserDto user) {
        userDao.addNewUser(new UserEntity() {{
            setFirstname(user.getFirstname());
            setLastname(user.getLastname());
            setAdress_id(userDao.getAdressId(user.getCountry(),
                                            user.getCity(),
                                            user.getStreet()));
        }});
    }

    public UserDto getUserByName(String fisrtname, String lastname) {
        final UserEntity user = userDao.getUserByName(fisrtname, lastname);
        return new UserDto() {{
            setFirstname(user.getFirstname());
            setLastname(user.getLastname());
            setCountry(user.getCountry());
            setCity(user.getCity());
            setStreet(user.getStreet());
        }};
    }

    public boolean nameExists(UserDto userDto) {
        if (userDao.checkForNameExistence(userDto.getFirstname(), userDto.getLastname())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean nameExists(String firstname, String lastname) {
        if (userDao.checkForNameExistence(firstname, lastname)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean adressExists(UserDto userDto) {
        if (userDao.checkForAdressExistence(userDto.getCountry(),
                userDto.getCity(), userDto.getStreet())) {
            return true;
        } else {
            return false;
        }
    }
}
