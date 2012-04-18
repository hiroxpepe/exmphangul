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
 * get the divided word string.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.value.WordDivideTransformer = {
    
    // TODO: not use ??
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        console.log("exmp.hangul.functor.value.WordDivideTransformer#transform");
        
        var value = obj.value;
        
        var initialStr = value.substr(0, 2);
        var peakStr = value.substr(2, 2);
        var finalStr = value.substr(4, 2);
        
        console.log("initialStr: " + initialStr);
        console.log("peakStr: " + peakStr);
        console.log("finalStr: " + finalStr);
        
        return {
            initialStr: initialStr,
            peakStr: peakStr,
            finalStr: finalStr
        };
    }
}