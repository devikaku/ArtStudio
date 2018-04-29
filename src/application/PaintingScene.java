package application;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import Controllers.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Tool;
//main painting scene
public class PaintingScene {
	//instance variables including current user, canvas class, colorpicker, paintbrush, toolsize, scene and paintingsceneclass
	private Canvas canvas=new Canvas(800, 500);
	private GraphicsContext g = canvas.getGraphicsContext2D();
	private ColorPicker color =new ColorPicker();
	private PaintModelScene pm;
	private Tool tool = new Tool(canvas, g);
	ComboBox tools;
	private Scene scene;
	private Color bgc = Color.WHITE;
	BorderPane b= new BorderPane();
	private ThemeController tc;
	Label l;
	Label l2;
	 Label cbb;
	Slider slider;
	Label l5;
	Button erase;
	Button paint;
	Button pencil;
	Button stamp;
	Button by;
	Button c;
	Button d;
	Button e;
	/**
	 * @param canvas
	 * @param color
	 * @param brush
	 */
	//constructor, intitialize paint functions
	public PaintingScene(PaintModelScene pm) {
		this.pm=pm;
		this.tc = pm.tc;
        tool.setColor(color.getValue());
        color.setOnAction(e->{
        	tool.setColor(color.getValue());
        });
        g.setFill(bgc);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        setUpCanvas();
		loadView();
		b.setCenter(canvas);
		scene = new Scene(b, 1200, 600, Color.CORNSILK);
	}
	//adding buttons on the left for toolbox-different types of tools and sizes
	//add buttons on the right for user functions such as settings, logout, save, instructions, etc.
	public void loadView() {
		VBox v = new VBox();
		l = new Label("Current Color:");
		l2 = new Label("Tool size:");
		slider = new Slider();
		slider.setMin(1);
		slider.setMax(100);
		slider.setValue(10);
		slider.setBlockIncrement(10);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    System.out.println(new_val.doubleValue());
                    l2.setText("Tool size: "+new_val.intValue());
                    tool.setToolSize(new_val.intValue());
            }
        });
		g.setLineWidth(slider.getValue());
		l5 = new Label("Tools:");
        erase = new Button("Eraser");
        erase.setPadding(new Insets(10, 10, 10, 10));
        erase.setOnAction(e->{
            tool.setTool("eraser");
        });
        paint = new Button("Brush");
        paint.setPadding(new Insets(10, 10, 10, 10));
        paint.setOnAction(e->{
        	tool.setTool("paintbrush");
        });
        
        pencil = new Button("Marker");
        pencil.setPadding(new Insets(10, 10, 10, 10));
        pencil.setOnAction(e->{
        	tool.setTool("pencil");
        });
        stamp = new Button("Stamp");
        stamp.setPadding(new Insets(10, 10, 10, 10));
        stamp.setOnAction(e->{
        	tool.setTool("stamp");
        });
        cbb = new Label("Stamps:");
        ComboBox cb = new ComboBox();
        cb.getItems().addAll("circle", "square", "line");
		v.getChildren().addAll(l, color, l2, slider, l5, erase, paint, pencil, stamp, cbb, cb);
		v.setSpacing(5);
		b.setLeft(v);
		b.setCenter(canvas);
		b.setTop(drawRegion(pm.getCurrentUser().getUsername() + "'s Art Studio"));
		VBox settingstuff = new VBox();
		settingstuff.setSpacing(5);
		by = new Button("Log Out");
		by.setOnAction(e->{
			pm.logout();
		});
		c = new Button("My Settings");
		c.setOnAction(e->{
			pm.launchSettings();
		});
		d = new Button("Save");
		
		//got inspiration for saving image here: http://java-buddy.blogspot.com/2013/04/save-canvas-to-png-file.html
		d.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
              
                //Show save file dialog
                File file = fileChooser.showSaveDialog(pm.primaryStage);
                
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
                        canvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        Logger.getLogger(JavaFX_DrawOnCanvas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
		e = new Button("Instructions");
		e.setOnAction(e->{
			pm.launchInstructions();
		});
		settingstuff.getChildren().addAll(by, c, d, e);
		b.setRight(settingstuff);
		setComponentThemeStyle();
		
	}
	public Scene getScene() {
		return scene;
	}
	//way to draw top region
	private Region drawRegion(String myText) {
		Text text = new Text(myText);
		text.setFont(Font.font("Courier", FontWeight.BOLD, 20));
		text.setFill(tc.getCurrent().getColor("txt"));
		StackPane stackPane = new StackPane();
		stackPane.setPadding(new Insets(20, 20, 20, 20));
		stackPane.setStyle("-fx-background-color: " +  tc.getCurrent().getBgColorHex());
		stackPane.setAlignment(Pos.CENTER);
		stackPane.getChildren().add(text);
		return stackPane;
	}
	public void resetComponents() {
		String s = "";
		String u = pm.getCurrentUser().getUsername();
		if(pm.rb.getLocale().getLanguage().equals("en")) {
			s = u+"'s Art Studio";
		}else if(pm.rb.getLocale().getLanguage().equals("es")) {
			s = "Studio de Arte de "+u; 
		}
		b.setTop(drawRegion(s));
	}
	public void setUpCanvas() {
		canvas.setOnMouseDragged(e ->{
			tool.drawDrag(e, bgc);
            
		});
        canvas.setOnMouseReleased(e->{
        	tool.drawRelease(e);
        });
        
        canvas.setOnMousePressed(e->{
        	tool.drawPress(e);
            });
		
	}
	public void setComponentThemeStyle() {
		 Image image1 = new Image(tc.getCurrent().getImage());
		
		    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);


		    b.setBackground(new Background(new BackgroundImage(image1,
		            BackgroundRepeat.REPEAT,
		            BackgroundRepeat.REPEAT,
		            BackgroundPosition.CENTER,
		            bSize)));
		    if(pm.getCurrentUser()!=null) {
			b.setTop(drawRegion(pm.getCurrentUser().getUsername() + "'s Art Studio"));
		    }
			setButtonStyle(erase);
			setButtonStyle(paint);
			setButtonStyle(pencil);
			setButtonStyle(stamp);
			setButtonStyle(by);
			setButtonStyle(c);
			setButtonStyle(d);
			setButtonStyle(e);
			setLabelStyle(l);
			setLabelStyle(l2);
			setLabelStyle(l5);
			setLabelStyle(cbb);
	}
	public void setButtonStyle(Button b) {
		b.setStyle("-fx-background-color: "+tc.getCurrent().getButtonColorHex());
		b.setMaxHeight(40);
		b.setMaxWidth(150);
		b.setFont(Font.font ("Courier", 10));
		b.setTextFill(tc.getCurrent().getColor("btntxt"));
	}
	public void setLabelStyle(Label l) {
		l.setFont(Font.font ("Courier", 15));
		l.setTextFill(tc.getCurrent().getColor("txt"));
	}
	public void reset() {
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(bgc);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        slider.setValue(1);
        color.setValue(Color.WHITE);
	}
	public void resetLanguageComponents() {
		l.setText((pm.rb).getString("currentcolor"));
		l2.setText((pm.rb).getString("toolsize"));
		l5.setText((pm.rb).getString("tool"));
		erase.setText((pm.rb).getString("eraser"));
		paint.setText((pm.rb).getString("paintbrush"));
		pencil.setText((pm.rb).getString("marker"));
		stamp.setText((pm.rb).getString("stamp"));
		 cbb.setText((pm.rb).getString("stamp")+"s");
		by.setText((pm.rb).getString("logout"));
		c.setText((pm.rb).getString("settings"));
		d.setText((pm.rb).getString("save"));
		e.setText((pm.rb).getString("instructions"));
		
		l.setAccessibleText((pm.rb).getString("currentcolor"));
		l2.setAccessibleText((pm.rb).getString("toolsize"));
		l5.setAccessibleText((pm.rb).getString("tool"));
		erase.setAccessibleText((pm.rb).getString("eraser"));
		paint.setAccessibleText((pm.rb).getString("paintbrush"));
		pencil.setAccessibleText((pm.rb).getString("marker"));
		stamp.setAccessibleText((pm.rb).getString("stamp"));
		by.setAccessibleText((pm.rb).getString("logout"));
		c.setAccessibleText((pm.rb).getString("settings"));
		d.setAccessibleText((pm.rb).getString("save"));
		e.setAccessibleText((pm.rb).getString("instructions"));
		cbb.setAccessibleText((pm.rb).getString("stamp")+"s");
	}
	 

}
