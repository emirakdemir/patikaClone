package com.patikadev.Model;

import com.patikadev.Helper.DbConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private String title;
    private String description;
    private String link;
    private String quizQue;
    private int subject_id;

    private Subject subject;

    public Content(int id, String title, String description, String link, String quizQue, int subject_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.quizQue = quizQue;
        this.subject_id = subject_id;
        this.subject = Subject.getFetch(subject_id);
    }

    public static ArrayList<Content> getListBySubjectId(int subjectId) {
        ArrayList<Content> contentList = new ArrayList<>();
        String query = "SELECT * FROM content WHERE subject_id = " + subjectId;
        Content obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String link = rs.getString("link");
                String quizQue = rs.getString("quizQue");
                int subj_id = rs.getInt("subject_id");
                obj = new Content(id, title, description, link, quizQue, subj_id);
                contentList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contentList;
    }

    public static boolean add(String title, String description, String link, String quizQue, int subject_id) {
        String query = "INSERT INTO content (title,description,link,quizQue,subject_id) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setString(1, title);
            pr.setString(2, description);
            pr.setString(3, link);
            pr.setString(4, quizQue);
            pr.setInt(5, subject_id);
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static Content getFetch(int id) {
        Content obj = null;
        String query = "SELECT * FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Content(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("link"), rs.getString("quizQue"), rs.getInt("subject_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static boolean update(int id, String title, String description, String link, String quizQue, int subject_id) {
        String query = "UPDATE content SET title = ?, description = ?, link = ?,quizQue = ?, subject_id=? WHERE id = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setString(1, title);
            pr.setString(2, description);
            pr.setString(3, link);
            pr.setString(4, quizQue);
            pr.setInt(5, subject_id);
            pr.setInt(6, id);
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DbConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static String searchQuery(String title) {
        String query = "SELECT * FROM content WHERE title LIKE '%{{title}}%'";
        query = query.replace("{{title}}",title);
        return query;
    }

    public static ArrayList<Content> searchContentList(String query) {
        ArrayList<Content> contentList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DbConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                String link = rs.getString("link");
                String quizQue = rs.getString("quizQue");
                int subject_id = rs.getInt("subject_id");
                obj = new Content(id, title, desc, link, quizQue,subject_id);
                contentList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contentList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQuizQue() {
        return quizQue;
    }

    public void setQuizQue(String quizQue) {
        this.quizQue = quizQue;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}