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
 * update the html table of the tweet list.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.dhtml.TweetListUpdateClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        //console.log("exmp.tweet.functor.dhtml.TweetListUpdateClosure#execute");
        
        var transformer = exmp.tweet.functor.htmltag.TweetListTransformer;
        
        $("#tweet-list-block").html(
            transformer.transform(
                obj
            )
        );
    }
}