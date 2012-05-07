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

package org.examproject.tweet.service

import java.lang.Integer
import java.lang.Long
import java.util.List

import org.examproject.tweet.dto.TweetDto

/**
 * @author hiroxpepe
 */
trait PermalinkService {
    
    def getTweetByStatusId(statusId: Long): TweetDto
    
    def getTweetListByDate(userName: String, year: Integer, month: Integer, day: Integer): List[TweetDto]
    
    def getTweetListByWord(userName: String, text: String): List[TweetDto]
    
    def update(tweetDto: TweetDto): TweetDto
    
}