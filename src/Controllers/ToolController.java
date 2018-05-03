package Controllers;
//Tool Controller
//Devika Kumar
//ITP 368, Spring 2018
//Final Project
//devikaku@usc.edu
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

//CONTROLLER: Tool Controller does all the actual drawing and handling of tool settings based on what the user wants in the paintingscene
//for each mousevent, the it draws based on what the current tool is, which is set in paint scene
//INSPIRATION FOR THIS CLASS AND FOR THIS PROJECT IN GENERAL *** I looked towards this link for inspiration and did my best to complete all features on my own, looking to it only when I was really stuck. 
//My intention was not to plagiarize anything, but some things may look similar as there were not a lot of ways to do some of the things i wanted to do.
//https://gist.github.com/abdelaziz321/e9932bd15e4b263c3dae08644c61600c
//mostly taught me to draw based on mousevents and tools, and how to handle drawing shapes.
public class ToolController {
	private String currentTool = "paintbrush"; // current tool we are working with
	private Paint color = Color.WHITE; // the current color of the tool
	private int toolsize = 10; // current size of the tool
	Canvas canvas; // our canvas we will draw on using graphics context
	GraphicsContext g; // our graphics context

	// shapes for each of our stamps
	Line l = new Line();
	Rectangle r = new Rectangle();
	Ellipse el = new Ellipse();

	public ToolController(Canvas canvas, GraphicsContext g) {
		this.canvas = canvas;
		this.g = g;
	}

	// draws based on tool and mouse dragged event
	public void drawDrag(MouseEvent e, Color bgc) {
		if (currentTool.equals("paintbrush")) {
			g.setFill(color);
			g.fillOval(e.getX() - toolsize / 2, e.getY() - toolsize / 2, toolsize, toolsize);
		} else if (currentTool.equals("eraser")) {
			g.setFill(bgc);
			g.fillRect(e.getX() - toolsize / 2, e.getY() - toolsize / 2, toolsize, toolsize);
		} else if (currentTool.equals("pencil")) {
			 
			g.setLineWidth(toolsize);
			g.lineTo(e.getX(), e.getY());
			g.stroke();
		}
	}

	// draws based on tool and mouse released event
	public void drawRelease(MouseEvent e) {
		if (currentTool.equals("pencil")) {
			g.setLineWidth(toolsize);
			g.lineTo(e.getX(), e.getY());
			g.stroke();
			g.closePath();
		} else if (currentTool.equals("line")) {
			g.setLineWidth(toolsize);
			l.setEndX(e.getX());
			l.setEndY(e.getY());
			g.strokeLine(l.getStartX(), l.getStartY(), l.getEndX(), l.getEndY());
		} else if (currentTool.equals("rectangle")) {
			double endx = (e.getX());
			double endy = (e.getY());
			double width = Math.abs(endx - r.getX());
			double height = Math.abs(endy - r.getY());
			if (r.getX() > e.getX()) {
				r.setX(e.getX());
			}
			if (r.getY() > e.getY()) {
				r.setY(e.getY());
			}

			g.fillRect(r.getX(), r.getY(), width, height);

		} else if (currentTool.equals("oval")) {
			double endx = (e.getX());
			double endy = (e.getY());
			double width = Math.abs(endx - el.getCenterX());
			double height = Math.abs(endy - el.getCenterY());
			if (el.getCenterX() > e.getX()) {
				el.setCenterX(e.getX());
			}
			if (el.getCenterY() > e.getY()) {
				el.setCenterY(e.getY());
			}
			g.fillOval(el.getCenterX(), el.getCenterY(), width, height);
		} else if (currentTool.equals("circle")) {

			double radius = Math.abs(e.getX() - el.getCenterX());
			if (el.getCenterX() > e.getX()) {
				el.setCenterX(e.getX());
			}
			if (el.getCenterY() > e.getY()) {
				el.setCenterY(e.getY());
			}
			g.fillOval(el.getCenterX(), el.getCenterY(), radius, radius);
		}
	}

	// draws based on tool and mouse pressed event
	public void drawPress(MouseEvent e) {
		if (currentTool.equals("pencil")) {
			g.setLineWidth(toolsize);
			g.setStroke(color);
			g.beginPath();

		} else if (currentTool.equals("line")) {
			g.setStroke(color);
			l.setStartX(e.getX());
			l.setStartY(e.getY());
		} else if (currentTool.equals("rectangle")) {
			g.setStroke(color);
			g.setFill(color);
			r.setX(e.getX());
			r.setY(e.getY());

		} else if (currentTool.equals("oval")) {
			g.setStroke(color);
			g.setFill(color);
			el.setCenterX(e.getX());
			el.setCenterY(e.getY());
		} else if (currentTool.equals("circle")) {
			g.setStroke(color);
			g.setFill(color);
			el.setCenterX(e.getX());
			el.setCenterY(e.getY());
		}

	}

	// getters/setters
	public void setToolSize(int intValue) {
		toolsize = intValue;

	}

	public int getToolSize() {
		return toolsize;

	}

	public void setTool(String string) {
		currentTool = string;

	}

	public void setColor(Color value) {
		color = value;

	}

	public Paint getColor() {
		return color;

	}

	// Clears canvas by writing over it with white
	public void clear() {
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
}
