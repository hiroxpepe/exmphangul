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

///////////////////////////////////////////////////////////////////////////////
/**
 * a functor class of the application.
 * build the event handler.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.event.EventBuildClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        
        var replyEventClosure = exmp.tweet.functor.event.ReplyEventClosure;
        var retweetEventClosure = exmp.tweet.functor.event.RetweetEventClosure;
        var favorEventClosure = exmp.tweet.functor.event.FavorEventClosure;
        
        for (var i = 0; i < obj.tweetModelList.length; i++) {
            var statusId = obj.tweetModelList[i].statusId;
            
            // set the event handler for reply.
            replyEventClosure.execute({
                statusId: statusId
            });
            
            // set the event handler for retweet.
            retweetEventClosure.execute({
                statusId: statusId
            });
            
            // set the event handler for favor.
            favorEventClosure.execute({
                statusId: statusId
            });
        }
    }
}