package coder.dog.chosen.view;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by megrez on 16/2/29.
 */
public class PasswordResponse extends Response {
    public PasswordResponse(final String passwd) {
        this.code = HTTP_OK;
        this.data = new JsonResponse() {
            @JsonView(View.BaseResponseView.class)
            public String password = passwd;
        };
    }
}
