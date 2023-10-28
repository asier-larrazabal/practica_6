
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import recursos.Tree;
import recursos.DataSet.DataSetMunicipios;

public class Ejercicio06_03 {
	
	private static DataSetMunicipios dataMunis;
	public static JFrame window;
	public static void main(String[] args) {
		window = new JFrame( "Ejercicio 6.3" );
		window.setLocationRelativeTo( null );
		window.setSize( 200, 80 );
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btnCarga = new JButton( "Cargar municipios > 100k" );
		window.add( btnCarga );
		
		btnCarga.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargaMunicipios();
			}
		});
		window.setVisible( true );
	}

	private static void cargaMunicipios() {
		window.setVisible(false);
		try {
			dataMunis = new DataSetMunicipios( "src\\Datos\\DatosMunicipios.txt" );
			new Tree();
			// TODO Resolver el ejercicio 6.3
		} catch (IOException e) {
			System.err.println( "Ha habido un error al caragar los municipios" );
		}
	}
}
