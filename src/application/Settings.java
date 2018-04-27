package application;

import java.util.Map;

import Controllers.PaintModelScene;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Theme;
//create new signup page with instance variables, accessibility, 
public class Settings {
	private PaintModelScene pm;
	private Scene scene;
	//error if signup fails
	Label error = new Label("");
	BorderPane b = new BorderPane();
	Label l;
	Label u;
	Label p;
	Label rp;
	Label ct;
	TextField username;
	TextField password;
	TextField rpassword;
	Button go;
	Button back;
	ThemeController tc;
	ComboBox<String> themes;
	Button english;
	Button spanish;
	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public Settings(PaintModelScene pm) {
		//set current user and backgroun image, as well as accessibility
		this.pm = pm;
		tc = pm.tc;
		loadView();

		scene = new Scene(b, 800, 400, Color.CORNSILK);
	}
	//add log in buttons, such as fields for username, password, repeatpassword,and back to home
	private void loadView() {
		l = new Label("Settings ");

		username = new TextField();
		password = new PasswordField();
		rpassword = new PasswordField();
		
		GridPane g = new GridPane();
		u = new Label("Change Username: ");

		p = new Label("Change Password: ");
		
		rp = new Label("Repeat changed password: ");
		
		ct = new Label("Change theme: ");
		themes = new ComboBox<String>();
		for (Map.Entry<String, Theme> entry : tc.getThemes().entrySet()) {
			themes.getItems().add(entry.getKey());
		}
				
	   themes.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	            System.out.println(t1);
	            tc.setCurrent(tc.getThemes().get(t1));
	            pm.resetTheme();
	        }    
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
		
		
		go = new Button("Save Changes");
		go.setOnAction(e->{
			//check if valid
			if(pm.EditUser(username.getText(), password.getText(), rpassword.getText())) {
				Alert alert = new Alert(AlertType.NONE, (pm.rb).getString("settingssaved"), ButtonType.CLOSE);
				alert.setTitle((pm.rb).getString("settings"));
				alert.showAndWait();
			}else {
				Alert alert = new Alert(AlertType.NONE, (pm.rb).getString("settingsnotsaved"), ButtonType.CLOSE);
				alert.setTitle((pm.rb).getString("settings"));
				alert.showAndWait();
			}
		});
		back = new Button("Back to Paint");
		back.setOnAction(e->{
			pm.launchPaint();
		});
		back.setCenterShape(true);
		VBox v = new VBox();
		v.getChildren().addAll(l, g, h, go, back);
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
	public void reset() {
		resetError();
		username.setText("");
		password.setText("");
		rpassword.setText("");
		
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
		u.setFont(Font.font ("Courier", 10));
		
		ct.setTextFill(tc.getCurrent().getColor("btntxt"));
		ct.setFont(Font.font ("Courier", 10));
		
		p.setTextFill(tc.getCurrent().getColor("btntxt"));
		p.setFont(Font.font ("Courier", 10));
		
		rp.setTextFill(tc.getCurrent().getColor("btntxt"));
		rp.setFont(Font.font ("Courier", 10));
		
        english.setStyle("-fx-background-color: "+tc.getCurrent().getButtonColorHex());
        english.setMaxHeight(40);
        english.setMaxWidth(150);
        english.setFont(Font.font ("Courier", 10));
		english.setTextFill(tc.getCurrent().getColor("btntxt"));
		
        spanish.setStyle("-fx-background-color: "+tc.getCurrent().getButtonColorHex());
        spanish.setMaxHeight(40);
        spanish.setMaxWidth(150);
        spanish.setFont(Font.font ("Courier", 10));
		spanish.setTextFill(tc.getCurrent().getColor("btntxt"));
		
		username.setAccessibleHelp("username");
		username.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex()+ "; -fx-text-fill: " + tc.getCurrent().getButtonTextColorHex());
		username.setMaxHeight(40);
		username.setMaxWidth(300);
		username.setFont(Font.font ("Courier", 15));
		
		password.setAccessibleHelp("password");
		password.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex()+ "; -fx-text-fill: " + tc.getCurrent().getButtonTextColorHex());
		password.setMaxHeight(40);
		password.setMaxWidth(300);
		password.setFont(Font.font ("Courier", 15));
		
		rpassword.setAccessibleHelp("password");
		rpassword.setStyle("-fx-background-color: "+tc.getCurrent().getSecondaryColorHex()+ "; -fx-text-fill: " + tc.getCurrent().getButtonTextColorHex());
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
