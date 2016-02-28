package coder.dog.chosen.view;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by megrez on 16/2/28.
 */
public class Response {
    public static final int HTTP_OK = 200;

    @JsonView(View.BaseResponseView.class)
    public int code;

    @JsonView(View.BaseResponseView.class)
    public String message;

    @JsonView(View.BaseResponseView.class)
    public JsonResponse data;
}
