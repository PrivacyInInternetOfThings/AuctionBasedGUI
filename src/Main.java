import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

public class Main {

	public int lastID = 0;
	public static ArrayList<Vehicle> vehicles;
	public static Group group1, group2;

	public static void main(String[] args) {
		vehicles = new ArrayList<>();
		group1 = new Group(1);
		group2 = new Group(2);
		Runnable r = new Runnable() {

			@Override
			public void run() {
				// Create Main Panel
				JPanel gui = new JPanel(new BorderLayout(3, 3));
				gui.setPreferredSize(new Dimension(1200, 400));

				// Panel For properties of vehicle
				JPanel vehiclePanel = new JPanel();
				JPanel vehiclePropertiesPanel = new JPanel();
				JPanel labelPanel = new JPanel();
				JPanel vehicleTypePanel = new JPanel();
				JPanel emergencyTypePanel = new JPanel();
				JPanel malfunctionTypePanel = new JPanel();
				JPanel numOfPeoplePanel = new JPanel();
				JPanel thresholdPanel = new JPanel();
				JPanel buttonPanel = new JPanel();
				JPanel groupListsPanel = new JPanel();
				JPanel group1panel = new JPanel();
				JPanel group2panel = new JPanel();

				// Panels
				vehiclePropertiesPanel.setLayout(new BoxLayout(vehiclePropertiesPanel, BoxLayout.X_AXIS));
				labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
				vehicleTypePanel.setLayout(new BoxLayout(vehicleTypePanel, BoxLayout.Y_AXIS));
				emergencyTypePanel.setLayout(new BoxLayout(emergencyTypePanel, BoxLayout.Y_AXIS));
				malfunctionTypePanel.setLayout(new BoxLayout(malfunctionTypePanel, BoxLayout.Y_AXIS));
				numOfPeoplePanel.setLayout(new BoxLayout(numOfPeoplePanel, BoxLayout.Y_AXIS));
				thresholdPanel.setLayout(new BoxLayout(thresholdPanel, BoxLayout.Y_AXIS));
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
				vehiclePanel.setLayout(new BoxLayout(vehiclePanel, BoxLayout.Y_AXIS));
				groupListsPanel.setLayout(new BoxLayout(groupListsPanel, BoxLayout.X_AXIS));
				

				// Label Panel
				JLabel propertiesLabel = new JLabel("Properties: ");
				JLabel privacyLabel = new JLabel("Privacy Values: ");

				labelPanel.add(propertiesLabel, BorderLayout.NORTH);
				labelPanel.add(privacyLabel);

				// Vehicle Type Panel
				JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
				JComboBox vehicleTypeChoice = new JComboBox(VEHICLETYPE.values());
				JSpinner vehicleTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0, 1, 0.001));
				((JSpinner.DefaultEditor) vehicleTypePrivacyField.getEditor()).getTextField().setColumns(3);
				vehicleTypePanel.add(vehicleTypeLabel, BorderLayout.NORTH);
				vehicleTypePanel.add(vehicleTypeChoice);
				vehicleTypePanel.add(vehicleTypePrivacyField);

				// Emergency Type Panel
				JLabel emergencyTypeLabel = new JLabel("Emergency Type");
				JComboBox emergencyTypeChoice = new JComboBox(EMERGENCYTYPE.values());
				JSpinner emergencyTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0, 1, 0.001));
				((JSpinner.DefaultEditor) emergencyTypePrivacyField.getEditor()).getTextField().setColumns(3);
				emergencyTypePanel.add(emergencyTypeLabel, BorderLayout.NORTH);
				emergencyTypePanel.add(emergencyTypeChoice);
				emergencyTypePanel.add(emergencyTypePrivacyField);

				// Malfunction Panel
				JLabel malfunctionTypeLabel = new JLabel("Malfunction Type");
				JComboBox malfunctionTypeChoice = new JComboBox(MALFUNCTIONTYPE.values());
				JSpinner malfunctionTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0, 1, 0.001));
				((JSpinner.DefaultEditor) malfunctionTypePrivacyField.getEditor()).getTextField().setColumns(3);
				malfunctionTypePanel.add(malfunctionTypeLabel, BorderLayout.NORTH);
				malfunctionTypePanel.add(malfunctionTypeChoice);
				malfunctionTypePanel.add(malfunctionTypePrivacyField);

				// Num Of People Panel
				JLabel numOfPeopleLabel = new JLabel("Number of People");
				JSpinner numOfPeopleSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 50, 1));
				JSpinner numOfPeoplePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0, 1, 0.001));
				((JSpinner.DefaultEditor) numOfPeoplePrivacyField.getEditor()).getTextField().setColumns(3);
				numOfPeoplePanel.add(numOfPeopleLabel, BorderLayout.NORTH);
				numOfPeoplePanel.add(numOfPeopleSpinner);
				numOfPeoplePanel.add(numOfPeoplePrivacyField);

				// Threshold Panel
				JLabel thresholdLabel = new JLabel("Threshold");
				JLabel thresholdEmptyLabel = new JLabel(" ");
				JSpinner thresholdPrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0, 1, 0.001));
				((JSpinner.DefaultEditor) thresholdPrivacyField.getEditor()).getTextField().setColumns(3);
				thresholdEmptyLabel.setPreferredSize(thresholdPrivacyField.getPreferredSize());
				thresholdPanel.add(thresholdLabel, BorderLayout.NORTH);
				thresholdPanel.add(thresholdEmptyLabel);
				thresholdPanel.add(thresholdPrivacyField);

				// Buttons
				JButton randomAssignButton = new JButton("Random Properties");
				randomAssignButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Random rand = new Random();
						vehicleTypeChoice
								.setSelectedItem(VEHICLETYPE.values()[rand.nextInt(VEHICLETYPE.values().length)]);
						emergencyTypeChoice
								.setSelectedItem(EMERGENCYTYPE.values()[rand.nextInt(EMERGENCYTYPE.values().length)]);
						malfunctionTypeChoice.setSelectedItem(
								MALFUNCTIONTYPE.values()[rand.nextInt(MALFUNCTIONTYPE.values().length)]);
						numOfPeopleSpinner.setValue(rand.nextDouble());
					}
				});

				JButton randomPrivacyValuesButton = new JButton("Random Privacy Values");
				randomPrivacyValuesButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Random rand = new Random();
						vehicleTypePrivacyField.setValue(rand.nextDouble());
						emergencyTypePrivacyField.setValue(rand.nextDouble());
						malfunctionTypePrivacyField.setValue(rand.nextFloat());
						numOfPeoplePrivacyField.setValue(rand.nextFloat());
						thresholdPrivacyField.setValue(rand.nextDouble());
					}
				});

				JButton addVehicle1Button = new JButton("Add Vehicle to Group 1");
				addVehicle1Button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				
				JButton addVehicle2Button = new JButton("Add Vehicle to Group 2");
				addVehicle2Button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				buttonPanel.add(randomAssignButton, BorderLayout.WEST);
				buttonPanel.add(randomPrivacyValuesButton);
				buttonPanel.add(addVehicle1Button);
				buttonPanel.add(addVehicle2Button);

				//Group 1 Panel
				JLabel group1label = new JLabel("GROUP 1");
				ArrayList<String> group1str = new ArrayList<>();
				for(Vehicle v: group1.vehicles){
					group1str.add(v.toString());
				}
				
				JList<Object> group1list = new JList<>( group1str.toArray() );
				
				group1panel.add(group1label, BorderLayout.CENTER);
				group1panel.add(group1list);
				
				
				
				
				//Group 2 panel
				JLabel group2label = new JLabel("GROUP 2");
				ArrayList<String> group2str = new ArrayList<>();
				for(Vehicle v: group2.vehicles){
					group1str.add(v.toString());
				}
				
				JList<Object> group2list = new JList<>( group2str.toArray() );
				
				group2panel.add(group2label, BorderLayout.CENTER);
				group2panel.add(group2list);
				

				// Show in the main panel
				vehiclePropertiesPanel.add(labelPanel, BorderLayout.WEST);
				vehiclePropertiesPanel.add(vehicleTypePanel);
				vehiclePropertiesPanel.add(emergencyTypePanel);
				vehiclePropertiesPanel.add(malfunctionTypePanel);
				vehiclePropertiesPanel.add(numOfPeoplePanel);
				vehiclePropertiesPanel.add(thresholdPanel);

				vehiclePanel.add(vehiclePropertiesPanel);
				vehiclePanel.add(buttonPanel);
				
				groupListsPanel.add(group1panel);
				groupListsPanel.add(group2panel);
				

				gui.add(vehiclePanel, BorderLayout.NORTH);
				gui.add(groupListsPanel);

				JOptionPane.showMessageDialog(null, gui, "Auction Based Traffic Simulation", JOptionPane.PLAIN_MESSAGE);

			}

		};
		SwingUtilities.invokeLater(r);
	}

}
