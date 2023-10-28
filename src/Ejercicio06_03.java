
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
		window = new JFrame( "Practica 6" );
		window.setLocationRelativeTo( null );
		window.setSize( 400, 80 );
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
		} catch (IOException e) {
			System.err.println( "Ha habido un error al caragar los municipios" );
		}
	}
}
