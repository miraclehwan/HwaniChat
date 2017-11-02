package daehwan.com.hwanitalk;

/**
 * Created by daehwan.kim on 2017-11-02.
 */

public class Recycler_Chat_item {

    String id;
    String message;

    Recycler_Chat_item(String id, String message){
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
