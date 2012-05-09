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
 * set the event handler of keyup.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.event.ClickHangulClosure = {
    
    _letterArray: [
        "g","kk","n","d","tt","r","m","b","pp","s",
        "ss","ng","j","jj","ch","k","t","p","h",
        "a","ae","ya","yae","eo","e","yeo","ye","o","wa","wae",
        "oe","yo","u","wo","we","wi","yu","eu","ui","i"
    ],
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        //console.log("exmp.hangul.functor.event.ClickHangulClosure#execute");
        
        this._createEventHandler();
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _createEventHandler: function() {
        
        // create the event handler.
        for (var i = 0; i < this._letterArray.length; i++) {
            var letter = this._letterArray[i];
            // click
            $("#" + letter).click(function(event) {
                var caret = $("#caretStart").val();
                var tweet = $("#tweet").val();
                var leftStr = tweet.substring(0, caret);
                var rightStr = tweet.substring(caret);
                $("#tweet").val(leftStr + event.target.innerText + rightStr);
            });
            // mouseover
            $("#" + letter).mouseover(function(event) {
                $("#" + event.target.id).css({
                    color: "black",
                    backgroundColor: "lavender"
                });
            });
            // mouseout
            $("#" + letter).mouseout(function(event) {
                $("#" + event.target.id).css({
                    color: "black",
                    backgroundColor: "#f3f3f3"
                });
            });
        }
    }
    
}