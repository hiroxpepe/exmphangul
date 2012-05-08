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
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("exmp.hangul.functor.event.ClickHangulClosure#execute");
        
        this._addHangul();
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _addHangul: function() {
        $("#g").click(function() {
            console.log("click g");
            
            var caret = $("span.caretStart").text();
            console.log("caret: " + caret);
            
            var tweet = $("#tweet").val();
            var leftStr = tweet.substring(0, caret);
            var rightStr = tweet.substring(caret);
            console.log("leftStr:" + leftStr);
            console.log("rightStr:" + rightStr);
            
            $("#tweet").val(leftStr + $("#g").html() + rightStr);
        });
        
    }
    
}