/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paths;

import Errors.ErrorUtils;
import Exceptions.ExceptionUtils;
import Exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author admin
 */
public final class Router {

    public Router() {
    }

    public Router.RouteMatch matchRoute(HttpServletRequest req) {
        String path = req.getPathInfo(); // gets path after servlet mapping
        HttpMethod method = HttpMethod.valueOf(req.getMethod().toUpperCase());

        Router.RouteMatch match = Router.match(path, method);
        if (match == null) {
            // If no route matches, send 404
            try {
                throw new ResourceNotFoundException();
            } catch (Exception e) {
                // todo : handle exception
            }
        }
        return match;
    }

    public static RouteMatch match(String path, HttpMethod method) {
        for (Route route : Route.values()) {
            if (route.getMethod() != method) {
                continue;
            }

            Map<String, String> params = extractParams(route.getPath(), path);
            if (params != null) {
                return new RouteMatch(route, params);
            }
        }
        return null; // no match
    }

    // Returns map of parameter name -> value if matches, else null
    private static Map<String, String> extractParams(String pattern, String path) {
        String[] patternParts = pattern.split("/");
        String[] pathParts = path.split("/");

        if (patternParts.length != pathParts.length) {
            return null;
        }

        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < patternParts.length; i++) {
            String pp = patternParts[i];
            String p = pathParts[i];
            if (pp.startsWith("{") && pp.endsWith("}")) {
                params.put(pp.substring(1, pp.length() - 1), p);
            } else if (!pp.equals(p)) {
                return null; // not matching
            }
        }
        return params;
    }

    public static class RouteMatch {

        private final Route route;
        private final Map<String, String> params;

        public RouteMatch(Route route, Map<String, String> params) {
            this.route = route;
            this.params = params;
        }

        public Route getRoute() {
            return route;
        }

        public Map<String, String> getParams() {
            return params;
        }
    }
}
