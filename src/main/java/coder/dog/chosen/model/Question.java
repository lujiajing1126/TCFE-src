package coder.dog.chosen.model;

import coder.dog.chosen.util.JsonListStringConverter;
import coder.dog.chosen.view.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

/**
 * Created by megrez on 16/2/26.
 */
@Entity
@SequenceGenerator(name = "qid_seq", sequenceName = "question_id_seq")
@Access(AccessType.FIELD)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "qid_seq")
    @JsonView(View.QuestionWithoutAnswer.class)
    private Long id;

    @JsonView(View.QuestionWithoutAnswer.class)
    private String content;

    @Convert(converter = JsonListStringConverter.class)
    @JsonView(View.QuestionWithoutAnswer.class)
    private List<String> choices;

    private int answer;

    public List<String> getChoices() {
        return choices;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getAnswer() {
        return answer;
    }
}
