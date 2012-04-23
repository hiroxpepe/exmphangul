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
 * get the decoded value.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.value.DecodedValueTransformer = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        console.log("exmp.hangul.functor.value.DecodedValueTransformer#transform");
        
        if (obj.initialId == null) {
            //console.log("obj.initialId is null.");
            return null;
        }
        
        var initialId = obj.initialId;
        
        if (obj.peakId == null) {
            //console.log("obj.peakId is null.");
            return null;
        }
        
        var peakId = obj.peakId;
        
        var finalId;
        if (obj.finalId == null) {
            finalId = 0;
        } 
        else {
            finalId = obj.finalId;
        }
        
        //console.log("initialId: " + initialId);
        //console.log("peakId:" + peakId);
        //console.log("finalId:" + finalId);
        
        // get the UTF-16 code.
        var code = 0xAC00 + (initialId * 21 * 28) + (peakId * 28) + (finalId * 1);
        //console.log("code:" + code);
        
        return code;
    }
}