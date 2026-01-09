package bg.tu_varna.sit.si.mappers;

import bg.tu_varna.sit.si.dtos.responses.AnswerDetailsResponse;
import bg.tu_varna.sit.si.entities.Answer;

public final class AnswerDetailsMapper {

    private AnswerDetailsMapper() {}

    public static AnswerDetailsResponse toResponse(Answer answer) {

        AnswerDetailsResponse response = new AnswerDetailsResponse();
        response.id = answer.id;
        response.questionId = answer.question.id;
        response.value = answer.value;

        return response;
    }
}