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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import org.examproject.hangul.dto.TweetDto;
import org.examproject.hangul.form.TweetForm;
import org.examproject.hangul.model.TweetModel;
import org.examproject.hangul.response.AjaxResponse;
import org.examproject.hangul.service.TweetService;
import org.examproject.hangul.value.OAuthValue;
import org.examproject.hangul.value.TweetAuthValue;
import org.examproject.hangul.value.TweetCookie;

/**
 * @author hiroxpepe
 */
@Controller
@Scope(value="session")
public class TweetController {
    
    private static final Log LOG = LogFactory.getLog(
        TweetController.class
    );

    private static final String TWEET_AUTH_VALUE_BEAN_ID = "tweetAuthValue";
    
    private static final String TWEET_SERVICE_BEAN_ID = "tweetService";
                
    private static final String AJAX_RESPONSE_BEAN_ID = "ajaxResponse";
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final Mapper mapper = null;
    
    @Inject
    private final OAuthValue authValue = null;

    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    @RequestMapping(
        value="/index",
        method=RequestMethod.GET
    )
    public String getForm(
        @RequestParam(value="locale", defaultValue="")
        String locale,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName,
        Model model
     ) {
        LOG.debug("called.");
        
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {            
            // get the current local.
            if (locale.equals("")) {
                Locale loc = Locale.getDefault();
                locale = loc.getLanguage();
            }

            // set the user id to form-object.
            TweetForm tweetForm = new TweetForm();
            tweetForm.setUserId(userId);
            
            // set the value of local.
            tweetForm.setLocale(locale);

            // set object to model.
            model.addAttribute(tweetForm);

            // normally, move to this view.
            return null;
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            AjaxResponse response = (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            model.addAttribute(response);
            return "error";
        } 
    }
    
    @RequestMapping(
        value="/login",
        method=RequestMethod.GET
    )
    public String doLogin(
        @RequestParam(value="locale", defaultValue="")
        String locale,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName,
        Model model
     ) {
        LOG.debug("called.");
        
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {            
            if (!isValidParameterOfGet(oauthToken, oauthTokenSecret, userId, screenName)) {            
                String msg = "is not a valid authentication.";
                LOG.info(msg);
                AjaxResponse response = (AjaxResponse) context.getBean(
                    AJAX_RESPONSE_BEAN_ID,
                    true,
                    msg
                );
                model.addAttribute(response);
                return "redirect:/login.html" + "?locale=" + locale;
            }
            
            // get the current local.
            if (locale.equals("")) {
                Locale loc = Locale.getDefault();
                locale = loc.getLanguage();
            }

            // set the user id to form-object.
            TweetForm tweetForm = new TweetForm();
            tweetForm.setUserId(userId);
            
            // set the value of local.
            tweetForm.setLocale(locale);

            // set object to model.
            model.addAttribute(tweetForm);

            // normally, move to this view.
            return null;
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            AjaxResponse response = (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            model.addAttribute(response);
            return "error";
        } 
    }
    
    @RequestMapping(
        value="/tweet",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody AjaxResponse doTweet(
        @RequestParam(value="tweet", defaultValue="")
        String content,
        @RequestParam(value="user_id", defaultValue="")
        String requestUserId,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName
    ) {        
        LOG.debug("called.");
        
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, content)) {
                return doAuthenticationIsInvalid();
            }

            // a check the user id.
            if (!requestUserId.equals(userId)) {
                return doUserIdIsInvalid();
            }

            // get the service object.
            TweetService service = (TweetService) context.getBean(
                TWEET_SERVICE_BEAN_ID,
                // get the authentication value object.
                (TweetAuthValue) context.getBean(
                    TWEET_AUTH_VALUE_BEAN_ID,
                    authValue.getConsumerKey(),
                    authValue.getConsumerSecret(),
                    oauthToken,
                    oauthTokenSecret
                )
            );

            // TODO: on error..
            service.update(
                content
            );

            // get the timeline.
            List<TweetDto> tweetDtoList = service.getTweetList();
            List<TweetModel> tweetModelList = new ArrayList<TweetModel>();
            for (TweetDto tweetDto : tweetDtoList) {
                TweetModel tweetModel = context.getBean(TweetModel.class);
                // map the form-object to the dto-object.
                mapper.map(
                    tweetDto,
                    tweetModel
                );
                tweetModelList.add(tweetModel);
            }

            // return the response object.
            AjaxResponse response = new AjaxResponse(
                tweetModelList
            );
            return response;
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            AjaxResponse response = (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            return response;
        }
    }
    
    @RequestMapping(
        value="/list",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public @ResponseBody AjaxResponse getTimeLine(
        @RequestParam(value="user_id", defaultValue="")
        String requestUserId,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName
    ) {        
        LOG.debug("called.");
        
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, "mock")) {
                doAuthenticationIsInvalid();
            }

            // a check the user id.
            if (!requestUserId.equals(userId)) {
                return doUserIdIsInvalid();
            }

            // get the value object for authentication.
            TweetAuthValue tweetAuthValue = new TweetAuthValue(
                authValue.getConsumerKey(),
                authValue.getConsumerSecret(),
                oauthToken,
                oauthTokenSecret
            );

            // get the service object.
            TweetService service = new TweetService(
                tweetAuthValue
            );

            // get the timeline.
            List<TweetDto> tweetDtoList = service.getTweetList();
            List<TweetModel> tweetModelList = new ArrayList<TweetModel>();
            for (TweetDto tweetDto : tweetDtoList) {
                TweetModel tweetModel = context.getBean(TweetModel.class);
                // map the form-object to the dto-object.
                mapper.map(
                    tweetDto,
                    tweetModel
                );
                tweetModelList.add(tweetModel);
            }
            
            // return the response object.
            AjaxResponse response = new AjaxResponse(
                tweetModelList
            );
            return response;
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            AjaxResponse response = (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            return response;
        }
    }
    
    @RequestMapping(
        value="/favor",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody AjaxResponse doFavor(
        @RequestParam(value="user_id", defaultValue="")
        String requestUserId,
        @RequestParam(value="status_id", defaultValue="")
        String statusId,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName
    ) {        
        LOG.debug("called.");
        
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, "mock")) {
                doAuthenticationIsInvalid();
            }

            // a check the user id.
            if (!requestUserId.equals(userId)) {
                return doUserIdIsInvalid();
            }

            // get the value object for authentication.
            TweetAuthValue tweetAuthValue = new TweetAuthValue(
                authValue.getConsumerKey(),
                authValue.getConsumerSecret(),
                oauthToken,
                oauthTokenSecret
            );

            // get the service object.
            TweetService service = new TweetService(
                tweetAuthValue
            );

            // TODO: on error..
            service.favor(
                Long.parseLong(statusId)
            );
            
            // return the response object.
            return (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                false,
                "favor."
            );
        
        } catch (Exception e) {
            LOG.fatal(e.getMessage());
            AjaxResponse response = (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            return response;
        }
    }
    
    @RequestMapping(
        value="/retweet",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody AjaxResponse doRetweet(
        @RequestParam(value="user_id", defaultValue="")
        String requestUserId,
        @RequestParam(value="status_id", defaultValue="")
        String statusId,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName
    ) {        
        LOG.debug("called.");
        
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, "mock")) {
                doAuthenticationIsInvalid();
            }

            // a check the user id.
            if (!requestUserId.equals(userId)) {
                return doUserIdIsInvalid();
            }

            // get the value object for authentication.
            TweetAuthValue tweetAuthValue = new TweetAuthValue(
                authValue.getConsumerKey(),
                authValue.getConsumerSecret(),
                oauthToken,
                oauthTokenSecret
            );

            // get the service object.
            TweetService service = new TweetService(
                tweetAuthValue
            );

            // TODO: on error..
            service.retweet(
                Long.parseLong(statusId)
            );
            
            // return the response object.
            return (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                false,
                "retweet."
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            AjaxResponse response = (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            return response;
        }
    }
    
    @RequestMapping(
        value="/reply",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody AjaxResponse doReply(
        @RequestParam(value="tweet", defaultValue="")
        String content,
        @RequestParam(value="user_id", defaultValue="")
        String requestUserId,
        @RequestParam(value="status_id", defaultValue="")
        String statusId,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName
    ) {        
        LOG.debug("called.");
        
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, content)) {
                doAuthenticationIsInvalid();
            }

            // a check the user id.
            if (!requestUserId.equals(userId)) {
                return doUserIdIsInvalid();
            }

            // get the value object for authentication.
            TweetAuthValue tweetAuthValue = new TweetAuthValue(
                authValue.getConsumerKey(),
                authValue.getConsumerSecret(),
                oauthToken,
                oauthTokenSecret
            );

            // get the service object.
            TweetService service = new TweetService(
                tweetAuthValue
            );

            // TODO: on error..
            service.reply(
                content,
                Long.parseLong(statusId)
            );

            // get the timeline.
            List<TweetDto> tweetDtoList = service.getTweetList();
            List<TweetModel> tweetModelList = new ArrayList<TweetModel>();
            for (TweetDto tweetDto : tweetDtoList) {
                TweetModel tweetModel = context.getBean(TweetModel.class);
                // map the form-object to the dto-object.
                mapper.map(
                    tweetDto,
                    tweetModel
                );
                tweetModelList.add(tweetModel);
            }

            // return the response object.
            AjaxResponse response = new AjaxResponse(
                tweetModelList
            );
            return response;
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            AjaxResponse response = (AjaxResponse) context.getBean(
                AJAX_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            return response;
        }
    }
    
    @RequestMapping(
        value="/logout",
        method=RequestMethod.GET        
    )
    public String doLogout(
        HttpServletResponse response,
        SessionStatus sessionStatus,
        Model model
    ) {
        // TODO: logout..
        Cookie cookie = new Cookie(TweetCookie.USER_ID.getName(), "");
        response.addCookie(cookie);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @RequestMapping(
        value="/error",
        method=RequestMethod.GET
    )
    public String doError(
        Model model
    ) {
        return "error";
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    // check the parameter.
    private boolean isValidParameterOfGet(
        String oauthToken,
        String oauthTokenSecret,
        String userId,
        String screenName
    ) {
        if ((oauthToken == null || oauthToken.equals("")) ||
           (oauthTokenSecret == null || oauthTokenSecret.equals("")) ||
           (userId == null || userId.equals("")) ||
           (screenName == null || screenName.equals(""))) {
            return false;
        }
        return true;
    }
    
    // check the parameter.
    private boolean isValidParameterOfTweet(
        String oauthToken,
        String oauthTokenSecret,
        String content
    ) {
        if ((oauthToken == null || oauthToken.equals("")) ||
            (oauthTokenSecret == null || oauthTokenSecret.equals("")) ||
            (content == null || content.equals(""))) {
            return false;
        }
        return true;
    }

    private AjaxResponse doAuthenticationIsInvalid() {
        String msg = "is not a valid authentication.";
        LOG.warn(msg);
        AjaxResponse response = (AjaxResponse) context.getBean(
            AJAX_RESPONSE_BEAN_ID,
            true,
            msg
        );
        return response;
    }
    
    private AjaxResponse doUserIdIsInvalid() {
        String msg = "a use the user id is invalid.";
        LOG.warn(msg);
        AjaxResponse response = (AjaxResponse) context.getBean(
            AJAX_RESPONSE_BEAN_ID,
            true,
            msg
        );
        return response;
    }
    
    private void debugOut(
        String oauthToken,
        String oauthTokenSecret,
        String userId,
        String screenName
    ) {
        LOG.debug("oauth_token: " + oauthToken);
        LOG.debug("oauth_token_secret: " + oauthTokenSecret);
        LOG.debug("user_id: " + userId);
        LOG.debug("screen_name: " + screenName);
    }
    
}