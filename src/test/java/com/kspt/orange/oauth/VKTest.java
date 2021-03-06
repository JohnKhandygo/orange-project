package com.kspt.orange.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VkontakteApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import java.util.Scanner;

public class VKTest {

  private static final String NETWORK_NAME = "Vkontakte.ru";

  private static final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/friends.search";

  private static final Token EMPTY_TOKEN = null;

  public static void main(String[] args) {
    // Replace these with your client id and secret
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

    // Now let's go and ask for a protected resource!
    System.out.println("Now we're going to access a protected resource...");
    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL/*, service*/);
    service.signRequest(accessToken, request);
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    System.out.println();
    System.out.println(response.getCode());
    System.out.println(response.getBody());

    System.out.println();
    System.out.println("Thats it man! Go and build something awesome with ScribeJava! :)");
  }
}
