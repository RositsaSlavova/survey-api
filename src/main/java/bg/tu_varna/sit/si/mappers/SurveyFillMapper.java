package bg.tu_varna.sit.si.mappers;

import bg.tu_varna.sit.si.dtos.responses.QuestionResponse;
import bg.tu_varna.sit.si.dtos.responses.SurveyFillResponse;
import bg.tu_varna.sit.si.entities.Question;
import bg.tu_varna.sit.si.entities.Survey;

import java.util.ArrayList;

public final class SurveyFillMapper {

    private SurveyFillMapper() {}

    public static SurveyFillResponse toResponse(Survey survey) {
        SurveyFillResponse response = new SurveyFillResponse();
        response.id = survey.id;
        response.title = survey.title;
        response.description = survey.description;

        response.questions = new ArrayList<>();
        for (Question question : survey.questions) {
            QuestionResponse questionResponse = QuestionMapper.toResponse(question);
            response.questions.add(questionResponse);
        }
        return response;
    }
}