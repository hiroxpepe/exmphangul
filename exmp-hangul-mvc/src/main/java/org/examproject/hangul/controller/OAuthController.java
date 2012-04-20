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

package org.examproject.hangul.controller;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuth;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.examproject.hangul.service.CallbackService;
import org.examproject.hangul.service.OAuthService;
import org.examproject.hangul.value.OAuthAccessorValue;
import org.examproject.hangul.value.OAuthValue;
import org.examproject.hangul.value.TweetCookie;

/**
 * @author hiroxpepe
 */
@Controller
@Scope(value="session")
public class OAuthController {
    
    private static final Log LOG = LogFactory.getLog(OAuthController.class);

    @Inject
    private final HttpServletRequest request = null;

    @Inject
    private final OAuthValue authValue = null;

    @RequestMapping(value="/oauth", method=RequestMethod.GET)
    public String doOAuth(
        Model model
    ) {
        LOG.info("in.");

        OAuthService service = new OAuthService();
        
        String redirectTo = service.execute(
            request.getParameter("dest"),
            request.getRequestURL().toString(),
            authValue
        );
        
        LOG.info("out.");
        
        return "redirect:" + redirectTo;
    }
    
    @RequestMapping(value="/callback", method=RequestMethod.GET)
    public String doCallback(
        HttpServletResponse response,
        Model model
    ) {
        LOG.info("in.");

        // TODO: check for authentication?
        String destUrl = request.getParameter("dest");        
        String requestToken = request.getParameter(OAuth.OAUTH_TOKEN);
        if (requestToken == null) {
            // TODO: part of the authentication check is required!
            return "redirect:" + destUrl;
        }    
        String verifire = request.getParameter(OAuth.OAUTH_VERIFIER);
        if (verifire == null) {
            // TODO: part of the authentication check is required!
            return "redirect:" + destUrl;
        }
        
        CallbackService service = new CallbackService();
        OAuthAccessorValue accessorValue = service.execute(
            request.getRequestURL().toString(),
            requestToken,
            verifire,
            authValue
        );

        // store access token and secret to cookie.
        storeTokenToCookie(
            response,
            accessorValue,
            86400
        );
        
        LOG.info("out.");
        
        return "redirect:/main.html";
    }
    
    private static void storeTokenToCookie(
        HttpServletResponse response,
        OAuthAccessorValue accessorValue,
        int maxAge
    ){        
        Cookie cookie = new Cookie(
            TweetCookie.REQUEST_TOKEN.getName(),
            accessorValue.getRequestToken()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        cookie = new Cookie(
            TweetCookie.ACCESS_TOKEN.getName(),
            accessorValue.getAccessToken()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie( cookie);

        cookie = new Cookie(
            TweetCookie.TOKEN_SECRET.getName(),
            accessorValue.getTokenSecret()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        cookie = new Cookie(
            TweetCookie.USER_ID.getName(),
            accessorValue.getId()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        cookie = new Cookie(
            TweetCookie.SCREEN_NAME.getName(),
            accessorValue.getScreenName()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    
}
