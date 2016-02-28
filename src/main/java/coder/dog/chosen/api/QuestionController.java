package coder.dog.chosen.api;

import coder.dog.chosen.service.QuestionService;
import coder.dog.chosen.view.ErrorResponse;
import coder.dog.chosen.view.Response;
import coder.dog.chosen.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    @Autowired
    QuestionService service;

    @RequestMapping("/api/question")
    @JsonView(View.QuestionWithoutAnswer.class)
    public Response questions(@RequestParam(value = "limit",defaultValue = "1") int limit) {
        if(limit > QuestionService.MAX_QUESTION_NUM) {
            return new ErrorResponse(400,"Invalid Limit Param");
        }
        return service.getRandomQuestions(limit);
    }

    public Response answer(String uuid,String answer) {
        return null;
    }
}