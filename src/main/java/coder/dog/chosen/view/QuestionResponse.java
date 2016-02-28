package coder.dog.chosen.view;

import coder.dog.chosen.model.Question;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

/**
 * Created by megrez on 16/2/28.
 */
public class QuestionResponse implements JsonResponse {
    @JsonView(View.QuestionWithoutAnswer.class)
    public List<Question> questions;

    @JsonView(View.QuestionWithoutAnswer.class)
    public String token;
}
