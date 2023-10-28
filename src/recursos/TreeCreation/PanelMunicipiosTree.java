package recursos.TreeCreation;

import javax.swing.JScrollPane;

import recursos.Tree;

public class PanelMunicipiosTree extends JScrollPane{
    private static MunicipiosTree munis;
    private Tree padre;
    public PanelMunicipiosTree (Tree ventanaTree){
        super(munis = new MunicipiosTree(ventanaTree));
        padre = ventanaTree;
    }
    public static MunicipiosTree getMunis() {return munis;}
}
