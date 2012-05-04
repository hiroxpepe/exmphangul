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
 * this class is a transformer that JSON data get by
 * Ajax HTTP requests and convert to HTML tags.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.htmltag.RecentTransformer = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        console.log("exmp.tweet.functor.htmltag.RecentTransformer#transform");
        
        // dynamically generate an html tags.
        var html = "<div>";
        if (obj.tweetModelList != null) {
            for (var i = 0; i < obj.tweetModelList.length; i++) {
                
                // get the value.
                var name = obj.tweetModelList[i].userName;
                var text = obj.tweetModelList[i].text;
                var statusId = obj.tweetModelList[i].statusId;
                
                // create an html tag.
                html += "<div class='recent-div'>" +
                            "<a href='/tweet/" + name + "/" + statusId + ".html" + 
                                "' id='recent-a-" + statusId + 
                                "' class='recent-a'>" + text.substring(0, 12) + 
                            "</a>" +
                        "</div>";
            }
            html += "</div>";
        }
        return html;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
}