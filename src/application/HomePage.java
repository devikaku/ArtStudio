package application;

import java.util.Locale;
import java.util.ResourceBundle;

import Controllers.PaintModelScene;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

//creating new home page
public class HomePage {
	//instance variables
	private PaintModelScene pm;
	private Scene scene;
	Label l;
	Button li;
	Button su;
	Button english;
	Button spanish;
	Button gen;
	BorderPane b = new BorderPane();
	ThemeController tc;
	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public HomePage(PaintModelScene pm) {
		//set current user and backgroun image, as well as accessibility
		this.pm = pm;
		this.tc = pm.tc;
		loadView();
		 Image image1 = new Image(tc.getCurrent().getImage());

		    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);


		    b.setAccessibleHelp("background");
		    b.setBackground(new Background(new BackgroundImage(image1,
		            BackgroundRepeat.REPEAT,
		            BackgroundRepeat.REPEAT,
		            BackgroundPosition.CENTER,
		            bSize)));
		scene = new Scene(b, 500, 300, Color.CORNSILK);
	}
	private void loadView() {
		//load homepage view with localization language change, and login and signup options
		l = new Label((pm.rb).getString("artstudio"));
		li = new Button((pm.rb).getString("login"));
		li.setAccessibleHelp("button");
		li.setOnAction(e->{
			pm.setCurrentUser(null);
			pm.setLoggedIn(false);
			pm.launchLogin();
		});
		su = new Button((pm.rb).getString("signup"));
		su.setOnAction(e->{
			pm.setCurrentUser(null);
			pm.setLoggedIn(false);
			pm.launchSignup();
		});
		
		english = new Button((pm.rb).getString("english"));
		english.setAccessibleHelp("button");
		english.setOnAction(e->{
			System.out.println("here");
			pm.setLanguage("english");
			pm.resetLanguageComponents();
		});
		spanish = new Button((pm.rb).getString("spanish"));
		spanish.setAccessibleHelp("button");
		spanish.setOnAction(e->{
			System.out.println("hereo");
			pm.setLanguage("spanish");
			pm.resetLanguageComponents();
		});
		gen = new Button("Random theme");
		gen.setAccessibleHelp("button");
		gen.setOnAction(e->{
			pm.tc.setRandomTheme();
			pm.resetTheme();
		});
		VBox v = new VBox(l, li, su, english, spanish, gen);
		v.setSpacing(5);
		b.setCenter(v);
        b.setPadding(new Insets(10,50,50,50));
        v.setPadding(new Insets(20,20,20,30));
        v.setAlignment(Pos.CENTER);
        setComponentThemeStyle();
		
	}
	public void resetComponents() {
		l.setText((pm.rb).getString("artstudio"));
		li.setText((pm.rb).getString("login"));
		su.setText((pm.rb).getString("signup"));
		
		english.setText((pm.rb).getString("english"));
		spanish.setText((pm.rb).getString("spanish"));
		setComponentThemeStyle();
		
	}
	public PaintModelScene getPm() {
		return pm;
	}
	public void setPm(PaintModelScene pm) {
		this.pm = pm;
	}
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public void setComponentThemeStyle() {
        su.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex());
        su.setMaxHeight(40);
        su.setMaxWidth(300);
		su.setTextFill(tc.getCurrent().getColor("btntxt"));
        su.setFont(Font.font ("Courier", 15));
		su.setAccessibleHelp("signup");
		
        li.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex());
        li.setMaxHeight(40);
        li.setMaxWidth(300);
        li.setFont(Font.font ("Courier", 15));
		li.setTextFill(tc.getCurrent().getColor("btntxt"));
		
		l.setFont(Font.font ("Courier", 40));
		l.setTextFill(tc.getCurrent().getColor("txt"));
		l.setAccessibleHelp("label");
		 Image image1 = new Image(tc.getCurrent().getImage());

		    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

	    b.setBackground(new Background(new BackgroundImage(image1,
	            BackgroundRepeat.REPEAT,
	            BackgroundRepeat.REPEAT,
	            BackgroundPosition.CENTER,
	            bSize)));
	}

}
