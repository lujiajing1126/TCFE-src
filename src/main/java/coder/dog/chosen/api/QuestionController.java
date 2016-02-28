package coder.dog.chosen.api;

import coder.dog.chosen.service.QuestionService;
import coder.dog.chosen.service.QuestionServiceImpl;
import coder.dog.chosen.view.ErrorResponse;
import coder.dog.chosen.view.Response;
import coder.dog.chosen.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class QuestionController {
    @Autowired
    QuestionService service;

    @RequestMapping("/api/question")
    @JsonView(View.QuestionWithoutAnswer.class)
    public Response questions(@RequestParam(value = "limit",defaultValue = "1") int limit) {
        if(limit > QuestionServiceImpl.MAX_QUESTION_NUM) {
            return new ErrorResponse(400,"Invalid Limit Param");
        }
        return service.getRandomQuestions(limit);
    }

    @RequestMapping(path = "/api/password/{uuid}",method = RequestMethod.POST)
    public Response answer(@PathVariable("uuid") String uuid,@RequestParam("answer") String answer) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<Long, Long> answerSheet = mapper.readValue(answer, HashMap.class);
            return service.getPassword(uuid,answerSheet);
        } catch (Exception ex) {
            return new ErrorResponse(400,"Error Parsing answer json object");
        }
    }
}