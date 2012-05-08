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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.dto.TagcrowdDto;
import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.entity.Tweet;
import org.examproject.tweet.entity.Vocab;
import org.examproject.tweet.entity.Word;
import org.examproject.tweet.repository.TweetRepository;
import org.examproject.tweet.repository.VocabRepository;
import org.examproject.tweet.repository.WordRepository;
import org.examproject.tweet.util.IsContainKrHangulCodePredicate;
import org.examproject.tweet.util.SentenceToWordsTransformer;

/**
 * @author hiroxpepe
 */
public class SimpleTagcrowdService implements TagcrowdService {
    
    private static final Log LOG = LogFactory.getLog(
        SimpleTagcrowdService.class
    );
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final TweetRepository tweetRepository = null;
    
    @Inject
    private final VocabRepository vocabRepository = null;
    
    @Inject
    private final WordRepository wordRepository = null;
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the tagcrowd dto list.
     */
    @Override
    public List<TagcrowdDto> getTagcrowdListByName(
        String userName
    ) {
        LOG.debug("called.");
        try {
            List<TagcrowdDto> tagcrowdDtoList = new ArrayList<TagcrowdDto>();
            Map<String, Integer> tmpWordMap = new HashMap<String, Integer>();
            
            // get the vocab entity list.
            List<Vocab> vocabList = vocabRepository.findByName(userName);
            
            // get the word count.
            for (Vocab vocab : vocabList) {
                Word word = vocab.getWord();
                String wordText = word.getText();
                if (tmpWordMap.containsKey(wordText)) {
                    Integer count = tmpWordMap.get(wordText);
                    tmpWordMap.put(wordText, count + 1);
                }
                else {
                    tmpWordMap.put(wordText, 1);
                }
            }
            
            // map to the tagcrowd dto.
            Iterator it = tmpWordMap.keySet().iterator();
            while (it.hasNext()) {
                String keyWord = (String) it.next();
                Integer count = tmpWordMap.get(keyWord);
                
                // create the link url.
                StringBuilder sb = new StringBuilder();
                sb.append("/word/")
                  .append(userName)
                  .append("/")
                  .append(keyWord)
                  .append(".html");
                String linkUrl = sb.toString();
                
                // map to the tagcrowd dto.
                TagcrowdDto tagcrowdDto = context.getBean(TagcrowdDto.class);
                tagcrowdDto.setUserName(userName);
                tagcrowdDto.setText(keyWord);
                tagcrowdDto.setLinkUrl(linkUrl);
                tagcrowdDto.setCount(count);
                tagcrowdDtoList.add(tagcrowdDto);
            }
            
            // return the object list.
            return tagcrowdDtoList;
            
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    /**
     * update the entity.
     */
    @Override
    public List<TagcrowdDto> update(
        TweetDto tweetDto
    ) {
        LOG.debug("called.");
        try {
            Long statusId = Long.parseLong(tweetDto.getStatusId());
            String content = tweetDto.getText();
            String userName = tweetDto.getUserName();
            
            // get the korean word only.
            Predicate predicate = new IsContainKrHangulCodePredicate();
            boolean isNeed = predicate.evaluate(content);
            if (isNeed) {
                
                // split words from the sentence.
                Transformer transformer = new SentenceToWordsTransformer();
                String[] words = (String[]) transformer.transform(content);
                
                // process all words.
                for (int i = 0; i < words.length; i++) {
                    String oneWord = words[i];
                    boolean isKr = predicate.evaluate(oneWord);
                    if (isKr) {
                        
                        // get the vocab entity.
                        Vocab vocab = context.getBean(Vocab.class);
                        
                        // get the word id.
                        List<Word> wordList = wordRepository.findByText(oneWord);
                        
                        // if the new word.
                        if (wordList.isEmpty()) {
                            
                            // create this word.
                            Word wordEntity = context.getBean(Word.class);
                            wordEntity.setText(oneWord);
                            Word newWordEntity = (Word) wordRepository.save(wordEntity);
                            vocab.setWord(newWordEntity);
                        }
                        // already exist.
                        else {
                            Word wordEntity = wordList.get(0);
                            vocab.setWord(wordEntity);
                        }
                        
                        // set vocabulary this tweet!
                        Tweet tweet = tweetRepository.findById(statusId);
                        vocab.setStatus(tweet);
                        vocab.setName(userName);
                        vocabRepository.save(vocab);
                    }
                }
            }
            
            // TODO: return tagcrowd dto list..
            List<TagcrowdDto> tagcrowdDtoList = null;
            return tagcrowdDtoList;
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
}
