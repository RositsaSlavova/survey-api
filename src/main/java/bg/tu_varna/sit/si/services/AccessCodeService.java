package bg.tu_varna.sit.si.services;

import bg.tu_varna.sit.si.dtos.requests.GenerateCodesRequest;
import bg.tu_varna.sit.si.dtos.responses.AccessCodeResponse;
import bg.tu_varna.sit.si.dtos.responses.GenerateCodesResponse;
import bg.tu_varna.sit.si.entities.AccessCode;
import bg.tu_varna.sit.si.entities.Survey;
import bg.tu_varna.sit.si.exceptions.EntityNotFoundException;
import bg.tu_varna.sit.si.exceptions.InvalidRequestException;
import bg.tu_varna.sit.si.mappers.AccessCodeMapper;
import bg.tu_varna.sit.si.repositories.AccessCodeRepository;
import bg.tu_varna.sit.si.repositories.SurveyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.security.SecureRandom;
import java.util.*;

@ApplicationScoped
public class AccessCodeService {

    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 10;
    private static final int MAX_ATTEMPTS_PER_CODE = 50;

    private final SurveyRepository surveyRepository;
    private final AccessCodeRepository accessCodeRepository;
    private final SecureRandom random = new SecureRandom();

    public AccessCodeService(SurveyRepository surveyRepository, AccessCodeRepository accessCodeRepository) {
        this.surveyRepository = surveyRepository;
        this.accessCodeRepository = accessCodeRepository;
    }

    @Transactional
    public GenerateCodesResponse generate(UUID surveyId, GenerateCodesRequest request) {

        if (request == null) {
            throw new InvalidRequestException("Request body is required");
        }

        Survey survey = surveyRepository.findById(surveyId);
        if (survey == null) {
            throw new EntityNotFoundException("Survey with id " + surveyId + " not found");
        }

        List<AccessCode> entities = new ArrayList<>(request.count);
        List<String> codes = new ArrayList<>(request.count);

        Set<String> local = new HashSet<>();

        for (int i = 0; i < request.count; i++) {
            String code = generateUniqueCode(local);

            AccessCode accessCode = new AccessCode();
            accessCode.survey = survey;
            accessCode.code = code;
            accessCode.used = false;
            accessCode.usedAt = null;

            entities.add(accessCode);
            codes.add(code);
            local.add(code);
        }

        accessCodeRepository.persist(entities);

        return new GenerateCodesResponse(request.count, codes);
    }

    private String generateUniqueCode(Set<String> local) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS_PER_CODE; attempt++) {
            String code = randomCode();

            if (local.contains(code)) continue;

            if (!accessCodeRepository.existsByCode(code)) {
                return code;
            }
        }
        throw new InvalidRequestException("Failed to generate unique codes. Try again.");
    }

    private String randomCode() {
        StringBuilder stringBuilder = new StringBuilder(AccessCodeService.CODE_LENGTH);
        for (int i = 0; i < AccessCodeService.CODE_LENGTH; i++) {
            int idx = random.nextInt(ALPHABET.length());
            stringBuilder.append(ALPHABET.charAt(idx));
        }
        return stringBuilder.toString();
    }

    public List<AccessCodeResponse> getActiveCodes(UUID surveyId) {
        Survey survey = surveyRepository.findById(surveyId);
        if (survey == null) {
            throw new EntityNotFoundException("Survey with id " + surveyId + " not found");
        }

        return accessCodeRepository.findUnusedBySurveyId(surveyId)
                .stream()
                .map(AccessCodeMapper::toResponse)
                .toList();
    }
}
