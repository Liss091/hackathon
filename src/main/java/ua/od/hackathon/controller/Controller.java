package ua.od.hackathon.controller;

import ua.od.hackathon.Service;
import ua.od.hackathon.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/userinfo")
public class Controller {

    private static Service userService = new Service();

    @POST
    @Path("add")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserDto addUser(UserDto user) {
        if (userService.nameExists(user)) {
            return new UserDto(204, "User " + user.getFirstname() + " " + user.getLastname()
                    + " already exists.");
        }
        if (!userService.adressExists(user)) {
            return new UserDto(406, "Adress " + user.getCountry() + " " + user.getCity() + " "
                    + user.getStreet() + " doesn`t exist.");
        }
        userService.addNewUser(user);
        return new UserDto(201, "User " + user.getFirstname() + " " + user.getLastname() + " was created.");
    }

    @GET
    @Path("get/{firstname}/{lastname}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserDto getUserByName(@PathParam("firstname") String firstname, @PathParam("lastname") String lastname) {
        if (!userService.nameExists(firstname, lastname)) {
            return new UserDto(204, "User " + firstname + " " + lastname + " doesn`t exist.");
        }
        UserDto user = userService.getUserByName(firstname, lastname);
        user.setStatus(200);
        return user;
    }

}
