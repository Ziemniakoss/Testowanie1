package com.testowanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ViewManager {
	private static Stage primarySage;

	public static void setPrimarySage(Stage s) {
		primarySage = s;
	}

	public static void loadView(String name) {
		Parent root = null;
		System.out.println(name+".fxml");
		try {
			root = FXMLLoader.load(ViewManager.class.getClassLoader().getResource(name+".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		primarySage.setScene(new Scene(root,1000,800));
	}

}
