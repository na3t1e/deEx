package ru.learnSchool.program.util;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class CustomTableModel<T> extends AbstractTableModel {
    private Class<T> cls;
    private List<T> allRows = new ArrayList<>();
    private List<T> filteredRows;
    private String[] columnNames;
    private Predicate<T>[] filters = new Predicate[]{null, null, null, null, null, null,};
    private Comparator<T> sorter;

    public CustomTableModel(Class<T> cls, String[] columnNames) {
        this.cls = cls;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return filteredRows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    public void updateFilteredRows(){
        filteredRows = new ArrayList<>(allRows);
        for(Predicate<T>filter:filters){
            if (filter!=null){
                filteredRows.removeIf(row->!filter.test(row));
            }
        }
        if (sorter!=null){
            filteredRows.sort(sorter);
        }
        fireTableDataChanged();
        onUpdateRows();
    }

    public void onUpdateRows(){

    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Field field = cls.getDeclaredFields()[columnIndex];
       field.setAccessible(true);
        try {
            return field.get(allRows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public List<T> getAllRows() {
        return allRows;
    }

    public void setAllRows(List<T> allRows) {
        this.allRows = allRows;
        this.updateFilteredRows();
    }

    public List<T> getFilteredRows() {
        return filteredRows;
    }

    public void setFilteredRows(List<T> filteredRows) {
        this.filteredRows = filteredRows;
    }

    public Predicate<T>[] getFilters() {
        return filters;
    }

    public void setFilters(Predicate<T>[] filters) {
        this.filters = filters;
    }

    public Comparator<T> getSorter() {
        return sorter;
    }

    public void setSorter(Comparator<T> sorter) {
        this.sorter = sorter;
        this.updateFilteredRows();
    }
}
