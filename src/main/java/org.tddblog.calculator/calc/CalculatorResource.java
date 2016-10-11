package org.tddblog.calculator.calc;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public abstract class CalculatorResource implements CalculatorService {
    @POST
    @Path("/create")
    public abstract CreateCalculatorResponseDTO createCalculator();

    @POST
    @Path("/press")
    public abstract PressResponseDTO press(@QueryParam("id") String id, @QueryParam("button") String button);
}