package com.patikadev.Model;

import com.patikadev.Helper.DbConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Subject {
    private int id;
    private int user_id;
    private int course_id;
    private String name;
    private String lang;

    private Course course;
    private User educator;

    public Subject(int id, int user_id, int course_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.course_id = course_id;
        this.name = name;
        this.lang = lang;
        this.educator = User.getFetch(user_id);
        this.course = Course.getFetch(course_id);
    }

    public static ArrayList<Subject> searchSubjectList(String query) {
        ArrayList<Subject> subjectList = new ArrayList<>();
        Subject obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int course_id = rs.getInt("course_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Subject(id, user_id, course_id, name, lang);
                subjectList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Subject> getList() {
        ArrayList<Subject> subjectList = new ArrayList<>();
        String query = "SELECT * FROM subject";
        Subject obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int course_id = rs.getInt("course_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Subject(id, user_id, course_id, name, lang);
                subjectList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectList;
    }

    public static boolean add(int user_id, int course_id, String name, String lang) {
        String query = "INSERT INTO subject (user_id, course_id, name, lang) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setInt(1, user_id);
            pr.setInt(2, course_id);
            pr.setString(3, name);
            pr.setString(4, lang);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Subject> getListByUser(int userId) {
        ArrayList<Subject> subjectList = new ArrayList<>();
        String query = "SELECT * FROM subject WHERE user_id = " + userId;
        Subject obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int course_id = rs.getInt("course_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Subject(id, user_id, course_id, name, lang);
                subjectList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectList;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM subject WHERE id = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static Subject getFetch(int id) {
        Subject obj = null;
        String query = "SELECT * FROM subject WHERE id = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Subject(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("course_id"), rs.getString("name"), rs.getString("lang"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static boolean update(int id, String name, String lang, int user_id, int course_id) {
        String query = "UPDATE subject SET name = ?, lang = ?, user_id = ?,course_id = ? WHERE id = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, lang);
            pr.setInt(3,user_id);
            pr.setInt(4,course_id);
            pr.setInt(5, id);
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static String searchQuery(String name,int user_id) {
        String query = "SELECT * FROM subject WHERE name LIKE '%{{name}}%' AND user_id = " + user_id;
        query = query.replace("{{name}}", name);
        return query;
    }
}