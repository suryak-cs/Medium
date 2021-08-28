public class FetchTokenUtility {

    public AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(<accessKey>, <secretKey>);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(<region>)
                .build();

    }
    public  String getToken() throws Exception {
        getAmazonCognitoIdentityClient();
        AuthenticationResultType authenticationResult = null;
        AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();

        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", <userName>);
        authParams.put("PASSWORD", <password>);
        authParams.put("SRP_A", new AuthenticationHelper(<userName>).getA().toString(16));

        final InitiateAuthRequest authRequest = new InitiateAuthRequest();
        authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withClientId(<clientId>)
                .withAuthParameters(authParams)
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH);

        InitiateAuthResult result = cognitoClient.initiateAuth(authRequest);
        authenticationResult = result.getAuthenticationResult();
        String token = authenticationResult.getIdToken();
        cognitoClient.shutdown();
        return token;
    }
}
