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
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import org.examproject.hangul.dto.TweetDto;
import org.examproject.hangul.value.TweetAuthValue;

/**
 * @author hiroxpepe
 */
public class TweetService {
 
    private static final Log LOG = LogFactory.getLog(TweetService.class);
    
    private final TweetAuthValue authValue;
    
    ///////////////////////////////////////////////////////////////////////////
    // constructor
    
    public TweetService(TweetAuthValue authValue){
        this.authValue = authValue;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    public List<TweetDto> getTweetList() {
        
        // map the value.
        ResponseList responseList = getResponseList();
        List<TweetDto> tweetList = new ArrayList<TweetDto>();
        for (Object response : responseList) {
           Status responseStatus = (Status) response;
           TweetDto tweet = new TweetDto();
           tweet.setUserProfileImageURL(responseStatus.getUser().getProfileImageURL().toString());
           tweet.setUserName(responseStatus.getUser().getScreenName());
           tweet.setText(responseStatus.getText());
           tweet.setStatusId(String.valueOf(responseStatus.getId()));
           tweet.setIsFavorited(responseStatus.isFavorited());
           tweetList.add(tweet);
        }
        return tweetList;
    }
    
    public void update(String content) {
        // TODO: on error.
        updateStatus(content);
    }
    
    public void favor(long statusId) {
        // TODO: on error.
        createFavorite(statusId);
    }
    
    public void retweet(long statusId) {
        // TODO: on error.
        retweetStatus(statusId);
    }
    
    public void reply(String content, long statusId) {
        // TODO: on error.
        replyStatus(content, statusId);
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
    
    ///////////////////////////////////////////////////////////////////////////
    // TODO:
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
    
    // TODO: not yet..
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
    
    ///////////////////////////////////////////////////////////////////////////
    
    private ResponseList getResponseList() {
        
        Twitter twitter = getTwitter();
        
        ResponseList responseList;
        try {
            // TODO: polymorphism to here?
            responseList = twitter.getFriendsTimeline();
        } catch (TwitterException te) {
            // TODO: transition to an error page here?
            throw new RuntimeException(te);
        }
        return responseList;
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
