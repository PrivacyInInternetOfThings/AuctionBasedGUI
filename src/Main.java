
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

public class Main {

	public static int lastID = 0;
	public static ArrayList<Vehicle> vehicles = new ArrayList<>();
	public static Group[] groups = { new Group(0), new Group(1) };
	public static DefaultListModel<String>[] groupListModel = new DefaultListModel[2];
	public static DefaultListModel<String>[] groupListModelImage = new DefaultListModel[2];
	public static boolean clickedNextTurn = false;
	public static double[] oldUtilities = { 0, 0 };
	public static double[] utilities = { 0, 0 };
	public static int turn = 0;
	public static Map<String, ImageIcon> imageMap = null;
	// public static ArrayList<String>[] vehicleTypes = new ArrayList[2];

	public static Map<String, ImageIcon> createImageMap() {
		Map<String, ImageIcon> map = new HashMap<>();
		try {
			map.put("EMERGENCY", new ImageIcon(new URL(
					"http://icdn.pro/images/en/c/a/car-emergency-ambulance-transport-vehicle-icone-4771-128.png")));
			map.put("ORDINARY", new ImageIcon(
					new URL("https://cdn0.iconfinder.com/data/icons/classic-cars-by-cemagraphics/128/red_128.png")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {

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
				setupPanel.setPreferredSize(new Dimension(1200, 400));

				// Create Auction Panel
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
				JPanel[] groupPanel = { new JPanel(), new JPanel() };
				JPanel buttonAuctionPanel = new JPanel();
				JPanel groupImagesPanel = new JPanel();
				JPanel[] imagesPanel = { new JPanel(), new JPanel() };

				// Panels
				auctionPanel.setLayout(new BoxLayout(auctionPanel, BoxLayout.Y_AXIS));
				vehiclePropertiesPanel.setLayout(new BoxLayout(vehiclePropertiesPanel, BoxLayout.X_AXIS));
				labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
				vehicleTypePanel.setLayout(new BoxLayout(vehicleTypePanel, BoxLayout.Y_AXIS));
				emergencyTypePanel.setLayout(new BoxLayout(emergencyTypePanel, BoxLayout.Y_AXIS));
				malfunctionTypePanel.setLayout(new BoxLayout(malfunctionTypePanel, BoxLayout.Y_AXIS));
				numOfPeoplePanel.setLayout(new BoxLayout(numOfPeoplePanel, BoxLayout.Y_AXIS));
				thresholdPanel.setLayout(new BoxLayout(thresholdPanel, BoxLayout.Y_AXIS));
				buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
				buttonAuctionPanel.setLayout(new BoxLayout(buttonAuctionPanel, BoxLayout.X_AXIS));
				vehiclePanel.setLayout(new BoxLayout(vehiclePanel, BoxLayout.Y_AXIS));
				groupListsPanel.setLayout(new BoxLayout(groupListsPanel, BoxLayout.X_AXIS));
				groupPanel[0].setLayout(new BoxLayout(groupPanel[0], BoxLayout.Y_AXIS));
				groupPanel[1].setLayout(new BoxLayout(groupPanel[1], BoxLayout.Y_AXIS));
				groupImagesPanel.setLayout(new BoxLayout(groupImagesPanel, BoxLayout.X_AXIS));
				imagesPanel[0].setLayout(new BoxLayout(imagesPanel[0], BoxLayout.Y_AXIS));
				imagesPanel[1].setLayout(new BoxLayout(imagesPanel[1], BoxLayout.Y_AXIS));

				// Label Panel
				JLabel propertiesLabel = new JLabel("Properties: ");
				JLabel privacyLabel = new JLabel("Privacy Values: ");

				labelPanel.add(propertiesLabel, BorderLayout.NORTH);
				labelPanel.add(privacyLabel);

				// Vehicle Type Panel
				JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
				JComboBox vehicleTypeChoice = new JComboBox(VEHICLETYPE.values());
				vehicleTypeChoice.setSelectedItem(VEHICLETYPE.ORDINARY);
				JSpinner vehicleTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) vehicleTypePrivacyField.getEditor()).getTextField().setColumns(3);
				vehicleTypePanel.add(vehicleTypeLabel, BorderLayout.NORTH);
				vehicleTypePanel.add(vehicleTypeChoice);
				vehicleTypePanel.add(vehicleTypePrivacyField);

				// Emergency Type Panel
				JLabel emergencyTypeLabel = new JLabel("Emergency Type");
				JComboBox emergencyTypeChoice = new JComboBox(EMERGENCYTYPE.values());
				emergencyTypeChoice.setSelectedItem(EMERGENCYTYPE.NOEMERGENCY);
				JSpinner emergencyTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) emergencyTypePrivacyField.getEditor()).getTextField().setColumns(3);
				emergencyTypePanel.add(emergencyTypeLabel, BorderLayout.NORTH);
				emergencyTypePanel.add(emergencyTypeChoice);
				emergencyTypePanel.add(emergencyTypePrivacyField);

				// Malfunction Panel
				JLabel malfunctionTypeLabel = new JLabel("Malfunction Type");
				JComboBox malfunctionTypeChoice = new JComboBox(MALFUNCTIONTYPE.values());
				malfunctionTypeChoice.setSelectedItem(MALFUNCTIONTYPE.NOMALFUNCTION);
				JSpinner malfunctionTypePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) malfunctionTypePrivacyField.getEditor()).getTextField().setColumns(3);
				malfunctionTypePanel.add(malfunctionTypeLabel, BorderLayout.NORTH);
				malfunctionTypePanel.add(malfunctionTypeChoice);
				malfunctionTypePanel.add(malfunctionTypePrivacyField);

				// Num Of People Panel
				JLabel numOfPeopleLabel = new JLabel("Number of People");
				JSpinner numOfPeopleSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 50, 1));
				JSpinner numOfPeoplePrivacyField = new JSpinner(new SpinnerNumberModel(0.001, 0.0, 1.0, 0.001));
				((JSpinner.DefaultEditor) numOfPeoplePrivacyField.getEditor()).getTextField().setColumns(3);
				numOfPeoplePanel.add(numOfPeopleLabel, BorderLayout.NORTH);
				numOfPeoplePanel.add(numOfPeopleSpinner);
				numOfPeoplePanel.add(numOfPeoplePrivacyField);

				// Threshold Panel
				JLabel thresholdLabel = new JLabel("Threshold");
				JLabel thresholdEmptyLabel = new JLabel(" ");
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
						emergencyTypeChoice
								.setSelectedItem(EMERGENCYTYPE.values()[rand.nextInt(EMERGENCYTYPE.values().length)]);
						malfunctionTypeChoice.setSelectedItem(
								MALFUNCTIONTYPE.values()[rand.nextInt(MALFUNCTIONTYPE.values().length)]);
						if(vehicleTypeChoice.getSelectedItem().equals(VEHICLETYPE.ORDINARY))
							numOfPeopleSpinner.setValue(rand.nextInt(50));
						else numOfPeopleSpinner.setValue(rand.nextInt(4));
					}
				});

				JButton randomPrivacyValuesButton = new JButton("Random Privacy Values");
				randomPrivacyValuesButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Random rand = new Random();
						vehicleTypePrivacyField.setValue(rand.nextDouble());
						emergencyTypePrivacyField.setValue(rand.nextDouble());
						malfunctionTypePrivacyField.setValue(rand.nextDouble());
						numOfPeoplePrivacyField.setValue(rand.nextDouble());
						thresholdPrivacyField.setValue(rand.nextDouble());
					}
				});

				JButton addVehicle1Button = new JButton("Add Vehicle to Group 0");
				addVehicle1Button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Vehicle newVehicle = new Vehicle((VEHICLETYPE) vehicleTypeChoice.getSelectedItem(),
								(EMERGENCYTYPE) emergencyTypeChoice.getSelectedItem(),
								(MALFUNCTIONTYPE) malfunctionTypeChoice.getSelectedItem(),
								(Integer) numOfPeopleSpinner.getValue(), ++lastID);
						newVehicle.setPrivacy((double) vehicleTypePrivacyField.getValue(),
								(double) emergencyTypePrivacyField.getValue(),
								(double) malfunctionTypePrivacyField.getValue(),
								(double) numOfPeoplePrivacyField.getValue());
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
								(EMERGENCYTYPE) emergencyTypeChoice.getSelectedItem(),
								(MALFUNCTIONTYPE) malfunctionTypeChoice.getSelectedItem(),
								(Integer) numOfPeopleSpinner.getValue(), ++lastID);
						newVehicle.setPrivacy((double) vehicleTypePrivacyField.getValue(),
								(double) emergencyTypePrivacyField.getValue(),
								(double) malfunctionTypePrivacyField.getValue(),
								(double) numOfPeoplePrivacyField.getValue());
						newVehicle.setThreshold((double) thresholdPrivacyField.getValue());
						groups[1].addVehicle(newVehicle);
						groupListModel[1].addElement(newVehicle.toString());
						groupListModelImage[1].addElement(newVehicle.vehicleType.toString());
						System.out.println(newVehicle.vehicleType.toString());
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
				vehiclePropertiesPanel.add(emergencyTypePanel);
				vehiclePropertiesPanel.add(malfunctionTypePanel);
				vehiclePropertiesPanel.add(numOfPeoplePanel);
				vehiclePropertiesPanel.add(thresholdPanel);

				vehiclePanel.add(vehiclePropertiesPanel);
				vehiclePanel.add(buttonPanel);

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

				String[] nl = { "EMERGENCY", "ORDINARY", "EMERGENCY", "ORDINARY" };
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
					groupListScrollPaneImage[index].setPreferredSize(new Dimension(300, 400));

					imagesPanel[index].add(groupLabelImage, BorderLayout.CENTER);
					imagesPanel[index].add(groupListScrollPaneImage[index]);
				}

				groupImagesPanel.add(imagesPanel[0], BorderLayout.WEST);
				groupImagesPanel.add(imagesPanel[1]);

				auctionPanel.add(buttonAuctionPanel, BorderLayout.NORTH);
				auctionPanel.add(groupImagesPanel);

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
		System.out.println("Group 0 Total Utility:" +utilities[0]);
		System.out.println("Group 1 Total Utility:" +utilities[1]);

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
		JLabel label = (JLabel) super.getListCellRendererComponent(list, Main.groups[leaderIndex].vehicles.get(index).emergencyType.toString() +" "+ Main.groups[leaderIndex].vehicles.get(index).numOfPeople, index, isSelected, cellHasFocus);
		label.setIcon(Main.imageMap.get((String) value.toString()));
		if (index == Main.groups[leaderIndex].sortedVehicles.get(0).groupOrder)
			label.setBackground(Color.CYAN);
		label.setHorizontalTextPosition(JLabel.RIGHT);
		label.setFont(font);
		return label;
	}
}
