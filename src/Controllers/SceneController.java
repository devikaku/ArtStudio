package Controllers;

//Scene Controller
//Devika Kumar
//ITP 368, Spring 2018
//Final Project
//devikaku@usc.edu
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import application.HomePage;
import application.Instructions;
import application.Login;
import application.PaintingScene;
import application.Settings;
import application.Signup;
import javafx.application.*;
import javafx.stage.Stage;
import model.User;

//CONTROLLER: Initializes all scenes, switches between scenes, and connects scenes to the themecontroller and usercontroller class
//handles all language changes, theme changes, and user changes, and broadcasts them to the relevant scenes
public class SceneController extends Application {
	// contains stage and all relevant scenes
	public Stage primaryStage;
	public PaintingScene p;
	private HomePage hp;
	private Login li;
	private Signup su;
	private Settings stg;
	private Instructions i;

	// language string tells what language we are using--currentlocale and rb
	// connect to localization files
	private String language = "english";
	public static Locale currentLocale = new Locale("en", "US");
	public static ResourceBundle rb = ResourceBundle.getBundle("bundle.localization", currentLocale);

	// themecontroller and usercontroller control user/theme databases--see json
	// files
	public ThemeController tc = new ThemeController();
	private UserController uc = new UserController(null, false);

	public void start(Stage stage) throws Exception {
		// intialize all scenes and show stage
		this.primaryStage = stage;
		hp = new HomePage(this);
		li = new Login(this);
		i = new Instructions(this);
		su = new Signup(this);
		stg = new Settings(this);
		primaryStage.setScene(hp.getScene());
		primaryStage.setTitle("My Art Studio");
		primaryStage.show();
	}

	public void launchPaint() {
		// initializes paint if null, resets all components such as canvas, slider
		// values, etc. and launches
		if (p == null) {
			p = new PaintingScene(this);
			p.reset();
			p.resetLanguageComponents();
		}
		p.resetComponents();
		primaryStage.setScene(p.getScene());
		primaryStage.show();
	}

	public void launchHomePage() {
		// launches first scene
		primaryStage.setScene(hp.getScene());
		primaryStage.show();

	}

	public void launchInstructions() {
		// launch instructions scene
		primaryStage.setScene(i.getScene());
		primaryStage.show();

	}

	public void launchSettings() {
		// resets setting components and launches
		stg.reset();
		primaryStage.setScene(stg.getScene());
		primaryStage.show();

	}

	public void launchLogin() {
		// reset login components and launches
		li.reset();
		primaryStage.setScene(li.getScene());
		primaryStage.show();
	}

	public void launchSignup() {
		// resets signup components and launches
		su.reset();
		primaryStage.setScene(su.getScene());
		primaryStage.show();
	}

	// sets current user in usercontroller
	public void setCurrentUser(User u) {
		uc.setCurrentUser(u);
	}

	// main method
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return uc.isLoggedIn();
	}

	/**
	 * @param loggedIn
	 *            the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		uc.setLoggedIn(loggedIn);
	}

	// logs in user using usercontroller--returns message of success or error
	public String LogIn(String username, String password) {
		// if user doesnt exist return false
		return uc.LogIn(username, password);
	}

	// signs in user using usercontroller--returns message of success or error
	public String SignUp(String username, String password, String rpassword) {
		return uc.SignUp(username, password, rpassword);
	}

	// get users from usercontroller
	public Map<String, User> getUsers() {
		return uc.getUsers();
	}

	// get currentuser from uesrcontroller
	public User getCurrentUser() {
		return uc.getCurrentUser();
	}

	// sets language based on string and updates locale and resourcebundle
	public void setLanguage(String string) {
		language = string;
		if (string.equals("english")) {
			 
			currentLocale = new Locale("en", "US");
		} else if (string.equals("spanish")) {
			 
			currentLocale = new Locale("es");
		}
		rb = ResourceBundle.getBundle("bundle.localization", currentLocale);
		 
	}

	// get language
	public String getLanguage() {
		return language;

	}

	// log out user using usercontroller and launch home page
	public void logout() {
		primaryStage.setTitle("My Art Studio");
		uc.logout();
		launchHomePage();

	}

	// resets all theme components based on new theme
	public void resetTheme() {

		hp.setComponentThemeStyle();
		li.setComponentThemeStyle();
		i.setComponentThemeStyle();
		su.setComponentThemeStyle();
		stg.setComponentThemeStyle();
		if (p != null) {
			p.setComponentThemeStyle();
		}
	}

	// edits user in usercontroller--returns message of status
	public String EditUser(String text, String text2, String text3) {
		return uc.editUser(text, text2, text3);
	}

	// get and set painting scene
	public PaintingScene getP() {
		return p;
	}

	public void setP(PaintingScene p) {
		this.p = p;
	}

	// reset language in all classes
	public void resetLanguageComponents() {
		hp.resetComponents();
		li.resetComponents();
		su.resetComponents();
		i.resetComponents();
		stg.resetComponents();
		if (p != null) {
			p.resetLanguageComponents();
		}

	}
}
