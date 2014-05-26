import javax.enterprise.context.SessionScoped;
import javax.inject.Singleton;
import java.io.Serializable;

/**
 * @author Evgenia
 */

@SessionScoped
public class UserData implements Serializable {
    private String userId;
    private String accessToken;
    private String refreshToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
