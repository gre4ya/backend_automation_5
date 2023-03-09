package api.pojo_classes.go_rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UpdateUserWithLombok {

    String email;
    String gender;
    String status;
}