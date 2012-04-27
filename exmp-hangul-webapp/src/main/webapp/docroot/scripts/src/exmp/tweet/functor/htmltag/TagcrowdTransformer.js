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
exmp.tweet.functor.htmltag.TagcrowdTransformer = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        console.log("exmp.tweet.functor.htmltag.TagcrowdTransformer#transform");
        
        // dynamically generate an html table.
        var table = "<table class='tagcrowd-list-table'>";
        if (obj.tagcrowdModelList != null) {
            for (var i = 0; i < obj.tagcrowdModelList.length; i++) {
                
                // get the value
                var statusId = obj.tagcrowdModelList[i].statusId;
                var text = obj.tagcrowdModelList[i].text;
                
                // create an html tag and set the entry code.
                table +=
                    "<tr class='tagcrowd-list-tr'>" +
                        "<td id='tagcrowd-list-td-" + statusId + "' class='tagcrowd-list-td' >" +
                            "<span id='id-" + statusId + "'>" + text + "</span>" + 
                        "</td>" +
                    "</tr>";
            }
            table += "</table>";
        }        
        return table;
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods

}