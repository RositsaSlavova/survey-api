package bg.tu_varna.sit.si.controllers;

import bg.tu_varna.sit.si.dtos.requests.GenerateCodesRequest;
import bg.tu_varna.sit.si.dtos.responses.AccessCodeResponse;
import bg.tu_varna.sit.si.dtos.responses.GenerateCodesResponse;
import bg.tu_varna.sit.si.services.AccessCodeService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/surveys/{surveyId}/codes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccessCodeController {

    private final AccessCodeService accessCodeService;

    public AccessCodeController(AccessCodeService accessCodeService) {
        this.accessCodeService = accessCodeService;
    }

    @POST
    public GenerateCodesResponse generate(@PathParam("surveyId") UUID surveyId,
                                          @Valid GenerateCodesRequest request) {
        return accessCodeService.generate(surveyId, request);
    }

    @GET
    @Path("/active")
    public List<AccessCodeResponse> getActive(@PathParam("surveyId") UUID surveyId) {
        return accessCodeService.getActiveCodes(surveyId);
    }
}