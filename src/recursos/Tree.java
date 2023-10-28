package recursos;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import recursos.Estadistica.EstadisticaPanel;
import recursos.Tabla.PanelTablaDatos;
import recursos.TreeCreation.PanelMunicipiosTree;

public class Tree extends JFrame {
    private PanelTablaDatos pnlTabla;
    private PanelMunicipiosTree pnlMunicipios;
    private EstadisticaPanel pnlEstadistica;
    private JLabel lblMensaje;

    public Tree (){
        this.setSize(1200, 1200);
        this.setTitle("Practica_6");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.pnlMunicipios = new PanelMunicipiosTree(this);
        this.add(pnlMunicipios, BorderLayout.WEST);

        this.pnlTabla = new PanelTablaDatos(this);
        this.add(pnlTabla, BorderLayout.CENTER);

        this.pnlEstadistica = new EstadisticaPanel(this);
        this.add(pnlEstadistica, BorderLayout.EAST);

        lblMensaje = new JLabel("España");
        lblMensaje.setSize(1920, 1080);
        this.add(lblMensaje, BorderLayout.NORTH);

        this.pack();
        this.setVisible(true);
    }

    public PanelTablaDatos getPnlTabla() {return pnlTabla;}
    public PanelMunicipiosTree getPnlMunicipios() { return pnlMunicipios;}
    public JLabel getLblMensaje() {return lblMensaje;}
    public EstadisticaPanel getPnlEstadistica() {return pnlEstadistica;}
}