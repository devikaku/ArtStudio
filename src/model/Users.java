
package model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users implements Serializable
{

    @SerializedName("Users")
    @Expose
    private List<User> users = null;
    private final static long serialVersionUID = -5260678295374079131L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Users() {
    }

    /**
     * 
     * @param users
     */
    public Users(List<User> users) {
        super();
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
