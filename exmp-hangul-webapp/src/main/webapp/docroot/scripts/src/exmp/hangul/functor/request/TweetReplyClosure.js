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
 * send HTTP request for reply post.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.request.TweetReplyClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("tweet reply begin.");
        
        var listUpdateClosure = exmp.hangul.functor.dhtml.TweetListUpdateClosure;
        var eventBuildClosure = exmp.hangul.functor.event.EventBuildClosure;
        
        new $.ajax({
            url: "reply.html",
            type: "POST",
            data: {
                tweet: obj.content,
                user_id: obj.userId,
                status_id: obj.statusId
            },
            dataType: "json",
            
            // callback function of the success.
            success: function(data, dataType) {
                if (data.isError) {
                    console.log("application error occurred.");
                    return;
                }
                
                // update the HTML table of the tweet list.
                listUpdateClosure.execute(
                    data
                );
                
                // build the event of the tweet list.
                eventBuildClosure.execute(
                    data
                );
                
                $("#reply-status-id").val("");
                $("#reply-user-name").val("");
                $("#tweet").val("");
                
                console.log("tweet reply complete.");
            },
            
            // callback function of the error.
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                console.log("http request error occurred.");
            }
        });
    }
}
