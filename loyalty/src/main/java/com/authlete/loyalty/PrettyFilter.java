package com.authlete.loyalty;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.EndpointConfigBase;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.ObjectWriterInjector;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.ObjectWriterModifier;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

// Adapted from https://stackoverflow.com/a/32649532
@Provider
@PreMatching
public class PrettyFilter implements ContainerResponseFilter {
  @Override
  public void filter(ContainerRequestContext reqCtx, ContainerResponseContext respCtx) throws IOException {
    UriInfo uriInfo = reqCtx.getUriInfo();

    MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
    if(queryParameters.containsKey("pretty")) {
      ObjectWriterInjector.set(new IndentingModifier(true));
    }
  }

  public static class IndentingModifier extends ObjectWriterModifier {
    private final boolean indent;

    public IndentingModifier(boolean indent) {
      this.indent = indent;
    }

    @Override
    public ObjectWriter modify(
        EndpointConfigBase<?> endpointConfigBase,
        MultivaluedMap<String, Object> multivaluedMap,
        Object o,
        ObjectWriter objectWriter,
        JsonGenerator jsonGenerator
    ) throws IOException {
      if (indent)
        jsonGenerator.useDefaultPrettyPrinter();
      return objectWriter;
    }
  }
}
