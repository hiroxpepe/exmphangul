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

package org.examproject.tweet.aspect;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.entity.Tweet;
import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.repository.TweetRepository;
import org.examproject.tweet.service.TweetService;

/**
 * @author hiroxpepe
 */
@Aspect
public class TweetServiceAspect {
    
    private static final Log LOG = LogFactory.getLog(
        TweetServiceAspect.class
    );
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final TweetRepository repository = null;
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    @Before("execution(* org.examproject.tweet.service.TweetService.update(..))")
    public void updateBefore(JoinPoint jp) {
        LOG.debug("called.");
        
        Object args[] = jp.getArgs();
        String content = (String) args[0];
        LOG.debug("content: " + content);
    }
    
    @After("execution(* org.examproject.tweet.service.TweetService.update(..))")
    public void updateAfter(JoinPoint jp) {
        LOG.debug("called.");
        
        TweetService service = (TweetService) jp.getThis();
        TweetDto tweetDto = service.getCurrent();
        Tweet tweet = context.getBean(Tweet.class);
        tweet.setId(Long.valueOf(tweetDto.getStatusId()));
        tweet.setDate(tweetDto.getCreated());
        tweet.setName(tweetDto.getUserName());
        tweet.setText(tweetDto.getText());
        repository.save(tweet);
    }
    
}