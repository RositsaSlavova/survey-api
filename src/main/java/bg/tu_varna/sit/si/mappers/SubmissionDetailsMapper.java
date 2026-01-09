package bg.tu_varna.sit.si.mappers;

import bg.tu_varna.sit.si.dtos.responses.AnswerDetailsResponse;
import bg.tu_varna.sit.si.dtos.responses.SubmissionDetailsResponse;
import bg.tu_varna.sit.si.entities.Answer;
import bg.tu_varna.sit.si.entities.Submission;

import java.util.ArrayList;
import java.util.List;

public final class SubmissionDetailsMapper {

    private SubmissionDetailsMapper() {}

    public static SubmissionDetailsResponse toResponse(Submission submission, List<Answer> answers) {
        SubmissionDetailsResponse response = new SubmissionDetailsResponse();
        response.id = submission.id;
        response.surveyId = submission.survey.id;
        response.accessCodeId = submission.accessCode.id;
        response.submittedAt = submission.submittedAt;

        response.answers = new ArrayList<>();
        for (Answer a : answers) {
            AnswerDetailsResponse answerDetailsResponse = AnswerDetailsMapper.toResponse(a);
            response.answers.add(answerDetailsResponse);
        }

        return response;
    }
}
