/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paths;

import Exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
        // Use the full application-relative path so we can match Route.getPath() values
        // which are absolute (start with '/'). This avoids issues caused by servlet
        // mapping and req.getPathInfo().
        String requestUri = req.getRequestURI(); // e.g. /app/todos/123
        String context = req.getContextPath();   // e.g. /app
        String path = requestUri.substring(context.length()); // e.g. /todos/123 or /
        HttpMethod method = HttpMethod.valueOf(req.getMethod().toUpperCase());

        Router.RouteMatch match = Router.match(path, method);
        if (match == null) {
            // No route matches -> propagate resource not found to be handled by global handler
            throw new ResourceNotFoundException();
        }
        return match;
    }

    public static RouteMatch match(String path, HttpMethod method) {
        if (path == null) return null;
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
        if (pattern == null || path == null) return null;

        // Normalize: remove leading and trailing slashes so split produces comparable arrays
        String pPattern = normalizePath(pattern);
        String pPath = normalizePath(path);

        // Special case: both are empty => match root
        if (pPattern.isEmpty() && pPath.isEmpty()) {
            return new HashMap<>();
        }

        String[] patternParts = pPattern.isEmpty() ? new String[0] : pPattern.split("/");
        String[] pathParts = pPath.isEmpty() ? new String[0] : pPath.split("/");

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

    private static String normalizePath(String s) {
        if (s == null) return "";
        // ensure we operate on the path part only
        String t = s;
        // Remove query if present (defensive)
        int q = t.indexOf('?');
        if (q >= 0) t = t.substring(0, q);
        // Strip leading slash(es)
        while (t.startsWith("/")) t = t.substring(1);
        // Strip trailing slash(es)
        while (t.endsWith("/")) t = t.substring(0, t.length() - 1);
        return t;
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
