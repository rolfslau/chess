package client;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {
    // how do I clear the app to start
    // how should I be handling when they do something wrong, it shouldn't end the whole thing right?
    // make sure that I am printing out what the user did wrong and what they should do -- don't break
    // why is creating a new game giving me that error ??

    private final String serverUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    public ServerFacade(String serverURL) {
        this.serverUrl = serverURL;
    }

    public String register(User user) {
        var request = buildRequest("POST", "/user", user, false, "");
        var response = sendRequest(request);
        return handleResponse(response, Auth.class).authToken();
    }

    public Auth login(User user) {
        var request = buildRequest("POST", "/session", user, false, "");
        var response = sendRequest(request);
        return handleResponse(response, Auth.class);
    }

    public Integer createGame(CreateGameReq game, String auth) {
        var request = buildRequest("POST", "/game", game, true, auth);
        var response = sendRequest(request);
        model.GameID gameID = new Gson().fromJson(response.body(), GameID.class);
        // make another record class for this that is just the gameID so I can pass it to handle response
        return handleResponse(response, GameID.class).gameID();
    }

    public void joinGame(JoinGameReq game, String auth) {
        var request = buildRequest("PUT", "/game", game, true, auth);
        var response = sendRequest(request);
        handleResponse(response, null);
    }

    public void listGames(String auth) {
        var request = buildRequest("GET", "/game", null, true, auth);
        var response = sendRequest(request);
        GamesList games = new Gson().fromJson(response.body(), GamesList.class);
        for (Game game : games.games()) {
            System.out.printf("%d : %s - white: %s, black - %s\n", game.gameID(), game.gameName(), game.whiteUsername(), game.blackUsername());
        }
    }

    public void logout(String auth) {
        var request = buildRequest("DELETE", "/session", null, true, auth);
        var response = sendRequest(request);
        handleResponse(response, null);
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    private HttpRequest buildRequest(String method, String path, Object body, boolean header, String auth) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if (header) {
            request.setHeader("Authorization", auth);
        }
        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw ResponseException.fromJson(body);
            }
            System.out.print("breaking in handle response 2 for some reason");
            throw new ResponseException(ResponseException.fromHttpStatusCode(status), "other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
