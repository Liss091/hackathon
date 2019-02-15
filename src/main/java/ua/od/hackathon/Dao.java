package ua.od.hackathon;

import ua.od.hackathon.dto.UserDto;
import ua.od.hackathon.entities.UserEntity;

import java.sql.*;

import static ua.od.hackathon.config.DataBaseConfig.*;

public class Dao {

    private Connection conn = null;

    private final String ADD_NEW_USER = "INSERT INTO User(firstname, lastname, adress_id) VALUES (?, ?, ?);";
    private final String GET_USER_BY_NAME = "SELECT u.id, u.firstname, u.lastname, u.adress_id, co.name AS country, ci.name AS city, st.name AS street \n" +
                                            "FROM user u\n" +
                                            "inner join adress a \n" +
                                            "on u.adress_id = a.id\n" +
                                            "inner join country co\n" +
                                            "on a.country_id = co.id\n" +
                                            "inner join city ci\n" +
                                            "on a.city_id = ci.id\n" +
                                            "inner join street st\n" +
                                            "on a.street_id = st.id\n" +
                                            "WHERE u.firstname = ? AND u.lastname = ?;";
    private final String CHECK_NAME_FOR_EXISTENCE = "SELECT EXISTS (SELECT * FROM User\n" +
                                                    "WHERE firstname = ? AND lastname = ?);";
    private final String CHECK_ADRESS_FOR_EXISTENCE = "SELECT EXISTS \n" +
                                                        "(SELECT *\n" +
                                                        "FROM adress a INNER JOIN country co\n" +
                                                        "ON a.country_id = co.id\n" +
                                                        "INNER JOIN city ci\n" +
                                                        "ON a.city_id = ci.id\n" +
                                                        "INNER JOIN street st\n" +
                                                        "ON a.street_id = st.id\n" +
                                                        "WHERE co.name = ? AND ci.name = ? AND st.name = ?);";
    private final String GET_ADRESS_ID = "select a.id\n" +
                                            "from adress a inner join country co \n" +
                                            "on a.country_id = co.id\n" +
                                            "inner join city ci\n" +
                                            "on a.city_id = ci.id\n" +
                                            "inner join street st\n" +
                                            "on a.street_id = st.id\n" +
                                            "where co.name = ? AND ci.name = ? AND st.name = ?;";

    public void addNewUser(UserEntity user) {
        PreparedStatement prepStat = null;
        openConnection();
        try {
            prepStat = conn.prepareStatement(ADD_NEW_USER);
            prepStat.setString(1, user.getFirstname());
            prepStat.setString(2, user.getLastname());
            prepStat.setInt(3, user.getAdress_id());
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public UserEntity getUserByName(String firstname, String lastname) {
        PreparedStatement prepStat = null;
        openConnection();
        UserEntity user = null;
        try {
            prepStat = conn.prepareStatement(GET_USER_BY_NAME);
            prepStat.setString(1, firstname);
            prepStat.setString(2, lastname);
            final ResultSet rs = prepStat.executeQuery();
            if (rs.next()) {
                user = new UserEntity() {{
                    setId(rs.getInt("id"));
                    setFirstname(rs.getString("firstname"));
                    setLastname(rs.getString("lastname"));
                    setAdress_id(rs.getInt("adress_id"));
                    setCountry(rs.getString("country"));
                    setCity(rs.getString("city"));
                    setStreet(rs.getString("street"));
                }};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return user;
    }

    public boolean checkForNameExistence(String firstname, String lastname) {
        boolean isPresent = false;
        PreparedStatement prepStat = null;
        openConnection();
        try {
            prepStat = conn.prepareStatement(CHECK_NAME_FOR_EXISTENCE);
            prepStat.setString(1, firstname);
            prepStat.setString(2, lastname);
            ResultSet rs = prepStat.executeQuery();
            rs.absolute(1);
            if (rs.getInt(1) == 1) {
                isPresent = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return isPresent;
    }

    public boolean checkForAdressExistence(String country, String city, String street) {
        boolean isPresent = false;
        PreparedStatement prepStat = null;
        openConnection();
        try {
            prepStat = conn.prepareStatement(CHECK_ADRESS_FOR_EXISTENCE);
            prepStat.setString(1, country);
            prepStat.setString(2, city);
            prepStat.setString(3, street);
            ResultSet rs = prepStat.executeQuery();
            rs.absolute(1);
            if (rs.getInt(1) == 1) {
                isPresent = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return isPresent;
    }

    public Integer getAdressId(String country, String city, String street) {
        Integer adressId = null;
        PreparedStatement prepStat = null;
        openConnection();
        try {
            prepStat = conn.prepareStatement(GET_ADRESS_ID);
            prepStat.setString(1, country);
            prepStat.setString(2, city);
            prepStat.setString(3, street);
            ResultSet rs = prepStat.executeQuery();
            rs.absolute(1);
            adressId = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return adressId;
    }

    private void openConnection() {
        try {
            conn = DriverManager.getConnection(DB_DATABASE_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
