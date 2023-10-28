package resource.TreeCreation;

import javax.swing.JScrollPane;

import resource.Tree;

public class PanelMunicipiosTree extends JScrollPane{
    private static MunicipiosTree municipiosTree;
    private Tree parent;
    public PanelMunicipiosTree (Tree ventanaTree){
        super(municipiosTree = new MunicipiosTree(ventanaTree));
        parent = ventanaTree;
    }
    public static MunicipiosTree getMunicipiosTree() {
        return municipiosTree;
    }
}
