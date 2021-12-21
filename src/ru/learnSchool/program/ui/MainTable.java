package ru.learnSchool.program.ui;

import ru.learnSchool.program.entity.EntityServices;
import ru.learnSchool.program.entityManager.EntityManagerServices;
import ru.learnSchool.program.util.BaseForm;
import ru.learnSchool.program.util.CustomTableModel;
import ru.learnSchool.program.util.Forms;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Predicate;

public class MainTable extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JLabel rows;
    private JButton costButton;
    private JButton resertButton;
    private JComboBox discountComboBox;
    private JButton createButton;
    private JButton helpButton;
    private JButton askButton;
    private JTextField searchTitleField;
    private JLabel allLabel;
    private JTextField searchDescField;
    private CustomTableModel<EntityServices> model;
    private boolean icost = false;

    public MainTable() {
        super(800, 600);
        setContentPane(mainPanel);
        setVisible(true);
        initTable();
        initButtons();
        initElements();
    }

    public void initTable() {
        table.setRowHeight(50);
        table.getTableHeader().setReorderingAllowed(false);
        model = new CustomTableModel<>(EntityServices.class, new String[]{"Id", "наименование услуги", "стоимость", "продолжительность", "единица измерения", "размер скидки", "описание", "путь", "миниатюра главного изображения"}) {
            @Override
            public void onUpdateRows() {
                rows.setText("Показано строк " + model.getFilteredRows().size() + " из " + model.getAllRows().size());
                if (model.getFilteredRows().size() == 0) {
                    Forms.showInfo(null, "Не найдено записей");
                }
            }
        };
        try {
            model.setAllRows(EntityManagerServices.all());
        } catch (SQLException e) {
            e.printStackTrace();
            Forms.showError(this, "Ошибка получения данных");
        }
        table.setModel(model);
        TableColumn column = table.getColumn("путь");
        column.setMinWidth(0);
        column.setPreferredWidth(0);
        column.setMaxWidth(0);
        model.getFilters()[0] = new Predicate<EntityServices>() {
            @Override
            public boolean test(EntityServices services) {
                String search = searchTitleField.getText();
                if (search == null || search.isEmpty()) {
                    return true;
                }
                return services.getTitle().toLowerCase().startsWith(search.toLowerCase());
            }
        };
        model.getFilters()[1] = new Predicate<EntityServices>() {
            @Override
            public boolean test(EntityServices services) {
                String search = searchDescField.getText();
                if (search == null || search.isEmpty()) {
                    return true;
                }
                String desc = services.getDescription();
                if (desc == null || desc.isEmpty()) {
                    desc = "";
                }
                return desc.toLowerCase().startsWith(search.toLowerCase());
            }
        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EditServisceTable(model.getAllRows().get(row));
                    }
                }
            }
        });
        discountComboBox.addItem("Все");
        discountComboBox.addItem("от 0 до 5%");
        discountComboBox.addItem("от 5% до 15%");
        discountComboBox.addItem("от 15% до 30%");
        discountComboBox.addItem("от 30% до 70%");
        discountComboBox.addItem("от 70% до 100%");

        model.getFilters()[2] = new Predicate<EntityServices>() {
            @Override
            public boolean test(EntityServices services) {
                int selected = discountComboBox.getSelectedIndex();
                switch (selected) {
                    case (1):
                        return (0 <= services.getDiscount() && services.getDiscount() < 5);
                    case (2):
                        return (5 <= services.getDiscount() && services.getDiscount() < 15);
                    case (3):
                        return (15 <= services.getDiscount() && services.getDiscount() < 30);
                    case (4):
                        return (30 <= services.getDiscount() && services.getDiscount() < 70);
                    case (5):
                        return (70 <= services.getDiscount() && services.getDiscount() < 100);
                    default:
                        return true;
                }
            }
        };
    }

    public void initElements() {
        searchTitleField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }
        });

        searchDescField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }
        });
        discountComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    model.updateFilteredRows();
                }
            }
        });
    }

    public void initButtons() {
         costButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 model.setSorter(new Comparator<EntityServices>() {
                     @Override
                     public int compare(EntityServices o1, EntityServices o2) {
                         if(!icost){
                             return o1.getCost().compareTo(o2.getCost());
                         }else {
                             return o2.getCost().compareTo(o1.getCost());
                         }
                     }

                 });
                 icost=!icost;
             }
         });
        createButton.addActionListener(e -> {
            dispose();
            new CreateServisceTable();
        });
        helpButton.addActionListener(e -> {
            Forms.showInfo(this, "Для редактирования дважды нажмите на строчку с услугой\nУдалить услугу можно в окне редактирования");
        });
        askButton.addActionListener(e -> {
            Forms.showInfo(this, "Для связи с разработчиком напишите на почту na3t1e@gmail.com");
        });
        resertButton.addActionListener(e -> {
            searchTitleField.setText("");
            searchDescField.setText("");
            model.setSorter(null);
            discountComboBox.setSelectedIndex(0);
        });
    }
}
