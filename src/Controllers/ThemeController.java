package Controllers;

//Theme Controller
//Devika Kumar
//ITP 368, Spring 2018
//Final Project
//devikaku@usc.edu
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import model.Theme;
import model.Themes;

//CONTROLLER: connects program to JSON database of themes, reads and writes themes to JSON--sets current theme and changes
public class ThemeController {
	// contains a map of string theme names to theme objects, the current theme, and
	// the json file name to parse
	private Map<String, Theme> themes = new HashMap<String, Theme>();
	private Theme current = null;
	private static final String JSON_FILE = "themefile.json";

	// parses json and sets default current theme
	public ThemeController() {
		parseJson();
		current = themes.get("cactus");
	}

	// parses json and gets all themes and puts in theme map
	private void parseJson() {
		try {
			Gson gson = new Gson();
			JsonReader jr;
			jr = new JsonReader(new FileReader(JSON_FILE));
			Themes t = gson.fromJson(jr, Themes.class);
			if (t == null) {
				return;
			}
			for (Theme u : t.getThemes()) {
				themes.put(u.getName(), u);
			}
			jr.close();
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			 
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			 
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// adds a theme based on the required values, and if the theme name doesn't
	// already exist
	// writes to json and saves
	public boolean addTheme(String name, String image, String bgColorHex, String buttonColorHex, String textColorHex,
			String buttonTextColorHex, String secondaryColorHex) {
		try {
			Theme u = new Theme(name, image, bgColorHex, buttonColorHex, textColorHex, buttonTextColorHex,
					secondaryColorHex);

			themes.put(u.getName(), u);
			Gson gson = new Gson();
			List<Theme> list = new ArrayList<Theme>();
			for (Map.Entry<String, Theme> entry : themes.entrySet()) {
				list.add(entry.getValue());
			}
			String userstojson = gson.toJson(list);
			FileWriter fw = new FileWriter(JSON_FILE);
			fw.write(userstojson);
			 
			 
			fw.close();
			return true;
		} catch (IOException e) {
			 
			return false;
		}

	}

	// sets current theme based on string name, if it exists
	public boolean setCurrentTheme(String theme) {
		if (themes.get(theme) == null) {
			return false;
		}
		current = themes.get(theme);
		return true;
	}

	// getters/setters
	public void setDefaultTheme() {
		current = themes.get("default");
	}

	public Map<String, Theme> getThemes() {
		return themes;
	}

	public void setThemes(Map<String, Theme> themes) {
		this.themes = themes;
	}

	public Theme getCurrent() {
		return current;
	}

	public void setCurrent(Theme current) {
		this.current = current;
	}

}
