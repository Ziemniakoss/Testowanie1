package com.testowanie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ViewManager {
	private static Stage primarySage;
	private static User user;

	public static Stage getPrimarySage() {
		return primarySage;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		ViewManager.user = user;
	}

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
		Scene scene = new Scene(root,1000,800);
		scene.getStylesheets().add(ViewManager.class.getClassLoader().getResource("main.css").toExternalForm());
		primarySage.setScene(scene);
	}

}
