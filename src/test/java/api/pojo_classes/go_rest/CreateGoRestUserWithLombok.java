package api.pojo_classes.go_rest;

import lombok.Builder;
import lombok.Data;

/**  With @Data we can eliminate the getters and setters */
@Data

/**  With @Builder we can assign value to the attribute */
@Builder

public class CreateGoRestUserWithLombok {

    /**
     {
     "name": "",
     "gender": "",
     "email": "Ophelia.Mraz@hotmail.com",
     "status": ""
     }
     */

     String name;
     String gender;
     String email;
     String status;



}
