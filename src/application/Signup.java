package application;

import Controllers.PaintModelScene;
import Controllers.ThemeController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
//create new signup page with instance variables, accessibility, 
public class Signup {
	private PaintModelScene pm;
	private Scene scene;
	//error if signup fails
	Label error = new Label("");
	BorderPane b = new BorderPane();
	Label l;
	Label u;
	Label p;
	Label rp;
	TextField username;
	TextField password;
	TextField rpassword;
	Button go;
	Button back;
	ThemeController tc;
	
	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public Signup(PaintModelScene pm) {
		//set current user and backgroun image, as well as accessibility
		this.pm = pm;
		tc = pm.tc;
		loadView();

		scene = new Scene(b, 500, 300, Color.CORNSILK);
	}
	//add log in buttons, such as fields for username, password, repeatpassword,and back to home
	private void loadView() {
		l = new Label("Sign up");

		username = new TextField();
		password = new PasswordField();
		rpassword = new PasswordField();
		
		GridPane g = new GridPane();
		u = new Label("Username: ");

		p = new Label("Password: ");
		
		rp = new Label("Repeat password: ");

		
		g.add(u, 0, 0);
		g.add(username, 1, 0);
		g.add(p, 0, 1);
		g.add(password, 1, 1);
		g.add(rp, 0, 2);
		g.add(rpassword, 1, 2);
		g.setAlignment(Pos.CENTER);
		
		go = new Button("Sign up");
		go.setOnAction(e->{
			//check if valid
			if(pm.SignUp(username.getText(), password.getText(), rpassword.getText())) {
				if(pm.getP()!=null) {
					pm.getP().reset();
				}
				pm.launchPaint();
			}else {
				error.setText(((pm.rb).getString("signuperror")));
			}
		});
		back = new Button("Back to Home");
		back.setOnAction(e->{
			pm.launchHomePage();
		});
		back.setCenterShape(true);
		VBox v = new VBox();
		v.getChildren().addAll(l, g, error, go, back);
		b.setCenter(v);
        b.setPadding(new Insets(10,50,50,50));
        v.setPadding(new Insets(20,20,20,30));
        v.setAlignment(Pos.CENTER);
        v.setSpacing(5);
        
        setComponentThemeStyle();
	}
	//getters/setters
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
	public void resetError() {
		error.setText("");
	}
	public void resetComponents() {
		
		l.setText((pm.rb).getString("signup"));
		u.setText((pm.rb).getString("username"));
		p.setText((pm.rb).getString("password"));
		rp.setText((pm.rb).getString("repeatpassword"));
		
		go.setText((pm.rb).getString("signup"));
		back.setText((pm.rb).getString("backtomenu"));
		setComponentThemeStyle();
		
	}
	public void setComponentThemeStyle() {
		 Image image1 = new Image(tc.getCurrent().getImage());

		    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

	    b.setBackground(new Background(new BackgroundImage(image1,
	            BackgroundRepeat.REPEAT,
	            BackgroundRepeat.REPEAT,
	            BackgroundPosition.CENTER,
	            bSize)));
	    
		l.setFont(Font.font ("Courier", 40));
		l.setTextFill(tc.getCurrent().getColor("txt"));
		
		u.setTextFill(tc.getCurrent().getColor("btntxt"));
		u.setFont(Font.font ("Courier", 15));
		
		p.setTextFill(tc.getCurrent().getColor("btntxt"));
		p.setFont(Font.font ("Courier", 15));
		
		rp.setTextFill(tc.getCurrent().getColor("btntxt"));
		rp.setFont(Font.font ("Courier", 15));
		
		
		
		username.setAccessibleHelp("username");
		username.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex());
		username.setMaxHeight(40);
		username.setMaxWidth(300);
		username.setFont(Font.font ("Courier", 15));
		
		password.setAccessibleHelp("password");
		password.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex());
		password.setMaxHeight(40);
		password.setMaxWidth(300);
		password.setFont(Font.font ("Courier", 15));
		
		rpassword.setAccessibleHelp("password");
		rpassword.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex());
		rpassword.setMaxHeight(40);
		rpassword.setMaxWidth(300);
		rpassword.setFont(Font.font ("Courier", 15));
		
		go.setAccessibleHelp("password");
		go.setStyle("-fx-background-color: "+tc.getCurrent().getButtonColorHex());
		go.setMaxHeight(40);
		go.setMaxWidth(100);
		go.setFont(Font.font ("Courier", 10));
		go.setTextFill(tc.getCurrent().getColor("btntxt"));
		
		back.setStyle("-fx-background-color: "+tc.getCurrent().getButtonColorHex());
		back.setMaxHeight(40);
		back.setMaxWidth(100);
		back.setFont(Font.font ("Courier", 10));
		back.setTextFill(tc.getCurrent().getColor("btntxt"));
	}
	public void reset() {
		resetError();
		username.setText("");
		password.setText("");
		rpassword.setText("");
		
	}

}