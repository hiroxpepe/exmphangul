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
 * send http request for retweet action.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.request.TweetRetweetClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("tweet retweet begin.");
        
        new $.ajax({
            url: "retweet.html",
            type: "POST",
            data: {
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
                
                console.log("tweet retweet complete.");
            },
            
            // callback function of the error.
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                console.log("http request error occurred.");
            }
        });
    }
}
