package bg.tu_varna.sit.si.mappers;

import bg.tu_varna.sit.si.dtos.responses.AccessCodeResponse;
import bg.tu_varna.sit.si.entities.AccessCode;

public class AccessCodeMapper {

    public static AccessCodeResponse toResponse(AccessCode code) {

        AccessCodeResponse response = new AccessCodeResponse();
        response.id = code.id;
        response.code = code.code;

        return response;
    }
}