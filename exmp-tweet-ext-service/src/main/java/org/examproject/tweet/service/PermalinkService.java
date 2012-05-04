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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.collections.Transformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.entity.Tweet;
import org.examproject.tweet.entity.Vocab;
import org.examproject.tweet.entity.Word;
import org.examproject.tweet.repository.TweetRepository;
import org.examproject.tweet.repository.VocabRepository;
import org.examproject.tweet.repository.WordRepository;
import org.examproject.tweet.util.DateValue;
import org.examproject.tweet.util.DayBeginDateTransformer;
import org.examproject.tweet.util.DayEndDateTransformer;

/**
 * @author hiroxpepe
 */
public class PermalinkService {
 
    private static final Log LOG = LogFactory.getLog(
        PermalinkService.class
    );
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final Mapper mapper = null;
    
    @Inject
    private final TweetRepository tweetRepository = null;
    
    @Inject
    private final VocabRepository vocabRepository = null;
    
    @Inject
    private final WordRepository wordRepository = null;
    
    ///////////////////////////////////////////////////////////////////////////
    // constructor
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    public List<TweetDto> getTweetListByDate(
        String userName,
        int year,
        int month,
        int day
    ) {
        LOG.debug("called.");
        try {
            // create a date conditions.
            Transformer beginDateTransformer = new DayBeginDateTransformer();
            Transformer endDateTransformer = new DayEndDateTransformer();
            DateValue dateValue = new DateValue(year, month, day);
            Date begin = (Date) beginDateTransformer.transform(dateValue);
            Date end = (Date) endDateTransformer.transform(dateValue);
            
            // get the tweet list.
            List<Tweet> tweetList = tweetRepository.findByNameAndDateBetween(
                userName,
                begin,
                end
            );
            LOG.debug("permalink tweet count: " + tweetList.size());
            
            // map the object.
            List<TweetDto> tweetDtoList = new ArrayList<TweetDto>();
            for (Tweet tweet : tweetList) {
                TweetDto tweetDto = context.getBean(TweetDto.class);
                // map the entity-object to the dto-object.
                mapper.map(
                    tweet,
                    tweetDto
                );
                tweetDtoList.add(
                    tweetDto
                );
            }
            
            return tweetDtoList;
            
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public List<TweetDto> getTweetListByWord(
        String userName,
        String text 
   ) {
        LOG.debug("called.");
        try {
            // get the word.
            List<Word> words = wordRepository.findByText(text);
            Word word = words.get(0);
            
            // get the vocab list.
            List<Vocab> vocabList = vocabRepository.findByWordId(word.getId());
                    
            LOG.debug("permalink vocab count: " + vocabList.size());
            
            // map the object.
            List<TweetDto> tweetDtoList = new ArrayList<TweetDto>();
            List<Long> tmpStatusIdList =  new ArrayList<Long>();
            for (Vocab vocab : vocabList) {
                Long statusId = vocab.getStatusId();
                if (tmpStatusIdList.contains(statusId)) {
                    continue;
                }
                // get the tweet.
                Tweet tweet = tweetRepository.findById(statusId);
                // map the entity-object to the dto-object.
                TweetDto tweetDto = context.getBean(TweetDto.class);
                mapper.map(
                    tweet,
                    tweetDto
                );
                tweetDtoList.add(
                    tweetDto
                );
                tmpStatusIdList.add(statusId);
            }
            
            LOG.debug("permalink tweet count: " + tweetDtoList.size());
            
            return tweetDtoList;
            
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
}
