package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static int screenCenter(String axis, Dimension size) {
        int point = switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
        return point;
    }

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().isEmpty();
    }

    public static boolean isAreaEmpty(JTextArea area) {
        return area.getText().isEmpty();
    }

    public static void showMsg(String str) {
        String msg;
        String title;
        switch (str) {
            case "fill" -> {
                msg = "Fill all the areas";
                title = "Blank Areas";
            }
            case "done" -> {
                msg = "Process succesfull.";
                title = "Success";
            }
            case "error" -> {
                msg = "User couldn't added!";
                title = "Error";
            }
            default -> {
                msg = str;
                title = "Message";
            }
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        String msg;
        switch (str) {
            case "sure":
                msg = "Are you sure?";
                break;
            default:
                msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION) == 0;
    }
}