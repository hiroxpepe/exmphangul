/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.examproject.hangul.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.ParameterStyle;
import net.oauth.client.OAuthClient;
import net.oauth.client.OAuthResponseMessage;
import net.oauth.client.URLConnectionClient;
import net.oauth.http.HttpResponseMessage;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.examproject.hangul.util.UrlUtil;
import org.examproject.hangul.value.OAuthAccessorValue;
import org.examproject.hangul.value.OAuthValue;

/**
 * @author hiroxpepe
 */
public class CallbackService {
    
    private static final Log LOG = LogFactory.getLog(CallbackService.class);
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    public OAuthAccessorValue execute(
        String requestUrl,
        String requestToken,
        String verifire,
        OAuthValue authValue
    ) { 
        try {
            OAuthClient client = new OAuthClient(new URLConnectionClient());

            OAuthServiceProvider provider = new OAuthServiceProvider(
                authValue.getRequestTokenUrl(),
                authValue.getAuthorizeUrl(),
                authValue.getAccessTokenUrl()
            );
            
            // get the site domain url.
            String siteDomain = UrlUtil.getDomain(requestUrl);
            LOG.debug("siteDomain: " + siteDomain);

            OAuthConsumer consumer = new OAuthConsumer(
                siteDomain + authValue.getCallbackUrlPath(),
                authValue.getConsumerKey(),
                authValue.getConsumerSecret(),
                provider
            );

            OAuthAccessor accessor = new OAuthAccessor(consumer);

            accessor.requestToken = requestToken;

            try {
                HashMap params = new HashMap();
                params.put(OAuth.OAUTH_VERIFIER, verifire);

                //get access token and secret from twitter.com
                OAuthMessage accessTokenMessage = client.getAccessToken(accessor, "GET", params.entrySet());

                String oauthToken = accessTokenMessage.getParameter("oauth_token");
                String oauthTokenSecret = accessTokenMessage.getParameter("oauth_token_secret");

                LOG.debug("oauth_token: " + oauthToken);
                LOG.debug("oauth_token_secret: " + oauthTokenSecret);
                
            } catch (OAuthException e) {
                throw new RuntimeException("failed to authenticate Twitter account.", e);
            } catch (URISyntaxException e) {
                throw new RuntimeException("failed to authenticate Twitter account.", e);
            }

            //Retrieve user's information
            OAuthMessage authMessage = null;
            try {
                authMessage = accessor.newRequestMessage("GET", "http://api.twitter.com/1/account/verify_credentials.json", null);
            } catch (OAuthException e) {
                throw new RuntimeException("failed to authenticate Twitter account.", e);
            } catch (URISyntaxException e) {
                throw new RuntimeException("failed to authenticate Twitter account.", e);
            }

            OAuthResponseMessage responseMessage = client.access(authMessage, ParameterStyle.AUTHORIZATION_HEADER);
            int status = responseMessage.getHttpResponse().getStatusCode();
            if( status == HttpResponseMessage.STATUS_OK){
                String jsonStr = responseMessage.readBodyAsString();
                JSONObject jsonObject = JSONObject.fromObject(jsonStr);
                
                String userId = jsonObject.optString("id");
                String screenName = jsonObject.optString("screen_name");

                accessor.setProperty("id", userId);
                accessor.setProperty("screen_name", screenName);

            }else{
                throw new RuntimeException("failed to authenticate Twitter account STATUS CODE: " + status);
            }

            // return the value object.      
            return new OAuthAccessorValue(
                accessor.requestToken,
                accessor.accessToken,
                accessor.tokenSecret,
                (String)accessor.getProperty("id"),
                (String)accessor.getProperty("screen_name")
            );
            
        } catch (IOException e) {
            throw new RuntimeException("failed.", e);
        }
    }
    
}
