//Home Page
// Devika Kumar
// ITP 368, Spring 2018
// Final Project
// devikaku@usc.edu
package application;

import java.util.Locale;
import java.util.ResourceBundle;

import Controllers.SceneController;
import Controllers.ThemeController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

//This is the main page that the user opens up the application to. It gives them the option to log in, or sign up.
public class HomePage {
	// instance variables: I have a scenecontroller which is the scene manager,
	// the scene associated with this class
	private SceneController pm;
	private Scene scene;
	// I have labels for my Title and buttons for my log in and sign up, as well as
	// buttons that allow the user to change the language
	Label l;
	Button li;
	Button su;
	Button english;
	Button spanish;

	// Everything is in a borderpane layout, which is the root of my scene. It
	// contains a rectangle that acts as a separator from background image.
	BorderPane b = new BorderPane();
	Rectangle r;

	// I have my themecontroller that controls/sets/updates the theme of this page.
	ThemeController tc;

	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public HomePage(SceneController pm) {
		// sets scenecontroller, themecontroller, loads all components, and sets
		// background and creates the scene
		this.pm = pm;
		this.tc = pm.tc;
		loadView();
		scene = new Scene(b, 500, 300, Color.CORNSILK);
	}

	private void loadView() {
		// load homepage view with localization language change, and login and signup
		// options
		// sets up buttons and their action handlers that take the user to the next page
		// or change the language of the application by calling the scenecontroller to
		// reset all language components of all scenes
		l = new Label((pm.rb).getString("artstudio"));

		li = new Button((pm.rb).getString("login"));

		li.setOnAction(e -> {
			pm.setCurrentUser(null);
			pm.setLoggedIn(false);
			pm.launchLogin();
		});
		su = new Button((pm.rb).getString("signup"));
		su.setOnAction(e -> {
			pm.setCurrentUser(null);
			pm.setLoggedIn(false);
			pm.launchSignup();
		});

		english = new Button((pm.rb).getString("english"));

		english.setOnAction(e -> {
			pm.setLanguage("english");
			pm.resetLanguageComponents();
		});
		spanish = new Button((pm.rb).getString("spanish"));

		spanish.setAccessibleHelp("button");
		spanish.setOnAction(e -> {
			 
			pm.setLanguage("spanish");
			pm.resetLanguageComponents();
		});

		// creates a new set of layouts, adds to borderpane, and formats
		HBox h = new HBox(english, spanish);
		h.setAlignment(Pos.CENTER);
		h.setSpacing(5);
		VBox v = new VBox(l, li, su, h);
		v.setSpacing(5);

		StackPane s = new StackPane();
		r = new Rectangle(400, 250);
		r.setFill(tc.getCurrent().getColor("bg"));
		r.setOpacity(0.8);
		s.getChildren().addAll(r, v);
		b.setCenter(s);
		b.setPadding(new Insets(10, 50, 50, 50));
		v.setPadding(new Insets(20, 20, 20, 30));
		v.setAlignment(Pos.CENTER);

		// sets the style of the components based on theme
		setComponentThemeStyle();
		resetComponents();

	}

	// sets the localization and accessibility of all components
	public void resetComponents() {
		l.setText((pm.rb).getString("artstudio"));
		li.setText((pm.rb).getString("login"));
		su.setText((pm.rb).getString("signup"));

		english.setText((pm.rb).getString("english"));
		spanish.setText((pm.rb).getString("spanish"));
		l.setAccessibleText((pm.rb).getString("labelartstudio"));
		li.setAccessibleText((pm.rb).getString("buttonlogin"));
		su.setAccessibleText((pm.rb).getString("buttonsignup"));
		english.setAccessibleText((pm.rb).getString("buttonenglish"));
		spanish.setAccessibleText((pm.rb).getString("buttonspanish"));
		setComponentThemeStyle();

	}

	// getters/setters
	public SceneController getPm() {
		return pm;
	}

	public void setPm(SceneController pm) {
		this.pm = pm;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	// set styles for buttons
	public void setButtonStyle(Button b) {
		b.setStyle("-fx-background-color: " + tc.getCurrent().getSecondaryColorHex());
		b.setMaxHeight(40);
		b.setMaxWidth(300);
		b.setTextFill(tc.getCurrent().getColor("btntxt"));
		b.setFont(Font.font("Courier", 15));
	}

	// sets style for labels
	public void setLabelStyle(Label l) {
		l.setFont(Font.font("Courier", 40));
		l.setTextFill(tc.getCurrent().getColor("txt"));
		l.setAccessibleHelp("label");
	}

	// sets theme style of all components--buttons and labels
	public void setComponentThemeStyle() {
		setButtonStyle(su);
		su.setAccessibleHelp("signup");
		setButtonStyle(li);
		setButtonStyle(english);
		english.setStyle("-fx-background-color: " + tc.getCurrent().getButtonColorHex() + ";");
		setButtonStyle(spanish);
		spanish.setStyle("-fx-background-color: " + tc.getCurrent().getButtonColorHex() + ";");
		setLabelStyle(l);
		r.setFill(tc.getCurrent().getColor("bg"));
		// sets theme background image
		Image image1 = new Image(tc.getCurrent().getImage());

		BackgroundSize bSize = new BackgroundSize(pm.primaryStage.getWidth() + 1000, pm.primaryStage.getHeight() + 1000,
				false, false, false, true);

		b.setBackground(new Background(new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize)));
	}

}
