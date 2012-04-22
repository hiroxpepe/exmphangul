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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;

import org.examproject.hangul.dto.TweetDto;
import org.examproject.hangul.value.SettingParamValue;
import org.examproject.hangul.value.TweetAuthValue;

/**
 * @author hiroxpepe
 */
public class TweetService {
 
    private static final Log LOG = LogFactory.getLog(
        TweetService.class
    );
    
    private final TweetAuthValue authValue;
    
    private final SettingParamValue paramValue;
    
    ///////////////////////////////////////////////////////////////////////////
    // constructor
    
    public TweetService(TweetAuthValue authValue){
        this.authValue = authValue;
        this.paramValue = new SettingParamValue("", "");
    }
    
    public TweetService(
        TweetAuthValue authValue,
        SettingParamValue paramValue
    ){
        this.authValue = authValue;
        this.paramValue = paramValue;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    public List<TweetDto> getTweetDtoList() {
        LOG.debug("called.");
        
        // map the value.
        ResponseList responseList = getResponseList();
        List<TweetDto> tweetDtoList = new ArrayList<TweetDto>();
        for (Object response : responseList) {
           Status responseStatus = (Status) response;
           TweetDto tweetDto = new TweetDto();
           tweetDto.setUserProfileImageURL(responseStatus.getUser().getProfileImageURL().toString());
           tweetDto.setUserName(responseStatus.getUser().getScreenName());
           tweetDto.setText(responseStatus.getText());
           tweetDto.setStatusId(String.valueOf(responseStatus.getId()));
           tweetDto.setIsFavorited(responseStatus.isFavorited());
           tweetDtoList.add(tweetDto);
        }
        return tweetDtoList;
    }
    
    public void update(String content) {
        LOG.debug("called.");
        
        // TODO: on error.
        updateStatus(content);
    }
    
    public void favor(long statusId) {
        LOG.debug("called.");
        
        // TODO: on error.
        createFavorite(statusId);
    }
    
    public void retweet(long statusId) {
        LOG.debug("called.");
        
        // TODO: on error.
        retweetStatus(statusId);
    }
    
    public void reply(String content, long statusId) {
        LOG.debug("called.");
        
        // TODO: on error.
        replyStatus(content, statusId);
    }
    
    public List<String> getUserListNameList() {
        LOG.debug("called.");
        
        // TODO: on error.
        List<String> userListNameList = new ArrayList<String>();
        PagableResponseList<UserList> userList = getUserList();
        for (UserList list : userList) {
            if (list.isPublic()) {
                LOG.debug("list FullName: " + list.getFullName());
                userListNameList.add(list.getFullName());
            }
        }
        return userListNameList;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    private Status updateStatus(String content) {
        Twitter twitter = getTwitter();
        Status status = null;
        try {
            status = twitter.updateStatus(content);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }
    
    private Status createFavorite(long statusId) {
        Twitter twitter = getTwitter();
        Status status = null;
        try {
            status = twitter.createFavorite(statusId);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }
    
    // TODO: not yet..?
    private Status destroyFavorite(long statusId) {
        Twitter twitter = getTwitter();
        Status status = null;
        try {
            status = twitter.destroyFavorite(statusId);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }
    
    private Status retweetStatus(long statusId) {
        Twitter twitter = getTwitter();
        Status status = null;        
        try {
            status = twitter.retweetStatus(statusId);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }
    
    private Status replyStatus(String content, long statusId) {
        Twitter twitter = getTwitter();
        Status status = null;
        StatusUpdate statusUpdate = new StatusUpdate(content);
        statusUpdate.setInReplyToStatusId(statusId);
        try {
            status = twitter.updateStatus(statusUpdate);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }
    
    private PagableResponseList<UserList> getUserList() {
        long cursol = -1;
        Twitter twitter = getTwitter();
        try {
            return twitter.getUserLists(twitter.getScreenName(), cursol);
        } catch (TwitterException te) {
            throw new RuntimeException(te);
        }
    }
    
    private ResponseList<Status> getResponseList() {
        
        long cursol = -1;
	int listId = 0;
        Paging paging = new Paging(1);
        
        Twitter twitter = getTwitter();
        
        try {
            // TODO: polymorphism to here? -> plugin.
            
            // home
            if (paramValue.getResponseListMode().equals("home")) {
                return twitter.getHomeTimeline();
            }
            
            // user
            if (paramValue.getResponseListMode().equals("user")) {
                return twitter.getUserTimeline();
            }
            
            // list
            if (paramValue.getResponseListMode().equals("list")) {
                if (paramValue.getUserListName().length() != 0) {      
                    PagableResponseList<UserList> lists = twitter.getUserLists(
                        twitter.getScreenName(),
                        cursol
                    );
                    for (UserList list : lists) {
                        if (list.isPublic()) {
                            String listFullName = list.getFullName();
                            if (listFullName.equals(paramValue.getUserListName())) {
                                listId = list.getId();
                                return twitter.getUserListStatuses(listId, paging);
                            }
                        }
                    }
                }
            }
            
            // default..
            return twitter.getHomeTimeline();
            
        } catch (TwitterException te) {
            // TODO: transition to an error page here?
            throw new RuntimeException(te);
        }
    }
    
    private Twitter getTwitter() {
        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer(
            authValue.getConsumerKey(),
            authValue.getConsumerSecret()
        );
        AccessToken accessToken = new AccessToken(
            authValue.getOauthToken(),
            authValue.getOauthTokenSecret()
        );
        twitter.setOAuthAccessToken(accessToken);
        return twitter;
    }
    
}
