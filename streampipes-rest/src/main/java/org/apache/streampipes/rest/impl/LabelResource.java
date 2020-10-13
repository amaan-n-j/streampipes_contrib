package org.apache.streampipes.rest.impl;

import com.google.gson.JsonObject;
import org.apache.streampipes.model.labeling.Category;
import org.apache.streampipes.model.labeling.Label;
import org.apache.streampipes.rest.api.ILabel;
import org.apache.streampipes.rest.shared.annotation.JacksonSerialized;
import org.apache.streampipes.serializers.json.JacksonSerializer;
import org.apache.streampipes.storage.management.StorageDispatcher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/v2/users/{username}/labeling/label")
public class LabelResource extends AbstractRestInterface implements ILabel {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonSerialized
    @Override
    public Response getAllLabels() {
        return ok(
                StorageDispatcher.INSTANCE
                .getNoSqlStore()
                .getLabelStorageAPI()
                .getAllLabels()
        );
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonSerialized
    @Override
    public Response addLabel(Label label) {
        Category categoryForLabel = StorageDispatcher.INSTANCE
                .getNoSqlStore()
                .getCategoryStorageAPI()
                .getCategory(label.getCategoryId());
        if (categoryForLabel == null) {
            String resString = String.format("Category with categoryId %s does not exist", label.getCategoryId());
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", resString);
            return badRequest(errorDetails);
        }
        StorageDispatcher.INSTANCE
                .getNoSqlStore()
                .getLabelStorageAPI()
                .storeLabel(label);
        return ok();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonSerialized
    @Override
    public Response updateLabel(Label label) {
        Category categoryForLabel = StorageDispatcher.INSTANCE
                .getNoSqlStore()
                .getCategoryStorageAPI()
                .getCategory(label.getCategoryId());
        if (categoryForLabel == null) {
            String resString = String.format("Category with categoryId %s does not exist", label.getCategoryId());
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", resString);
            return badRequest(errorDetails);
        }
        StorageDispatcher.INSTANCE
                .getNoSqlStore()
                .getLabelStorageAPI()
                .updateLabel(label);
        return ok();
    }

    @POST
    @Path("/delete/{labelId}")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonSerialized
    @Override
    public Response deleteLabel(@PathParam("labelId") String labelId) {
        StorageDispatcher.INSTANCE
                .getNoSqlStore()
                .getLabelStorageAPI()
                .deleteLabel(labelId);
        return ok();
    }

    @GET
    @Path("category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonSerialized
    @Override
    public Response getLabelsForCategory(@PathParam("categoryId") String categoryId) {
        return ok(
                StorageDispatcher.INSTANCE
                .getNoSqlStore()
                .getLabelStorageAPI()
                .getAllForCategory(categoryId)
        );
    }
}
