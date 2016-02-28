package coder.dog.chosen.service;

import coder.dog.chosen.dao.QuestionRepository;
import coder.dog.chosen.model.Question;
import coder.dog.chosen.view.QuestionResponse;
import coder.dog.chosen.view.Response;
import com.google.common.collect.Lists;
import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by megrez on 16/2/28.
 */
@Service(value = "QuestionService")
public class QuestionServiceImpl implements QuestionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static int MAX_QUESTION_NUM = 4;
    public static int AN_HOUR = 3600;

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private MemcachedClient client;

    public Response getRandomQuestions(int limit) {
        Iterable<Question> questionList = repository.findAll(getRandom(limit,MAX_QUESTION_NUM));
        Response response = new Response();
        response.code = Response.HTTP_OK;
        response.message = "";
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.questions = Lists.newArrayList(questionList);
        questionResponse.token = UUID.randomUUID().toString();
        // Cache Answers To Memcached
        try {
            client.set(questionResponse.token, AN_HOUR, "HelloWorld");
            String str = client.get(questionResponse.token);
            logger.info(str);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
        }
        response.data = questionResponse;
        return response;
    }

    public Response getPassword(String uuid,Map<Long,Long> answerSheet) {
        return null;
    }

    private Map<Long,Long> getAnswerWithUUID(String UUID) {
        return null;
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
