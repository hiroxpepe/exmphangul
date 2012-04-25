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
 * set the event handler of tweet list effect.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.event.TweetListEffectClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        
        // dynamically generate an event handler.
        $("#tweet-list-td-" + obj.statusId).hover(
            function () {
                //console.log("in");
                $("#action-reply-" + obj.statusId).css({
                    cursor: "pointer",
                    color: "tan"
                });
                $("#action-delete-" + obj.statusId).css({
                    cursor: "pointer",
                    color: "tan"
                });
                $("#action-retweet-" + obj.statusId).css({
                    cursor: "pointer",
                    color: "tan"
                });
                $("#action-favorite-" + obj.statusId).css({
                    cursor: "pointer",
                    color: "tan"
                });
            },
            function () {
                //console.log("out");
                $("#action-reply-" + obj.statusId).css({
                    color: "lightyellow"
                });
                $("#action-delete-" + obj.statusId).css({
                    color: "lightyellow"
                });
                $("#action-retweet-" + obj.statusId).css({
                    color: "lightyellow"
                });
                $("#action-favorite-" + obj.statusId).css({
                    color: "lightyellow"
                });
            }
        );
        
    }
}