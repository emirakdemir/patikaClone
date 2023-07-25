package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;

import javax.swing.*;

public class UpdateCourseGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_course_name;
    private JButton btn_course_update;
    private Course course;

    public UpdateCourseGUI(Course course) {
        this.course = course;
        add(wrapper);
        setSize(300, 150);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_course_name.setText(course.getName());
        btn_course_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_name)) {
                Helper.showMsg("fill");
            } else {
                if (Course.update(course.getId(), fld_course_name.getText())) {
                    Helper.showMsg("done");
                }
                dispose();
            }
        });
    }
}