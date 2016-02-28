package coder.dog.chosen.service;

import coder.dog.chosen.dao.QuestionRepository;
import coder.dog.chosen.model.Question;
import coder.dog.chosen.view.QuestionResponse;
import coder.dog.chosen.view.Response;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by megrez on 16/2/28.
 */
@Service
public class QuestionService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static int MAX_QUESTION_NUM = 4;

    @Autowired
    private QuestionRepository repository;

    public Response getRandomQuestions(int limit) {
        Iterable<Question> questionList = repository.findAll(getRandom(limit,MAX_QUESTION_NUM));
        Response response = new Response();
        response.code = Response.HTTP_OK;
        response.message = "";
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.questions = Lists.newArrayList(questionList);
        questionResponse.token = UUID.randomUUID().toString();
        response.data = questionResponse;
        return response;
    }

    private Set<Long> getRandom(int limit,int max) {
        Set<Long> hashSet = new HashSet<Long>();
        Random random = new Random(System.currentTimeMillis());
        while(hashSet.size() < limit) {
            long number = random.nextInt(max) + 1;
            hashSet.add(number);
        }
        logger.info(Arrays.toString(hashSet.toArray()));
        return hashSet;
    }
}
