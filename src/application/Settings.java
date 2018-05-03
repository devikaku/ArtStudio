package application;

//Settings
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
import model.Theme;

//Creates new Settings Page that allows user to change user settings, theme, and language
public class Settings {
	// instance variables: I have a scenecontroller which is the scene manager,
	// the scene associated with this class
	private SceneController pm;
	private Scene scene;
	// borderpane acts as scene root, contains rectangle to overlay
	Rectangle r;
	BorderPane b = new BorderPane();

	// all components in Settings
	Label l; // title
	// labels for username, password, repeatpassword, changetheme
	Label u;
	Label p;
	Label rp;
	Label ct;

	// textfields for receiving new info
	TextField username;
	TextField password;
	TextField rpassword;

	// button to save info
	Button go;

	// button to go back to paint scene
	Button back;

	// themecontroller sets style based on theme
	ThemeController tc;

	// combobox of all themes available
	ComboBox<String> themes;

	// language buttons
	Button english;
	Button spanish;

	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public Settings(SceneController pm) {
		// sets scenecontroller, themecontroller, loads all components, and sets
		// background and creates the scene
		this.pm = pm;
		tc = pm.tc;
		loadView();

		scene = new Scene(b, 800, 400, Color.CORNSILK);

		// KEYBOARD SHORTCUTS--press enter to attempt to save settings, throw alert if
		// invalid
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				String success = pm.EditUser(username.getText().toLowerCase(), password.getText(), rpassword.getText());
				Alert alert = new Alert(AlertType.NONE, (pm.rb).getString(success), ButtonType.CLOSE);
				alert.setTitle((pm.rb).getString("settings"));
				alert.showAndWait();
			}
		});
	}

	// add all components
	private void loadView() {
		l = new Label("Settings ");

		username = new TextField();
		password = new PasswordField();
		rpassword = new PasswordField();

		// put all components in gridpane to format
		GridPane g = new GridPane();
		u = new Label("Change Username: ");

		p = new Label("Change Password: ");

		rp = new Label("Repeat changed password: ");

		// create new combobox with all themes as strings
		ct = new Label("Change theme: ");
		themes = new ComboBox<String>();
		for (Map.Entry<String, Theme> entry : tc.getThemes().entrySet()) {
			themes.getItems().add(entry.getKey());
		}

		themes.valueProperty().addListener(new ChangeListener<String>() {
			// sets current theme when new one is selected and updates all pages through
			// scenecontroller
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				 
				tc.setCurrent(tc.getThemes().get(t1));
				pm.resetTheme();
			}
		});

		// creates language buttons and uses scenecontroller to reset language
		// components of all pages
		english = new Button((pm.rb).getString("english"));
		english.setAccessibleHelp("button");
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

		go = new Button("Save Changes");
		go.setOnAction(e -> {
			// check if valid--try to edit user through scenecontroller and receive a
			// success message
			// edits user if successful
			String success = pm.EditUser(username.getText().toLowerCase(), password.getText(), rpassword.getText());
			// creates alert updating user about status of changes
			Alert alert = new Alert(AlertType.NONE, (pm.rb).getString(success), ButtonType.CLOSE);
			alert.setTitle((pm.rb).getString("settings"));
			alert.showAndWait();
		});
		// launches paint page
		back = new Button("Back to Paint");
		back.setOnAction(e -> {
			pm.launchPaint();
		});
		back.setCenterShape(true);
		// layout formatting--add all to borderpane
		HBox h = new HBox(english, spanish);
		h.setAlignment(Pos.CENTER);
		h.setSpacing(5);

		g.add(u, 0, 0);
		g.add(username, 1, 0);
		g.add(p, 0, 1);
		g.add(password, 1, 1);
		g.add(rp, 0, 2);
		g.add(rpassword, 1, 2);
		g.add(ct, 0, 3);
		g.add(themes, 1, 3);
		g.setAlignment(Pos.CENTER);
		g.setHgap(5);
		g.setVgap(10);
		VBox v = new VBox();
		v.getChildren().addAll(l, g, h, go, back);
		StackPane s = new StackPane();
		r = new Rectangle(600, 300);
		r.setFill(tc.getCurrent().getColor("bg"));
		r.setOpacity(0.8);
		s.getChildren().addAll(r, v);
		b.setCenter(s);
		b.setPadding(new Insets(10, 50, 50, 50));
		v.setPadding(new Insets(20, 20, 20, 30));
		v.setAlignment(Pos.CENTER);
		v.setSpacing(5);

		// sets style of components based on theme
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

	// resets all components
	public void reset() {
		username.setText("");
		password.setText("");
		rpassword.setText("");
		themes.getSelectionModel().select(tc.getCurrent().getName());

	} // set styles for buttons

	public void setButtonStyle(Button b) {
		b.setStyle("-fx-background-color: " + tc.getCurrent().getButtonColorHex());
		b.setMaxHeight(40);
		b.setMaxWidth(150);
		b.setFont(Font.font("Courier", 10));
		b.setTextFill(tc.getCurrent().getColor("btntxt"));
	}

	// sets style for labels
	public void setLabelStyle(Label l) {
		l.setTextFill(tc.getCurrent().getColor("btntxt"));
		l.setFont(Font.font("Courier", 10));
		l.setAccessibleHelp("label");
	}

	// set style for textfields
	public void setTextFieldStyle(TextField t) {
		t.setStyle("-fx-background-color: " + tc.getCurrent().getSecondaryColorHex() + "; -fx-text-fill: "
				+ tc.getCurrent().getButtonTextColorHex());
		t.setMaxHeight(40);
		t.setMaxWidth(300);
		t.setFont(Font.font("Courier", 15));
	}

	// resets background and style of labels, textfields, and buttons based on new
	// theme
	public void setComponentThemeStyle() {
		Image image1 = new Image(tc.getCurrent().getImage());

		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		b.setBackground(new Background(new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, bSize)));

		l.setFont(Font.font("Courier", 40));
		l.setTextFill(tc.getCurrent().getColor("txt"));
		setLabelStyle(u);
		setLabelStyle(ct);
		setLabelStyle(p);
		setLabelStyle(rp);
		setButtonStyle(english);
		setButtonStyle(spanish);
		setButtonStyle(go);
		setButtonStyle(back);
		username.setAccessibleHelp("username");
		setTextFieldStyle(username);
		password.setAccessibleHelp("password");
		setTextFieldStyle(password);
		rpassword.setAccessibleHelp("password");
		setTextFieldStyle(rpassword);
		r.setFill(tc.getCurrent().getColor("bg"));

	}

	// resets accessibility/localization
	public void resetComponents() {
		l.setText((pm.rb).getString("settings"));
		u.setText((pm.rb).getString("changeusername"));
		p.setText((pm.rb).getString("changepassword"));
		rp.setText((pm.rb).getString("repeatchangedpassword"));
		ct.setText((pm.rb).getString("changetheme"));
		english.setText((pm.rb).getString("english"));
		spanish.setText((pm.rb).getString("spanish"));

		go.setText((pm.rb).getString("save"));
		back.setText((pm.rb).getString("backtopaint"));

		username.setAccessibleText((pm.rb).getString("textfieldcusername"));
		password.setAccessibleText((pm.rb).getString("textfieldcpassword"));
		rpassword.setAccessibleText((pm.rb).getString("textfieldcrpassword"));
		l.setAccessibleText((pm.rb).getString("labelsettings"));
		go.setAccessibleText((pm.rb).getString("buttonsave"));
		back.setAccessibleText((pm.rb).getString("buttonbacktopaint"));

		ct.setAccessibleText((pm.rb).getString("buttonthemechange"));
		english.setAccessibleText((pm.rb).getString("buttonenglish"));
		spanish.setAccessibleText((pm.rb).getString("buttonspanish"));
		setComponentThemeStyle();

	}

}
