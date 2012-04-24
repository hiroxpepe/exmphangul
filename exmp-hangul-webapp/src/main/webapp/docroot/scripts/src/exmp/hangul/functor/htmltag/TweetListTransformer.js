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
 * Ajax HTTP requests and convert to HTML tables.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.htmltag.TweetListTransformer = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        console.log("exmp.hangul.functor.htmltag.TweetListTransformer#transform");
        
        // DDDDDDDDDDDDDDDDDDDDDDDDD
        for (var h = 0; h < obj.userListNameList.length; h++) {
            console.log(obj.userListNameList[h]);
        }
        
        // dynamically generate an html table.
        var table = "<table class='tweet-list-table'>";
        for (var i = 0; i < obj.tweetModelList.length; i++) {
            
            // get the value
            var image = obj.tweetModelList[i].userProfileImageURL;
            var name = obj.tweetModelList[i].userName;
            var text = obj.tweetModelList[i].text;
            var statusId = obj.tweetModelList[i].statusId;
            var isFavorited = obj.tweetModelList[i].isFavorited;
            
            // get the icon path for action command.
            var favoriteImg = this._getFavoriteImg(isFavorited);
            var retweetImg = this._getRetweetImg();
            var replyImg = this._getReplyImg();
            
            // create an html tag and set the entry code.
            table +=
                "<tr class='tweet-list-tr'>" +
                    "<td class='tweet-icon-td'>" + 
                        "<div class='tweet-icon'><img src='" + image + "' width='48' height='48' border='0'></div>" +
                    "</td>" +
                    "<td class='tweet-list-td' >" +
                        "<b>" + name + "</b> <span id='id-" + statusId + "'>" + text + "</span>" + 
                    "</td>" +
                    "<td class='tweet-action-td'>" +
                    
                        "<table>" +
                           "<tr>" + 
                               "<td>" + 
                                   "<div class='action-favorite' id='action-favorite-" + statusId + "' status-id='" + statusId + "' user-name='" + name + "'>" +
                                       /* "<img src='" + favoriteImg + "' width='16' height='16' border='0' />"  + */
                                   "</div>" +
                               "</td>" + 
                           "</tr>" +
                           "<tr>" + 
                               "<td>" + 
                                   "<div class='action-retweet' id='action-retweet-" + statusId + "' status-id='" + statusId + "' user-name='" + name + "'>" +
                                       /* "<img src='" + retweetImg + "' width='16' height='16' border='0' />"  + */
                                   "</div>" +
                               "</td>" +
                           "</tr>" +
                           "<tr>" + 
                               "<td>" + 
                                   "<div class='action-reply' id='action_reply-" + statusId + "' status-id='" + statusId + "' user-name='" + name + "'>" +
                                       /* "<img src='" + replyImg + "' width='16' height='16' border='0' />"  + */
                                   "</div>" +
                               "</td>" + 
                           "</tr>" +
                        "</table>" +                       
                        
                    "</td>" +
                "</tr>";
        }
        table += "</table>";
        return table;
    },
    
    _getFavoriteImg: function(isFavorited) {
        if (isFavorited) {
            return "../docroot/images/favorite_true.png";
        }
        return "../docroot/images/favorite_false.png";
    },

    _getRetweetImg: function() {
        return "../docroot/images/retweet.png";

    },

    _getReplyImg: function() {
        return "../docroot/images/reply.png";
    }
}