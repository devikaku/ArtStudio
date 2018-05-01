package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Tool {
	private String currentTool = "paintbrush";
	private Paint color = Color.WHITE;
	private int toolsize = 10;
	Canvas canvas;
	GraphicsContext g;
	Line l = new Line();
	Rectangle r = new Rectangle();
	Ellipse el = new Ellipse();
	public Tool(Canvas canvas, GraphicsContext g) {
		this.canvas = canvas;
		this.g = g;
	}

	public void setColor(Color value) {
		color = value;

	}

	public Paint getColor() {
		return color;

	}

	public void drawDrag(MouseEvent e, Color bgc) {
		if (currentTool.equals("paintbrush")) {
			g.setFill(color);
			g.fillOval(e.getX() - toolsize / 2, e.getY() - toolsize / 2, toolsize, toolsize);
		} else if (currentTool.equals("eraser")) {
			g.setFill(bgc);
			g.fillRect(e.getX() - toolsize / 2, e.getY() - toolsize / 2, toolsize, toolsize);
		} else if (currentTool.equals("pencil")) {
			System.out.println("mouse dragged");
			g.setLineWidth(toolsize);
			g.lineTo(e.getX(), e.getY());
			g.stroke();
		} else if (currentTool.equals("stamp")) {

		}
	}

	public void drawPress() {

	}

	public void drawRelease(MouseEvent e) {
		if (currentTool.equals("pencil")) {
			g.setLineWidth(toolsize);
			g.lineTo(e.getX(), e.getY());
			g.stroke();
			g.closePath();
		}else if (currentTool.equals("line")) {
			l.setEndX(e.getX());
			l.setEndY(e.getY());
			g.strokeLine(l.getStartX(), l.getStartY(), l.getEndX(), l.getEndY());
		} else if (currentTool.equals("rectangle")) {
			double endx = (e.getX());
			double endy = (e.getY());
			double width = Math.abs(endx-r.getX());
			double height = Math.abs(endy-r.getY());
            if(r.getX() > e.getX()) {
                r.setX(e.getX());
            }
            if(r.getY() > e.getY()) {
                r.setY(e.getY());
            }
			
			g.fillRect(r.getX(), r.getY(), width, height);

		} else if (currentTool.equals("oval")) {
			double endx = (e.getX());
			double endy = (e.getY());
			double width = Math.abs(endx-el.getCenterX());
			double height = Math.abs(endy-el.getCenterY());
            if(el.getCenterX() > e.getX()) {
                el.setCenterX(e.getX());
            }
            if(el.getCenterY() > e.getY()) {
                el.setCenterY(e.getY());
            }
			g.fillOval(el.getCenterX(), el.getCenterY(), width, height);
		}
	}

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
		}

	}

	public void setToolSize(int intValue) {
		toolsize = intValue;

	}

	public int getToolSize() {
		return toolsize;

	}

	public void setTool(String string) {
		currentTool = string;

	}

	public void clear() {
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
}
