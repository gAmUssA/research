import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Evgenia
 */

@Path("/bp")
public class BloodPressureService {
    private UserData userData;

    @GET
    public String getData() {
        StringBuilder res = new StringBuilder();

        try {
            String cs = HealthLabsInfo.charset;

            //build url
            String url = String.format(HealthLabsInfo.iHealthOpenApiUrl + "user/%s/bp.json/",
                    URLEncoder.encode(userData.getUserId(), cs));

            //build query
            //required params
            String query = String.format("client_id=%s&client_secret=%s&redirect_uri=%s&access_token=%s&sc=%s&sv=%s",
                    URLEncoder.encode(HealthLabsInfo.clientId, cs),
                    URLEncoder.encode(HealthLabsInfo.clientSecret, cs),
                    URLEncoder.encode(HealthLabsInfo.redirectUri, cs),
                    URLEncoder.encode(userData.getAccessToken(), cs),
                    URLEncoder.encode(HealthLabsInfo.SC, cs),
                    URLEncoder.encode(HealthLabsInfo.OpenApiBPSV, cs));


            //optional params: start time, end time, page index, locale
            Calendar calendar = new GregorianCalendar(1970, 0, 1);
            query = query + "&start_time=" + calendar.getTimeInMillis();

            //request
            HttpURLConnection connection = (HttpURLConnection) new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", cs);
            InputStream responseStream = connection.getInputStream();

            //parse response
            BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, cs));

            String line;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.toString();
    }

    @Inject
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
