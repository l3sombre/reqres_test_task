package json_user;
import org.json.simple.JSONObject;


/**
 * The class handles JSON-object representing user
 */

public class JSONUser {
    JSONObject obj = new JSONObject();

    public JSONUser(String name, String job) {
        setUserName(name);
        setUserJob(job);
    }

    public String toJSONString(){
        return this.obj.toJSONString();
    }

    public String getUserName(){
        return this.obj.get("name").toString();
    }

    public String getUserJob(){
        return this.obj.get("job").toString();
    }

    public <T> void setUserName(T name) {
        this.obj.put("name", name);
    }

    public <T> void setUserJob(T job) {
        this.obj.put("job", job);
    }


}
