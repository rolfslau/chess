package server;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

public class ServerFacade {

    private final String serverUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    public ServerFacade(String serverURL) {
        this.serverUrl = serverURL;
    }

    public void register(User user) {
        var request = buildRequest("POST", "/user", user, false, "");
        var response = sendRequest(request);
        handleResponse(response, Auth.class);
    }

    public Auth login(User user) {
        var request = buildRequest("POST", "/session", user, false, "");
        var response = sendRequest(request);
        return handleResponse(response, Auth.class);
    }

    public Integer createGame(CreateGameReq game, String auth) {
        var request = buildRequest("POST", "/game", game, true, auth);
        var response = sendRequest(request);
        return handleResponse(response, Integer.class);
    }

    public void joinGame(JoinGameReq game, String auth) {
        var request = buildRequest("PUT", "/game", game, true, auth);
        var response = sendRequest(request);
        handleResponse(response, null);
    }

    public void listGames(String auth) {
        var request = buildRequest("GET", "/game", null, true, auth);
        var response = sendRequest(request);
        System.out.print(response);
    }

    public Game observeGame(Integer ID) {
        // what ?? do I need to build the whole pipeline for this??

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
