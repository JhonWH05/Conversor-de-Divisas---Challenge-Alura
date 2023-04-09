package Diseño;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import Conversor.ApiDivisas;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;

public class MenuMoneda extends JFrame {

	private JPanel PanelMoneda;
	private JTextField textField;
	private JLabel lblResultado;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println(ApiDivisas.recuperarMonedas());
					MenuMoneda frame = new MenuMoneda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuMoneda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 767, 272);
		PanelMoneda = new JPanel();
		PanelMoneda.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(PanelMoneda);
		PanelMoneda.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 751, 233);
		PanelMoneda.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Conversor de Divisas");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(271, 11, 236, 44);
		panel.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setBounds(35, 110, 195, 22);
		this.inicializarLista(comboBox);
		panel.add(comboBox);
		
		textField = new JTextField();	
		textField.setDocument(new SoloNumerosDocument());
		textField.setBounds(283, 111, 195, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(524, 110, 217, 22);
		this.inicializarLista(comboBox_1);
		panel.add(comboBox_1);
		
		JLabel lblNewLabel_1 = new JLabel("Valor");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1.setBounds(353, 79, 53, 19);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Desde Divisa");
		lblNewLabel_2.setFont(new Font("Yu Gothic Light", Font.BOLD, 16));
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(86, 82, 144, 22);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Hasta Divisa");
		lblNewLabel_2_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_2_1.setFont(new Font("Yu Gothic Light", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(568, 82, 144, 22);
		panel.add(lblNewLabel_2_1);
		
		JButton btnConvertir = new JButton("Convertir");
		btnConvertir.setForeground(new Color(255, 255, 255));
		btnConvertir.setBackground(new Color(51, 153, 153));
		btnConvertir.setBounds(338, 166, 89, 23);
		btnConvertir.addActionListener(this::botonClick);
		panel.add(btnConvertir);
		
		lblResultado = new JLabel("Resultado");
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblResultado.setForeground(new Color(255, 255, 255));
		lblResultado.setBounds(524, 163, 188, 22);
		panel.add(lblResultado);
	}
	
	private void inicializarLista (JComboBox<String> lista) {
		String[] nombres = ApiDivisas.getNombres().toArray(String[]::new);
		lista.setModel(new DefaultComboBoxModel<String>(nombres));
	}
	
	private String recuperarNombre(JComboBox lista) {
		return(String)lista.getSelectedItem();
	}
	 
	private void botonClick(ActionEvent e) {
		String nombre1 = recuperarNombre(comboBox);
		String nombre2 = recuperarNombre(comboBox_1);
		double tasaDeCambio= ApiDivisas.recuperarTasaDeCambio(nombre1, nombre2);
		
		String valorEntrada = textField.getText();
		double valor = 0;
		
		if(valorEntrada.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe Ingresar Un Valor numerico");
		} else {
			
		valor =	Double.parseDouble(valorEntrada);
		double salida = valor * tasaDeCambio;
		lblResultado.setText(String.format("%.8f", salida));
		}
		
		
		//double valor= Double.parseDouble(textField.getText());
		
		
		
	}
	private class SoloNumerosDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return;
                }
            }

            super.insertString(offs, str, a);
        }
    }

}
