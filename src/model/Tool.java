package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Tool {
	private String currentTool = "paintbrush";
	private Paint color = Color.WHITE;
	private int toolsize = 10;
	Canvas canvas;
	GraphicsContext g;
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
        if(currentTool.equals("paintbrush")) {
        g.setFill(color);
        g.fillOval(e.getX() - toolsize / 2, e.getY() - toolsize / 2, toolsize, toolsize);
        }
        else if(currentTool.equals("eraser")) {
        	g.setFill(bgc);
        	g.fillRect(e.getX() - toolsize / 2, e.getY() - toolsize / 2, toolsize, toolsize);
        }else if(currentTool.equals("pencil")) {
        	System.out.println("mouse dragged");
            g.lineTo(e.getX(), e.getY());
            g.stroke();
        }else if(currentTool.equals("stamp")) {

        }
	}
	public void drawPress() {
		
	}
	public void drawRelease(MouseEvent e) {
        if(currentTool.equals("pencil")) {
            g.lineTo(e.getX(), e.getY());
            g.stroke();
            g.closePath();
        }
	}
	public void drawPress(MouseEvent e) {
        if(currentTool.equals("pencil")) {
            g.setStroke(color);
            g.beginPath();
		
	}

	}
	public void setToolSize(int intValue) {
		toolsize = intValue;
		
	}
	public void setTool(String string) {
		currentTool = string;
		
	}
}
