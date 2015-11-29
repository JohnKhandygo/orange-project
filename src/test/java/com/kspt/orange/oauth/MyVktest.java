package com.kspt.orange.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kspt.orange.frameworks.AuthenticationCredentials;
import com.kspt.orange.frameworks.api.vk_unworked.VkApiBuilder;
import com.kspt.orange.frameworks.api.vk_unworked.endpoints.VkNewsApi;
import com.kspt.orange.frameworks.api.vk_unworked.entities.VkDataObject;
import com.kspt.orange.frameworks.api.vk_unworked.entities.VkNewsPortion;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VkontakteApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Scanner;

@Path("token")
@Produces(MediaType.APPLICATION_JSON)
interface AuthByPassword {

  @GET
  public AccessToken auth(
      final @QueryParam("grant_type") String g,
      final @QueryParam("client_id") long cid,
      final @QueryParam("client_secret") String cs,
      final @QueryParam("username") String u,
      final @QueryParam("password") String p,
      final @QueryParam("scope") String s);
}

public class MyVkTest {

  private static final String NETWORK_NAME = "Vkontakte.ru";

  private static final Token EMPTY_TOKEN = null;

  public static void main(String[] args) {
    final String clientId = "5160753";
    final String clientSecret = "kNG3HDFLTLJmHxCTN4Yx";
    OAuthService service = new ServiceBuilder()
        .provider(VkontakteApi.class)
        .apiKey(clientId)
        .apiSecret(clientSecret)
        .scope("friends,wall,offline") // replace with desired scope
        .callback("https://oauth.vk.com/blank.html")
        .build();
    Scanner in = new Scanner(System.in);

    System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
    System.out.println();

    // Obtain the Authorization URL
    System.out.println("Fetching the Authorization URL...");
    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
    System.out.println("Got the Authorization URL!");
    System.out.println("Now go and authorize ScribeJava here:");
    System.out.println(authorizationUrl);
    System.out.println("And paste the authorization code here");
    System.out.print(">>");
    Verifier verifier = new Verifier(in.nextLine());
    System.out.println();

    // Trade the Request Token and Verfier for the Access Token
    System.out.println("Trading the Request Token for an Access Token...");
    Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
    System.out.println("Got the Access Token!");
    System.out.println("(if your curious it looks like this: " + accessToken + " )");
    System.out.println();

    final Token consumerToken = new Token(clientId, clientSecret);
    final VkNewsApi build = VkApiBuilder
        .build(VkNewsApi.class, new AuthenticationCredentials(consumerToken, accessToken));
    final VkDataObject search = build.search(null, null, null, null, 10);
    final VkNewsPortion portion = search.portion();
    final int a = 0;
  }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class AccessToken {
  @JsonProperty("access_token")
  public String token;
}