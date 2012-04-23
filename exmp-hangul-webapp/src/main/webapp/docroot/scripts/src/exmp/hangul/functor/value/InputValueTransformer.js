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
 * get the input char string.
 * 
 * @author hiroxpepe
 */
exmp.hangul.functor.value.InputValueTransformer = {
    
    _array: ["", "", ""],
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        console.log("exmp.hangul.functor.value.InputValueTransformer#transform");
        
        // reset array.
        if (obj.value === " ") {
            for (var i = 0; i < this._array.length; i++) {
                this._array[i] = "";
            }
            return null;
        }
        
        // shift the value..
        this._array[0] = this._array[1]
        this._array[1] = this._array[2]
        this._array[2] = obj.value;
              
        var ret = "";
        for (var i = 0; i < this._array.length; i++) {
            ret += this._array[i];
        }
        
        return ret;
    }
}