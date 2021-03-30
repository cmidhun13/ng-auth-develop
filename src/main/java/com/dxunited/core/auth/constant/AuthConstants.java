package com.dxunited.core.auth.constant;

public class AuthConstants {

    /**
     * KeyCloak URL's
     **/
    public static final String GENERATE_TOKEN_ENDPOINT = "/realms/%s/protocol/openid-connect/token";
    public static final String VALIDATE_TOKEN_ENDPOINT = GENERATE_TOKEN_ENDPOINT + "/introspect";
    public static final String CREATE_USER_ENDPOINT = "/admin/realms/%s/users";
    public static final String GET_USER_ENDPOINT = CREATE_USER_ENDPOINT + "?username=%s";
    public static final String INVALIDATE_TOKEN_ENDPOINT = "/realms/%s/protocol/openid-connect/logout";
    public static final String ATTACK_DETECTION_ENDPOINT = "/admin/realms/%s/attack-detection/brute-force/users/%s";
    public static final String GET_USER_BY_MEMBER_ID_ENDPOINT = "/realms/%s/admin/users/%s";
    public static final String PUT_RESET_PASSWORD = "/admin/realms/%s/users/%s/reset-password";
    public static final String GET_MY_INFO_ENDPOINT = "/admin/master/console/whoami";
    public static final String ROLE_MAP_ENDPOINT = "/admin/realms/%s/users/%s/role-mappings/realm";
    /**
     * Request Body Constants
     **/
    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";

    public static final String APPLICATION_NAME = "ng-auth-service";

    /**
     * Grant Types
     **/
    public static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";
    public static final String PASSWORD_GRANT_TYPE = "password";
    public static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";


    public static final String TOKEN = "token";
    public static final String ACCESS_TOKEN = "access_token";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String MEMBER_ID = "member_id";
    public static final String CUSTOMER_ID = "member_id";

    public static final String X_CORRELATION_ID = "x-correlation-id";
    public static final String TENANT_ID = "tenant-id";
}
