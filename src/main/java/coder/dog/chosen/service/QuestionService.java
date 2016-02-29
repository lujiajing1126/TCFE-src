package coder.dog.chosen.service;

import coder.dog.chosen.view.Response;

import java.util.Map;

/**
 * Created by megrez on 16/2/28.
 */
public interface QuestionService {
    Response getRandomQuestions(int limit);
    Response getPassword(String uuid,Map<String,Integer> answerSheet);
}
