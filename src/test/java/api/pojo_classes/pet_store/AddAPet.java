package api.pojo_classes.pet_store;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder

public class AddAPet {

    /**
     {
        "id": 5,
        "category": {
            "id": 4,
            "name": "fish"
        },
        "name": "nemo",
        "photoUrls": [
                "nemoURL"
        ],
        "tags": [
            {
                "id": 50,
                "name": "red"
            }
        ],
        "status": "available"
     }
     */

    /*
    curl --location --request POST 'https://gorest.co.in/public-api/comments' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e' \
--data-raw '{
    "code": 408,
    "meta": {
        "pagination": {
            "total": 2000,
            "pages": 211,
            "page": 1,
            "limit": 5,
            "links": {
                "previous": null,
                "current": "https://gorest.co.in/public/v1/comments?page=1",
                "next": "https://gorest.co.in/public/v1/comments?page=2"
            }
        }
    },
    "data": [
        {
            "id": 2220,
            "post_id": 5585,
            "name": "Tecch Global",
            "email": "random email",
            "body": "random text"
        },
         {
            "id": 2221,
            "post_id": 5586,
            "name": "Tecch Global",
            "email": "random email",
            "body": "random text"
        },
         {
            "id": 2222,
            "post_id": 5587,
            "name": "Tecch Global",
            "email": "random email",
            "body": "random text"
        }
    ]
}'
     */

    private int id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tags> tags;
    private String status;


}
