package com.app.renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author fytta
 *
 */
public class MainController {
	
	private List<File> files = new ArrayList<File>();
	
	@FXML
	Button btnFiles;
	
	@FXML
	Button btnRename;
	
	@FXML
	TextArea taSelectedFiles;
	
	@FXML
	Label lblnFiles;
	
	@FXML
	TextField tfName;
	
	@FXML
	TextField tfIndex;

	
	@FXML
	public void openFileChooser() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Multiple Files");
		File defaultDir = new File((System.getProperty("user.home") + System.getProperty("file.separator")));
		fileChooser.setInitialDirectory(defaultDir);
		files = fileChooser.showOpenMultipleDialog(new Stage());
		int nfiles = 0;
		
		if (files != null) {
			StringBuilder sb = new StringBuilder();
			for (File file: files) {
				nfiles++;
				sb.append(file.getName());
				sb.append("\n");
			}
			
			taSelectedFiles.setText(sb.toString());	
			lblnFiles.setText(Integer.toString(nfiles));
		}
	}
	
	@FXML
	public void rename() {
		
		String namePattern = tfName.getText();
		String strIndex = tfIndex.getText();
		
		long index = 0;
		if (namePattern.equals("") || strIndex.equals("")) {
			showAlertDialog(AlertType.ERROR, "ERROR", "Empty fields", "Fields cannot be empty.");
		}
		else if (!isLong(strIndex)) {
			showAlertDialog(AlertType.ERROR, "ERROR", "Incompatible type", "Index must be a number.");
		}
		else if (files.isEmpty()) {
			showAlertDialog(AlertType.INFORMATION, "INFO", "Files empty", "You have to select files.");
		}
		else {
			index = Long.parseLong(strIndex);
			// GET PATH
			String aux = files.get(0).getName();
			int lenName = aux.length();
			String path = files.get(0).getAbsolutePath().substring(0, (files.get(0).getAbsolutePath().length()-lenName));
			// END GET PATH
			
			for (File file: files) {
				int dot = file.getName().indexOf(".");
				String ext = file.getName().substring(dot);		
				strIndex = buildIndex(index);
				
				String newFileName = String.format("%s%s%s%s", path, namePattern, strIndex, ext);
				file.renameTo(new File(newFileName));
				index++;
			}
			String content = "Renamed " + lblnFiles.getText() + " files.";
			showAlertDialog(AlertType.INFORMATION, "INFO", "Files renamed", content);
			clearFields();
		}
	}
	
	private void clearFields() {
		taSelectedFiles.clear();
		lblnFiles.setText("0");
		setFiles(null);
	}

	private String buildIndex(long index) {
		int nFilesLen = lblnFiles.getText().length();
		int auxLenIndex = Long.toString(index).length();
		
		int diff = nFilesLen - auxLenIndex;
		String result = "";
		for (int i=0; i<diff; i++) {
			result += 0;
		}
		result += index;
		return result;
	}
	
	private void showAlertDialog(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		alert.showAndWait();
	}
	
	public List<File> getFiles() {
		return files;
	}
	
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	private boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
}
