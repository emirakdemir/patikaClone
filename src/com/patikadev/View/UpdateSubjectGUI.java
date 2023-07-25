package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Subject;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateSubjectGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_subject_name_update;
    private JTextField fld_subject_lang_update;
    private JComboBox cmb_subject_course_update;
    private JComboBox cmb_subject_user_update;
    private JButton btn_subject_update2;
    private Subject subject;

    public UpdateSubjectGUI(Subject subject) {
        this.subject = subject;
        add(wrapper);
        setSize(400, 300);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        Helper.setLayout();

        fld_subject_name_update.setText(subject.getName());
        fld_subject_lang_update.setText(subject.getLang());
        loadSubjectCourseComboBox();
        loadSubjectEducatorComboBox();

        btn_subject_update2.addActionListener(e -> {
            Item userItem = (Item) cmb_subject_user_update.getSelectedItem();
            Item courseItem = (Item) cmb_subject_course_update.getSelectedItem();
            if (Helper.isFieldEmpty(fld_subject_name_update) || Helper.isFieldEmpty(fld_subject_lang_update)) {
                Helper.showMsg("fill");
            } else {
                if (Subject.update(subject.getId(), fld_subject_name_update.getText(), fld_subject_lang_update.getText(), userItem.getKey(), courseItem.getKey())) {
                    Helper.showMsg("done");
                }
                dispose();
            }
        });
    }

    public void loadSubjectCourseComboBox() {
        cmb_subject_course_update.removeAllItems();
        for (Course course : Course.getList()) {
            cmb_subject_course_update.addItem(new Item(course.getId(), course.getName()));
        }
    }

    public void loadSubjectEducatorComboBox() {
        cmb_subject_user_update.removeAllItems();
        for (User user : User.getListOnlyEducator()) {
            cmb_subject_user_update.addItem(new Item(user.getId(), user.getName()));
        }
    }

}