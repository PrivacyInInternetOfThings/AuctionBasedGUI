
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

public class Main {

	
	public static DatabaseController dbController;
	public static ArrayList<Vehicle> vehicles = new ArrayList<>();
	public static Group[] groups = { new Group(0), new Group(1) };
	public static DefaultListModel<String>[] groupListModel = new DefaultListModel[2];
	public static DefaultListModel<String>[] groupListModelImage = new DefaultListModel[2];
	public static DefaultListModel<String>[] groupListModelOffer = new DefaultListModel[2];
	public static boolean clickedNextTurn = false;
	public static double[] oldUtilities = { 0, 0 };
	public static double[] utilities = { 0, 0 };
	public static int turn = 0;
	public static Map<String, ImageIcon> imageMap = null;
	// public static ArrayList<String>[] vehicleTypes = new ArrayList[2];

	public static Map<String, ImageIcon> createImageMap() {
		Map<String, ImageIcon> map = new HashMap<>();
		try {
			map.put("AMBULANCE", new ImageIcon(new URL(
					"http://icdn.pro/images/en/c/a/car-emergency-ambulance-transport-vehicle-icone-4771-128.png")));
			map.put("FIRETRUCK",
					new ImageIcon(new URL("http://iconbug.com/download/size/128/icon/1475/red-fire-truck/")));
			map.put("POLICE", new ImageIcon(new URL(
					"https://d2ujflorbtfzji.cloudfront.net/key-image/fca5557d-41ee-4e44-a17a-0b99d187e9ab.png")));
			map.put("CAR", new ImageIcon(
					new URL("https://cdn0.iconfinder.com/data/icons/classic-cars-by-cemagraphics/128/red_128.png")));
			map.put("TAXI", new ImageIcon(new URL("https://image.flaticon.com/icons/png/128/75/75780.png")));
			map.put("VAN", new ImageIcon(new URL(
					"http://image.trucktrend.com/f/40978839+w128+h128+re0+cr1+ar0/1206dp-01%2B2011-gmc-savana-3500-cargo-van%2Bgmc-cargo-van-front-three-quarter.jpg")));
			map.put("BUS", new ImageIcon(new URL(
					"https://static.turbosquid.com/Preview/2016/08/30__15_43_38/2015_bluebird_school_00001200.jpgFEC98E46-5B35-497F-84E2-F446AF5F5542Medium.jpg")));
			map.put("MINIBUS", new ImageIcon(new URL(
					"http://img.tamindir.com/rs/128x128/ti_e_ul/canerdil/p/minibus-dolmus-soforu-logo_300x300.png")));
			map.put("MOTORCYCLE", new ImageIcon(new URL(
					"https://static.turbosquid.com/Preview/2014/05/12__18_42_15/green0000.jpgcb9e7cff-0749-4105-967f-7ce82fa010a0Medium.jpg")));
			map.put("AGRICULTURALVEHICLE", new ImageIcon(new URL(
					"http://img.tamindir.com/rs/128x128/ti_e_ul/alpercet/p/traktor-simulatoru-logo_300x300.jpg")));
			map.put("OTHER", new ImageIcon(new URL(
					"https://cdn1.iconfinder.com/data/icons/rounded-flat-country-flag-collection-1/2000/_Unknown.png")));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) throws UnknownHostException {
		dbController = new DatabaseController();
		Runnable r = new Runnable() {

			@Override
			public void run() {
				// Create Main JFrame
				JFrame mainFrame = new JFrame("Privacy Preservation Via Auction");
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Create Main Panel
				JPanel mainPanel = new JPanel(new CardLayout());

				// Create Setup Panel
				JPanel setupPanel = new JPanel(new BorderLayout(3, 3));
				setupPanel.setPreferredSize(new Dimension(1200, 800));

				// Create Auction Panel
				JPanel auctionPanel = new JPanel();

				// Panel For properties of vehicle
				JPanel vehiclePanel = new JPanel();
				JPanel vehiclePropertiesPanel = new JPanel();
				JPanel labelPanel = new JPanel();
				JPanel vehicleTypePanel = new JPanel();
				JPanel journeyTypePanel = new JPanel();
				JPanel malfunctionTypePanel = new JPanel();
				JPanel ageOfVehiclePanel = new JPanel();
				JPanel thresholdPanel = new JPanel();
				JPanel buttonPanel = new JPanel();
				JPanel importPanel = new JPanel();
				JPanel groupListsPanel = new JPanel();
				JPanel[] groupPanel = { new JPanel(), new JPanel() };
				JPanel buttonAuctionPanel = new JPanel();
				JPanel groupImagesPanel = new JPanel();
				JPanel[] imagesPanel = { new JPanel(), new JPanel() };
				JPanel offerListsPanel = new JPanel();
				JPanel[] offersPanel = { new JPanel(), new JPanel() };

				// Panels
				auctionPanel.setLayout(new BoxLayout(auctionPanel, BoxLayout.Y_AXIS));
				vehiclePropertiesPanel.setLayout(new BoxLayout(vehiclePropertiesPanel, BoxLayout.X_AXIS));
				labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
				vehicleTypePanel.setLayout(new BoxLayout(vehicleTypePanel, BoxLayout.Y_AXIS));
				journeyTypePanel.setLayout(new BoxLayout(journeyTypePanel, BoxLayout.Y_AXIS));
				malfunctionTypePanel.setLayout(new BoxLayout(malfunctionTypePanel, BoxLayout.Y_AXIS));
				ageOfVehiclePanel.setLayout(new BoxLayout(ageOfVehiclePanel, BoxLayout.Y_AXIS));
				thresholdPanel.setLayout(new BoxLayout(thresholdPanel, BoxLayout.Y_AXIS));
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
				importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.X_AXIS));
				buttonAuctionPanel.setLayout(new BoxLayout(buttonAuctionPanel, BoxLayout.X_AXIS));
				vehiclePanel.setLayout(new BoxLayout(vehiclePanel, BoxLayout.Y_AXIS));
				groupListsPanel.setLayout(new BoxLayout(groupListsPanel, BoxLayout.X_AXIS));
				groupPanel[0].setLayout(new BoxLayout(groupPanel[0], BoxLayout.Y_AXIS));
				groupPanel[1].setLayout(new BoxLayout(groupPanel[1], BoxLayout.Y_AXIS));
				groupImagesPanel.setLayout(new BoxLayout(groupImagesPanel, BoxLayout.X_AXIS));
				imagesPanel[0].setLayout(new BoxLayout(imagesPanel[0], BoxLayout.Y_AXIS));
				imagesPanel[1].setLayout(new BoxLayout(imagesPanel[1], BoxLayout.Y_AXIS));
				offerListsPanel.setLayout(new BoxLayout(offerListsPanel, BoxLayout.X_AXIS));
				offersPanel[0].setLayout(new BoxLayout(offersPanel[0], BoxLayout.Y_AXIS));
				offersPanel[1].setLayout(new BoxLayout(offersPanel[1], BoxLayout.Y_AXIS));

				// Label Panel
				JLabel propertiesLabel = new JLabel("Properties: ");
				JLabel privacyLabel = new JLabel("Privacy Values: ");

				labelPanel.add(propertiesLabel, BorderLayout.NORTH);
				labelPanel.add(privacyLabel);

				// Vehicle Type Panel
				JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
				JComboBox vehicleTypeChoice = new JComboBox(VEHICLETYPE.values());
				vehicleTypeChoice.setSelectedItem(VEHICLETYPE.CAR);
				JSpinner vehicleTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) vehicleTypePrivacyField.getEditor()).getTextField().setColumns(3);
				vehicleTypePanel.add(vehicleTypeLabel, BorderLayout.NORTH);
				vehicleTypePanel.add(vehicleTypeChoice);
				vehicleTypePanel.add(vehicleTypePrivacyField);

				// Journey of Purpose Panel
				JLabel journeyTypeLabel = new JLabel("Journey of Purpose");
				JComboBox journeyTypeChoice = new JComboBox(JOURNEYPURPOSE.values());
				journeyTypeChoice.setSelectedItem(JOURNEYPURPOSE.OTHER);
				JSpinner journeyTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) journeyTypePrivacyField.getEditor()).getTextField().setColumns(3);
				journeyTypePanel.add(journeyTypeLabel, BorderLayout.NORTH);
				journeyTypePanel.add(journeyTypeChoice);
				journeyTypePanel.add(journeyTypePrivacyField);

				// Malfunction Panel
				JLabel malfunctionTypeLabel = new JLabel("Malfunction Type");
				JComboBox malfunctionTypeChoice = new JComboBox(MALFUNCTIONTYPE.values());
				malfunctionTypeChoice.setSelectedItem(MALFUNCTIONTYPE.NOMALFUNCTION);
				JSpinner malfunctionTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) malfunctionTypePrivacyField.getEditor()).getTextField().setColumns(3);
				malfunctionTypePanel.add(malfunctionTypeLabel, BorderLayout.NORTH);
				malfunctionTypePanel.add(malfunctionTypeChoice);
				malfunctionTypePanel.add(malfunctionTypePrivacyField);

				// Age Of Vehicle Panel
				JLabel ageOfVehicleLabel = new JLabel("Age Of Vehicle");
				JSpinner ageOfVehicleSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 50, 1));
				JSpinner ageOfVehiclePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) ageOfVehiclePrivacyField.getEditor()).getTextField().setColumns(3);
				ageOfVehiclePanel.add(ageOfVehicleLabel, BorderLayout.NORTH);
				ageOfVehiclePanel.add(ageOfVehicleSpinner);
				ageOfVehiclePanel.add(ageOfVehiclePrivacyField);

				// Threshold Panel
				JLabel thresholdLabel = new JLabel("Threshold");
				JLabel thresholdEmptyLabel = new JLabel(" ");
				// JSpinner thresholdAgeSpinner = new JSpinner(new
				// SpinnerNumberModel(1, 0, 50, 1));
				JSpinner thresholdPrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) thresholdPrivacyField.getEditor()).getTextField().setColumns(3);
				thresholdEmptyLabel.setPreferredSize(thresholdPrivacyField.getPreferredSize());
				thresholdPanel.add(thresholdLabel, BorderLayout.NORTH);
				thresholdPanel.add(thresholdEmptyLabel);
				thresholdPanel.add(thresholdPrivacyField);

				// Group Panels

				JList[] groupList = new JList[2];
				JScrollPane[] groupListScrollPane = new JScrollPane[2];
				JButton[] removeFromGroupButton = new JButton[2];
				for (int i = 0; i < 2; i++) {
					final int index = i;
					JLabel groupLabel = new JLabel("GROUP " + index);
					groupListModel[index] = new DefaultListModel<>();
					for (Vehicle v : groups[index].vehicles) {
						groupListModel[index].addElement(v.toString());
					}

					groupList[index] = new JList(groupListModel[index]);
					groupListScrollPane[index] = new JScrollPane(groupList[index]);

					removeFromGroupButton[index] = new JButton("Remove Selected Vehicle From " + i);
					removeFromGroupButton[index].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							int selected = groupList[index].getSelectedIndex();

						}
					});

					groupPanel[index].add(groupLabel, BorderLayout.CENTER);
					groupPanel[index].add(groupListScrollPane[index]);
				}

				// Buttons
				JButton randomAssignButton = new JButton("Random Properties");
				randomAssignButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Random rand = new Random();
						vehicleTypeChoice
								.setSelectedItem(VEHICLETYPE.values()[rand.nextInt(VEHICLETYPE.values().length)]);
						journeyTypeChoice
								.setSelectedItem(JOURNEYPURPOSE.values()[rand.nextInt(JOURNEYPURPOSE.values().length)]);
						malfunctionTypeChoice.setSelectedItem(
								MALFUNCTIONTYPE.values()[rand.nextInt(MALFUNCTIONTYPE.values().length)]);
						ageOfVehicleSpinner.setValue(rand.nextInt(50));
						// thresholdAgeSpinner.setValue(rand.nextInt(20));
					}
				});

				JButton randomPrivacyValuesButton = new JButton("Random Privacy Values");
				randomPrivacyValuesButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Random rand = new Random();
						vehicleTypePrivacyField.setValue(rand.nextDouble());
						journeyTypePrivacyField.setValue(rand.nextDouble());
						malfunctionTypePrivacyField.setValue(rand.nextDouble());
						ageOfVehiclePrivacyField.setValue(rand.nextDouble());
						thresholdPrivacyField.setValue(rand.nextDouble());
					}
				});

				JButton addVehicle1Button = new JButton("Add Vehicle to Group 0");
				addVehicle1Button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Vehicle newVehicle = new Vehicle((VEHICLETYPE) vehicleTypeChoice.getSelectedItem(),
								(JOURNEYPURPOSE) journeyTypeChoice.getSelectedItem(),
								(MALFUNCTIONTYPE) malfunctionTypeChoice.getSelectedItem(),
								(Integer) ageOfVehicleSpinner.getValue());
						newVehicle.setPrivacy((double) vehicleTypePrivacyField.getValue(),
								(double) journeyTypePrivacyField.getValue(),
								(double) malfunctionTypePrivacyField.getValue(),
								(double) ageOfVehiclePrivacyField.getValue());
						newVehicle.setThreshold((double) thresholdPrivacyField.getValue());
						groups[0].addVehicle(newVehicle);
						groupListModel[0].addElement(newVehicle.toString());
						groupListModelImage[0].addElement(newVehicle.vehicleType.toString());
					}
				});

				JButton addVehicle2Button = new JButton("Add Vehicle to Group 1");
				addVehicle2Button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Vehicle newVehicle = new Vehicle((VEHICLETYPE) vehicleTypeChoice.getSelectedItem(),
								(JOURNEYPURPOSE) journeyTypeChoice.getSelectedItem(),
								(MALFUNCTIONTYPE) malfunctionTypeChoice.getSelectedItem(),
								(Integer) ageOfVehicleSpinner.getValue());
						newVehicle.setPrivacy((double) vehicleTypePrivacyField.getValue(),
								(double) journeyTypePrivacyField.getValue(),
								(double) malfunctionTypePrivacyField.getValue(),
								(double) ageOfVehiclePrivacyField.getValue());
						newVehicle.setThreshold((double) thresholdPrivacyField.getValue());
						groups[1].addVehicle(newVehicle);
						groupListModel[1].addElement(newVehicle.toString());
						groupListModelImage[1].addElement(newVehicle.vehicleType.toString());

					}
				});
				buttonPanel.add(randomAssignButton, BorderLayout.WEST);
				buttonPanel.add(randomPrivacyValuesButton);
				buttonPanel.add(addVehicle1Button);
				buttonPanel.add(addVehicle2Button);

				// Import Buttons
//				String[] incidents = { "incident 0", "incident 1", "incident 2", "incident 3", "incident 4","incident 5" };
				ArrayList<String> accidentsIndexes = (ArrayList<String>) dbController.getAccidentIndexes();
				JComboBox importChoice = new JComboBox(accidentsIndexes.toArray());
				JButton importButton = new JButton("Import Incident");
				importButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String str = String.valueOf(importChoice.getSelectedItem());
						ArrayList<Vehicle> v = dbController.getVehiclesByAccidentIndex(str.substring(16,29));
						importVehicles(v);
					}
				});
				importPanel.add(importChoice);
				importPanel.add(importButton);

				// Start Auction Button
				JButton startAuctionButton = new JButton("Start Auction");
				startAuctionButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CardLayout cl = (CardLayout) (mainPanel.getLayout());// get
																				// cards
						cl.next(mainPanel);
						if (!groups[0].vehicles.isEmpty() || !groups[1].vehicles.isEmpty()) {
							// nextTurn();
							displayGroups();
						}

					}
				});

				// Show in the main panel
				vehiclePropertiesPanel.add(labelPanel, BorderLayout.WEST);
				vehiclePropertiesPanel.add(vehicleTypePanel);
				vehiclePropertiesPanel.add(journeyTypePanel);
				vehiclePropertiesPanel.add(malfunctionTypePanel);
				vehiclePropertiesPanel.add(ageOfVehiclePanel);
				vehiclePropertiesPanel.add(thresholdPanel);

				vehiclePanel.add(vehiclePropertiesPanel);
				vehiclePanel.add(buttonPanel);
				vehiclePanel.add(importPanel);

				groupListsPanel.add(groupPanel[0], BorderLayout.WEST);
				groupListsPanel.add(groupPanel[1]);

				setupPanel.add(vehiclePanel, BorderLayout.NORTH);
				setupPanel.add(groupListsPanel);
				setupPanel.add(startAuctionButton, BorderLayout.SOUTH);

				// Auction Panel
				JButton nextTurnButton = new JButton("Next Turn");
				nextTurnButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (!groups[0].vehicles.isEmpty() || !groups[1].vehicles.isEmpty()) {
							nextTurn();
							displayGroups();
							updateOffers();
						}

					}
				});
				buttonAuctionPanel.add(nextTurnButton, BorderLayout.NORTH);

				JButton goBackToSetupButton = new JButton("Go Back To Setup");
				goBackToSetupButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CardLayout cl = (CardLayout) (mainPanel.getLayout());// get
																				// cards
						cl.next(mainPanel);
					}
				});
				buttonAuctionPanel.add(goBackToSetupButton);

				JList[] groupListOffer = new JList[2];
				JScrollPane[] groupListScrollPaneOffer = new JScrollPane[2];
				for (int i = 0; i < 2; i++) {
					final int index = i;
					JLabel groupLabelOffer = new JLabel("OFFER OF GROUP " + index);

					groupListModelOffer[index] = new DefaultListModel<>();
					groupListModelOffer[index].addElement("Vehicle Type: ?");
					groupListModelOffer[index].addElement("Journey of Purpose: ?");
					groupListModelOffer[index].addElement("Malfunction Type: ?");
					groupListModelOffer[index].addElement("Number of People: ?");

					groupListOffer[index] = new JList(groupListModelOffer[index]);
					groupListScrollPaneOffer[index] = new JScrollPane(groupListOffer[index]);
					groupListScrollPaneOffer[index].setPreferredSize(new Dimension(200, 100));

					offersPanel[index].add(groupLabelOffer, BorderLayout.CENTER);
					offersPanel[index].add(groupListScrollPaneOffer[index]);
				}

				offerListsPanel.add(offersPanel[0], BorderLayout.WEST);
				offerListsPanel.add(offersPanel[1]);

				imageMap = createImageMap();
				JList[] groupListImage = new JList[2];
				JScrollPane[] groupListScrollPaneImage = new JScrollPane[2];
				for (int i = 0; i < 2; i++) {
					final int index = i;
					JLabel groupLabelImage = new JLabel("GROUP " + index);

					groupListModelImage[index] = new DefaultListModel<>();
					for (Vehicle v : groups[index].vehicles) {
						groupListModelImage[index].addElement(v.vehicleType.toString());
					}

					groupListImage[index] = new JList(groupListModelImage[index]);
					groupListImage[index].setCellRenderer(new MarioListRenderer(index));
					groupListScrollPaneImage[index] = new JScrollPane(groupListImage[index]);
					groupListScrollPaneImage[index].setPreferredSize(new Dimension(300, 600));

					imagesPanel[index].add(groupLabelImage, BorderLayout.CENTER);
					imagesPanel[index].add(groupListScrollPaneImage[index]);
				}

				groupImagesPanel.add(imagesPanel[0], BorderLayout.WEST);
				groupImagesPanel.add(imagesPanel[1]);

				auctionPanel.add(buttonAuctionPanel, BorderLayout.NORTH);
				auctionPanel.add(groupImagesPanel);
				auctionPanel.add(offerListsPanel);

				mainPanel.add(setupPanel, "Setup Vehicles");
				mainPanel.add(auctionPanel, "Auction");

				mainFrame.add(mainPanel);

				mainFrame.pack();
				mainFrame.setVisible(true);

				// JOptionPane.showMessageDialog(null, mainFrame, "Auction Based
				// Traffic Simulation", JOptionPane.PLAIN_MESSAGE);

			}

		};
		SwingUtilities.invokeLater(r);
	}

	public static void nextTurn() {
		System.out.println("Turn Of Group " + (turn));
		if (groups[turn].vehicles.isEmpty()) {
			turn = 1 - turn;
			return;
		}
		if (groups[1 - turn].vehicles.isEmpty()) {
			groups[turn].updateGroup(groups[turn].vehicles.size() - 1);
			utilities[turn] = 0;
			oldUtilities[turn] = 0;
			groupListModel[turn].clear();
			groupListModelImage[turn].clear();
			for (Vehicle v : groups[turn].vehicles) {
				groupListModel[turn].addElement(v.toString());
				groupListModelImage[turn].addElement(v.vehicleType.toString());
			}
			return;
		}

		oldUtilities[turn] = utilities[turn];
		utilities[turn] = groups[turn].makeOffer(utilities[1 - turn]);
		System.out.println("Group 0 Total Utility:" + utilities[0]);
		System.out.println("Group 1 Total Utility:" + utilities[1]);

		if (utilities[turn] - oldUtilities[turn] < 0.0000001) {
			groups[1 - turn].updateGroup(groups[1 - turn].sortedVehicles.get(0).groupOrder);
			utilities[1 - turn] = 0;
			oldUtilities[1 - turn] = 0;
			groupListModel[1 - turn].clear();
			groupListModelImage[1 - turn].clear();
			for (Vehicle v : groups[1 - turn].vehicles) {
				groupListModel[1 - turn].addElement(v.toString());
				groupListModelImage[1 - turn].addElement(v.vehicleType.toString());
			}
			utilities[1 - turn] = 0;
			oldUtilities[1 - turn] = 0;

		}

		turn = 1 - turn;

	}

	public static void updateOffers() {
		for (int index = 0; index < 2; index++) {
			if (groups[index].sortedVehicles.isEmpty()) {
				groupListModelOffer[index].clear();
				groupListModelOffer[index].addElement("Vehicle Type: ?");
				groupListModelOffer[index].addElement("Journey of Purpose: ?");
				groupListModelOffer[index].addElement("Malfunction Type: ?");
				groupListModelOffer[index].addElement("Number of People: ?");
				continue;
			}
			groupListModelOffer[index].clear();
			if (groups[index].sortedVehicles.get(0).enabled[0])
				groupListModelOffer[index]
						.addElement("Vehicle Type: " + groups[index].sortedVehicles.get(0).vehicleType);
			else
				groupListModelOffer[index].addElement("Vehicle Type: ?");
			if (groups[index].sortedVehicles.get(0).enabled[1])
				groupListModelOffer[index]
						.addElement("Journey of Purpose: " + groups[index].sortedVehicles.get(0).journeyType);
			else
				groupListModelOffer[index].addElement("Journey of Purpose: ?");
			if (groups[index].sortedVehicles.get(0).enabled[2])
				groupListModelOffer[index]
						.addElement("Malfunction Type: " + groups[index].sortedVehicles.get(0).malfunctionType);
			else
				groupListModelOffer[index].addElement("Malfunction Type: ?");
			if (groups[index].sortedVehicles.get(0).enabled[3])
				groupListModelOffer[index]
						.addElement("Number of People: " + groups[index].sortedVehicles.get(0).ageOfCar);
			else
				groupListModelOffer[index].addElement("Number of People: ?");
		}
	}

	public static void displayGroups() {
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("Positions of Groups");
		System.out.println("Group " + groups[0].id + "\t\t\t\t\t|\tGroup " + groups[1].id);

		for (int i = 0; i < Math.max(groups[0].vehicles.size(), groups[1].vehicles.size()); i++) {
			if (i < groups[0].vehicles.size()) {
				System.out.print(groups[0].vehicles.get(i));
				if (groups[0].vehicles.get(i).equals(groups[0].sortedVehicles.get(0))) {
					System.out.print("   <-");
				}
			} else {
				System.out.print("\t\t\t\t");
			}

			System.out.print("\t|\t");

			if (i < groups[1].vehicles.size()) {
				System.out.print(groups[1].vehicles.get(i));
				if (groups[1].vehicles.get(i).equals(groups[1].sortedVehicles.get(0))) {
					System.out.print(" <-");
				}
			}
			System.out.println();
		}

		System.out.println("-----------------------------------------------------------------------------------------");

	}

	public static void importVehicles(ArrayList<Vehicle> vehicles){
		Random rnd = new Random();
		for(Vehicle v : vehicles){
			int r = rnd.nextInt(2);
			v.setPrivacyRandom();
			groups[r].addVehicle(v);
			groupListModel[r].addElement(v.toString());
			groupListModelImage[r].addElement(v.toString());
		}
	}
	
//	public static void importVehicles(int indexOfIncident) {
//		Vehicle v1, v2, v3, v4, v5, v6;
//		switch (indexOfIncident) {
//		// TODO clear vehicles and groups while adding
//		case 0:
//			// Group0
//			v1 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.SCHOOL, MALFUNCTIONTYPE.NOMALFUNCTION, 1);
//			v2 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.COMMUTINGTOWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 1);
//			// Group1
//			v3 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 16);
//			v4 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.SCHOOL, MALFUNCTIONTYPE.NOMALFUNCTION, 12);
//			v5 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 8);
//			v1.setPrivacyRandom();
//			v2.setPrivacyRandom();
//			v3.setPrivacyRandom();
//			v4.setPrivacyRandom();
//			v5.setPrivacyRandom();
//			
//			groups[0].vehicles.clear();
//			groups[1].vehicles.clear();
//
//			groups[0].addVehicle(v1);
//			groups[0].addVehicle(v2);
//			groups[1].addVehicle(v3);
//			groups[1].addVehicle(v4);
//			groups[1].addVehicle(v5);
//
//			groupListModel[0].addElement(v1.toString());
//			groupListModel[0].addElement(v2.toString());
//			groupListModel[1].addElement(v3.toString());
//			groupListModel[1].addElement(v4.toString());
//			groupListModel[1].addElement(v5.toString());
//			groupListModelImage[0].addElement(v1.vehicleType.toString());
//			groupListModelImage[0].addElement(v2.vehicleType.toString());
//			groupListModelImage[1].addElement(v3.vehicleType.toString());
//			groupListModelImage[1].addElement(v4.vehicleType.toString());
//			groupListModelImage[1].addElement(v5.vehicleType.toString());
//
//			return;
//		case 1:
//			// Group0
//			v1 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.PARTOFWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 6, 1);
//			// Group1
//			v2 = new Vehicle(VEHICLETYPE.MOTORCYCLE, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 5, 2);
//			v1.setPrivacyRandom();
//			v2.setPrivacyRandom();
//
//			groups[0].vehicles.clear();
//			groups[1].vehicles.clear();
//
//			groups[0].addVehicle(v1);
//			groups[1].addVehicle(v2);
//
//			groupListModel[0].addElement(v1.toString());
//			groupListModel[1].addElement(v2.toString());
//
//			groupListModelImage[0].addElement(v1.vehicleType.toString());
//			groupListModelImage[1].addElement(v2.vehicleType.toString());
//
//			return;
//		case 2:
//			// Group0
//			v1 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 10, 1);
//			v2 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 8, 2);
//			v3 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 4, 3);
//			v4 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 4, 4);
//			// Group1
//			v5 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.PARTOFWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 1, 5);
//			v6 = new Vehicle(VEHICLETYPE.VAN, JOURNEYPURPOSE.PARTOFWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 2, 6);
//			v1.setPrivacyRandom();
//			v2.setPrivacyRandom();
//			v3.setPrivacyRandom();
//			v4.setPrivacyRandom();
//			v5.setPrivacyRandom();
//			v6.setPrivacyRandom();
//			
//			groups[0].vehicles.clear();
//			groups[1].vehicles.clear();
//
//			groups[0].addVehicle(v1);
//			groups[0].addVehicle(v2);
//			groups[0].addVehicle(v3);
//			groups[0].addVehicle(v4);
//			groups[1].addVehicle(v5);
//			groups[1].addVehicle(v6);
//
//			groupListModel[0].addElement(v1.toString());
//			groupListModel[0].addElement(v2.toString());
//			groupListModel[0].addElement(v3.toString());
//			groupListModel[0].addElement(v4.toString());
//			groupListModel[1].addElement(v5.toString());
//			groupListModel[1].addElement(v6.toString());
//			groupListModelImage[0].addElement(v1.vehicleType.toString());
//			groupListModelImage[0].addElement(v2.vehicleType.toString());
//			groupListModelImage[0].addElement(v3.vehicleType.toString());
//			groupListModelImage[0].addElement(v4.vehicleType.toString());
//			groupListModelImage[1].addElement(v5.vehicleType.toString());
//			groupListModelImage[1].addElement(v6.vehicleType.toString());
//
//			return;
//		case 3:
//			// Group0
//			v1 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 9, 1);
//			v2 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.PARTOFWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 8, 2);
//			// Group1
//			v3 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 9, 3);
//			v1.setPrivacyRandom();
//			v2.setPrivacyRandom();
//			v3.setPrivacyRandom();
//
//			groups[0].vehicles.clear();
//			groups[1].vehicles.clear();
//
//			groups[0].addVehicle(v1);
//			groups[0].addVehicle(v2);
//			groups[1].addVehicle(v3);
//
//			groupListModel[0].addElement(v1.toString());
//			groupListModel[0].addElement(v2.toString());
//			groupListModel[1].addElement(v3.toString());
//
//			groupListModelImage[0].addElement(v1.vehicleType.toString());
//			groupListModelImage[0].addElement(v2.vehicleType.toString());
//			groupListModelImage[1].addElement(v3.vehicleType.toString());
//			return;
//		case 4:
//			// Group0
//			v1 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.COMMUTINGTOWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 7, 1);
//			v2 = new Vehicle(VEHICLETYPE.BUS, JOURNEYPURPOSE.PARTOFWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 3, 2);
//			// Group1
//			v3 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 8, 3);
//			v1.setPrivacyRandom();
//			v2.setPrivacyRandom();
//			v3.setPrivacyRandom();
//			
//			groups[0].vehicles.clear();
//			groups[1].vehicles.clear();
//
//			groups[0].addVehicle(v1);
//			groups[0].addVehicle(v2);
//			groups[1].addVehicle(v3);
//
//			groupListModel[0].addElement(v1.toString());
//			groupListModel[0].addElement(v2.toString());
//			groupListModel[1].addElement(v3.toString());
//
//			groupListModelImage[0].addElement(v1.vehicleType.toString());
//			groupListModelImage[0].addElement(v2.vehicleType.toString());
//			groupListModelImage[1].addElement(v3.vehicleType.toString());
//			return;
//		case 5:
//			// Group0
//			v1 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 13, 1);
//			v2 = new Vehicle(VEHICLETYPE.TAXI, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 5, 2);
//			// Group1
//			v3 = new Vehicle(VEHICLETYPE.CAR, JOURNEYPURPOSE.OTHER, MALFUNCTIONTYPE.NOMALFUNCTION, 7, 3);
//			v1.setPrivacyRandom();
//			v2.setPrivacyRandom();
//			v3.setPrivacyRandom();
//			
//			groups[0].vehicles.clear();
//			groups[1].vehicles.clear();
//
//			groups[0].addVehicle(v1);
//			groups[0].addVehicle(v2);
//			groups[1].addVehicle(v3);
//
//			groupListModel[0].addElement(v1.toString());
//			groupListModel[0].addElement(v2.toString());
//			groupListModel[1].addElement(v3.toString());
//
//			groupListModelImage[0].addElement(v1.vehicleType.toString());
//			groupListModelImage[0].addElement(v2.vehicleType.toString());
//			groupListModelImage[1].addElement(v3.vehicleType.toString());
//
//			return;
//
//		default:
//			return;
//		}
//	}
}

class MarioListRenderer extends DefaultListCellRenderer {

	int leaderIndex;

	public MarioListRenderer(int index) {
		this.leaderIndex = index;
	}

	Font font = new Font("helvitica", Font.BOLD, 24);

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list,
				Main.groups[leaderIndex].vehicles.get(index).journeyType.toString() + " "
						+ Main.groups[leaderIndex].vehicles.get(index).ageOfCar,
				index, isSelected, cellHasFocus);
		label.setIcon(Main.imageMap.get((String) value.toString()));
		if (index == Main.groups[leaderIndex].sortedVehicles.get(0).groupOrder)
			label.setBackground(Color.CYAN);
		label.setHorizontalTextPosition(JLabel.RIGHT);
		label.setFont(font);
		return label;
	}
}
