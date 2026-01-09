package bg.tu_varna.sit.si.services;

import bg.tu_varna.sit.si.dtos.responses.QuestionResultResponse;
import bg.tu_varna.sit.si.dtos.responses.SurveyResultsResponse;
import bg.tu_varna.sit.si.entities.Answer;
import bg.tu_varna.sit.si.entities.Question;
import bg.tu_varna.sit.si.entities.Survey;
import bg.tu_varna.sit.si.exceptions.EntityNotFoundException;
import bg.tu_varna.sit.si.repositories.AnswerRepository;
import bg.tu_varna.sit.si.repositories.QuestionRepository;
import bg.tu_varna.sit.si.repositories.SubmissionRepository;
import bg.tu_varna.sit.si.repositories.SurveyRepository;
import bg.tu_varna.sit.si.results.utilities.ResultsStatisticsUtils;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class ResultsService {

    private static final int TEXT_SAMPLE_LIMIT = 50;

    private final SurveyRepository surveyRepository;
    private final SubmissionRepository submissionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public ResultsService(SurveyRepository surveyRepository,
                          SubmissionRepository submissionRepository,
                          QuestionRepository questionRepository,
                          AnswerRepository answerRepository) {

        this.surveyRepository = surveyRepository;
        this.submissionRepository = submissionRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public SurveyResultsResponse getResults(UUID surveyId) {
        Survey survey = surveyRepository.findById(surveyId);
        if (survey == null) {
            throw new EntityNotFoundException("Survey with id " + surveyId + " not found");
        }

        long totalSubmissions = submissionRepository.count("survey.id", surveyId);
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        SurveyResultsResponse response = new SurveyResultsResponse();
        response.surveyId = surveyId;
        response.totalSubmissions = totalSubmissions;
        response.questions = new ArrayList<>();

        for (Question question : questions) {
            List<Answer> answers = answerRepository.findByQuestionId(question.id);

            QuestionResultResponse questionResultResponse = new QuestionResultResponse();
            questionResultResponse.questionId = question.id;
            questionResultResponse.text = question.text;
            questionResultResponse.type = question.type.name();
            questionResultResponse.orderIndex = question.orderIndex;
            questionResultResponse.answeredCount = answers.size();

            switch (question.type) {
                case TEXT -> {
                    questionResultResponse.samples = answers.stream()
                            .map(a -> a.value)
                            .filter(Objects::nonNull)
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .limit(TEXT_SAMPLE_LIMIT)
                            .toList();
                }

                case NUMBER -> {
                    var stats = ResultsStatisticsUtils.numericStatsFromAnswers(answers);
                    questionResultResponse.min = stats.min; questionResultResponse.max = stats.max; questionResultResponse.avg = stats.avg;
                }

                case RATING -> {
                    var stats = ResultsStatisticsUtils.numericStatsFromAnswers(answers);
                    questionResultResponse.min = stats.min; questionResultResponse.max = stats.max; questionResultResponse.avg = stats.avg;

                    var counts = ResultsStatisticsUtils.singleValueCounts(answers);
                    questionResultResponse.distribution = counts;
                    questionResultResponse.distributionPercent = ResultsStatisticsUtils.toPercentMap(counts, questionResultResponse.answeredCount);
                }

                case SINGLE_CHOICE -> {
                    var counts = ResultsStatisticsUtils.singleValueCounts(answers);
                    questionResultResponse.distribution = counts;
                    questionResultResponse.distributionPercent = ResultsStatisticsUtils.toPercentMap(counts, questionResultResponse.answeredCount);
                }

                case MULTIPLE_CHOICE -> {
                    var counts = ResultsStatisticsUtils.multiChoiceCountsCsv(answers);
                    questionResultResponse.distribution = counts;
                    questionResultResponse.distributionPercent = ResultsStatisticsUtils.toPercentMap(counts, questionResultResponse.answeredCount);
                }
            }

            response.questions.add(questionResultResponse);
        }

        response.questions.sort(Comparator.comparingInt(a -> a.orderIndex));
        return response;
    }
}
