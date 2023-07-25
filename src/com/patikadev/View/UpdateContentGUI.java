package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateContentGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_content_title_update;
    private JTextArea area_content_desc_update;
    private JTextField fld_content_link_update;
    private JTextArea area_content_ques_update;
    private JComboBox cmb_content_subject_update;
    private JButton btn_content_update;
    private Content content;

    public UpdateContentGUI(Content content){
        this.content = content;
        add(wrapper);
        setSize(400, 500);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_content_title_update.setText(content.getTitle());
        area_content_desc_update.setText(content.getDescription());
        fld_content_link_update.setText(content.getLink());
        area_content_ques_update.setText(content.getQuizQue());
        loadContentSubjectComboBox();


        btn_content_update.addActionListener(e -> {
            Item subjectItem = (Item) cmb_content_subject_update.getSelectedItem();
            if (Helper.isFieldEmpty(fld_content_title_update) || Helper.isFieldEmpty(fld_content_link_update)||Helper.isAreaEmpty(area_content_desc_update)||Helper.isAreaEmpty(area_content_ques_update)) {
                Helper.showMsg("fill");
            } else {
                if (Content.update(content.getId(), fld_content_title_update.getText(), area_content_desc_update.getText(), fld_content_link_update.getText(), area_content_ques_update.getText(),subjectItem.getKey())) {
                    Helper.showMsg("done");
                }
                dispose();
            }
        });
    }

    public void loadContentSubjectComboBox() {
        cmb_content_subject_update.removeAllItems();
        for (Subject subject : Subject.getListByUser(content.getSubject().getUser_id())) {
            cmb_content_subject_update.addItem(new Item(subject.getId(), subject.getName()));
        }
    }
}