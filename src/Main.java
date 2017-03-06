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
				JPanel general = new JPanel();
				JPanel labelPanel = new JPanel();
				JPanel vehicleTypePanel = new JPanel();
				JPanel emergencyTypePanel = new JPanel();
				JPanel malfunctionTypePanel = new JPanel();
				JPanel numOfPeoplePanel = new JPanel();
				JPanel thresholdPanel = new JPanel();
				JPanel buttonPanel = new JPanel();
				
				general.setLayout(new BoxLayout(general, BoxLayout.X_AXIS));
				labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
				vehicleTypePanel.setLayout(new BoxLayout(vehicleTypePanel, BoxLayout.Y_AXIS));
				emergencyTypePanel.setLayout(new BoxLayout(emergencyTypePanel, BoxLayout.Y_AXIS));
				malfunctionTypePanel.setLayout(new BoxLayout(malfunctionTypePanel, BoxLayout.Y_AXIS));
				numOfPeoplePanel.setLayout(new BoxLayout(numOfPeoplePanel, BoxLayout.Y_AXIS));
				thresholdPanel.setLayout(new BoxLayout(thresholdPanel, BoxLayout.Y_AXIS));
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
				
				JLabel tableLabel = new JLabel("Parameters: ");
				JLabel propertiesLabel = new JLabel("Properties: ");
				JLabel privacyLabel = new JLabel("Privacy Values: ");
				labelPanel.add(tableLabel,BorderLayout.NORTH);
				labelPanel.add(propertiesLabel);
				labelPanel.add(privacyLabel);
				
				
				JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
				JComboBox vehicleTypeChoice = new JComboBox(VEHICLETYPE.values());
				JSpinner vehicleTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)vehicleTypePrivacyField.getEditor()).getTextField().setColumns(3);
				vehicleTypePanel.add(vehicleTypeLabel,BorderLayout.NORTH);
				vehicleTypePanel.add(vehicleTypeChoice);
				vehicleTypePanel.add(vehicleTypePrivacyField);
				
				
				JLabel emergencyTypeLabel = new JLabel("Emergency Type");
				JComboBox emergencyTypeChoice = new JComboBox(EMERGENCYTYPE.values());
				JSpinner emergencyTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)emergencyTypePrivacyField.getEditor()).getTextField().setColumns(3);
				emergencyTypePanel.add(emergencyTypeLabel,BorderLayout.NORTH);
				emergencyTypePanel.add(emergencyTypeChoice);
				emergencyTypePanel.add(emergencyTypePrivacyField);
				
				
				JLabel malfunctionTypeLabel = new JLabel("Malfunction Type");
				JComboBox malfunctionTypeChoice = new JComboBox(MALFUNCTIONTYPE.values());
				JSpinner malfunctionTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)malfunctionTypePrivacyField.getEditor()).getTextField().setColumns(3);
				malfunctionTypePanel.add(malfunctionTypeLabel,BorderLayout.NORTH);
				malfunctionTypePanel.add(malfunctionTypeChoice);
				malfunctionTypePanel.add(malfunctionTypePrivacyField);
				
				
				JLabel numOfPeopleLabel = new JLabel("Number of People");
				JSpinner numOfPeopleSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 50, 1));
				JSpinner numOfPeoplePrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)numOfPeoplePrivacyField.getEditor()).getTextField().setColumns(3);
				numOfPeoplePanel.add(numOfPeopleLabel,BorderLayout.NORTH);
				numOfPeoplePanel.add(numOfPeopleSpinner);
				numOfPeoplePanel.add(numOfPeoplePrivacyField);
				
				
				JLabel thresholdLabel = new JLabel("Threshold");
				JLabel thresholdEmptyLabel = new JLabel(" ");
				JSpinner thresholdPrivacyField = new JSpinner(new SpinnerNumberModel(0.001,0,1,0.001));
				((JSpinner.DefaultEditor)thresholdPrivacyField.getEditor()).getTextField().setColumns(3);
				thresholdEmptyLabel.setPreferredSize(thresholdPrivacyField.getPreferredSize());
				thresholdPanel.add(thresholdLabel,BorderLayout.NORTH);
				thresholdPanel.add(thresholdEmptyLabel);
				thresholdPanel.add(thresholdPrivacyField);
				
				
				
				JButton randomAssignButton = new JButton("Random Properties");
				JButton randomPrivacyValuesButton  = new JButton("Random Privacy Values");
				buttonPanel.add(randomAssignButton,BorderLayout.WEST);
				buttonPanel.add(randomPrivacyValuesButton);
				


				
				
				//Show in the main panel
				general.add(labelPanel, BorderLayout.WEST);
				general.add(vehicleTypePanel);
				general.add(emergencyTypePanel);
				general.add(malfunctionTypePanel);
				general.add(numOfPeoplePanel);
				general.add(thresholdPanel);
				
				
				gui.add(general, BorderLayout.NORTH);
				gui.add(buttonPanel);
				JOptionPane.showMessageDialog(null, gui, "Auction Based Traffic Simulation", JOptionPane.PLAIN_MESSAGE);

			}
			
		};
		SwingUtilities.invokeLater(r);
	}
	


}
