package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JLabel lbl_icon;
    private JTextField fld_login_username;
    private JPasswordField fld_login_password;
    private JButton btn_login;

    public LoginGUI() {
        add(wrapper);
        setSize(600, 500);
        setLocation(Helper.screenCenter("x", getSize()), (Helper.screenCenter("y", getSize())));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        ImageIcon imageIcon = new ImageIcon(new ImageIcon("icon.png").getImage().getScaledInstance(280, 200, Image.SCALE_DEFAULT));
        lbl_icon.setIcon(imageIcon);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_login_username) || Helper.isFieldEmpty(fld_login_password)) {
                Helper.showMsg("fill");
            } else {
                User user = User.getFetch(fld_login_username.getText(),fld_login_password.getText());
                if(user == null){
                    Helper.showMsg("User not found!");
                } else{
                    switch (user.getUserType()){
                        case "operator":
                            OperatorGUI opGUI = new OperatorGUI((Operator)user);
                            break;
                        case "educator":
                            EducatorGUI eduGUI = new EducatorGUI(user);
                            break;
                        case "student":
                            StudentGUI stuGUI =new StudentGUI();
                            break;
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI login = new LoginGUI();
    }
}