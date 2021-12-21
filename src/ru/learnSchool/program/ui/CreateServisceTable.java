package ru.learnSchool.program.ui;

import ru.learnSchool.program.entity.EntityServices;
import ru.learnSchool.program.entityManager.EntityManagerServices;
import ru.learnSchool.program.util.BaseForm;
import ru.learnSchool.program.util.Forms;

import javax.swing.*;
import java.sql.SQLException;

public class CreateServisceTable extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleTextField;
    private JTextField discountTextField;
    private JTextField costTextField;
    private JTextField durationTextField;
    private JTextField descriptionTextField;
    private JTextField pathTextField;
    private JLabel createLabel;
    private JComboBox unitComboBox;
    private JButton createButton;
    private JButton backButton;

    public CreateServisceTable() {
        super(400, 500);
        setContentPane(mainPanel);
        initButtons();
        initBoxes();
        setVisible(true);
    }

    public void initBoxes() {
        unitComboBox.addItem("сек");
        unitComboBox.addItem("мин");
    }

    public void initButtons() {
        createButton.addActionListener(e -> {
            String title = titleTextField.getText();
            if (title.isEmpty() || title.length() > 100) {
                Forms.showError(this, "Название введено неправильно или длиннее 100 символов");
                return;
            }
            double cost = -1;
            try {
                cost = Double.parseDouble(costTextField.getText());
            } catch (Exception ex) {
                Forms.showError(this, "Стоимость введена неправильно или меньше 0");
                return;
            }
            if(cost<0){
                Forms.showError(this, "Стоимость введена неправильно или меньше 0");
                return;
            }
            int duration = -1;
            try {
                duration = Integer.parseInt(durationTextField.getText());
            } catch (Exception ex) {
                Forms.showError(this, "Продолжительность введена неправильно или меньше 0");
                return;
            }
            if(duration<0){
                Forms.showError(this, "Продолжительность введена неправильно или меньше 0");
                return;
            }
            double discount = -1;
            try {
                discount = Double.parseDouble(discountTextField.getText());
            } catch (Exception ex) {
                Forms.showError(this, "Скидка введена неправильно или меньше 0");
                return;
            }
            if(discount<0){
                Forms.showError(this, "Скидка введена неправильно или меньше 0");
                return;
            }
            EntityServices services = new EntityServices(title, cost, duration, unitComboBox.getSelectedItem().toString(), discount, descriptionTextField.getText(), pathTextField.getText());
            try {
                EntityManagerServices.insert(services);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Forms.showError(this, "Ошибка добавления данных");
                return;
            }
            Forms.showInfo(this, "Услуга успешно добавлена");
            dispose();
            new MainTable();
        });

        backButton.addActionListener(e -> {
            dispose();
            new MainTable();
        });
    }
}
