import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runnable r = new Runnable() {

			@Override
			public void run() {
				//Create Main Panel
				JPanel gui = new JPanel(new BorderLayout(3, 3));
				gui.setPreferredSize(new Dimension(1200, 400));
				
				//Panel For properties of vehicle
				JPanel propertiesPanel = new JPanel();
				
				JLabel propertiesLabel = new JLabel("Properties: ");
				JComboBox vehicleTypeChoice = new JComboBox(VEHICLETYPE.values());
				JComboBox emergencyTypeChoice = new JComboBox(EMERGENCYTYPE.values());
				JComboBox malfunctionTypeChoice = new JComboBox(MALFUNCTIONTYPE.values());
				JSpinner numOfPeopleSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 50, 1));
				JButton randomAssignButton = new JButton("Random Properties");
				
				propertiesPanel.add(propertiesLabel);
				propertiesPanel.add(vehicleTypeChoice);
				propertiesPanel.add(emergencyTypeChoice);
				propertiesPanel.add(malfunctionTypeChoice);
				propertiesPanel.add(numOfPeopleSpinner);
				propertiesPanel.add(randomAssignButton);
				
				//Panel for privacy Values
				JPanel privacyValuesPanel = new JPanel();
				
				JLabel privacyLabel = new JLabel("Privacy Values: ");
				
				NumberFormat privacyFormatter = NumberFormat.getNumberInstance();
				privacyFormatter.setMaximumIntegerDigits(1);
				privacyFormatter.setMinimumFractionDigits(4);
				
				JSpinner vehicleTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)vehicleTypePrivacyField.getEditor()).getTextField().setColumns(3);
				JSpinner emergencyTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)emergencyTypePrivacyField.getEditor()).getTextField().setColumns(3);
				JSpinner malfunctionTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)malfunctionTypePrivacyField.getEditor()).getTextField().setColumns(3);
				JSpinner numOfPeoplePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)numOfPeoplePrivacyField.getEditor()).getTextField().setColumns(3);
				JSpinner thresholdPrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)thresholdPrivacyField.getEditor()).getTextField().setColumns(3);
				JButton randomPrivacyValuesButton  = new JButton("Random Privacy Values");
				
				privacyValuesPanel.add(privacyLabel);
				privacyValuesPanel.add(vehicleTypePrivacyField);
				privacyValuesPanel.add(emergencyTypePrivacyField);
				privacyValuesPanel.add(malfunctionTypePrivacyField);
				privacyValuesPanel.add(numOfPeoplePrivacyField);
				privacyValuesPanel.add(thresholdPrivacyField);
				privacyValuesPanel.add(randomPrivacyValuesButton);
				
				
				//Show in the main panel
				gui.add(propertiesPanel, BorderLayout.NORTH);
				gui.add(privacyValuesPanel);
				JOptionPane.showMessageDialog(null, gui, "Auction Based Traffic Simulation", JOptionPane.PLAIN_MESSAGE);

			}
			
		};
		SwingUtilities.invokeLater(r);
	}
	


}
