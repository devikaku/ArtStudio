package Controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import model.Theme;
import model.Themes;

public class ThemeController {
	private Map<String, Theme> themes = new HashMap<String, Theme>();
	private Random r = new Random();
	private Theme current = null;
	private static final String JSON_FILE = "themefile.json";

	public ThemeController() {
		parseJson();
		current = themes.get("cactus");
	}

	private void parseJson() {
		try {
			Gson gson = new Gson();
			JsonReader jr;
			jr = new JsonReader(new FileReader(JSON_FILE));
			Themes t = gson.fromJson(jr, Themes.class);
			if (t == null) {
				System.out.println("Users list null");
				return;
			}
			for (Theme u : t.getThemes()) {
				themes.put(u.getName(), u);
			}
			jr.close();
			System.out.println("file found");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("That file could not be found.");
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("That file is not a well-formed JSON file.");
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid JSON file.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			System.out.println("File has been saved.");
			System.out.println();
			fw.close();
			return true;
		} catch (IOException e) {
			System.out.println("Unable to write to file");
			return false;
		}

	}
	
	public boolean setCurrentTheme(String theme) {
		if(themes.get(theme)==null) {return false;}
		current = themes.get(theme);
		return true;
	}
	
	public void setRandomTheme() {
		List<String> keysAsArray = new ArrayList<String>(themes.keySet());
		current = themes.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
	}
	
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
