package bg.tu_varna.sit.si.mappers;

import bg.tu_varna.sit.si.dtos.responses.OptionResponse;
import bg.tu_varna.sit.si.dtos.responses.QuestionResponse;
import bg.tu_varna.sit.si.entities.Question;
import bg.tu_varna.sit.si.entities.QuestionOption;

import java.util.ArrayList;

public final class QuestionMapper {

    private QuestionMapper() {}

    public static QuestionResponse toResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        response.id = question.id;
        response.text = question.text;
        response.type = question.type.name();
        response.required = question.required;
        response.orderIndex = question.orderIndex;

        response.options = new ArrayList<>();
        if (question.options != null) {
            for (QuestionOption questionOption : question.options) {
                OptionResponse optionResponse = OptionMapper.toResponse(questionOption);
                response.options.add(optionResponse);
            }
        }

        return response;
    }
}