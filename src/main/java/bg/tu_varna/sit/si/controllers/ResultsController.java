package bg.tu_varna.sit.si.controllers;

import bg.tu_varna.sit.si.dtos.responses.SurveyResultsResponse;
import bg.tu_varna.sit.si.services.ResultsService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/surveys/{surveyId}/results")
@Produces(MediaType.APPLICATION_JSON)
public class ResultsController {

    private final ResultsService resultsService;

    public ResultsController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @GET
    public SurveyResultsResponse get(@PathParam("surveyId") UUID surveyId) {
        return resultsService.getResults(surveyId);
    }
}
