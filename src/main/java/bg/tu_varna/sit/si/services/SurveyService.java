package bg.tu_varna.sit.si.services;

import bg.tu_varna.sit.si.dtos.requests.CreateSurveyRequest;
import bg.tu_varna.sit.si.dtos.responses.SurveyFillResponse;
import bg.tu_varna.sit.si.dtos.responses.SurveyResponse;
import bg.tu_varna.sit.si.entities.AccessCode;
import bg.tu_varna.sit.si.entities.Survey;
import bg.tu_varna.sit.si.exceptions.EntityNotFoundException;
import bg.tu_varna.sit.si.exceptions.InvalidRequestException;
import bg.tu_varna.sit.si.mappers.SurveyFillMapper;
import bg.tu_varna.sit.si.mappers.SurveyMapper;
import bg.tu_varna.sit.si.repositories.AccessCodeRepository;
import bg.tu_varna.sit.si.repositories.SurveyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final AccessCodeRepository accessCodeRepository;

    public SurveyService(SurveyRepository surveyRepository, AccessCodeRepository accessCodeRepository) {
        this.surveyRepository = surveyRepository;
        this.accessCodeRepository = accessCodeRepository;
    }

    @Transactional
    public SurveyResponse create(CreateSurveyRequest request) {

        Survey survey = new Survey();
        survey.title = request.title;
        survey.description = request.description;

        surveyRepository.persist(survey);

        return SurveyMapper.toResponse(survey);
    }

    public SurveyResponse getById(UUID id) {
        Survey survey = surveyRepository.findById(id);

        if (survey == null) {
            throw new EntityNotFoundException(
                    "Survey with id " + id + " not found"
            );
        }

        return SurveyMapper.toResponse(survey);
    }

    @Transactional
    public SurveyFillResponse getForFilling(UUID surveyId, String code) {

        if (code == null || code.trim().isEmpty()) {
            throw new InvalidRequestException("code is required");
        }

        String normalized = code.trim();

        Survey survey = surveyRepository.findById(surveyId);
        if (survey == null) {
            throw new EntityNotFoundException("Survey with id " + surveyId + " not found");
        }

        AccessCode accessCode = accessCodeRepository
                .findUnusedBySurveyIdAndCode(surveyId, normalized)
                .orElseThrow(() -> new InvalidRequestException("Invalid or used access code"));

        survey.questions.size();
        for (var question : survey.questions) {
            question.options.size();
        }

        return SurveyFillMapper.toResponse(survey);
    }
}
