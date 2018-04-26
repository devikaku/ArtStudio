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
//create new signup page with instance variables, accessibility, 
public class Instructions {
	private PaintModelScene pm;
	private Scene scene;
	//error if signup fails
	BorderPane b = new BorderPane();
	StackPane s = new StackPane();
	Rectangle r;
	Label l;
	Text t;
	Button back;
	ThemeController tc;
	
	/**
	 * @param currentUser
	 * @param pm
	 * @param scene
	 */
	public Instructions(PaintModelScene pm) {
		//set current user and backgroun image, as well as accessibility
		this.pm = pm;
		tc = pm.tc;
		loadView();

		scene = new Scene(b, 800, 400, Color.CORNSILK);
	}
	//add log in buttons, such as fields for username, password, repeatpassword,and back to home
	private void loadView() {
		l = new Label("Instructions:");
		
		t = new Text("Click on the buttons to paint.efkefkerfkjenfkenrfjkenrjnfejnrfekrnfenrfejk ");
		r = new Rectangle(500, 200);
		s.getChildren().addAll(r, t);

		back = new Button("Back to Paint");
		back.setOnAction(e->{
			pm.launchPaint();
		});
		back.setCenterShape(true);
		VBox v = new VBox();
		v.getChildren().addAll(l, s, back);
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
		
		t.setFill(tc.getCurrent().getColor("txt"));
		t.setFont(Font.font ("Courier", 10));
		
		r.setFill(tc.getCurrent().getColor("bg"));
		
		back.setStyle("-fx-background-color: "+tc.getCurrent().getButtonColorHex());
		back.setMaxHeight(40);
		back.setMaxWidth(100);
		back.setFont(Font.font ("Courier", 10));
		back.setTextFill(tc.getCurrent().getColor("btntxt"));
	}
	public void resetComponents() {

		
		l.setText((pm.rb).getString("instructions"));
		t.setText((pm.rb).getString("instructionstext"));
		back.setText((pm.rb).getString("backtopaint"));
		
		setComponentThemeStyle();
		
		
	}

}
