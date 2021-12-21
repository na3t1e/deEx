package ru.learnSchool.program.ui;

import ru.learnSchool.program.entity.EntityServices;
import ru.learnSchool.program.entityManager.EntityManagerServices;
import ru.learnSchool.program.util.BaseForm;
import ru.learnSchool.program.util.Forms;

import javax.swing.*;
import java.sql.SQLException;

public class EditServisceTable extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleTextField;
    private JTextField discountTextField;
    private JTextField costTextField;
    private JTextField durationTextField;
    private JTextField descriptionTextField;
    private JTextField pathTextField;
    private JLabel createLabel;
    private JComboBox unitComboBox;
    private JButton editButton;
    private JButton backButton;
    private JButton deleteButton;
    private JTextField idTextField;

    private EntityServices services;

    public EditServisceTable(EntityServices services) {
        super(400, 500);
        this.services = services;
        setContentPane(mainPanel);
        initButtons();
        initBoxes();
        initElements();
        setVisible(true);
    }

    public void initElements(){
        idTextField.setEditable(false);
        idTextField.setText(String.valueOf(services.getId()));
        titleTextField.setText(services.getTitle());
        discountTextField.setText(services.getDiscount().toString());
        costTextField.setText(services.getCost().toString());
        durationTextField.setText(String.valueOf(services.getDuration()));
        descriptionTextField.setText(services.getDescription());
        pathTextField.setText(services.getPath());
    }

    public void initBoxes() {
        unitComboBox.addItem("сек");
        unitComboBox.addItem("мин");
        unitComboBox.setSelectedItem(services.getUnit());
    }

    public void initButtons() {
        editButton.addActionListener(e -> {
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
            services.setTitle(title);
            services.setCost(cost);
            services.setDuration(duration);
            services.setUnit(unitComboBox.getSelectedItem().toString());
            services.setDiscount(discount);
            services.setDescription(descriptionTextField.getText());
            services.setPath(pathTextField.getText());
            try {
                EntityManagerServices.update(services);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Forms.showError(this, "Ошибка добавления данных");
                return;
            }
            Forms.showInfo(this, "Услуга успешно обновлена");
            dispose();
            new MainTable();
        });

        deleteButton.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this,"Вы точно хотите удалить услугу?", "Вопрос", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    EntityManagerServices.delete(services);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Forms.showError(this, "Ошибка удаления");
                    return;
                }
                Forms.showInfo(this, "Услуга успешно удалена");
                dispose();
                new MainTable();
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new MainTable();
        });
    }
}
