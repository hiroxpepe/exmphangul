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
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.URLConnectionClient;

import org.examproject.hangul.util.UrlUtil;
import org.examproject.hangul.value.OAuthValue;

/**
 * @author hiroxpepe
 */
public class OAuthService {
      
    public String execute(
        String destUrl,
        String requestUrl,
        OAuthValue authValue
    ) {
        OAuthClient client = new OAuthClient(new URLConnectionClient());

        OAuthServiceProvider provider = new OAuthServiceProvider(
            authValue.getRequestTokenUrl(),
            authValue.getAuthorizeUrl(),
            authValue.getAccessTokenUrl()
        );
        
        // get domain
        String siteDomain = UrlUtil.getDomain(requestUrl);
        System.out.println("siteDomain : " + siteDomain);
    
        OAuthConsumer consumer = new OAuthConsumer(
            siteDomain + authValue.getCallbackUrlPath(),
            authValue.getConsumerKey(),
            authValue.getConsumerSecret(),
            provider
        );

        OAuthAccessor accessor = new OAuthAccessor( consumer);

        String redirectTo = null;
        try{
            try {
                //get request token first from Twitter.com
                HashMap params = new HashMap();
                params.put(
                    "oauth_callback",
                    OAuth.addParameters(
                        accessor.consumer.callbackURL,
                        "dest",
                        destUrl
                    )
                );

                //get request token first from Twitter.com
                client.getRequestToken(accessor, null, params.entrySet());
                
            } catch (OAuthException e) {
                throw new RuntimeException("It failed to authenticate Twitter account", e);
            } catch (URISyntaxException e) {
                throw new RuntimeException("It failed to authenticate Twitter account", e);
            }

            //build redirect path to twitter authentication page
            redirectTo = OAuth.addParameters(
                accessor.consumer.serviceProvider.userAuthorizationURL,
                "oauth_token",
                accessor.requestToken
            );
        } catch (IOException e) {
            throw new RuntimeException("It failed to authenticate Twitter account", e);
        }
     
        return redirectTo;
    }
}
