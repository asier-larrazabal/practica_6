package recursos.Tabla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import recursos.Tree;
import recursos.DataSet.DataSetMunicipios;
import recursos.DataSet.Municipio;

public class PanelTablaDatos extends JPanel{
    
    private JScrollPane scrollPane;
    private JTable table;
    private Model model;
    private JButton btnAnadir;
    private JButton btnBorrar;
    private JButton btnOrden;

    private Tree parent;

    public PanelTablaDatos (Tree Tree){
        parent = Tree;
        this.setLayout(new BorderLayout());
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(setAnadirButton());
        btnPanel.add(setBorrarButton());
        btnPanel.add(setOrdenButton());
        setModel(DataSetMunicipios.getDataSetMunicipios());
        setTable(DataSetMunicipios.getDataSetMunicipios());
        scrollPane = new JScrollPane(table);
        this.add(btnPanel, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void setModel(DataSetMunicipios municipios){
        Object[] fields = {"ID", "Municipio", "Poblacion", "Provincia", "Comunidad Autonoma"};
        ArrayList<Municipio> arrayMunicipios = new ArrayList<>(municipios.getListaMunicipios());
        Object[][] lMunicipios = new Object[arrayMunicipios.size()][5];
        for (int i = 0; i<arrayMunicipios.size(); i++){
            lMunicipios[i][0] = arrayMunicipios.get(i).getCodigo();
            lMunicipios[i][1] = arrayMunicipios.get(i).getNombre();
            lMunicipios[i][2] = arrayMunicipios.get(i).getHabitantes();
            lMunicipios[i][3] = arrayMunicipios.get(i).getProvincia();
            lMunicipios[i][4] = arrayMunicipios.get(i).getAutonomia();
        }
        this.model = new Model(lMunicipios, fields, municipios);
    }

    private void setModel(TreeSet<Municipio> municipios){
        Object[] fields = {"ID", "Municipio", "Poblacion", "Provincia", "Comunidad Autonoma"};
        Object[][] lMunicipios = new Object[municipios.size()][5];
        Iterator<Municipio> iterator = municipios.iterator();
        int i = 0;
        Municipio municipio = iterator.next();
        while (iterator.hasNext()) {
            lMunicipios[i][0] = municipio.getCodigo();
            lMunicipios[i][1] = municipio.getNombre();
            lMunicipios[i][2] = municipio.getHabitantes();
            lMunicipios[i][3] = municipio.getProvincia();
            lMunicipios[i][4] = municipio.getAutonomia();
            municipio = iterator.next();
        }
        this.model = new Model(lMunicipios, fields, DataSetMunicipios.getDataSetMunicipios());
    }
    
    private JButton setBorrarButton(){
        btnBorrar = new JButton("Borrar Municipio");
        btnBorrar.setSize( 100, 50);
        btnBorrar.addActionListener(e -> borrar());
        return btnBorrar;
    }

    private void borrar() {
        try{
            int[] indexes = table.getSelectedRows().clone();
            model.removePackRow(indexes);
        }catch(java.lang.ArrayIndexOutOfBoundsException e){
            System.err.println("No hay nada que borrar");
        }catch(java.lang.IndexOutOfBoundsException e1){
            System.err.println("Borrado primer elemento");
        }
    }

    private JButton setAnadirButton(){
        btnAnadir = new JButton("Insertar Municipio");
        btnAnadir.setSize( 100, 50);
        btnAnadir.addActionListener(e -> anadir());
        return btnAnadir;
    }

    private void anadir() {
        String[] rootPath = parent.getLblMensaje().getText().split("/");

        switch (rootPath.length) {
            case 4:
                Object[] enMunicipio = {0, rootPath[0], 100000, rootPath[1], rootPath[2]};
                model.addRow(enMunicipio);
                return;
            case 3:
                Object[] enProvincia = {0, "", 100000, rootPath[0], rootPath[1]};
                model.addRow(enProvincia);
                return;
            case 2:
                Object[] enAutonomia = {0, "", 100000, "", rootPath[0]};
                model.addRow(enAutonomia);
                return;
        }
    }
    private void anadir(Municipio municipio) {
        Object[] newRow = {municipio.getCodigo(), municipio.getNombre(), municipio.getHabitantes(), municipio.getProvincia(), municipio.getAutonomia()};
        model.addRow(newRow);
    }
    private void anadirMunicipios(TreeSet<Municipio> municipios){
        for(Municipio municipio: municipios) anadir(municipio);
    }

    private JTable setTable(DataSetMunicipios municipios){
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(150);
        table.getColumnModel().getColumn(2).setMaxWidth(150);
        table.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JProgressBar progressBar = new JProgressBar(100000, 5000000);
                try{
                    progressBar.setValue((Integer)value);
                }catch(java.lang.ClassCastException e){
                    progressBar.setValue(Integer.parseInt((String)value));
                }
                progressBar.setToolTipText(String.valueOf(progressBar.getValue()));

                progressBar.setStringPainted(true);

                int newRedValue = (int) Math.round( (255 * ((int)value)) / 5000000);
                Color newColor = new Color(newRedValue, 255 - newRedValue, 0);
                progressBar.setBackground(newColor);
                return progressBar;
            }
        });


        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                int j = table.getSelectedColumn();
                cleanColumn(j);
                if(table.getColumnName(j).equalsIgnoreCase("Comunidad Autonoma")){
                    changeColumn(j, (String)table.getValueAt(i, j));
                }
                mayorMenor((int)table.getModel().getValueAt(i, 2));
            }
            
        });

        return table;
    }

    public void changeColumn(int column_index, String nombre){
        System.out.println(nombre);
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String comparator = (String)table.getValueAt(row, column);
                if(nombre.equalsIgnoreCase(comparator)){
                    component.setBackground(Color.CYAN);
                }else{
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        });
        table.repaint();
    }

    public void cleanColumn(int column_index){
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(Color.WHITE);
                return component;
            }
        });
        table.repaint();
    }

    public void mayorMenor (int comparator){
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if((int)table.getValueAt(row, 2) > comparator){
                    component.setForeground(Color.RED);
                }else{  
                    component.setForeground(Color.GREEN);
                }
                return component;
            }
        });

    }

    private JButton setOrdenButton(){
        this.btnOrden = new JButton("Ordenar Municipios");
        btnOrden.addActionListener(e -> Ordenar());
        return btnOrden;
    }

    private void Ordenar() {
        Collections.sort(DataSetMunicipios.getDataSetMunicipios().getListaMunicipios());
    }

    public void changeTable(TreeSet<Municipio> municipios){
        model.removeAll();
        anadirMunicipios(municipios);
        table.repaint();
    }
}
