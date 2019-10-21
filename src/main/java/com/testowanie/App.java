package com.testowanie;


import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewManager.setPrimarySage(primaryStage);
		ViewManager.loadView("VLoginScreen");
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
}
