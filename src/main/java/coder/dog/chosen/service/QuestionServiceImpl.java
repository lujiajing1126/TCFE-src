package coder.dog.chosen.service;

import coder.dog.chosen.dao.QuestionRepository;
import coder.dog.chosen.model.Question;
import coder.dog.chosen.view.ErrorResponse;
import coder.dog.chosen.view.PasswordResponse;
import coder.dog.chosen.view.QuestionResponse;
import coder.dog.chosen.view.Response;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
    private ThreadPoolTaskExecutor pool;

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
        response.data = questionResponse;
        // RUN Background Cache
        pool.execute(() -> {
            Map<String, Integer> map = questionResponse.questions.stream().collect(HashMap::new,
                    (imap, question) -> imap.put(question.getId().toString(), question.getAnswer()),
                    Map::putAll);
            // Cache Answers To Memcached
            try {
                client.set(questionResponse.token, AN_HOUR, map);
                Map<String, Integer> oldMap = client.get(questionResponse.token);
                oldMap.forEach((aLong, integer) -> {
                    String str = String.format("%s - %d", aLong, integer);
                    logger.info(str);
                });
            } catch (Exception ex) {
                logger.debug(ex.getMessage());
            }
        });
        return response;
    }

    public Response getPassword(String uuid,Map<String,Integer> answerSheet) {
        Optional<Map<String, Integer>> correctMap;
        try {
            correctMap = getAnswerWithUUID(uuid);
        } catch (Exception ex) {
            return new ErrorResponse(500,"Internal Error");
        }
        if(correctMap.isPresent()) {
            MapDifference<String, Integer> differences = Maps.difference(correctMap.get(), answerSheet);
            if (differences.areEqual()) {
                return new PasswordResponse(UUID.randomUUID().toString());
            } else {
                return new ErrorResponse(10000, "Error Answer");
            }
        } else {
            return new ErrorResponse(10001, "Error UUID given");
        }
    }

    private synchronized Optional<Map<String,Integer>> getAnswerWithUUID(String UUID) throws Exception {
        Optional<Map<String,Integer>> map = Optional.ofNullable(client.get(UUID));
        if(map.isPresent()) {
            client.delete(UUID);
        }
        return map;
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
