package api.pojo_classes.tg;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostStudent {

    /*
    Use Log4j to log and debug
    Fetch the value of the response with JayWay
    Validate it with Hamcrest
     */

    private String firstName;
    private String lastName;
    private String email;
    private String dob;
}
