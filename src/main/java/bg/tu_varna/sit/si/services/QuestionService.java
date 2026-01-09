package bg.tu_varna.sit.si.services;

import bg.tu_varna.sit.si.dtos.requests.AddQuestionRequest;
import bg.tu_varna.sit.si.dtos.requests.OptionRequest;
import bg.tu_varna.sit.si.dtos.responses.QuestionResponse;
import bg.tu_varna.sit.si.entities.Question;
import bg.tu_varna.sit.si.entities.QuestionType;
import bg.tu_varna.sit.si.entities.Survey;
import bg.tu_varna.sit.si.exceptions.EntityNotFoundException;
import bg.tu_varna.sit.si.exceptions.InvalidRequestException;
import bg.tu_varna.sit.si.mappers.OptionMapper;
import bg.tu_varna.sit.si.mappers.QuestionMapper;
import bg.tu_varna.sit.si.repositories.QuestionRepository;
import bg.tu_varna.sit.si.repositories.SurveyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@ApplicationScoped
public class QuestionService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public QuestionService(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public QuestionResponse addQuestion(UUID surveyId, AddQuestionRequest request) {
        Survey survey = surveyRepository.findById(surveyId);
        if (survey == null) {
            throw new EntityNotFoundException("Survey with id " + surveyId + " not found");
        }

        QuestionType questionType;
        try {
            questionType = QuestionType.valueOf(request.type);
        } catch (Exception e) {
            throw new InvalidRequestException("Invalid question questionType: " + request.type);
        }

        boolean isChoice = questionType == QuestionType.SINGLE_CHOICE || questionType == QuestionType.MULTIPLE_CHOICE;

        if (isChoice) {
            if (request.options == null || request.options.isEmpty()) {
                throw new InvalidRequestException("Options are required for choice questions");
            }

        } else {
            if (request.options != null && !request.options.isEmpty()) {
                throw new InvalidRequestException("Options are allowed only for choice questions");
            }
        }

        int orderIndex;
        if (request.orderIndex != null) {
            orderIndex = request.orderIndex;
        } else {
            Integer max = questionRepository.findMaxOrderIndex(surveyId);
            orderIndex = (max == null) ? 1 : (max + 1);
        }

        Question question = new Question();
        question.survey = survey;
        question.text = request.text;
        question.type = questionType;
        question.required = request.required;
        question.orderIndex = orderIndex;

        question.options = new ArrayList<>();
        if (isChoice) {
            for (int i = 0; i < request.options.size(); i++) {
                OptionRequest optReq = request.options.get(i);
                question.options.add(OptionMapper.toEntity(optReq, question, i + 1));
            }
        }

        questionRepository.persist(question);

        return QuestionMapper.toResponse(question);
    }

    public QuestionResponse getById(UUID questionId) {

        Question question = questionRepository.findById(questionId);
        if (question == null) {
            throw new EntityNotFoundException("Question with id " + questionId + " not found");
        }

        question.options.size();

        return QuestionMapper.toResponse(question);
    }
}