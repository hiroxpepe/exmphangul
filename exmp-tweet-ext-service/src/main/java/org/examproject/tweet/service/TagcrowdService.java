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
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.dto.TagcrowdDto;
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
public class TagcrowdService {
 
    private static final Log LOG = LogFactory.getLog(
        TagcrowdService.class
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
    
    public List<TagcrowdDto> getList(String username) {
        LOG.debug("called.");
        try {
            List<TagcrowdDto> tagcrowdDtoList = new ArrayList<TagcrowdDto>();
            List<Vocab> vocabList = vocabRepository.findByName(username);
            List<String> tmpStrList = new ArrayList<String>();
            for (Vocab vocab : vocabList) {
                Word word = vocab.getWord();
                if (tmpStrList.contains(word.getText())) {
                    continue;
                }
                TagcrowdDto tagcrowdDto = context.getBean(TagcrowdDto.class);
                tagcrowdDto.setStatusId(String.valueOf(vocab.getStatus().getId()));
                tagcrowdDto.setUserName(vocab.getName());
                tagcrowdDto.setText(word.getText());
                tagcrowdDto.setLinkUrl(
                    "/word/" + 
                    username + 
                    "/" + word.getText() +
                    ".html"
                );
                tagcrowdDtoList.add(tagcrowdDto);
                tmpStrList.add(word.getText());
            }
            
            // return the object list.
            return tagcrowdDtoList;
            
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public List<TagcrowdDto> update(
        Long statusId,
        String content,
        String username
    ) {
        LOG.debug("called.");
        List<TagcrowdDto> tagcrowdDtoList = null;
        try {
            Predicate predicate = new IsContainKrHangulCodePredicate();
            boolean isNeed = predicate.evaluate(content);
            if (isNeed) {
                Transformer transformer = new SentenceToWordsTransformer();
                String[] words = (String[]) transformer.transform(content);
                for (int i = 0; i < words.length; i++) {
                    String oneWord = words[i];
                    boolean isKr = predicate.evaluate(oneWord);
                    if (isKr) {
                        ///////////////////////////////////////////////////////
                        // get the vocab entity.
                        Vocab vocab = context.getBean(Vocab.class);
                        ///////////////////////////////////////////////////////
                        // get the word id.
                        Long wordId = null;
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
                        ///////////////////////////////////////////////////////
                        // set vocabulary this tweet!
                        Tweet tweet = tweetRepository.findById(statusId);
                        vocab.setStatus(tweet);
                        vocab.setName(username);
                        vocabRepository.save(vocab);
                    }
                }
            }
            return tagcrowdDtoList;
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
}
