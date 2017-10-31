
package server.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import server.controllers.MainController;
import server.database.DBConnection;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.util.Date;

/**
 * FROM https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
 */

/**
 * Class responsible for ???
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    MainController mc;
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    /**
     * Only allows tokens that are authenticated to pass
     *
     * @param containerRequestContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        //Get the Authorization header from the request
        String authHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        //Validate the Authorization header
        if (!isTokenBasedAuthentication(authHeader)) {
            abortWithUnauthorized(containerRequestContext);
            return;
        }

        //Extract the token from the Authorization header
        String token = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {

            //Validate the token
            validateToken(token);


        } catch (Exception e) {
            abortWithUnauthorized(containerRequestContext);
        }

    }


    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        //Check if the Authorization header is valid
        //It must not be null and must be prefixed with "Bearer" plus a whitespace
        //Authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        //Abort with filter chain with a 401 status code
        //The "WWW-Authenticate" header is sent along with the response:
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME).build());
    }

    /**
     * Method that checks if the token is valid by verifying the token with the JWT library and if it is expired
     *
     * @param token
     * @return
     * @throws Exception
     */
    private boolean validateToken(String token) throws Exception {
        mc = new MainController();
        boolean isValidToken;

        isValidToken = mc.checkTokenOwner(token);

        if (!isValidToken) {
            throw new Exception();
        }

        return isValidToken;
    }
}

