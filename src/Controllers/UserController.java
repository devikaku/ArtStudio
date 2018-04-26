package Controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
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
	public boolean LogIn(String username, String password) {
		//if user doesnt exist return false
		if(users.get(username)==null) {
			return false;
		}
		//if user exists and password is incorrect return false
		if(!users.get(username).getPassword().equals(password)) {
			return false;
		}
		loggedIn = true;
		currentUser = users.get(username);
		return true;
	}
	public boolean SignUp(String username, String password, String rpassword) {
		//if passwords dont match return false
		if(username.equals("") || username.equals(null) || password.equals("") || password.equals(null) || rpassword.equals("") || rpassword.equals(null)) {
			return false;
		}
		if(!password.equals(rpassword)) {
			return false;
		}
		//if user exists, retrun false
		if(users.get(username)!=null) {
			return false;
		}
		return addUser(username, password);
		
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

	public boolean editUser(String username, String password, String rpassword) {
		//if user enters something for username--aka they want to change username
		if(username!=null && !username.equals("")) {
			//if already exists, dont change anything
			if(users.get(username)!=null) {
				return false;
			}
		}
		//same for password
		if(password!=null && !password.equals("")) {
			//if passwords dont match, dont change anything
			if(!password.equals(rpassword)) {
				return false;
			}
		}
		if(username!=null && !username.equals("")) {
			//if already exists, dont change anything
			currentUser.setUsername(username);
		}
		//same for password
		if(password!=null && !password.equals("")) {
			//if passwords dont match, dont change anything
			currentUser.setPassword(password);
		}
		try {
		Gson gson = new Gson();
		String userstojson = gson.toJson(userslist);
			FileWriter fw;
				fw = new FileWriter(JSON_FILE);
			fw.write(userstojson);
			System.out.println("File has been saved.");
			System.out.println();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
