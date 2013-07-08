package org.springframework.security.oauth2.provider;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

/**
 * Represents an OAuth2 token request, made at the {@link TokenEndpoint}. The requestParameters map should
 * contain the original, unmodified parameters from the original OAuth2 request.
 * 
 * In the implicit flow, a token is requested through the {@link AuthorizationEndpoint} directly, and in
 * that case the {@link AuthorizationRequest} is converted into a {@link TokenRequest} for processing
 * through the token granting chain. 
 * 
 * @author Amanda Anganes
 *
 */
public class TokenRequest extends BaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String grantType;
	
	/**
	 * Default constructor
	 */
	protected TokenRequest() {
		super("<NOCLIENT>");
	}
	
	/**
	 * Full constructor. Sets this TokenRequest's requestParameters map to an unmodifiable version of the one provided.
	 * 
	 * @param requestParameters
	 * @param clientId
	 * @param scope
	 * @param grantType
	 */
	public TokenRequest(Map<String, String> requestParameters, String clientId, Set<String> scope, String grantType) {
		super(clientId);
		setRequestParameters(requestParameters);
		setScope(scope);
		this.grantType = grantType;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public OAuth2Request createOAuth2Request() {
		// Remove password if present to prevent leaks 
		Map<String,String> requestParameters = getRequestParameters();
		HashMap<String, String> modifiable = new HashMap<String, String>(requestParameters);
		modifiable.remove("password");
		return new OAuth2Request(modifiable, this.getClientId(), null, true, this.getScope(), null, null, null);
	}
	
}
