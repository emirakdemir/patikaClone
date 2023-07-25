package com.patikadev.Model;

import com.patikadev.Helper.DbConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String userType;

    public User() {
    }

    public User(int id, String name, String username, String password, String userType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("userType"));
                userList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static ArrayList<User> getListOnlyEducator() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user WHERE userType = 'educator'";
        User obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("userType"));
                userList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static boolean add(String name, String username, String password, String userType) {
        String query = "INSERT INTO user (name,username,password,userType) VALUES (?,?,?,?)";

        User findUser = User.getFetch(username);
        if (findUser != null) {
            Helper.showMsg("This username is already used.");
            return false;
        }

        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setString(3, password);
            pr.setString(4, userType);

            int response = pr.executeUpdate();

            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static User getFetch(String username) {
        User obj = null;
        String query = "SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setString(1, username);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("userType"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("userType"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(String username, String password) {
        User obj = null;
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("userType")) {
                    case "operator":
                        obj = new Operator();
                        break;
                    default:
                        obj = new User();
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("userType"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        ArrayList<Subject> subjectList = Subject.getListByUser(id);
        for (Subject subject : subjectList) {
            Subject.delete(subject.getId());
        }
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name, String username, String password, String userType) {
        String query = "UPDATE user SET name = ?,username = ?,password=?,userType=? WHERE id=?";

        User findUser = User.getFetch(username);
        if (findUser != null && findUser.getId() != id) {
            Helper.showMsg("This username is already used.");
            return false;
        }

        HashSet<String> userTypes = new HashSet<>();
        userTypes.add("operator");
        userTypes.add("educator");
        userTypes.add("student");
        if (!userTypes.contains(userType)) {
            Helper.showMsg("This is not a user type!");
            return false;
        }

        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, username);
            pr.setString(3, password);
            pr.setString(4, userType);
            pr.setInt(5, id);
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setUserType(rs.getString("userType"));
                userList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static String searchQuery(String name, String username, String userType) {
        String query = "SELECT * FROM user WHERE name LIKE '%{{name}}%' AND username LIKE '%{{username}}%' AND userType LIKE '%%{{userType}}'";
        query = query.replace("{{name}}", name);
        query = query.replace("{{username}}", username);
        query = query.replace("{{userType}}", userType);
        return query;
    }

}