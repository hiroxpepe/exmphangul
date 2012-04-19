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
exmp.hangul.functor.value.LetterIdArrayTransformer = {
    
    _initialArray: ["g","kk","n","d","tt","r","m","b","bb","s","ss","-","j","jj","c","k","t","p","h"],
    _peakArray: ["a","ae","ya","yae","eo","e","yeo","ye","o","wa","wae","oe","yo","u","weo","we","wi","yu","eu","yi","i"],
    _finalArray: ["-","g","kk","ks","n","nj","nh","d","r","rg","rm","rb","rs","rt","rp","rh","m","b","bs","s","ss","ng","j","c","k","t","p","h"],
    
    _remains: "",
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        console.log("exmp.hangul.functor.value.LetterIdArrayTransformer#transform");
        
        var LetterIdArray = [];
        
        var value = obj.value;
        var text = "";
        
        // TODO: this is still provisional..
        for (var i = 0; i < 50; i++) {
            
            // finally not match..
            if (i == 49) {
                var remains = this._remains;
                LetterIdArray[i] = {
                    initialId: null,
                    peakId: null,
                    finalId: null,
                    remains: remains
                };
                return LetterIdArray;
            }
            
            if (i == 0) {
                text = value;
            }
            else {
                text = this._remains;
                if (text == "") {
                    break;
                }
            }
            LetterIdArray[i] = this._getLetterId(text);
        }
        
        if (LetterIdArray[0] != null) {
            return LetterIdArray;
        }
        
        return null;
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _getLetterId: function(value) {
        
        var initialId = null;
        var peakId = null;
        var finalId = null;
        
        ///////////////////////////////////////////////////////////////////////
        // find the initial word.
        
        // if the initial word is two value.
        var initialStr2 = value.substr(0, 2);
        for (var initialIndex2 in this._initialArray) {
            if (initialStr2 == this._initialArray[initialIndex2]) {
                initialId = initialIndex2;
                value = value.substr(2);
                console.log("initialId: " + initialId);
            }
        }
        
        // if the initial word is one value.
        var initialStr1 = value.substr(0, 1);
        for (var initialIndex1 in this._initialArray) {
            if (initialStr1 == this._initialArray[initialIndex1]) {
                initialId = initialIndex1;
                value = value.substr(1);
                console.log("initialId: " + initialId);
            }
        }
        
        // if not found the initial word complemented to 11.
        if (initialId == null) {
            console.log("initial word is not found.");
            console.log("initial word is complemented to 11.");
            initialId = 11;
        }
        
        ///////////////////////////////////////////////////////////////////////
        // find the peak word.
        
        // if the peak word is three value.
        var peakStr3 = value.substr(0, 3);
        for (var peakIndex3 in this._peakArray) {
            if (peakStr3 == this._peakArray[peakIndex3]) {
                peakId = peakIndex3;
                value = value.substr(3);
                console.log("peakId: " + peakId);
            }
        }
        
        // if the peak word is two value.
        var peakStr2 = value.substr(0, 2);
        for (var peakIndex2 in this._peakArray) {
            if (peakStr2 == this._peakArray[peakIndex2]) {
                peakId = peakIndex2;
                value = value.substr(2);
                console.log("peakId: " + peakId);
            }
        }
        
        // if the peak word is one value.
        var peakStr1 = value.substr(0, 1);
        for (var peakIndex1 in this._peakArray) {
            if (peakStr1 == this._peakArray[peakIndex1]) {
                peakId = peakIndex1;
                value = value.substr(1);
                console.log("peakId: " + peakId);
            }
        }
        
        ///////////////////////////////////////////////////////////////////////
        // find the final word.
        
        // if the final word is two value.
        var finalStr2 = value.substr(0, 2);
        for (var finalIndex2 in this._finalArray) {
            if (finalStr2 == this._finalArray[finalIndex2]) {
                finalId = finalIndex2;
                value = value.substr(2);
                console.log("finalId: " + finalId);
            }
        }
        
        // if the final word is one value.
        var finalStr1 = value.substr(0, 1);
        for (var finalIndex1 in this._finalArray) {
            if (finalStr1 == this._finalArray[finalIndex1]) {
                finalId = finalIndex1;
                value = value.substr(1);
                console.log("finalId: " + finalId);
            }
        }
        
        this._remains = value;
        console.log("_remains: " + value);
        
        return {
            initialId: initialId,
            peakId: peakId,
            finalId: finalId,
            remains: null
        };
    }
}