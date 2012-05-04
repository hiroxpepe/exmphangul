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
exmp.tweet.functor.htmltag.TweetListTransformer = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        //console.log("exmp.tweet.functor.htmltag.TweetListTransformer#transform");
        
        // set the user list name for the select.
        if (obj.userListNameList != null) {
            for (var h = 0; h < obj.userListNameList.length; h++) {
                var listName = obj.userListNameList[h];
                $("#user-list-name").append(
                    $("<option value='" + listName +"'>" + listName + "</option>")
                );
                // TODO: set the value in this?
            }
        }
        
        // dynamically generate an html table.
        var table = "<table class='tweet-list-table'>";
        if (obj.tweetModelList != null) {
            for (var i = 0; i < obj.tweetModelList.length; i++) {
                
                // get the value.
                var image = obj.tweetModelList[i].userProfileImageURL;
                var name = obj.tweetModelList[i].userName;
                var text = obj.tweetModelList[i].text;
                var statusId = obj.tweetModelList[i].statusId;
                var isFavorited = obj.tweetModelList[i].isFavorited;
                
                // create an html tag and set the entry code.
                table +=
                    "<tr class='tweet-list-tr'>" +
                        "<td class='tweet-icon-td'>" +
                            "<div class='tweet-icon'>" +
                                "<img src='" + image + "' width='48' height='48' border='0'>" +
                            "</div>" +
                        "</td>" +
                        "<td id='tweet-list-td-" + statusId + "' class='tweet-list-td' >" +
                            "<div>" + 
                                "<span><b>" + name + "</b></span>" +
                                this._getReplySpan(name, statusId) +
                                this._getDeleteSpan(name, statusId) +
                                this._getRetweetSpan(name, statusId) +
                                this._getFavoriteSpan(name, statusId, isFavorited) +
                            "</div>" +
                            "<span id='id-" + statusId + "'>" + text + "</span>" +
                        "</td>" +
                    "</tr>";
            }
            table += "</table>";
        }
        return table;
        
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _getReplySpan: function(name, statusId) {
        return "<span id='action-reply-" + statusId +
                   "' class='action-reply' status-id='" + statusId +
                   "' user-name='" + name + "'>Reply</span>";
    },
    
    _getDeleteSpan: function(name, statusId) {
        if (name != $("#screen-name").val()) {
            return "";
        }
        return "<span id='action-delete-" + statusId +
                   "' class='action-delete' status-id='" + statusId +
                   "' user-name='" + name + "'>Delete</span>";
    },
    
    _getRetweetSpan: function(name, statusId) {
        if (name == $("#screen-name").val()) {
            return "";
        }
        return "<span id='action-retweet-" + statusId +
                   "' class='action-retweet' status-id='" + statusId +
                   "' user-name='" + name + "'>Retweet</span>";
    },
    
    _getFavoriteSpan: function(name, statusId, isFavorited) {
        var str = "Favorite";
        if (isFavorited) {
            str = "Favorited";
        }
        return "<span id='action-favorite-" + statusId +
                   "' class='action-favorite' status-id='" + statusId +
                   "' user-name='" + name + "'>" + str + "</span>";
    }
    
}