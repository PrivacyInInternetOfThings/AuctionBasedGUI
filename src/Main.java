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

	public static int lastID = 0;
	public static ArrayList<Vehicle> vehicles;
	public static Group group1, group2;


	public static void main(String[] args) {
		vehicles = new ArrayList<>();
		group1 = new Group(1);
		group2 = new Group(2);
		Runnable r = new Runnable() {

			@Override
			public void run() {
				//Create Main JFrame
				JFrame mainFrame = new JFrame();
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// Create Main Panel
				JPanel mainPanel = new JPanel(new CardLayout());
				
				//Create Setup Panel
				JPanel setupPanel = new JPanel(new BorderLayout(3, 3));
				setupPanel.setPreferredSize(new Dimension(1200, 400));
				
				//Create Auction Panel
				JPanel auctionPanel = new JPanel();

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
				group1panel.setLayout(new BoxLayout(group1panel, BoxLayout.Y_AXIS));
				group2panel.setLayout(new BoxLayout(group2panel, BoxLayout.Y_AXIS));

				// Label Panel
				JLabel propertiesLabel = new JLabel("Properties: ");
				JLabel privacyLabel = new JLabel("Privacy Values: ");

				labelPanel.add(propertiesLabel, BorderLayout.NORTH);
				labelPanel.add(privacyLabel);

				// Vehicle Type Panel
				JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
				JComboBox vehicleTypeChoice = new JComboBox(VEHICLETYPE.values());
				vehicleTypeChoice.setSelectedItem(VEHICLETYPE.ORDINARY);
				JSpinner vehicleTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0, 1, 0.001));
				((JSpinner.DefaultEditor) vehicleTypePrivacyField.getEditor()).getTextField().setColumns(3);
				vehicleTypePanel.add(vehicleTypeLabel, BorderLayout.NORTH);
				vehicleTypePanel.add(vehicleTypeChoice);
				vehicleTypePanel.add(vehicleTypePrivacyField);

				// Emergency Type Panel
				JLabel emergencyTypeLabel = new JLabel("Emergency Type");
				JComboBox emergencyTypeChoice = new JComboBox(EMERGENCYTYPE.values());
				emergencyTypeChoice.setSelectedItem(EMERGENCYTYPE.NOEMERGENCY);
				JSpinner emergencyTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0, 1, 0.001));
				((JSpinner.DefaultEditor) emergencyTypePrivacyField.getEditor()).getTextField().setColumns(3);
				emergencyTypePanel.add(emergencyTypeLabel, BorderLayout.NORTH);
				emergencyTypePanel.add(emergencyTypeChoice);
				emergencyTypePanel.add(emergencyTypePrivacyField);

				// Malfunction Panel
				JLabel malfunctionTypeLabel = new JLabel("Malfunction Type");
				JComboBox malfunctionTypeChoice = new JComboBox(MALFUNCTIONTYPE.values());
				malfunctionTypeChoice.setSelectedItem(MALFUNCTIONTYPE.NOMALFUNCTION);
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

				// Group 1 Panel
				JLabel group1label = new JLabel("GROUP 1");
				DefaultListModel<String> group1listModel = new DefaultListModel<>();
				for (Vehicle v : group1.vehicles) {
					group1listModel.addElement(v.toString());
				}

				JList group1list = new JList(group1listModel);
				JScrollPane group1listScrollPane = new JScrollPane(group1list);
				group1panel.add(group1label, BorderLayout.CENTER);
				group1panel.add(group1listScrollPane);

				// Group 2 panel
				JLabel group2label = new JLabel("GROUP 2");
				DefaultListModel<String> group2listModel = new DefaultListModel<>();
				for (Vehicle v : group2.vehicles) {
					group2listModel.addElement(v.toString());
				}

				JList group2list = new JList(group2listModel);
				JScrollPane group2listScrollPane = new JScrollPane(group2list);
				group2panel.add(group2label, BorderLayout.CENTER);
				group2panel.add(group2listScrollPane);
				
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
						numOfPeopleSpinner.setValue(rand.nextInt(50));
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
						Vehicle newVehicle = new Vehicle((VEHICLETYPE) vehicleTypeChoice.getSelectedItem(),
								(EMERGENCYTYPE) emergencyTypeChoice.getSelectedItem(),
								(MALFUNCTIONTYPE) malfunctionTypeChoice.getSelectedItem(),
								(Integer) numOfPeopleSpinner.getValue(), ++lastID);
						group1.addVehicle(newVehicle);
						group1listModel.addElement(newVehicle.toString());
					}
				});

				JButton addVehicle2Button = new JButton("Add Vehicle to Group 2");
				addVehicle2Button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Vehicle newVehicle = new Vehicle((VEHICLETYPE) vehicleTypeChoice.getSelectedItem(),
								(EMERGENCYTYPE) emergencyTypeChoice.getSelectedItem(),
								(MALFUNCTIONTYPE) malfunctionTypeChoice.getSelectedItem(),
								(Integer) numOfPeopleSpinner.getValue(), ++lastID);
						group2.addVehicle(newVehicle);
						group2listModel.addElement(newVehicle.toString());

					}
				});
				buttonPanel.add(randomAssignButton, BorderLayout.WEST);
				buttonPanel.add(randomPrivacyValuesButton);
				buttonPanel.add(addVehicle1Button);
				buttonPanel.add(addVehicle2Button);

				// Start Auction Button
				JButton startAuctionButton = new JButton("Start Auction");
				startAuctionButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						CardLayout cl = (CardLayout) (mainPanel.getLayout());//get cards
		                cl.next(mainPanel);
						
					}
				});
				
				// Show in the main panel
				vehiclePropertiesPanel.add(labelPanel, BorderLayout.WEST);
				vehiclePropertiesPanel.add(vehicleTypePanel);
				vehiclePropertiesPanel.add(emergencyTypePanel);
				vehiclePropertiesPanel.add(malfunctionTypePanel);
				vehiclePropertiesPanel.add(numOfPeoplePanel);
				vehiclePropertiesPanel.add(thresholdPanel);

				vehiclePanel.add(vehiclePropertiesPanel);
				vehiclePanel.add(buttonPanel);

				groupListsPanel.add(group1panel, BorderLayout.WEST);
				groupListsPanel.add(group2panel);

				setupPanel.add(vehiclePanel, BorderLayout.NORTH);
				setupPanel.add(groupListsPanel);
				setupPanel.add(startAuctionButton,BorderLayout.SOUTH);

				// Auction Panel
				JButton goBackToSetupButton = new JButton("Go Back To Setup");
				goBackToSetupButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						CardLayout cl = (CardLayout) (mainPanel.getLayout());//get cards
		                cl.next(mainPanel);	
					}
				});
				auctionPanel.add(goBackToSetupButton);
				
				
				mainPanel.add(setupPanel, "Setup Vehicles");
				mainPanel.add(auctionPanel, "Auction");
				
				mainFrame.add(mainPanel);
				
				mainFrame.pack();
		        mainFrame.setVisible(true);
				
				//JOptionPane.showMessageDialog(null, mainFrame, "Auction Based Traffic Simulation", JOptionPane.PLAIN_MESSAGE);

			}

		};
		SwingUtilities.invokeLater(r);
	}

}
