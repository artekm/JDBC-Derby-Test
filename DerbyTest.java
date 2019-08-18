import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class DerbyTest {

	private JTextArea textCommand;
	private JTextArea textResult;
	private JTextArea textStructure;

	private DatabaseService databaseService = new DatabaseService();

	private void localizeText() {
		UIManager.put("FileChooser.lookInLabelText", "Szukaj w");
		UIManager.put("FileChooser.lookInLabelMnemonic", "" + KeyEvent.VK_W);

		UIManager.put("FileChooser.saveInLabelText", "Zapisz w");
		UIManager.put("FileChooser.saveInLabelMnemonic", "" + KeyEvent.VK_W);

		UIManager.put("FileChooser.fileNameLabelText", "Nazwa pliku:");
		UIManager.put("FileChooser.fileNameLabelMnemonic", "" + KeyEvent.VK_N);

		UIManager.put("FileChooser.folderNameLabelText", "Nazwa folderu:");
		UIManager.put("FileChooser.folderNameLabelMnemonic", "" + KeyEvent.VK_N);

		UIManager.put("FileChooser.filesOfTypeLabelText", "Pliki typu:");
		UIManager.put("FileChooser.filesOfTypeLabelMnemonic", "" + KeyEvent.VK_P);

		UIManager.put("FileChooser.upFolderToolTipText", "Poziom wy¿ej");
		UIManager.put("FileChooser.homeFolderToolTipText", "Pulpit");
		UIManager.put("FileChooser.newFolderToolTipText", "Nowy folder");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegó³y");

		UIManager.put("FileChooser.fileNameHeaderText", "Nazwa");
		UIManager.put("FileChooser.fileSizeHeaderText", "Rozmiar");
		UIManager.put("FileChooser.fileTypeHeaderText", "Typ");
		UIManager.put("FileChooser.fileDateHeaderText", "Modyfikacja");
		UIManager.put("FileChooser.fileAttrHeaderText", "Atrybuty");

		UIManager.put("FileChooser.newFolderErrorText", "B³¹d podczas tworzenia folderu");

		UIManager.put("FileChooser.saveButtonText", "Zapisz");
		UIManager.put("FileChooser.saveButtonMnemonic", "" + KeyEvent.VK_Z);

		UIManager.put("FileChooser.openButtonText", "Otwórz");
		UIManager.put("FileChooser.openButtonMnemonic", "" + KeyEvent.VK_O);

		UIManager.put("FileChooser.cancelButtonText", "Anuluj");
		UIManager.put("FileChooser.openButtonMnemonic", "" + KeyEvent.VK_R);

		UIManager.put("FileChooser.openDialogTitleText", "Otwieranie");
		UIManager.put("FileChooser.saveDialogTitleText", "Zapisywanie");

		UIManager.put("FileChooser.saveButtonToolTipText", "Zapisanie pliku");
		UIManager.put("FileChooser.openButtonToolTipText", "Otwarcie pliku");
		UIManager.put("FileChooser.cancelButtonToolTipText", "Anuluj");
		UIManager.put("FileChooser.acceptAllFileFilterText", "Wszystkie pliki");

		UIManager.put("FileChooser.directoryOpenButtonText", "Otwórz folder");
		UIManager.put("FileChooser.directoryOpenButtonToolTipText", "Otwiera folder");

		UIManager.put("FileChooser.foldersLabelText", "Nazwa folderu: ");
		UIManager.put("FileChooser.pathLabelText", "Œcie¿ka: ");
		UIManager.put("FileChooser.directoryDescriptionText", "Scie¿ka folderu, opis");
		UIManager.put("FileChooser.foldersLabelText", "Foldery");
		UIManager.put("FileChooser.newFolderAccessibleName", "Nowy folder");
		UIManager.put("FileChooser.newFolderToolTipText", "Nowy folder");
		UIManager.put("FileChooser.other.newFolder", "Nowy folder");
		UIManager.put("FileChooser.other.newFolder.subsequent", "Nowy folder");
		UIManager.put("FileChooser.win32.newFolder", "Nowy folder");
		UIManager.put("FileChooser.win32.newFolder.subsequent", "Nowy folder");
	}

	public void runGUI() {
		localizeText();

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setTitle("Derby Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setResizable(true);
		frame.setLayout(new GridBagLayout());
		frame.setPreferredSize(new Dimension(900, 500));

		JButton buttonSend = new JButton("Wykonaj");
		buttonSend.setMnemonic(KeyEvent.VK_W);
		buttonSend.setEnabled(false);

		JButton buttonClear = new JButton("Czyœæ");
		buttonClear.setMnemonic(KeyEvent.VK_C);
		buttonClear.setEnabled(false);

		JButton buttonDB = new JButton("Wybór bazy danych");
		buttonDB.setMnemonic(KeyEvent.VK_D);

		JButton buttonEnd = new JButton("Koniec");
		buttonEnd.setMnemonic(KeyEvent.VK_K);

		textCommand = new JTextArea();
		textCommand.setLineWrap(true);
		textCommand.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		textCommand.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(textCommand);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Komenda DB"));

		textResult = new JTextArea("Wybierz bazê danych...");
		textResult.setEditable(false);
		JScrollPane scrollPane2 = new JScrollPane(textResult);
		scrollPane2.setBorder(BorderFactory.createTitledBorder("Wynik"));
		scrollPane2.setMinimumSize(new Dimension(300, 200));
		textResult.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

		textStructure = new JTextArea();
		textStructure.setEditable(false);
		JScrollPane scrollPane3 = new JScrollPane(textStructure);
		scrollPane3.setBorder(BorderFactory.createTitledBorder("Struktura"));
		textStructure.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));

		frame.add(buttonSend, new GBC(0, 0).setFill(GBC.HORIZONTAL).setInsets(2));
		frame.add(buttonClear, new GBC(0, 1).setFill(GBC.HORIZONTAL).setInsets(2));
		frame.add(buttonDB, new GBC(0, 2).setFill(GBC.HORIZONTAL).setInsets(2));
		frame.add(buttonEnd, new GBC(0, 3).setFill(GBC.HORIZONTAL).setInsets(2));
		frame.add(scrollPane, new GBC(1, 0, 1, 4).setFill(GBC.BOTH).setWeight(100, 0));
		frame.add(scrollPane2, new GBC(1, 4, 1, 4).setFill(GBC.BOTH).setWeight(100, 100));
		frame.add(scrollPane3, new GBC(0, 4, 1, 4).setFill(GBC.BOTH).setWeight(0, 100));

		buttonSend.addActionListener(event -> {
			String cmd = textCommand.getText();
			String res = databaseService.executeDB(cmd);
			textResult.setText(res);
		});

		buttonClear.addActionListener(event -> {
			textCommand.setText("");
			textCommand.grabFocus();
		});

		buttonDB.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogTitle("Wybierz bazê danych");
			int result = chooser.showDialog(null, "Wybierz");
			if (result == JFileChooser.APPROVE_OPTION) {
				String nazwa = chooser.getSelectedFile().getName().toUpperCase();
				String newName = chooser.getSelectedFile().toString();
				textResult.setText(databaseService.changeDB(newName));
				if (newName.equals(databaseService.getDbName())) {
					databaseService.loadStructure(textStructure);
					frame.setTitle("Derby Test : " + nazwa);
					buttonSend.setEnabled(true);
					buttonClear.setEnabled(true);
					textCommand.setEnabled(true);
					textCommand.grabFocus();
				}
			}
		});

		buttonEnd.addActionListener(event -> {
			databaseService.disconnectDB();
			SwingUtilities.getWindowAncestor(buttonEnd).dispose();
		});

		textCommand.addKeyListener((KeyPressedListener) event -> {
			if (event.getKeyCode() == KeyEvent.VK_ENTER
					&& (event.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) == KeyEvent.CTRL_DOWN_MASK) {
				String cmd = textCommand.getText();
				String res = databaseService.executeDB(cmd);
				textResult.setText(res);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new DerbyTest().runGUI());
	}

}