package ru.learnSchool.program.util;

import ru.learnSchool.program.entity.EntityServices;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class BaseForm extends JFrame{
    public static final String APP_TITLE = "Learn School";
    public static Image APP_IMAGE = null;
    static {
        try {
            APP_IMAGE = ImageIO.read(EntityServices.class.getClassLoader().getResource("school_logo.png"));
        } catch (Exception e) {
            e.printStackTrace();
            Forms.showError(null, "Ошибка загрузки картинки");
        }
    }
    public BaseForm(int width, int heigth){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, heigth));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - width/2,
                Toolkit.getDefaultToolkit().getScreenSize().height/2 - heigth/2);
       setTitle(APP_TITLE);
       if(APP_IMAGE!= null){
           setIconImage(APP_IMAGE);
       }
    }
}
