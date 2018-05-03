package application;

//PAINT SCENE
//Devika Kumar
//ITP 368, Spring 2018
//Final Project
//devikaku@usc.edu
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.InputMap;
import javax.swing.JComponent;

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

//MAIN PAINTING SCENE
public class PaintingScene {
	// instance variables:
	// canvas for user to draw on
	// graphicscontext object associated with canvas
	// colorpicker associated with color of current toolController
	// scenecontroller--scene manager
	// current toolController that takes in our canvas and graphicscontext
	// stampname that tells what is current shape stamp
	// scene associated with class
	// boolean that tells if stamp toolController is selected
	// backgroundcolor--default to white
	// borderpane--contains whole layout
	// themecontroller-controls all theme changes
	private Canvas canvas = new Canvas(800, 500);
	private GraphicsContext g = canvas.getGraphicsContext2D();
	private ColorPicker color = new ColorPicker();
	private SceneController pm;
	private ToolController toolController = new ToolController(canvas, g);
	String stampname = "";
	private Scene scene;
	boolean stampselect = false;
	private Color bgc = Color.WHITE;
	BorderPane b = new BorderPane();
	private ThemeController tc;

	// LABELS/BUTTONS associated with tools
	Label l;
	Label l2;
	Label ls;
	Slider slider;
	Label l5;
	Button erase;
	Button paint;
	Button pencil;
	Button stamp;
	// all stamp options
	ComboBox<String> stamps = new ComboBox<String>();

	// associated with save, clear, log out, settings, and instructions
	Button by;
	Button c;
	Button d;
	Button e;
	Button clear;

	/**
	 * @param canvas
	 * @param color
	 * @param brush
	 */
	// sets scenecontroller, themecontroller, loads all components, and sets
	// background and creates the scene
	public PaintingScene(SceneController pm) {
		this.pm = pm;
		this.tc = pm.tc;

		// initializes all toolController settings and sets up canvas, loads all components
		setUpCanvas();
		loadView();
		b.setCenter(canvas);
		scene = new Scene(b, 1200, 600, Color.CORNSILK);

		// KEYBOARD SHORTCUTS FOR SCENE:
		scene.setOnKeyPressed(e -> {
			// UP AND DOWN ARROWS ALLOW SLIDER VALUE/TOOLSIZE TO INCREMENT/DECREMENT AND
			// UPDATE LABEL
			if (e.getCode() == KeyCode.UP) {
				if (slider.getValue() < 100) {
					slider.requestFocus();
					slider.increment();
					l2.setText("tool size: " + (int) slider.getValue());
					toolController.setToolSize((int) slider.getValue());
				}
			}
			if (e.getCode() == KeyCode.DOWN) {
				 
				if (slider.getValue() > 0) {
					slider.requestFocus();
					slider.decrement();
					l2.setText("tool size: " + (int) slider.getValue());
					toolController.setToolSize((int) slider.getValue());
				}
			}

			// Q ALLOWS USER TO LOG OUT--SEE LOADVIEW FUNCTION FOR DETAILS
			if (e.getCode() == KeyCode.Q) {
				ButtonType logout = new ButtonType((pm.rb).getString("logout"), ButtonBar.ButtonData.OK_DONE);
				ButtonType save = new ButtonType((pm.rb).getString("save"), ButtonBar.ButtonData.CANCEL_CLOSE);
				ButtonType cancel = new ButtonType((pm.rb).getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
				Alert alert = new Alert(AlertType.NONE, (pm.rb).getString("logoutorsave"), logout, save, cancel);

				alert.setTitle((pm.rb).getString("logout"));
				Optional<ButtonType> result = alert.showAndWait();

				if (result.isPresent() && result.get() == save) {
					FileChooser fileChooser = new FileChooser();

					// Set extension filter
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)",
							"*.png");
					fileChooser.getExtensionFilters().add(extFilter);

					// Show save file dialog
					File file = fileChooser.showSaveDialog(pm.primaryStage);

					if (file != null) {
						try {
							WritableImage writableImage = new WritableImage((int) canvas.getWidth(),
									(int) canvas.getHeight());
							canvas.snapshot(null, writableImage);
							RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
							ImageIO.write(renderedImage, "png", file);
						} catch (IOException ex) {
						}
					}
					pm.logout();
				} else if (result.isPresent() && result.get() == logout) {
					pm.logout();
				}
			}

			// W ALLOWS USER TO SAVE, SEE LOADVIEW FUNCTION FOR DETAILS
			if (e.getCode() == KeyCode.W) {

				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
				fileChooser.getExtensionFilters().add(extFilter);

				// Show save file dialog
				File file = fileChooser.showSaveDialog(pm.primaryStage);

				if (file != null) {
					try {
						WritableImage writableImage = new WritableImage((int) canvas.getWidth(),
								(int) canvas.getHeight());
						canvas.snapshot(null, writableImage);
						RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
						ImageIO.write(renderedImage, "png", file);
					} catch (IOException ex) {
					}
				}

			}
			// SET KEYCODES TO SELECT CERTAIN TOOLS BASED ON FIRST LETTER OF WORD
			// DIFFERENT IN SPANISH AND ENGLISH
			if ((pm.rb).getLocale().getLanguage().equals("en")) {
				if (e.getCode() == KeyCode.E) {
					toolController.setTool("eraser");
					setSelected(erase);
				}
				if (e.getCode() == KeyCode.P) {
					toolController.setTool("paintbrush");
					setSelected(paint);
				}
				if (e.getCode() == KeyCode.M) {
					toolController.setTool("pencil");
					setSelected(pencil);
				}
				if (e.getCode() == KeyCode.C) {
					ButtonType clear = new ButtonType((pm.rb).getString("clear"), ButtonBar.ButtonData.OK_DONE);
					ButtonType cancel = new ButtonType((pm.rb).getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
					Alert alert = new Alert(AlertType.NONE, (pm.rb).getString("clearwork"), clear, cancel);

					alert.setTitle((pm.rb).getString("clear"));
					Optional<ButtonType> result = alert.showAndWait();

					if (result.isPresent() && result.get() == clear) {
						toolController.clear();
					}
				}

			} else if (((pm.rb).getLocale().getLanguage().equals("es"))) {
				if (e.getCode() == KeyCode.B) {
					toolController.setTool("eraser");
					setSelected(erase);
				}
				if (e.getCode() == KeyCode.C) {
					toolController.setTool("paintbrush");
					setSelected(paint);
				}
				if (e.getCode() == KeyCode.R) {
					toolController.setTool("pencil");
					setSelected(pencil);
				}
				if (e.getCode() == KeyCode.L) {
					ButtonType clear = new ButtonType((pm.rb).getString("clear"), ButtonBar.ButtonData.OK_DONE);
					ButtonType cancel = new ButtonType((pm.rb).getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
					Alert alert = new Alert(AlertType.NONE, (pm.rb).getString("clearwork"), clear, cancel);

					alert.setTitle((pm.rb).getString("clear"));
					Optional<ButtonType> result = alert.showAndWait();

					if (result.isPresent() && result.get() == clear) {
						toolController.clear();
					}
				}

			}
		});
	}

	// adding buttons on the left for toolbox-different types of tools and sizes
	// add buttons on the right for user functions such as settings, logout, save,
	// instructions, etc.
	public void loadView() {
		VBox v = new VBox();
		// sets labels for current color and toolController size
		l = new Label("Current Color:");
		l2 = new Label("ToolController size:");

		// slider associated with toolsize
		slider = new Slider();
		slider.setMin(1);
		slider.setMax(100);
		slider.setValue(10);
		slider.setBlockIncrement(5);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				 
				// when slider changed, toolsize updates as does label
				l2.setText("tool size: " + new_val.intValue());
				toolController.setToolSize(new_val.intValue());
			}
		});
		// initialize graphicscontext stroke
		g.setLineWidth(slider.getValue());
		l5 = new Label("Tools:");
		ls = new Label("Current Stamp:");

		// create eraser-onclick set the toolController to erase and change style
		erase = new Button("Eraser");
		erase.setPadding(new Insets(10, 10, 10, 10));
		erase.setOnAction(e -> {
			toolController.setTool("eraser");
			setSelected(erase);
		});
		// create paint button-onclick set toolController to paint and change style
		paint = new Button("Brush");
		paint.setPadding(new Insets(10, 10, 10, 10));
		paint.setOnAction(e -> {
			toolController.setTool("paintbrush");
			setSelected(paint);
		});

		// create marker button-onclick set toolController to pencil and change style
		pencil = new Button("Marker");
		pencil.setPadding(new Insets(10, 10, 10, 10));
		pencil.setOnAction(e -> {
			toolController.setTool("pencil");
			setSelected(pencil);
		});
		// create stamp button-onclick set toolController to stamp and change style
		stamp = new Button("Stamp");
		stamp.setPadding(new Insets(10, 10, 10, 10));
		stamp.setOnAction(e -> {
			// shows we have selected stamp and updates toolController to selected stamp
			stampselect = true;
			toolController.setTool(stampname);
			setSelected(stamp);
		});

		// adds all stamp shapes to combobox and changes toolController when selected
		stamps.getItems().addAll("line", "rectangle", "oval", "circle");
		stamps.setFocusTraversable(false);
		stamps.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				 
				stampname = t1;
				if (stampselect) {
					toolController.setTool(stampname);
				}
				slider.requestFocus();
			}
		});
		// clear--throws alert to user and allows them to confirm clear
		clear = new Button("Clear");
		clear.setPadding(new Insets(10, 10, 10, 10));
		clear.setOnAction(e -> {
			ButtonType clear = new ButtonType((pm.rb).getString("clear"), ButtonBar.ButtonData.OK_DONE);
			ButtonType cancel = new ButtonType((pm.rb).getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
			Alert alert = new Alert(AlertType.NONE, (pm.rb).getString("clearwork"), clear, cancel);

			alert.setTitle((pm.rb).getString("clear"));
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == clear) {
				toolController.clear();
			}
		});

		d = new Button("Save");
		d.setPadding(new Insets(10, 10, 10, 10));

		// got inspiration for saving image here:
		// http://java-buddy.blogspot.com/2013/04/save-canvas-to-png-file.html
		d.setOnAction(new EventHandler<ActionEvent>() {
			// use filechooser object to save our canvas as an image
			@Override
			public void handle(ActionEvent t) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
				fileChooser.getExtensionFilters().add(extFilter);

				// Show save file dialog
				File file = fileChooser.showSaveDialog(pm.primaryStage);

				if (file != null) {
					try {
						WritableImage writableImage = new WritableImage((int) canvas.getWidth(),
								(int) canvas.getHeight());
						canvas.snapshot(null, writableImage);
						RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
						ImageIO.write(renderedImage, "png", file);
					} catch (IOException ex) {
					}
				}
			}

		});
		// formatting and add to borderpane
		v.getChildren().addAll(l, color, ls, stamps, l2, slider, l5, erase, paint, pencil, stamp, clear, d);
		v.setSpacing(5);
		b.setLeft(v);
		b.setCenter(canvas);
		// create top title
		b.setTop(drawRegion(pm.getCurrentUser().getUsername() + "'s Art Studio"));
		VBox settingstuff = new VBox();
		settingstuff.setSpacing(5);
		by = new Button("Log Out");

		// gives an option to cancel or save work before logging out, using the
		// filechoose object above
		by.setOnAction(e -> {
			ButtonType logout = new ButtonType((pm.rb).getString("logout"), ButtonBar.ButtonData.OK_DONE);
			ButtonType save = new ButtonType((pm.rb).getString("save"), ButtonBar.ButtonData.CANCEL_CLOSE);
			ButtonType cancel = new ButtonType((pm.rb).getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
			Alert alert = new Alert(AlertType.NONE, (pm.rb).getString("logoutorsave"), logout, save, cancel);

			alert.setTitle((pm.rb).getString("logout"));
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == save) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
				fileChooser.getExtensionFilters().add(extFilter);

				// Show save file dialog
				File file = fileChooser.showSaveDialog(pm.primaryStage);

				if (file != null) {
					try {
						WritableImage writableImage = new WritableImage((int) canvas.getWidth(),
								(int) canvas.getHeight());
						canvas.snapshot(null, writableImage);
						RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
						ImageIO.write(renderedImage, "png", file);
						pm.logout();
					} catch (IOException ex) {
					}
				}
			} else if (result.isPresent() && result.get() == logout) {
				pm.logout();
			}
		});
		// launch settings
		c = new Button("My Settings");
		c.setOnAction(e -> {
			pm.launchSettings();
		});

		// launch instructions
		e = new Button("Instructions");
		e.setOnAction(e -> {
			pm.launchInstructions();
		});

		// add all to borderpane, set default toolController, language, and style
		settingstuff.getChildren().addAll(by, c, e);
		b.setRight(settingstuff);
		setComponentThemeStyle();
		resetLanguageComponents();
		setSelected(paint);
		toolController.setTool("paintbrush");

	}

	// get scene
	public Scene getScene() {
		return scene;
	}

	// way to draw top region--contains username and title
	private Region drawRegion(String myText) {
		Text text = new Text(myText);
		text.setFont(Font.font("Courier", FontWeight.BOLD, 20));
		text.setFill(tc.getCurrent().getColor("txt"));
		StackPane stackPane = new StackPane();
		stackPane.setPadding(new Insets(20, 20, 20, 20));
		stackPane.setStyle("-fx-background-color: " + tc.getCurrent().getBgColorHex());
		stackPane.setAlignment(Pos.CENTER);
		stackPane.getChildren().add(text);
		return stackPane;
	}

	// resets top panel title based on settings
	public void resetComponents() {
		if(pm.getCurrentUser()!=null) {
		String s = "";
		String u = pm.getCurrentUser().getUsername();
		if (pm.rb.getLocale().getLanguage().equals("en")) {
			s = u + "'s Art Studio";
		} else if (pm.rb.getLocale().getLanguage().equals("es")) {
			s = "Studio de Arte de " + u;
		}
		b.setTop(drawRegion(s));
		pm.primaryStage.setTitle(s);
		}
	}

	public void setUpCanvas() {
		// intializes toolController settings and connects our canvas/toolController with our mouse,
		toolController.setColor(color.getValue());
		color.setOnAction(e -> {
			toolController.setColor(color.getValue());
		});
		g.setFill(bgc);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		canvas.setOnMouseDragged(e -> {
			toolController.drawDrag(e, bgc);

		});
		canvas.setOnMouseReleased(e -> {
			toolController.drawRelease(e);
		});

		canvas.setOnMousePressed(e -> {
			toolController.drawPress(e);
		});

	}

	// sets styles of all components based on theme
	public void setComponentThemeStyle() {
		Image image1 = new Image(tc.getCurrent().getImage());

		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		b.setBackground(new Background(new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, bSize)));
		if (pm.getCurrentUser() != null) {
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
		setLabelStyle(ls);
		setButtonStyle(clear);
	}

	// set button style
	public void setButtonStyle(Button b) {
		b.setStyle("-fx-background-color: " + tc.getCurrent().getButtonColorHex());
		b.setMaxHeight(40);
		b.setMaxWidth(150);
		b.setFont(Font.font("Courier", 10));
		b.setTextFill(tc.getCurrent().getColor("btntxt"));
	}

	// set label style
	public void setLabelStyle(Label l) {
		l.setFont(Font.font("Courier", 15));
		l.setTextFill(tc.getCurrent().getColor("txt"));
	}

	// resets canvas and other components
	public void reset() {
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g.setFill(bgc);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		slider.setValue(1);
		color.setValue(Color.WHITE);
	}

	// sets localization/accessibility
	public void resetLanguageComponents() {
		l.setText((pm.rb).getString("currentcolor"));
		l2.setText((pm.rb).getString("toolsize"));
		l5.setText((pm.rb).getString("tool"));
		ls.setText((pm.rb).getString("currstamp"));
		erase.setText((pm.rb).getString("eraser"));
		paint.setText((pm.rb).getString("paintbrush"));
		pencil.setText((pm.rb).getString("marker"));
		stamp.setText((pm.rb).getString("stamp"));
		by.setText((pm.rb).getString("logout"));
		c.setText((pm.rb).getString("settings"));
		d.setText((pm.rb).getString("save"));
		e.setText((pm.rb).getString("instructions"));
		clear.setText((pm.rb).getString("clear"));

		l.setAccessibleText((pm.rb).getString("currentcolor"));
		l2.setAccessibleText((pm.rb).getString("toolsize"));
		l5.setAccessibleText((pm.rb).getString("tool"));
		ls.setAccessibleText((pm.rb).getString("currstamp"));
		erase.setAccessibleText((pm.rb).getString("eraser"));
		paint.setAccessibleText((pm.rb).getString("paintbrush"));
		pencil.setAccessibleText((pm.rb).getString("marker"));
		stamp.setAccessibleText((pm.rb).getString("stamp"));
		by.setAccessibleText((pm.rb).getString("logout"));
		c.setAccessibleText((pm.rb).getString("settings"));
		d.setAccessibleText((pm.rb).getString("save"));
		e.setAccessibleText((pm.rb).getString("instructions"));
		clear.setAccessibleText((pm.rb).getString("clear"));
		resetComponents();
	}

	// sets style of selected button
	public void setSelected(Button b) {
		setButtonStyle(erase);
		setButtonStyle(paint);
		setButtonStyle(pencil);
		setButtonStyle(stamp);
		if (b.equals(stamp)) {
			stampselect = true;
		} else {
			stampselect = false;
		}
		b.setStyle("-fx-background-color: " + tc.getCurrent().getSecondaryColorHex());
		b.setMaxHeight(40);
		b.setMaxWidth(150);
		b.setFont(Font.font("Courier", 10));
		b.setTextFill(tc.getCurrent().getColor("txt"));

	}

}
