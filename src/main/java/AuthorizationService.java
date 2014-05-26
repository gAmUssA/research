import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author Evgenia
 */

@Path("/authorization")
public class AuthorizationService {
    private UserData userData;

    @GET
    @Path("/iHealthLabsAuthorizationUrl")
    public Response getIHealthLabsAuthorizationUrl() {
        try {
            String cs = HealthLabsInfo.charset;

            String query = String.format("client_id=%s&response_type=%s&redirect_uri=%s&APIName=%s",
                    URLEncoder.encode(HealthLabsInfo.clientId, cs),
                    URLEncoder.encode(HealthLabsInfo.responseType, cs),
                    URLEncoder.encode(HealthLabsInfo.loginCallBackUri, cs),
                    URLEncoder.encode(HealthLabsInfo.APIName, cs));

            return Response.ok(HealthLabsInfo.iHealthAuthorizationUrl + "?" + query).build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/requestAccessToken")
    public Response requestAccessToken(@QueryParam("code") String code,
                                       @Context HttpServletRequest request,
                                       @Context HttpServletResponse response) {
        try {
            String cs = HealthLabsInfo.charset;
            //build query
            String query = String.format("client_id=%s&client_secret=%s&grant_type=%s&redirect_uri=%s&code=%s",
                    URLEncoder.encode(HealthLabsInfo.clientId, cs),
                    URLEncoder.encode(HealthLabsInfo.clientSecret, cs),
                    URLEncoder.encode(HealthLabsInfo.grantType, cs),
                    URLEncoder.encode(HealthLabsInfo.redirectUri, cs),
                    URLEncoder.encode(code, cs));

            //go to iHealthLabs
            HttpURLConnection connection = (HttpURLConnection) new URL(HealthLabsInfo.iHealthAuthorizationUrl + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", cs);
            InputStream responseStream = connection.getInputStream();

            //process response
            BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, cs));
            StringBuilder res = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }

            JsonReader reader = Json.createReader(new StringReader(res.toString()));
            JsonObject jsonObject = reader.readObject();

            try {
                userData.setAccessToken(jsonObject.getString("AccessToken"));
                userData.setRefreshToken(jsonObject.getString("RefreshToken"));
                userData.setUserId(jsonObject.getString("UserID"));

                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/#bp");
                return Response.status(Response.Status.ACCEPTED).build();

            } catch (NullPointerException e) {
                //there was an error, login did not success
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        } catch (IOException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Inject
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
