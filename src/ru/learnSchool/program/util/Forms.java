package ru.learnSchool.program.util;

import javax.swing.*;
import java.awt.*;

public class Forms extends JOptionPane {
    public static void showError(Component component, String text){
        JOptionPane.showConfirmDialog(component, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
    public static void showInfo(Component component, String text){
        JOptionPane.showMessageDialog(component, text, "Внимание", JOptionPane.INFORMATION_MESSAGE);
    }
}
