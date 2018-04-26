package model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Themes implements Serializable
{

@SerializedName("Themes")
@Expose
private List<Theme> themes = null;
private final static long serialVersionUID = 74943896516481892L;

/**
* No args constructor for use in serialization
* 
*/
public Themes() {
}

/**
* 
* @param themes
*/
public Themes(List<Theme> themes) {
super();
this.themes = themes;
}

public List<Theme> getThemes() {
return themes;
}

public void setThemes(List<Theme> themes) {
this.themes = themes;
}

}