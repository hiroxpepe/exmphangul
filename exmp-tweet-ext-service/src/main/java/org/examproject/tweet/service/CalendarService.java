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

package org.examproject.tweet.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.collections.Transformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.dto.CalendarDto;
import org.examproject.tweet.entity.Tweet;
import org.examproject.tweet.repository.TweetRepository;
import org.examproject.tweet.util.DateValue;
import org.examproject.tweet.util.MonthBeginDateTransformer;
import org.examproject.tweet.util.MonthEndDateTransformer;

/**
 * @author hiroxpepe
 */
public class CalendarService {
 
    private static final Log LOG = LogFactory.getLog(
        CalendarService.class
    );
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final TweetRepository tweetRepository = null;
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    public List<CalendarDto> getList(
        String username,
        int year,
        int month
    ) {
        LOG.debug("called.");
        try {
            // create a date conditions.
            Transformer beginDateTransformer = (Transformer) new MonthBeginDateTransformer();
            Transformer endDateTransformer = (Transformer) new MonthEndDateTransformer();
            DateValue dateValue = new DateValue(year, month, 1);
            Date begin = (Date) beginDateTransformer.transform(dateValue);
            Date end = (Date) endDateTransformer.transform(dateValue);
            
            // get the tweet list.
            List<CalendarDto> calendarDtoList = new ArrayList<CalendarDto>();
            List<Tweet> tweetList = tweetRepository.findByNameAndDateBetween(
                username,
                begin,
                end
            );
            LOG.debug("calendar tweet day count: " + tweetList.size());
            
            // create values for the month. 
            for (int i = 0; i < 31; i++) {
                CalendarDto calendarDto = context.getBean(CalendarDto.class);
                calendarDto.setDay(i + 1);
                calendarDtoList.add(calendarDto);
            }
            
            // check the existence of tweet.
            SimpleDateFormat dateFormat = new SimpleDateFormat("d");
            SimpleDateFormat linkDateFormat = new SimpleDateFormat("/yyyy/MM/dd");
            for (Tweet tweet : tweetList) {
                int dIdx = Integer.parseInt(dateFormat.format(tweet.getDate()));
                CalendarDto calendarDto = calendarDtoList.get(dIdx - 1);
                calendarDto.setDate(tweet.getDate());
                calendarDto.setLinkUrl(
                    "/tweet/" + 
                    username + 
                    linkDateFormat.format(tweet.getDate()) + 
                    ".html"
                );
                calendarDto.setIsExist(true);
            }
            
            // return the object list.
            return calendarDtoList;
            
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
}