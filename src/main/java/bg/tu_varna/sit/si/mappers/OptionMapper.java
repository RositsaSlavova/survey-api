package bg.tu_varna.sit.si.mappers;

import bg.tu_varna.sit.si.dtos.requests.OptionRequest;
import bg.tu_varna.sit.si.dtos.responses.OptionResponse;
import bg.tu_varna.sit.si.entities.Question;
import bg.tu_varna.sit.si.entities.QuestionOption;

public final class OptionMapper {

    private OptionMapper() {}

    public static QuestionOption toEntity(OptionRequest request, Question question, int fallbackOrderIndex) {

        QuestionOption option = new QuestionOption();
        option.question = question;
        option.label = request.label;
        option.orderIndex = (request.orderIndex != null) ? request.orderIndex : fallbackOrderIndex;

        return option;
    }

    public static OptionResponse toResponse(QuestionOption option) {

        OptionResponse response = new OptionResponse();
        response.id = option.id;
        response.label = option.label;
        response.orderIndex = option.orderIndex;

        return response;
    }
}