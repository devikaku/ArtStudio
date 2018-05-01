/*
  * ===================================================
  *                      contents
  * ===================================================
  * 00- Free draw
  * 01- rubber
  * 02- draw Line
  * 03- draw Rectangele
  * 04- draw Circle
  * 05- draw Ellipse
  * 06- Text
  *
  * ----------------------------------------------------
  *                     Features
  * ----------------------------------------------------
  * - the ability to change Line color
  * - the ability to change Fill color
  * - the ability to change Line width
  * - Undo & Redo
  * - Open Image && save Image
  *
  * ____________________________________________________
  * problems
  * - undo & redo : not working with free draw and rubber
  * - Line & Rect & Circ ... aren't be updated while drawing
  * ===================================================
*/
package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Paint extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        ToggleButton linebtn = new ToggleButton("Line");
        ToggleButton rectbtn = new ToggleButton("Rectange");
        ToggleButton circlebtn = new ToggleButton("Circle");
        ToggleButton elpslebtn = new ToggleButton("Ellipse");
        ToggleButton textbtn = new ToggleButton("Text");
        
        ToggleButton[] toolsArr = {linebtn, rectbtn, circlebtn, elpslebtn, textbtn};
        
        ToggleGroup tools = new ToggleGroup();
        
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        
        ColorPicker cpLine = new ColorPicker(Color.BLACK);
        ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);
        
        TextArea text = new TextArea();
        text.setPrefRowCount(1);
        
        Slider slider = new Slider(1, 50, 3);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        
        Label line_color = new Label("Line Color");
        Label fill_color = new Label("Fill Color");
        Label line_width = new Label("3.0");
        
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button save = new Button("Save");
        Button open = new Button("Open");
        
        Button[] basicArr = {undo, redo, save, open};

        for(Button btn : basicArr) {
            btn.setMinWidth(90);
            btn.setCursor(Cursor.HAND);
            btn.setTextFill(Color.WHITE);
            btn.setStyle("-fx-background-color: #666;");
        }
        save.setStyle("-fx-background-color: #80334d;");
        open.setStyle("-fx-background-color: #80334d;");
        
        VBox btns = new VBox(10);
        btns.getChildren().addAll(linebtn, rectbtn, circlebtn, elpslebtn,
                textbtn, text, line_color, cpLine, fill_color, cpFill, line_width, slider, undo, redo, open, save);
        btns.setPadding(new Insets(5));
        btns.setStyle("-fx-background-color: #999");
        btns.setPrefWidth(100);
        
        /* ----------Drow Canvas---------- */
        Canvas canvas = new Canvas(1080, 790);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        
        Line line = new Line();
        Rectangle rect = new Rectangle();
        Circle circ = new Circle();
        Ellipse elps = new Ellipse();
                        
        canvas.setOnMousePressed(e->{
if(linebtn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                line.setStartX(e.getX());
                line.setStartY(e.getY());
            }
            else if(rectbtn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                rect.setX(e.getX());                
                rect.setY(e.getY());
            }
            else if(circlebtn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
            }
            else if(elpslebtn.isSelected()) {
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
            }
            else if(textbtn.isSelected()) {
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                gc.fillText(text.getText(), e.getX(), e.getY());
                gc.strokeText(text.getText(), e.getX(), e.getY());
            }
        });

        
        canvas.setOnMouseReleased(e->{
if(linebtn.isSelected()) {
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                
         }
            else if(rectbtn.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }

                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                
         }
            else if(circlebtn.isSelected()) {
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);
                
                if(circ.getCenterX() > e.getX()) {
                    circ.setCenterX(e.getX());
                }
                if(circ.getCenterY() > e.getY()) {
                    circ.setCenterY(e.getY());
                }
                
                gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                
           }
            else if(elpslebtn.isSelected()) {
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));
                
                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }
                
                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                
            }
            
        });
        // color picker
        cpLine.setOnAction(e->{
                gc.setStroke(cpLine.getValue());
        });
        cpFill.setOnAction(e->{
                gc.setFill(cpFill.getValue());
        });
        
        // slider
        slider.valueProperty().addListener(e->{
            double width = slider.getValue();
            if(textbtn.isSelected()){
                gc.setLineWidth(1);
                gc.setFont(Font.font(slider.getValue()));
                line_width.setText(String.format("%.1f", width));
                return;
            }
            line_width.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });
        
        /*------- Undo & Redo ------*/
        // Undo

        
        // Redo

        
        /*------- Save & Open ------*/
        // Open
        open.setOnAction((e)->{
            FileChooser openFile = new FileChooser();
            openFile.setTitle("Open File");
            File file = openFile.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    gc.drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });
        
        // Save


        /* ----------STAGE & SCENE---------- */
        BorderPane pane = new BorderPane();
        pane.setLeft(btns);
        pane.setCenter(canvas);
    
        Scene scene = new Scene(pane, 1200, 800);
        
        primaryStage.setTitle("Paint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}