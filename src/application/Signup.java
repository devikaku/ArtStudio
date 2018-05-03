package application;

//Sign Up
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

//create new Sign Up page with fields for username, password, repeatedpassword, and buttons to check signup and go back to home
public class Signup {
	// instance variables: I have a scenecontroller which is the scene manager,
	// the scene associated with this class
	private SceneController pm;
	private Scene scene;
	// borderpane acts as scene root, contains rectangle to overlay
	Rectangle r;
	BorderPane b = new BorderPane();

	Label l; // title
	// labels for username, password, repeatpassword
	Label u;
	Label p;
	Label rp;

	// textfields for receiving new info
	TextField username;
	TextField password;
	TextField rpassword;

	// button to sign up
	Button go;

	// button to go back to home
	Button back;

	// themecontroller sets style based on theme
	ThemeController tc;

	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public Signup(SceneController pm) {
		// sets scenecontroller, themecontroller, loads all components, and sets
		// background and creates the scene
		this.pm = pm;
		tc = pm.tc;
		loadView();

		scene = new Scene(b, 500, 300, Color.CORNSILK);
		// KEYBOARD SHORTCUTS--can use enter to check signup
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				String success = pm.SignUp(username.getText().toLowerCase(), password.getText(), rpassword.getText());
				if ((success.equals("success"))) {
					if (pm.getP() != null) {
						pm.getP().reset();
					}
					pm.launchPaint();
				} else {
					Alert alert = new Alert(AlertType.NONE, (pm.rb).getString(success), ButtonType.CLOSE);
					alert.setTitle((pm.rb).getString("signup"));
					alert.showAndWait();
				}
			}
		});
	}

	// add sign up buttons, such as fields for username, password,
	// repeatpassword,and back to home
	private void loadView() {
		l = new Label("Sign up");

		username = new TextField();
		password = new PasswordField();
		rpassword = new PasswordField();

		// gridpane formats all components
		GridPane g = new GridPane();
		u = new Label("Username: ");

		p = new Label("Password: ");

		rp = new Label("Repeat password: ");

		go = new Button("Sign up");
		go.setOnAction(e -> {
			// check if credentials are valid--calls method in scenecontroller and checks
			// for success-adds user if successful
			String success = pm.SignUp(username.getText().toLowerCase(), password.getText(), rpassword.getText());
			if ((success.equals("success"))) {
				// resets paint scene if exists and launches
				if (pm.getP() != null) {
					pm.getP().reset();
				}
				pm.launchPaint();
			} else {
				// throws alert with message returned from signup
				Alert alert = new Alert(AlertType.NONE, (pm.rb).getString(success), ButtonType.CLOSE);
				alert.setTitle((pm.rb).getString("signup"));
				alert.showAndWait();
			}
		});

		// launches home page
		back = new Button("Back to Home");
		back.setOnAction(e -> {
			pm.launchHomePage();
		});
		back.setCenterShape(true);
		// formatting layout and adding to borderpane
		g.add(u, 0, 0);
		g.add(username, 1, 0);
		g.add(p, 0, 1);
		g.add(password, 1, 1);
		g.add(rp, 0, 2);
		g.add(rpassword, 1, 2);
		g.setAlignment(Pos.CENTER);
		g.setHgap(5);
		g.setVgap(10);
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

		// reset language/accessibility/style based on theme
		resetComponents();
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

	// resets accessibility/localization
	public void resetComponents() {

		l.setText((pm.rb).getString("signup"));
		u.setText((pm.rb).getString("username"));
		p.setText((pm.rb).getString("password"));
		rp.setText((pm.rb).getString("repeatpassword"));

		go.setText((pm.rb).getString("signup"));
		back.setText((pm.rb).getString("backtomenu"));

		username.setAccessibleText((pm.rb).getString("textfieldusername"));
		password.setAccessibleText((pm.rb).getString("textfieldpassword"));
		rpassword.setAccessibleText((pm.rb).getString("textfieldrpassword"));
		l.setAccessibleText((pm.rb).getString("labelsignup"));
		go.setAccessibleText((pm.rb).getString("buttongo"));
		back.setAccessibleText((pm.rb).getString("buttonbacktohome"));

	}// set styles for buttons

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
		setLabelStyle(p);
		setLabelStyle(rp);
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

	// reset components
	public void reset() {
		username.setText("");
		password.setText("");
		rpassword.setText("");

	}

}
