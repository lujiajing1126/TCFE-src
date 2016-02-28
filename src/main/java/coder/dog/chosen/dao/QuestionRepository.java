package coder.dog.chosen.dao;


import coder.dog.chosen.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by megrez on 16/2/27.
 */
public interface QuestionRepository extends CrudRepository<Question,Long> {
}
