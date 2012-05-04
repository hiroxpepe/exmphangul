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
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.examproject.tweet.dto.CalendarDto;
import org.examproject.tweet.dto.TagcrowdDto;
import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.model.CalendarModel;
import org.examproject.tweet.model.TagcrowdModel;
import org.examproject.tweet.model.TweetModel;
import org.examproject.tweet.response.CalendarResponse;
import org.examproject.tweet.response.TagcrowdResponse;
import org.examproject.tweet.response.TweetResponse;
import org.examproject.tweet.service.CalendarService;
import org.examproject.tweet.service.RecentService;
import org.examproject.tweet.service.TagcrowdService;

/**
 * @author hiroxpepe
 */
@Controller
@Scope(value="session")
public class ExtensionController {
    
    private static final Log LOG = LogFactory.getLog(
        ExtensionController.class
    );
    
    private static final String CALENDAR_SERVICE_BEAN_ID = "calendarService";
                
    private static final String CALENDAR_RESPONSE_BEAN_ID = "calendarResponse";
    
    private static final String TAGCROWD_SERVICE_BEAN_ID = "tagcrowdService";
                
    private static final String TAGCROWD_RESPONSE_BEAN_ID = "tagcrowdResponse";
    
    private static final String RECENT_SERVICE_BEAN_ID = "recentService";
    
    private static final String RECENT_RESPONSE_BEAN_ID = "tweetResponse";
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final Mapper mapper = null;

    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the calendar.
     * expected ajax http request is '/calendar.html'
     */
    @RequestMapping(
        value="/calendar",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody CalendarResponse doCalendar(
        @RequestParam(value="user_id", defaultValue="")
        String requestUserId,
        @RequestParam(value="year", defaultValue="")
        String year,
        @RequestParam(value="month", defaultValue="")
        String month,
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
        try {
            // TODO: debug
            LOG.debug("screenName: " + screenName);
            LOG.debug("user_id: " + requestUserId);
            LOG.debug("year: " + year);
            LOG.debug("month: " + month);
            
            // get the service object.
            CalendarService service = (CalendarService) context.getBean(
                CALENDAR_SERVICE_BEAN_ID
            );
            
            // get the calendar.
            List<CalendarDto> calendarDtoList = service.getList(
                screenName,
                Integer.valueOf(year),
                Integer.valueOf(month)
            );

            // map the object.
            List<CalendarModel> calendarModelList=  new ArrayList<CalendarModel>();
            for (CalendarDto calendarDto : calendarDtoList) {
                CalendarModel calendarModel = context.getBean(CalendarModel.class);
                // map the form-object to the dto-object.
                mapper.map(
                    calendarDto,
                    calendarModel
                );
                calendarModelList.add(
                    calendarModel
                );
            }

            // return the response object.
            return new CalendarResponse(
                calendarModelList
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (CalendarResponse) context.getBean(
                CALENDAR_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        } 
    }
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the tagcrowd.
     * expected ajax http request is '/tagcrowd.html'
     */
    @RequestMapping(
        value="/tagcrowd",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TagcrowdResponse doTagcrowd(
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
        try {
            // TODO: debug
            LOG.debug("screenName: " + screenName);
            
            // get the service object.
            TagcrowdService service = (TagcrowdService) context.getBean(
                TAGCROWD_SERVICE_BEAN_ID
            );
            
            // get the tagcrowd.
            List<TagcrowdDto> tagcrowdDtoList = service.getList(
                screenName
            );

            // map the object.
            List<TagcrowdModel> tagcrowdModelList=  new ArrayList<TagcrowdModel>();
            for (TagcrowdDto tagcrowdDto : tagcrowdDtoList) {
                TagcrowdModel tagcrowdModel = context.getBean(TagcrowdModel.class);
                // map the form-object to the dto-object.
                mapper.map(
                    tagcrowdDto,
                    tagcrowdModel
                );
                tagcrowdModelList.add(
                    tagcrowdModel
                );
            }

            // return the response object.
            return new TagcrowdResponse(
                tagcrowdModelList
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TagcrowdResponse) context.getBean(
                TAGCROWD_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        } 
    }
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the recent.
     * expected ajax http request is '/recent.html'
     */
    @RequestMapping(
        value="/recent",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TweetResponse doRecent(
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
        try {
            // TODO: debug
            LOG.debug("screenName: " + screenName);
            
            // get the service object.
            RecentService service = (RecentService) context.getBean(
                RECENT_SERVICE_BEAN_ID
            );
            
            // get the recent tweet.
            List<TweetDto> tweetDtoList=  service.getList(
                screenName
            );

            // map the object.
            List<TweetModel> tweetModelList=  new ArrayList<TweetModel>();
            for (TweetDto tweetDto : tweetDtoList) {
                TweetModel tweetModel = context.getBean(TweetModel.class);
                // map the form-object to the dto-object.
                mapper.map(
                    tweetDto,
                    tweetModel
                );
                tweetModelList.add(
                    tweetModel
                );
            }

            // return the response object.
            return new TweetResponse(
                tweetModelList
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TweetResponse) context.getBean(
                RECENT_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        } 
    }
    
}