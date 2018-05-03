package model;

//THEME OBJECT-parsed in JSON
//Devika Kumar
//ITP 368, Spring 2018
//Final Project
//devikaku@usc.edu
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javafx.scene.paint.Color;

public class Theme implements Serializable {

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("image")
	@Expose
	private String image;

	@SerializedName("bgColorHex")
	@Expose
	private String bgColorHex;

	@SerializedName("buttonColorHex")
	@Expose
	private String buttonColorHex;

	@SerializedName("textColorHex")
	@Expose
	private String textColorHex;

	@SerializedName("buttonTextColorHex")
	@Expose
	private String buttonTextColorHex;

	@SerializedName("secondaryColorHex")
	@Expose
	private String secondaryColorHex;
	private final static long serialVersionUID = 9140233228304964974L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Theme() {
	}

	/**
	 * 
	 * @param buttonTextColorHex
	 * @param textColorHex
	 * @param buttonColorHex
	 * @param name
	 * @param secondaryColorHex
	 * @param image
	 * @param bgColorHex
	 */
	public Theme(String name, String image, String bgColorHex, String buttonColorHex, String textColorHex,
			String buttonTextColorHex, String secondaryColorHex) {
		super();
		this.name = name;
		this.image = image;
		this.bgColorHex = bgColorHex;
		this.buttonColorHex = buttonColorHex;
		this.textColorHex = textColorHex;
		this.buttonTextColorHex = buttonTextColorHex;
		this.secondaryColorHex = secondaryColorHex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBgColorHex() {
		return bgColorHex;
	}

	public void setBgColorHex(String bgColorHex) {
		this.bgColorHex = bgColorHex;
	}

	public String getButtonColorHex() {
		return buttonColorHex;
	}

	public void setButtonColorHex(String buttonColorHex) {
		this.buttonColorHex = buttonColorHex;
	}

	public String getTextColorHex() {
		return textColorHex;
	}

	public void setTextColorHex(String textColorHex) {
		this.textColorHex = textColorHex;
	}

	public String getButtonTextColorHex() {
		return buttonTextColorHex;
	}

	public void setButtonTextColorHex(String buttonTextColorHex) {
		this.buttonTextColorHex = buttonTextColorHex;
	}

	public String getSecondaryColorHex() {
		return secondaryColorHex;
	}

	public void setSecondaryColorHex(String secondaryColorHex) {
		this.secondaryColorHex = secondaryColorHex;
	}

	/**
	 * 
	 * @param bg
	 * @param btn
	 * @param btntxt
	 * @param txt
	 * @param sec
	 */
	public Color getColor(String id) {
		if (id.equals("bg")) {
			return Color.web(bgColorHex);
		}
		if (id.equals("btn")) {
			return Color.web(buttonColorHex);
		}
		if (id.equals("btntxt")) {
			 
			return Color.web(buttonTextColorHex);
		}
		if (id.equals("txt")) {
			 
			return Color.web(textColorHex);
		}
		if (id.equals("sec")) {
			return Color.web(secondaryColorHex);
		}
		return null;
	}

}
