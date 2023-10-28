package recursos.TreeCreation;

import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import recursos.Tree;
import recursos.DataSet.DataSetMunicipios;
import recursos.DataSet.Municipio;

public class MunicipiosTree extends JTree {
    private Tree padre;

    public MunicipiosTree (Tree padre){
        super(DataSetMunicipios.getDataSetMunicipios().getHashTableForTree());
        this.padre = padre;
        this.setEditable(false);

        this.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                String[] nodeName = getNodeNameByPath(e);
                TreeSet<Municipio> municipios = getMunicipiosInNode(nodeName);
                padre.getPnlTabla().changeTable(municipios);
                padre.getLblMensaje().setText(String.join("/", nodeName));
                padre.getPnlEstadistica().ActualizarBarras();
            }
        });
    }

    private String[] getNodeNameByPath(TreeSelectionEvent e){
        String[] names = new String[e.getPath().getPathCount()];
        TreePath treePath = e.getPath();
        int i = 0;
        while (treePath != null) {
            names[i] = treePath.getLastPathComponent().toString();
            treePath = treePath.getParentPath();
            i++;
        }
        return(names);
    }

    public TreeSet<Municipio> getMunicipiosInNode(String[] name){
        DataSetMunicipios dataset = DataSetMunicipios.getDataSetMunicipios();
        switch (name.length) {
            case 4:
                Municipio municipio = dataset.getHashTableForTree().get(name[2]).get(name[1]).get(name[0]);
                TreeSet<Municipio> municipios = new TreeSet<Municipio>();
                municipios.add(municipio);
                return municipios;
            case 3:
                return dataset.getProvinciaHashTable(name[1], name[0]);
            case 2:
                TreeSet<Municipio> municipiosTotal = new TreeSet<>();
                Hashtable <String, TreeSet<Municipio>> autonomia = dataset.getAutonomiaHasTable(name[0]);
                Set<String> provinciasTotales = autonomia.keySet();
                for(String provincia: provinciasTotales){
                    municipiosTotal.addAll(autonomia.get(provincia));
                }
                return municipiosTotal;
        }
        return null;
    }
}
