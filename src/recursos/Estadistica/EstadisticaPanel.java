package recursos.Estadistica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.TreeSet;

import javax.swing.*;

import recursos.Tree;
import recursos.DataSet.DataSetMunicipios;
import recursos.DataSet.Municipio;
import recursos.TreeCreation.PanelMunicipiosTree;

public class EstadisticaPanel extends JPanel{
    DataSetMunicipios dataSet;
    Graphics2D dibujado;
    Tree parent;
    final int BarraEstado = 700;
    String[] nodes;

    public EstadisticaPanel(Tree parent){
        super();
        dataSet = DataSetMunicipios.getDataSetMunicipios();
        this.parent = parent;
        this.setPreferredSize(new Dimension(500, 800));
    }

    @Override
    public void paint(Graphics g){

        dibujado = (Graphics2D) g;
        int tamañoPoblacionEstado = BarraEstado;
        int anchuraDebarras = 100;
        int posicionX = 100;
        int posicionY = this.getPreferredSize().height - tamañoPoblacionEstado;

        int posicionXComparador = posicionX+200;
        int posicionYComparador = this.getPreferredSize().height - getBarraComparadorTamaño(nodes);
        
        dibujado.drawString("Total España", posicionX, posicionY-5);
        dibujado.fillRect(posicionX, posicionY, anchuraDebarras, tamañoPoblacionEstado);

        if(nodes == null)return;
        dibujado.drawString(nodes[0], posicionXComparador, posicionYComparador-5);
        drawBarraComparador(dibujado, posicionXComparador, posicionYComparador, anchuraDebarras, getBarraComparadorTamaño(nodes), nodes);
    }

    public void ActualizarBarras(){
        System.out.println("Actualizado");
        nodes = parent.getLblMensaje().getText().split("/");
        parent.repaint();
    }

    private int getComparadorPoblacion(String[] nodePath){
        if(nodePath == null || nodePath.length == 1){
            return 0; 
        }
        switch (nodePath.length) {
            case 2:
                return dataSet.getAutonomiaPoblacion(nodePath[0]);
            case 3:
                return dataSet.getProvinciaPoblacion(nodePath[1], nodePath[0]);
            case 4:
                return dataSet.getHashTableForTree().get(nodePath[2]).get(nodePath[1]).get(nodePath[0]).getHabitantes();
        }
        return 0;
    }

    private int getBarraComparadorTamaño(String[] nodePath){
        if (dataSet.getPoblacionTotal() == 0){
            return BarraEstado;
        }
        return (int) Math.round((BarraEstado*getComparadorPoblacion(nodePath))/dataSet.getPoblacionTotal());
    }

    private int getMunicipioBarraTamaño(int municipiosHabitantes){
        return (int) Math.round((BarraEstado*municipiosHabitantes)/dataSet.getPoblacionTotal());
    }

    private void drawBarraComparador(Graphics2D dinujado ,int posicionX, int posicionY, int anchuraDebarras, int TamañoTotal, String[] name){
        if(name == null){ return;}
        parent.getPnlMunicipios();
        TreeSet<Municipio> municipios = PanelMunicipiosTree.getMunis().getMunicipiosInNode(name);

        int y = posicionY; 
        for(Municipio municipio: municipios){
            int proporcionDeMunicipio = getMunicipioBarraTamaño(municipio.getHabitantes());
            if (proporcionDeMunicipio==0){proporcionDeMunicipio++;}
            Color color = new Color((int)Math.round(Math.random()*255), (int)Math.round(Math.random()*255), (int)Math.round(Math.random()*255));
            dinujado.setColor(color);
            dinujado.drawRect(posicionX, y, anchuraDebarras, proporcionDeMunicipio);
            y+=proporcionDeMunicipio;
        }
    }
}
