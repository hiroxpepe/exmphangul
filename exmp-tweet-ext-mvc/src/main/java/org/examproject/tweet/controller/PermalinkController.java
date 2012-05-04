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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.examproject.tweet.dto.ProfileDto;
import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.form.TweetForm;
import org.examproject.tweet.model.ProfileModel;
import org.examproject.tweet.model.TweetModel;
import org.examproject.tweet.service.PermalinkService;
import org.examproject.tweet.service.TweetService;
import org.examproject.tweet.value.OAuthValue;
import org.examproject.tweet.value.SettingParamValue;
import org.examproject.tweet.value.TweetAuthValue;

/**
 * @author hiroxpepe
 */
@Controller
@Scope(value="session")
public class PermalinkController {
    
    private static final Log LOG = LogFactory.getLog(
        PermalinkController.class
    );
    
    private static final String PERMALINK_SERVICE_BEAN_ID = "permalinkService";
    
    private static final String TWEET_AUTH_VALUE_BEAN_ID = "tweetAuthValue";
  
    private static final String SETTING_PARAM_VALUE_BEAN_ID = "settingParamValue";
    
    private static final String TWEET_SERVICE_BEAN_ID = "tweetService";
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final Mapper mapper = null;
    
    @Inject
    private final OAuthValue authValue = null;
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * permalink page request.
     * expected http request is '/tweet/username/2012/05/04.html'
     */
    @RequestMapping(
        value="/tweet/{userName}/{year}/{month}/{day}.html",
        method=RequestMethod.GET
    )
    public String doDatePermalink(
        @PathVariable
        String userName,
        @PathVariable
        String year,
        @PathVariable
        String month,
        @PathVariable
        String day,
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
        try {
            // TODO: debug
            LOG.debug("userName: " + userName);
            LOG.debug("year: " + year);
            LOG.debug("month: " + month);
            LOG.debug("day: " + day);
            
            // get the current local.
            if (locale.equals("")) {
                Locale loc = Locale.getDefault();
                locale = loc.getLanguage();
            }
            
            // get the form.
            TweetForm tweetForm = getForm(
                userId,
                screenName,
                locale,
                responseListMode,
                userListName
            );
            
            // set the form-object to the model. 
            model.addAttribute(tweetForm);
            
            // get the service object.
            PermalinkService permalinkService = (PermalinkService) context.getBean(
                PERMALINK_SERVICE_BEAN_ID
            );
            
            // get the tweet.
            List<TweetDto> tweetDtoList = permalinkService.getTweetListByDate(
                userName,
                Integer.valueOf(year),
                Integer.valueOf(month),
                Integer.valueOf(day)
            );
            LOG.debug("tweetDtoList size: " + tweetDtoList.size());
            
            // map the object.
            List<TweetModel> tweetModelList = new ArrayList<TweetModel>();
            for (TweetDto tweetDto : tweetDtoList) {
                TweetModel tweetModel = context.getBean(TweetModel.class);
                // map the dto-object to the model-object.
                mapper.map(
                    tweetDto,
                    tweetModel
                );
                tweetModelList.add(
                    tweetModel
                );
            }
            
            // set the list-object to the model. 
            model.addAttribute(tweetModelList);
            model.addAttribute(year);
            model.addAttribute(month);
            model.addAttribute(day);
            
            if (isValidParameterOfGet(
                oauthToken,
                oauthTokenSecret,
                userId,
                screenName)
            ) {
                // get the profile.
                ProfileModel profileModel = getProfile(
                    oauthToken,
                    oauthTokenSecret,
                    responseListMode,
                    userListName,
                    screenName
                );
                
                // set the profile model.
                model.addAttribute(profileModel);
            }
            
            // return view name.
            return "permalink";
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return "error";
        } 
    }
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * permalink page request.
     * expected http request is '/tweet/username/2012/05/04.html'
     */
    @RequestMapping(
        value="/tweet/{userName}/{statusId}.html",
        method=RequestMethod.GET
    )
    public String doTweetPermalink(
        @PathVariable
        String userName,
        @PathVariable
        String statusId,
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
        try {
            // TODO: debug
            LOG.debug("userName: " + userName);
            LOG.debug("statusId: " + statusId);
            
            // get the current local.
            if (locale.equals("")) {
                Locale loc = Locale.getDefault();
                locale = loc.getLanguage();
            }
            
            // get the form.
            TweetForm tweetForm = getForm(
                userId,
                screenName,
                locale,
                responseListMode,
                userListName
            );
            
            // set the form-object to the model. 
            model.addAttribute(tweetForm);
            
            // get the service object.
            PermalinkService permalinkService = (PermalinkService) context.getBean(
                PERMALINK_SERVICE_BEAN_ID
            );
            
            // get the tweet.
            TweetDto tweetDto = permalinkService.getTweetByStatusId(
                Long.valueOf(statusId)
            );
            LOG.debug("tweetDto statusId: " + tweetDto.getStatusId());
            
            // map the object.
            List<TweetModel> tweetModelList = new ArrayList<TweetModel>();
            TweetModel tweetModel = context.getBean(TweetModel.class);
            // map the dto-object to the model-object.
            mapper.map(
                tweetDto,
                tweetModel
            );
            tweetModelList.add(
                tweetModel
            );
            
            // set the list-object to the model. 
            model.addAttribute(tweetModelList);
            model.addAttribute("statusId", tweetModel.getStatusId());
            
            if (isValidParameterOfGet(
                oauthToken,
                oauthTokenSecret,
                userId,
                screenName)
            ) {
                // get the profile.
                ProfileModel profileModel = getProfile(
                    oauthToken,
                    oauthTokenSecret,
                    responseListMode,
                    userListName,
                    screenName
                );
                
                // set the profile model.
                model.addAttribute(profileModel);
            }
            
            // return view name.
            return "permalink";
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return "error";
        } 
    }
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * permalink page request.
     * expected http request is '/tweet/username/word.html'
     */
    @RequestMapping(
        value="/word/{userName}/{word}.html",
        method=RequestMethod.GET
    )
    public String doWordPermalink(
        @PathVariable
        String userName,
        @PathVariable
        String word,
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
        try {
            // TODO: debug
            LOG.debug("userName: " + userName);
            LOG.debug("word: " + word);
            
            // get the current local.
            if (locale.equals("")) {
                Locale loc = Locale.getDefault();
                locale = loc.getLanguage();
            }
            
            // get the form.
            TweetForm tweetForm = getForm(
                userId,
                screenName,
                locale,
                responseListMode,
                userListName
            );
            
            // set the form-object to the model. 
            model.addAttribute(tweetForm);
            
            // get the service object.
            PermalinkService permalinkService = (PermalinkService) context.getBean(
                PERMALINK_SERVICE_BEAN_ID
            );
            
            // get the tweet.
            List<TweetDto> tweetDtoList = permalinkService.getTweetListByWord(
                userName,
                word
            );
            LOG.debug("tweetDtoList size: " + tweetDtoList.size());
            
            // map the object.
            List<TweetModel> tweetModelList = new ArrayList<TweetModel>();
            for (TweetDto tweetDto : tweetDtoList) {
                TweetModel tweetModel = context.getBean(TweetModel.class);
                // map the dto-object to the model-object.
                mapper.map(
                    tweetDto,
                    tweetModel
                );
                tweetModelList.add(
                    tweetModel
                );
            }
            
            // set the list-object to the model. 
            model.addAttribute(tweetModelList);
            model.addAttribute(word);
            
            if (isValidParameterOfGet(
                oauthToken,
                oauthTokenSecret,
                userId,
                screenName)
            ) {
                // get the profile.
                ProfileModel profileModel = getProfile(
                    oauthToken,
                    oauthTokenSecret,
                    responseListMode,
                    userListName,
                    screenName
                );
                
                // set the profile model.
                model.addAttribute(profileModel);
            }
            
            // return view name.
            return "permalink";
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return "error";
        } 
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    private TweetForm getForm(
        String userId,
        String screenName,
        String locale,
        String responseListMode,
        String userListName
    ) {
        // create the form-object.
        TweetForm tweetForm = new TweetForm();
        
        // set the cookie value to the form-object.
        tweetForm.setUserId(userId);
        tweetForm.setScreenName(screenName);
        tweetForm.setLocale(locale);
        tweetForm.setResponseListMode(responseListMode);
        tweetForm.setUserListName(userListName);
        
        return tweetForm;
    }
    
    private ProfileModel getProfile(
            String oauthToken,
            String oauthTokenSecret,
            String responseListMode,
            String userListName,
            String screenName
    ) throws MappingException {
        
        // get the service object.
        TweetService tweetService = (TweetService) context.getBean(
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
        ProfileDto profileDto = tweetService.getProfile(screenName);
        ProfileModel profileModel = context.getBean(ProfileModel.class);
        mapper.map(
            profileDto,
            profileModel
        );
        
        return profileModel;
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
    
}
