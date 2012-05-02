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

package org.examproject.tweet.controller;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import org.examproject.tweet.dto.ProfileDto;
import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.form.TweetForm;
import org.examproject.tweet.model.ProfileModel;
import org.examproject.tweet.model.TweetModel;
import org.examproject.tweet.response.TweetResponse;
import org.examproject.tweet.service.TweetService;
import org.examproject.tweet.value.OAuthValue;
import org.examproject.tweet.value.SettingParamValue;
import org.examproject.tweet.value.TweetAuthValue;
import org.examproject.tweet.value.TweetCookie;

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
  
    private static final String SETTING_PARAM_VALUE_BEAN_ID = "settingParamValue";
    
    private static final String TWEET_SERVICE_BEAN_ID = "tweetService";
                
    private static final String TWEET_RESPONSE_BEAN_ID = "tweetResponse";
    
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
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName,
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
            
            // create the form-object.
            TweetForm tweetForm = new TweetForm();
            
            // set the cookie value to the form-object.
            tweetForm.setUserId(userId);
            tweetForm.setScreenName(screenName);
            tweetForm.setLocale(locale);
            tweetForm.setResponseListMode(responseListMode);
            tweetForm.setUserListName(userListName);
            
            // set the form-object to the model. 
            model.addAttribute(tweetForm);
            
            ///////////////////////////////////////////////////////////////////
            // TODO: add to get the profile.
            
            if (isValidParameterOfGet(
                oauthToken,
                oauthTokenSecret,
                userId,
                screenName)
            ) {
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
                    ),
                    // get the setting value object.
                    (SettingParamValue) context.getBean(
                        SETTING_PARAM_VALUE_BEAN_ID,
                        responseListMode,
                        userListName
                    )
                );
                
                // get the dto-object and map to the model-object.
                ProfileDto profileDto = service.getProfile(screenName);
                ProfileModel profileModel = context.getBean(ProfileModel.class);
                mapper.map(
                    profileDto,
                    profileModel
                );
                
                // set the profile model.
                model.addAttribute(profileModel);
            }
            // normally, move to this view.
            return null;
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            TweetResponse response = (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
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
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName,
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

            // create the form-object.
            TweetForm tweetForm = new TweetForm();
            
            // set the cookie value to the form-object.
            tweetForm.setUserId(userId);
            tweetForm.setScreenName(screenName);
            tweetForm.setLocale(locale);
            tweetForm.setResponseListMode(responseListMode);
            tweetForm.setUserListName(userListName);

            // set the form-object to the model. 
            model.addAttribute(tweetForm);

            // normally, move to this view.
            return null;
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            TweetResponse response = (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
            model.addAttribute(response);
            return "error";
        } 
    }
    
    @RequestMapping(
        value="/list",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public @ResponseBody TweetResponse doList(
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
        String screenName,
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName
    ) {        
        LOG.debug("called.");
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, "mock")) {
                doAuthenticationIsInvalid();
            }

            // check the user id.
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
                ),
                // get the setting value object.
                (SettingParamValue) context.getBean(
                    SETTING_PARAM_VALUE_BEAN_ID,
                    responseListMode,
                    userListName
                )
            );
            
            // return the response object.
            return list(
                service
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        }
    }
    
    @RequestMapping(
        value="/update",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TweetResponse doUpdate(
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
        String screenName,
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName
    ) {        
        LOG.debug("called.");
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, content)) {
                return doAuthenticationIsInvalid();
            }

            // check the user id.
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
                ),
                // get the setting value object.
                (SettingParamValue) context.getBean(
                    SETTING_PARAM_VALUE_BEAN_ID,
                    responseListMode,
                    userListName
                )
            );
            
            // update the cpntent.
            return update(
                content,
                service 
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        }
    }
    
    @RequestMapping(
        value="/delete",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TweetResponse doDelete(
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
        String screenName,
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName
    ) {        
        LOG.debug("called.");
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, "mock")) {
                doAuthenticationIsInvalid();
            }

            // check the user id.
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
                ),
                // get the setting value object.
                (SettingParamValue) context.getBean(
                    SETTING_PARAM_VALUE_BEAN_ID,
                    responseListMode,
                    userListName
                )
            );

            // delete the cpntent.
            return delete(
                Long.parseLong(statusId),
                service
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        }
    }
    
    @RequestMapping(
        value="/reply",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TweetResponse doReply(
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
        String screenName,
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName
    ) {        
        LOG.debug("called.");
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, content)) {
                doAuthenticationIsInvalid();
            }

            // check the user id.
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
                ),
                // get the setting value object.
                (SettingParamValue) context.getBean(
                    SETTING_PARAM_VALUE_BEAN_ID,
                    responseListMode,
                    userListName
                )
            );

            // reply the cpntent.
            return reply(
                content,
                Long.parseLong(statusId),
                service
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        }
    }
    
    @RequestMapping(
        value="/favorite",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TweetResponse doFavorite(
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
        String screenName,
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName
    ) {        
        LOG.debug("called.");
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, "mock")) {
                doAuthenticationIsInvalid();
            }

            // check the user id.
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
                ),
                // get the setting value object.
                (SettingParamValue) context.getBean(
                    SETTING_PARAM_VALUE_BEAN_ID,
                    responseListMode,
                    userListName
                )
            );

            // favorite the statusId.
            return favorite(
                Long.parseLong(statusId),
                service
            );
        
        } catch (Exception e) {
            LOG.fatal(e.getMessage());
            return (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        }
    }
    
    @RequestMapping(
        value="/retweet",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TweetResponse doRetweet(
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
        String screenName,
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName
    ) {        
        LOG.debug("called.");    
        debugOut(oauthToken, oauthTokenSecret, userId, screenName);
        
        try {
            if (!isValidParameterOfTweet(oauthToken, oauthTokenSecret, "mock")) {
                doAuthenticationIsInvalid();
            }

            // check the user id.
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
                ),
                // get the setting value object.
                (SettingParamValue) context.getBean(
                    SETTING_PARAM_VALUE_BEAN_ID,
                    responseListMode,
                    userListName
                )
            );
            
            // retweet the id.
            service.retweet(
                Long.parseLong(statusId)
            );
            
            // return the response object.
            return (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                false,
                "retweet complete."
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TweetResponse) context.getBean(
                TWEET_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
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
        Cookie cookie = new Cookie(TweetCookie.REQUEST_TOKEN.getName(), "");
        response.addCookie(cookie);
        cookie = new Cookie(TweetCookie.ACCESS_TOKEN.getName(), "");
        response.addCookie( cookie);
        cookie = new Cookie(TweetCookie.TOKEN_SECRET.getName(), "");
        response.addCookie(cookie);
        cookie = new Cookie(TweetCookie.USER_ID.getName(), "");
        response.addCookie(cookie);
        cookie = new Cookie(TweetCookie.SCREEN_NAME.getName(), "");
        response.addCookie(cookie);
        cookie = new Cookie(TweetCookie.USER_LIST_NAME.getName(), "");
        response.addCookie(cookie);
        cookie = new Cookie(TweetCookie.RESPONSE_LIST_MODE.getName(), "");
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
    /**
     * if an error is occured, this method will be called.
     */
    @ExceptionHandler
    @ResponseBody
    public TweetResponse handleException(
        Exception e
    ) {
        LOG.debug("called");
        LOG.fatal(e.getMessage());
        return (TweetResponse) context.getBean(
            TWEET_RESPONSE_BEAN_ID,
            true,
            e.getMessage()
        );
    } 

    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    private TweetResponse list(
        TweetService service
    ) {
        // get the timeline.
        List<TweetDto> tweetDtoList = service.getList("");
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
        
        // create the response object.
        TweetResponse response = new TweetResponse(
            tweetModelList
        );
        
        // add the twitter userList names.
        response.setUserListNameList(
            service.getUserListNameList()
        );
        
        return response;
    }
    
    private TweetResponse update(
        String content,
        TweetService service
    ) {
        // updata and get the timeline.
        List<TweetDto> tweetDtoList = service.update(
            content
        );
        
        // map the object.
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
        
        // create the response object.
        TweetResponse response = new TweetResponse(
            tweetModelList
        );
        
        // add the twitter userList names.
        response.setUserListNameList(
            service.getUserListNameList()
        );
        
        return response;
    }
    
    private TweetResponse delete(
        Long statusId,
        TweetService service
    ) {
        // delete and get the timeline.
        List<TweetDto> tweetDtoList = service.delete(
            statusId
        );
        
        // map the object.
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
        
        // create the response object.
        TweetResponse response = new TweetResponse(
            tweetModelList
        );
        
        // add the twitter userList names.
        response.setUserListNameList(
            service.getUserListNameList()
        );
        
        return response;
    }
    
    private TweetResponse reply(
        String content,
        Long statusId,
        TweetService service
    ) {
        // reply and get the timeline.
        List<TweetDto> tweetDtoList = service.reply(
            content,
            statusId
        );
        
        // map the object.
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
        
        // create the response object.
        TweetResponse response = new TweetResponse(
            tweetModelList
        );
        
        // add the twitter userList names.
        response.setUserListNameList(
            service.getUserListNameList()
        );
        
        return response;
    }
    
    private TweetResponse favorite(
        Long statusId,
        TweetService service
    ) {
        // delete and get the timeline.
        List<TweetDto> tweetDtoList = service.favorite(
            statusId
        );
        
        // map the object.
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
        
        // create the response object.
        TweetResponse response = new TweetResponse(
            tweetModelList
        );
        
        // add the twitter userList names.
        response.setUserListNameList(
            service.getUserListNameList()
        );
        
        return response;
    }
    
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

    private TweetResponse doAuthenticationIsInvalid() {
        String msg = "is not a valid authentication.";
        LOG.warn(msg);
        TweetResponse response = (TweetResponse) context.getBean(
            TWEET_RESPONSE_BEAN_ID,
            true,
            msg
        );
        return response;
    }
    
    private TweetResponse doUserIdIsInvalid() {
        String msg = "a use the user id is invalid.";
        LOG.warn(msg);
        TweetResponse response = (TweetResponse) context.getBean(
            TWEET_RESPONSE_BEAN_ID,
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