package bg.tu_varna.sit.si.services;

import bg.tu_varna.sit.si.dtos.requests.AnswerRequest;
import bg.tu_varna.sit.si.dtos.requests.SubmitSurveyRequest;
import bg.tu_varna.sit.si.dtos.responses.SubmissionDetailsResponse;
import bg.tu_varna.sit.si.dtos.responses.SubmitSurveyResponse;
import bg.tu_varna.sit.si.entities.*;
import bg.tu_varna.sit.si.exceptions.EntityNotFoundException;
import bg.tu_varna.sit.si.exceptions.InvalidRequestException;
import bg.tu_varna.sit.si.mappers.SubmissionDetailsMapper;
import bg.tu_varna.sit.si.repositories.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.*;

@ApplicationScoped
public class SubmissionService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AccessCodeRepository accessCodeRepository;
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;

    public SubmissionService(SurveyRepository surveyRepository, QuestionRepository questionRepository, AccessCodeRepository accessCodeRepository, SubmissionRepository submissionRepository, AnswerRepository answerRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.accessCodeRepository = accessCodeRepository;
        this.submissionRepository = submissionRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional
    public SubmitSurveyResponse submit(UUID surveyId, SubmitSurveyRequest request) {

        Survey survey = surveyRepository.findById(surveyId);
        if (survey == null) {
            throw new EntityNotFoundException("Survey with id " + surveyId + " not found");
        }

        String code = request.code.trim();
        AccessCode accessCode = accessCodeRepository
                .findUnusedBySurveyIdAndCode(surveyId, code)
                .orElseThrow(() -> new InvalidRequestException("Invalid or used access code"));

        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        Map<UUID, Question> byId = new HashMap<>();
        for (Question question : questions) {
            byId.put(question.id, question);
        }

        Set<UUID> answered = new HashSet<>();
        for (AnswerRequest answerRequest : request.answers) {
            if (answerRequest.questionId != null) answered.add(answerRequest.questionId);
        }

        for (Question question : questions) {
            if (question.required && !answered.contains(question.id)) {
                throw new InvalidRequestException("Missing answer for required question: " + question.id);
            }
        }

        Submission submission = new Submission();
        submission.survey = survey;
        submission.accessCode = accessCode;
        submission.submittedAt = Instant.now();
        submissionRepository.persist(submission);

        List<Answer> answers = new ArrayList<>();
        for (AnswerRequest answerRequest : request.answers) {
            Question question = byId.get(answerRequest.questionId);
            if (question == null) {
                throw new InvalidRequestException("Question does not belong to this survey: " + answerRequest.questionId);
            }

            Answer answer = new Answer();
            answer.submission = submission;
            answer.question = question;
            answer.value = answerRequest.value.trim();
            answers.add(answer);
        }
        answerRepository.persist(answers);

        accessCode.used = true;
        accessCode.usedAt = Instant.now();

        return new SubmitSurveyResponse(submission.id, submission.submittedAt);
    }

    @Transactional
    public SubmissionDetailsResponse getById(UUID submissionId) {

        Submission submission = submissionRepository.findById(submissionId);
        if (submission == null) {
            throw new EntityNotFoundException("Submission with id " + submissionId + " not found");
        }

        List<Answer> answers = answerRepository.findBySubmissionId(submissionId);

        submission.survey.id.toString();
        submission.accessCode.id.toString();
        for (Answer answer : answers) {
            answer.question.id.toString();
        }

        return SubmissionDetailsMapper.toResponse(submission, answers);
    }
}
