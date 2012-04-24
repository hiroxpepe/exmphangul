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
 * set the event handler of the retweet tweet.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.event.RetweetEventClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("exmp.hangul.functor.event.RetweetEventClosure#execute");
        
        var retweetClosure = exmp.hangul.functor.request.TweetRetweetClosure;
               
        // dynamically generate an event handler.
        $("#action-retweet-" + obj.statusId).click(function() {
            retweetClosure.execute({
                userId: $("#user-id").val(),
                statusId: obj.statusId
            });
        });
    }
}