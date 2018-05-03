package Controllers;

//User Controller
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

import model.User;
import model.Users;

//CONTROLLER: User controller reads/writes to JSON database of users, manages log ins and sign ups, tracks current user, and edits user details
public class UserController {
	// Map of all users
	private Map<String, User> users = new HashMap<String, User>();
	// current user
	private User currentUser = null;
	// tells whether or not we are logged in
	private boolean loggedIn = false;
	// Users object used for parsing from json
	private Users userslist = null;
	// json filename
	private static final String JSON_FILE = "usersfile.json";
	// create id for all users
	private static int ID;

	/**
	 * @param users
	 * @param currentUser
	 * @param loggedIn
	 */

	public UserController(User currentUser, boolean loggedIn) {
		this.currentUser = currentUser;
		this.loggedIn = loggedIn;
		// parse json file and create user map
		parseJson();
		createMap();

	}

	// clear anything already there
	private void createMap() {
		users.clear();
		if (userslist == null) {
			 
			return;
		}
		// for all users in the parsed Users object, put in map
		for (User u : userslist.getUsers()) {
			users.put(u.getUsername(), u);
		}
	}

	// get all users and create the userlist object, as well as initialize id;
	private void parseJson() {
		try {
			Gson gson = new Gson();
			JsonReader jr;
			jr = new JsonReader(new FileReader(JSON_FILE));
			userslist = gson.fromJson(jr, Users.class);
			ID = userslist.getUsers().size();
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

	// get all users
	public Map<String, User> getUsers() {
		return users;
	}

	// add a user based on username and password
	public boolean addUser(String username, String password) {
		try {
			// create new user with new id and put in map and Users object
			// assume this is current user
			// set logged in
			// write to file
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
			 
			 
			fw.close();
			return true;
		} catch (IOException e) {
			 
			return false;
		}

	}

	// checks if the log in credentials work and if they do, log the user in
	public String LogIn(String username, String password) {
		// returns a specific message to display to user based on situation of invalid
		// credentials
		if (username.equals("") || username == null || password.equals("") || password == null) { // if a field is blank
			return "fillfields";
		}
		if (users.get(username) == null) { // if user doesn't exist
			return "incorrectusernameorpassword";
		}

		// if user exists and password is incorrect
		if (!users.get(username).getPassword().equals(password)) {
			return "incorrectusernameorpassword";
		}
		// assume everything ok, return message success
		loggedIn = true;
		currentUser = users.get(username);
		return "success";
	}

	// check if sign up credentials work and if they do, sign user up, add them as
	// current user, and add them to database
	public String SignUp(String username, String password, String rpassword) {
		// returns a specific message to display to user based on situation of invalid
		// credentials
		if (username.equals("") || username.equals(null) || password.equals("") || password.equals(null)
				|| rpassword.equals("") || rpassword.equals(null)) {
			// if any fields empty
			return "fillfields";
		}
		if (!password.equals(rpassword)) { // if passwords do not match
			return "passwordmatch";
		}
		// if user exists already
		if (users.get(username) != null) {
			return "useralreadyexists";
		}
		// if we were successful in adding user to database
		if (addUser(username, password)) {
			return "success";
		} else {
			// internal error of some kind
			return "unabletosignup";
		}

	}

	// getters/setters
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

	// log user out-set current to null and loggedin to false
	public void logout() {
		currentUser = null;
		loggedIn = false;
	}

	// edit user details given the fields
	public String editUser(String username, String password, String rpassword) {
		// returns message of change status
		String previous = currentUser.getUsername(); // get users previous username to delete from database

		if ((username == null || username.equals("")) && (password.equals("") || password == null)
				&& (rpassword.equals("") || rpassword == null)) {
			// if all fields empty, there is no change to make
			return "nochange";
		}
		if (username != null && !username.equals("") && !username.equals(previous)) {
			// if user enters something for username--aka they want to change username
			// if the user entered a username that was not there own, check if it's already
			// in the database
			if (users.get(username) != null) {
				return "uexists";
			}
		}
		// if the user tried to change their password
		if (password != null && !password.equals("")) {
			// if passwords dont match, dont change anything
			if (!password.equals(rpassword)) {
				return "pnomatch";
			}
		}
		// booleans that tell what has been changed to send customized message
		boolean u = false;
		boolean p = false;
		boolean s = false;

		if (username != null && !username.equals("")) {
			// if already exists, dont change anything
			// we know they have entered in a username--change their details
			currentUser.setUsername(username);
			u = true;
		}
		// same for password
		if (password != null && !password.equals("")) {
			currentUser.setPassword(password);
			p = true;
		}
		// update database
		try {
			Gson gson = new Gson();
			// remove user based on previous username if it changed
			users.remove(previous);
			// re put the user in the map
			users.put(currentUser.getUsername(), currentUser);
			List<User> newlist = new ArrayList<User>();
			for (Map.Entry<String, User> entry : users.entrySet()) {
				newlist.add(entry.getValue());
			}
			// reset userslist
			userslist.setUsers(newlist);
			// write userslist to JSON file
			String userstojson = gson.toJson(userslist);
			FileWriter fw;
			fw = new FileWriter(JSON_FILE);
			fw.write(userstojson);
			 
			 
			fw.close();
			// file successfully saved
			s = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (s) { // file successfully saved
			// return custom message based on what changed
			if (p && u) {
				return "upchanged";
			}
			if (p) {
				return "pchanged";
			}
			if (u) {
				return "uchanged";
			}
		}
		return "settingserror"; // internal error
	}

}
