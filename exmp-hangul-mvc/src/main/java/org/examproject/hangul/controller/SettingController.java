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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.examproject.hangul.form.TweetForm;
import org.examproject.hangul.value.TweetCookie;

/**
 * @author hiroxpepe
 */
@Controller
@Scope(value="session")
public class SettingController {
    
    private static final Log LOG = LogFactory.getLog(
        SettingController.class
    );
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    @RequestMapping(
        value="/setting",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public String doSetting(
        @RequestBody
        TweetForm tweetForm,
        HttpServletResponse response
    ) {
        LOG.debug("in.");

        // store access token and secret to cookie.
        storeTokenToCookie(
            tweetForm,
            response,
            604800
        );
        
        LOG.debug("out.");
        
        return "redirect:/index.html";
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    private static void storeTokenToCookie(
        TweetForm tweetForm,
        HttpServletResponse response,
        int maxAge
    ){        
        Cookie cookie = new Cookie(
            TweetCookie.USER_LIST_NAME.getName(),
            tweetForm.getUserListName()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

        cookie = new Cookie(
            TweetCookie.RESPONSE_LIST_MODE.getName(),
            tweetForm.getResponseListMode()
        );
        cookie.setMaxAge(maxAge);
        response.addCookie( cookie);
    }
    
}
