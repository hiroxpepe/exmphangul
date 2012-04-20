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
 * send HTTP request for tweet post.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.request.TweetClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("tweet begin.");
        
        new $.ajax({
            url: "tweet.html",
            type: "POST",
            data: {
                tweet: obj.content,
                user_id: obj.userId
            },
            dataType: "json",
            success: function(data, dataType) {
                if (data.isError) {
                    console.log("application error occurred.");
                    return;
                }
                // todo for update.
                
                $("#hangul").val("");
                
                console.log("tweet complete.");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                console.log("http request error occurred.");
            }
        });
    }
}