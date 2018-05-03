package application;
//Instructions
//Devika Kumar
//ITP 368, Spring 2018
//Final Project
//devikaku@usc.edu
import java.util.Map;

import Controllers.SceneController;
import Controllers.ThemeController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Theme;

//Creates a new Intstructions page that gives the user instructions on how to use the app, as well as keyboard shortcuts
public class Instructions {
	// instance variables: I have a scenecontroller which is the scene manager,
	// the scene associated with this class
	private SceneController pm;
	private Scene scene;

	// everything is put into a borderpane, which contains a stackpane and a
	// rectangle to overlay
	BorderPane b = new BorderPane();
	StackPane s = new StackPane();
	Rectangle r;

	// labels for title, instructions text, and back button
	Label l;
	Text t;
	Button back;

	// themecontroller sets theme
	ThemeController tc;

	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public Instructions(SceneController pm) {
		// sets scenecontroller, themecontroller, loads all components, and sets
		// background and creates the scene
		this.pm = pm;
		tc = pm.tc;
		loadView();

		scene = new Scene(b, 800, 400, Color.CORNSILK);
	}

	// add all components
	private void loadView() {
		// add new label for title, instructionstext, and the rectangle to overlay
		l = new Label("Instructions:");

		t = new Text("Click on the buttons to paint.efkefkerfkjenfkenrfjkenrjnfejnrfekrnfenrfejk ");
		r = new Rectangle(500, 200);
		s.getChildren().addAll(r, t);

		// create button that calls method in scenecontroller that launches paint scene
		back = new Button("Back to Paint");
		back.setOnAction(e -> {
			pm.launchPaint();
		});
		back.setCenterShape(true);

		// sets all layouts and adds to borderpane
		VBox v = new VBox();
		v.getChildren().addAll(l, s, back);
		b.setCenter(v);
		b.setPadding(new Insets(10, 50, 50, 50));
		v.setPadding(new Insets(20, 20, 20, 30));
		v.setAlignment(Pos.CENTER);
		v.setSpacing(5);

		// set component styles based on theme and resets localization/accessibility
		setComponentThemeStyle();
		resetComponents();
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

	// updates style of all components based on current theme
	public void setComponentThemeStyle() {
		Image image1 = new Image(tc.getCurrent().getImage());

		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		b.setBackground(new Background(new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, bSize)));

		l.setFont(Font.font("Courier", 40));
		l.setTextFill(tc.getCurrent().getColor("txt"));

		t.setFill(tc.getCurrent().getColor("txt"));
		t.setFont(Font.font("Courier", 10));

		r.setFill(tc.getCurrent().getColor("bg"));

		back.setStyle("-fx-background-color: " + tc.getCurrent().getButtonColorHex());
		back.setMaxHeight(40);
		back.setMaxWidth(100);
		back.setFont(Font.font("Courier", 10));
		back.setTextFill(tc.getCurrent().getColor("btntxt"));
	}

	// resets accessibility and localization
	public void resetComponents() {

		l.setText((pm.rb).getString("instructions"));
		t.setText((pm.rb).getString("instructionstext"));
		back.setText((pm.rb).getString("backtopaint"));
		l.setAccessibleText((pm.rb).getString("labelinstructions"));
		t.setAccessibleText((pm.rb).getString("instructionstext"));
		back.setAccessibleText((pm.rb).getString("buttonbacktopaint"));

		setComponentThemeStyle();

	}

}
