package application;

//Log In
//Devika Kumar
//ITP 368, Spring 2018
//Final Project
//devikaku@usc.edu
import Controllers.SceneController;
import Controllers.ThemeController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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

//Creates new Log In page that allows user to log into app
public class Login {
	// instance variables: I have a scenecontroller which is the scene manager,
	// the scene associated with this class
	private SceneController pm;
	private Scene scene;
	// borderpane acts as scene root, contains rectangle to overlay
	Rectangle r;
	BorderPane b = new BorderPane();
	// themecontroller controls current theme
	ThemeController tc;

	// components: title, textfields for username password and corresponding labels,
	// button to sign in and button to go back to home
	Label l;
	Label u;
	Label p;
	TextField username;
	TextField password;
	Button go;
	Button back;

	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public Login(SceneController pm) {
		// sets scenecontroller, themecontroller, loads all components, and sets
		// background and creates the scene
		this.pm = pm;
		this.tc = pm.tc;
		loadView();
		Image image1 = new Image(tc.getCurrent().getImage());
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		b.setAccessibleHelp("background");
		b.setBackground(new Background(new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, bSize)));

		scene = new Scene(b, 500, 300, Color.CORNSILK);
		// KEYBOARD SHORTCUTS--press enter to attempt to log in, open an alert with
		// error if doesn't work
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				String success = pm.LogIn(username.getText().toLowerCase(), password.getText());
				if ((success.equals("success"))) {
					if (pm.getP() != null) {
						pm.getP().reset();
					}
					pm.launchPaint();
				} else {
					Alert alert = new Alert(AlertType.NONE, (pm.rb).getString(success), ButtonType.CLOSE);
					alert.setTitle((pm.rb).getString("login"));
					alert.showAndWait();
				}
			}
		});
	}

	// add log in buttons, such as fields for username, password, and back to home
	private void loadView() {
		// put all components in gridpane for formatting
		l = new Label("Log In");
		l.setAccessibleHelp("label");
		GridPane g = new GridPane();
		u = new Label("Username: ");
		p = new Label("Password: ");
		username = new TextField();
		password = new PasswordField();

		// format gridpane
		g.add(u, 0, 0);
		g.add(username, 1, 0);
		g.add(p, 0, 1);
		g.add(password, 1, 1);
		g.setAlignment(Pos.CENTER);
		g.setHgap(5);
		g.setVgap(10);

		go = new Button("Log In");
		go.setAccessibleHelp("button");
		go.setOnAction(e -> {
			// check if valid--call pm.log in and get returning message
			String success = pm.LogIn(username.getText().toLowerCase(), password.getText());
			if (success.equals("success")) {
				// we were successful--reset paintscene if exists, and launch with current user
				if (pm.getP() != null) {
					pm.getP().reset();
				}
				pm.launchPaint();
			} else {
				// create new alert with message and allow user to try again
				Alert alert = new Alert(AlertType.NONE, (pm.rb).getString(success), ButtonType.CLOSE);
				alert.setTitle((pm.rb).getString("login"));
				alert.showAndWait();
			}
		});

		back = new Button("Back to Home");
		back.setAccessibleHelp("back");
		back.setOnAction(e -> {
			// takes back to home page
			pm.launchHomePage();
		});
		back.setAccessibleHelp("home page");

		// set layouts and add to borderpane
		VBox v = new VBox();
		v.getChildren().addAll(l, g, go, back);
		StackPane s = new StackPane();
		r = new Rectangle(400, 250);
		r.setFill(tc.getCurrent().getColor("bg"));
		r.setOpacity(0.8);
		s.getChildren().addAll(r, v);
		b.setCenter(s);
		b.setPadding(new Insets(10, 50, 50, 50));
		v.setPadding(new Insets(20, 20, 20, 30));
		v.setAlignment(Pos.CENTER);
		v.setSpacing(5);

		// set style of components based on theme
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

	public void reset() {
		username.setText("");
		password.setText("");
	}

	// resetlanguage/accessibility
	public void resetComponents() {

		l.setText((pm.rb).getString("login"));
		u.setText((pm.rb).getString("username"));
		p.setText((pm.rb).getString("password"));

		go.setText((pm.rb).getString("login"));
		back.setText((pm.rb).getString("backtomenu"));
		username.setAccessibleText((pm.rb).getString("textfieldusername"));
		password.setAccessibleText((pm.rb).getString("textfieldpassword"));
		l.setAccessibleText((pm.rb).getString("labellogin"));
		go.setAccessibleText((pm.rb).getString("buttongo"));
		back.setAccessibleText((pm.rb).getString("buttonbacktohome"));
		setComponentThemeStyle();

	}

	// sets styles of all components based on theme, and changes background
	public void setComponentThemeStyle() {
		Image image1 = new Image(tc.getCurrent().getImage());

		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		b.setBackground(new Background(new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, bSize)));

		l.setFont(Font.font("Courier", 40));
		l.setTextFill(tc.getCurrent().getColor("txt"));

		u.setTextFill(tc.getCurrent().getColor("btntxt"));
		u.setFont(Font.font("Courier", 15));

		p.setTextFill(tc.getCurrent().getColor("btntxt"));
		p.setFont(Font.font("Courier", 15));
		r.setFill(tc.getCurrent().getColor("bg"));

		username.setAccessibleHelp("username");
		username.setStyle("-fx-background-color: " + tc.getCurrent().getSecondaryColorHex() + ";" + "-fx-text-fill: "
				+ tc.getCurrent().getButtonTextColorHex());
		username.setMaxHeight(40);
		username.setMaxWidth(300);
		username.setFont(Font.font("Courier", 15));

		password.setAccessibleHelp("password");
		password.setStyle("-fx-background-color: " + tc.getCurrent().getSecondaryColorHex() + ";" + "-fx-text-fill: "
				+ tc.getCurrent().getButtonTextColorHex());
		password.setMaxHeight(40);
		password.setMaxWidth(300);
		password.setFont(Font.font("Courier", 15));

		go.setAccessibleHelp("password");
		go.setStyle("-fx-background-color: " + tc.getCurrent().getButtonColorHex() + ";");
		go.setMaxHeight(40);
		go.setMaxWidth(100);
		go.setFont(Font.font("Courier", 10));
		go.setTextFill(tc.getCurrent().getColor("btntxt"));

		back.setStyle("-fx-background-color: " + tc.getCurrent().getButtonColorHex());
		back.setMaxHeight(40);
		back.setMaxWidth(100);
		back.setFont(Font.font("Courier", 10));
		back.setTextFill(tc.getCurrent().getColor("btntxt"));
	}

}
