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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.examproject.tweet.service.TweetService;

/**
 * @author hiroxpepe
 */
@Aspect
public class TweetServiceAspect {
    
    private static final Log LOG = LogFactory.getLog(
        TweetServiceAspect.class
    );
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    @Before("execution(* org.examproject.tweet.service.TweetService.update(..))")
    public void updateBefore(JoinPoint jp) {
        LOG.debug("update() is running!");
        LOG.debug("hijacked: " + jp.getSignature().getName());
        
        Object args[] = jp.getArgs();
        String content = (String) args[0];
        LOG.debug("content: " + content);
    }
}