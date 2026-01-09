package bg.tu_varna.sit.si.mappers;

import bg.tu_varna.sit.si.dtos.requests.CreateSurveyRequest;
import bg.tu_varna.sit.si.dtos.responses.SurveyResponse;
import bg.tu_varna.sit.si.entities.Survey;

public class SurveyMapper {

    private SurveyMapper() {}

    public static Survey toEntity(CreateSurveyRequest request) {

        Survey survey = new Survey();
        survey.title = request.title;

        return survey;
    }

    public static SurveyResponse toResponse(Survey survey) {

        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.id = survey.id;
        surveyResponse.title = survey.title;
        surveyResponse.description = survey.description;
        surveyResponse.active = survey.active;
        surveyResponse.createdAt = survey.createdAt;

        return surveyResponse;
    }
}
