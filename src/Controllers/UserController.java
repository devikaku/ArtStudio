package Controllers;

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

import model.User;
import model.Users;

public class UserController {
	private Map<String, User> users = new HashMap<String, User>();
	private User currentUser = null;
	private boolean loggedIn = false;
	private Users userslist = null;
	private static final String JSON_FILE = "usersfile.json";
	private static int ID;

	/**
	 * @param users
	 * @param currentUser
	 * @param loggedIn
	 */
	public UserController(User currentUser, boolean loggedIn) {
		this.currentUser = currentUser;
		this.loggedIn = loggedIn;
		parseJson();
		createMap();

	}

	private void createMap() {
		users.clear();
		if (userslist == null) {
			System.out.println("Users list null");
			return;
		}
		for (User u : userslist.getUsers()) {
			users.put(u.getUsername(), u);
		}
	}

	private void parseJson() {
		try {
			Gson gson = new Gson();
			JsonReader jr;
			jr = new JsonReader(new FileReader(JSON_FILE));
			userslist = gson.fromJson(jr, Users.class);
			ID = userslist.getUsers().size();
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

	public Map<String, User> getUsers() {
		return users;
	}

	public boolean addUser(String username, String password) {
	try {
		User u = new User(username, password, ID);
		ID++;
		users.put(u.getUsername(), u);
		userslist.getUsers().add(u);
		currentUser = u;
		loggedIn = true;
		Gson gson = new Gson();
		String userstojson = gson.toJson(userslist);
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
	public String LogIn(String username, String password) {
		//if user doesnt exist return false
		if(username.equals("") || username==null || password.equals("") || password==null) {
			return "fillfields";
		}
		if(users.get(username)==null) {
			return "incorrectusernameorpassword";
		}
		
		//if user exists and password is incorrect return false
		if(!users.get(username).getPassword().equals(password)) {
			return "incorrectusernameorpassword";
		}
		loggedIn = true;
		currentUser = users.get(username);
		return "success";
	}
	public String SignUp(String username, String password, String rpassword) {
		//if passwords dont match return false
		if(username.equals("") || username.equals(null) || password.equals("") || password.equals(null) || rpassword.equals("") || rpassword.equals(null)) {
			return "fillfields";
		}
		if(!password.equals(rpassword)) {
			return "passwordmatch";
		}
		//if user exists, retrun false
		if(users.get(username)!=null) {
			return "useralreadyexists";
		}
		if(addUser(username, password)) {
			return "success";
		}else {
			return "unabletosignup";
		}
		
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void logout() {
		currentUser = null;
		loggedIn = false;
	}

	public String editUser(String username, String password, String rpassword) {
		//if user enters something for username--aka they want to change username
String previous = currentUser.getUsername();
		if((username==null || username.equals("")) && (password.equals("") || password==null) && (rpassword.equals("") || rpassword==null)) {
			return "nochange";
		}
		if(username!=null && !username.equals("") && !username.equals(previous)) {
			//if already exists, dont change anything
			if(users.get(username)!=null) {
				return "uexists";
			}
		}
		//same for password
		if(password!=null && !password.equals("")) {
			//if passwords dont match, dont change anything
			if(!password.equals(rpassword)) {
				return "pnomatch";
			}
		}
		boolean u = false;
		boolean p = false;
		boolean s = false;
		if(username!=null && !username.equals("")) {
			//if already exists, dont change anything
			currentUser.setUsername(username);
			u = true;
		}
		//same for password
		if(password!=null && !password.equals("")) {
			//if passwords dont match, dont change anything
			currentUser.setPassword(password);
			p = true;
		}
		try {
		Gson gson = new Gson();
		users.remove(previous);
		users.put(currentUser.getUsername(), currentUser);
		List<User> newlist = new ArrayList<User>();
		for (Map.Entry<String, User> entry : users.entrySet())
		{
		    newlist.add(entry.getValue());
		}
		userslist.setUsers(newlist);
		String userstojson = gson.toJson(userslist);
			FileWriter fw;
				fw = new FileWriter(JSON_FILE);
			fw.write(userstojson);
			System.out.println("File has been saved.");
			System.out.println();
			fw.close();
			s = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(s) {
			if(p&&u) {
				return "upchanged";
			}
			if(p) {
				return "pchanged";
			}
			if(u) {
				return "uchanged";
			}
		}
		return "settingserror";
	}

}
