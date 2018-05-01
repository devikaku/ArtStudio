package Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class PaintModelScene extends Application{
	public Stage primaryStage;
	public PaintingScene p;
	private HomePage hp;
	private Login li;
	private Signup su;
	private boolean changedUser = false;
	private Settings stg;
	private Instructions i;
	private UserController uc = new UserController(null, false);
	private String language = "english";
	public static Locale currentLocale = new Locale("en", "US");
	public ThemeController tc = new ThemeController();
	public static ResourceBundle rb = ResourceBundle.getBundle("bundle.localization", currentLocale);

public void start(Stage stage) throws Exception {
	this.primaryStage = stage;
		hp = new HomePage(this);
		li = new Login(this);
		i = new Instructions(this);
		su = new Signup(this);
		stg = new Settings(this);
        primaryStage.setScene(hp.getScene());
        primaryStage.setTitle("Paint App");
        primaryStage.show();
    }
public void launchPaint() {
	if(p==null) {
		p = new PaintingScene(this);
		p.reset();
		p.resetLanguageComponents();
	}
	p.resetComponents();
	primaryStage.setScene(p.getScene());
	primaryStage.show();
}
public void launchHomePage() {
	primaryStage.setScene(hp.getScene());
	primaryStage.show();
	
}
public void launchInstructions() {
	primaryStage.setScene(i.getScene());
	primaryStage.show();
	
}
public void launchSettings() {
	stg.reset();
	primaryStage.setScene(stg.getScene());
	primaryStage.show();
	
}
public void launchLogin() {
	li.reset();
	primaryStage.setScene(li.getScene());
	primaryStage.show();
}
public void launchSignup() {
	su.reset();
	primaryStage.setScene(su.getScene());
	primaryStage.show();
}
public void setCurrentUser(User u) {
	uc.setCurrentUser(u);
}

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
	 * @param loggedIn the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		uc.setLoggedIn(loggedIn);
	}
	public String LogIn(String username, String password) {
		//if user doesnt exist return false
		return uc.LogIn(username, password);
	}
	public String SignUp(String username, String password, String rpassword) {
		//if passwords dont match return false
		System.out.println(password);
		System.out.println(rpassword);
		return uc.SignUp(username, password, rpassword);
	}
	public Map<String, User> getUsers() {
		return uc.getUsers();
	}
	public User getCurrentUser() {
		return uc.getCurrentUser();
	}
	public void setLanguage(String string) {
		language = string;
		if(string.equals("english")){
			System.out.println("hi");
			currentLocale = new Locale("en", "US");
		}else if(string.equals("spanish")) {
			System.out.println("hi2");
			currentLocale = new Locale("es");
		}
		rb = ResourceBundle.getBundle("bundle.localization", currentLocale);
		System.out.println(rb.getLocale().getLanguage());
	}
	public String getLanguage() {
		return language;
		
	}
	public void logout() {
		uc.logout();
		launchHomePage();
		
	}
	public void resetTheme() {
		
		hp.setComponentThemeStyle();
		li.setComponentThemeStyle();
		i.setComponentThemeStyle();
		su.setComponentThemeStyle();
		stg.setComponentThemeStyle();
		if(p!=null) {
			p.setComponentThemeStyle();
		}
	}
	public String EditUser(String text, String text2, String text3) {
		return uc.editUser(text, text2, text3);
	}
	public PaintingScene getP() {
		return p;
	}
	public void setP(PaintingScene p) {
		this.p = p;
	}
	public void resetLanguageComponents() {
		hp.resetComponents();
		li.resetComponents();
		su.resetComponents();
		i.resetComponents();
		stg.resetComponents();
		if(p!=null) {
			p.resetLanguageComponents();
		}
		
	}
}
