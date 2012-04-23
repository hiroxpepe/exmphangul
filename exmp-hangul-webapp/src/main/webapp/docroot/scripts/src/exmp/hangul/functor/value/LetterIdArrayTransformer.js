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
    
    _initialArray: ["g","kk","n","d","tt","r","m","b","pp","s","ss","-","j","jj","ch","k","t","p","h"],
    _peakArray: ["a","ae","ya","yae","eo","e","yeo","ye","o","wa","wae","oe","yo","u","wo","we","wi","yu","eu","ui","i"],
    _finalArray: ["-","g","kk","ks","n","nj","nh","d","r","rg","rm","rb","rs","rt","rp","rh","m","b","bs","s","ss","ng","j","ch","k","t","p","h"],
    
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
            
            // the first word is processed.
            if (i == 0) {
                text = value;
            }
            // other is the rest 
            // that after the word has been processed.
            else {
                text = this._remains;
                // processing until the text is null.
                if (text == "") {
                    break;
                }
            }
            LetterIdArray[i] = this._getLetterId(text);
        }
        
        if (LetterIdArray[0] != null) {
            return LetterIdArray;
        }
        
        // else.
        return null;
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _getLetterId: function(value) {
        console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        console.log("_getLetterId begin.");
        
        var initialId = null;
        var peakId = null;
        var finalId = null;
        
        var _initialStr = "";
        var _peakIdStr = "";
        var _finalIdStr = "";
        
        ///////////////////////////////////////////////////////////////////////
        // find the initial word.
        
        // if the initial word is two value.
        var initialStr2 = value.substr(0, 2);
        for (var initialIndex2 in this._initialArray) {
            if (initialStr2 == this._initialArray[initialIndex2]) {
                initialId = initialIndex2;
                _initialStr = initialStr2;
                value = value.substr(2);
                console.log("-----------------------");
                console.log("initialId: " + initialId);
                console.log("peakId: " + peakId);
                console.log("finalId: " + finalId);
            }
        }
        
        // if the initial word is one value.
        var initialStr1 = value.substr(0, 1);
        for (var initialIndex1 in this._initialArray) {
            if (initialStr1 == this._initialArray[initialIndex1]) {
                initialId = initialIndex1;
                _initialStr = initialStr1;
                value = value.substr(1);
                console.log("-----------------------");
                console.log("initialId: " + initialId);
                console.log("peakId: " + peakId);
                console.log("finalId: " + finalId);
            }
        }
        
        // if not found the initial word complemented to 11.
        if (initialId == null) {
            console.log("initial word is not found.");
            console.log("initial word is complemented to 11.");
            initialId = "11";
            _initialStr = "";
        }
        
        ///////////////////////////////////////////////////////////////////////
        // find the peak word.
        
        // if the peak word is three value.
        var peakStr3 = value.substr(0, 3);
        for (var peakIndex3 in this._peakArray) {
            if (peakStr3 == this._peakArray[peakIndex3]) {
                peakId = peakIndex3;
                _peakIdStr = peakStr3;
                value = value.substr(3);
                console.log("-----------------------");
                console.log("initialId: " + initialId);
                console.log("peakId: " + peakId);
                console.log("finalId: " + finalId);
            }
        }
        
        // if the peak word is two value.
        var peakStr2 = value.substr(0, 2);
        for (var peakIndex2 in this._peakArray) {
            if (peakStr2 == this._peakArray[peakIndex2]) {
                peakId = peakIndex2;
                _peakIdStr = peakStr2;
                value = value.substr(2);
                console.log("-----------------------");
                console.log("initialId: " + initialId);
                console.log("peakId: " + peakId);
                console.log("finalId: " + finalId);
            }
        }
        
        // if the peak word is one value.
        var peakStr1 = value.substr(0, 1);
        for (var peakIndex1 in this._peakArray) {
            if (peakStr1 == this._peakArray[peakIndex1]) {
                peakId = peakIndex1;
                _peakIdStr = peakStr1;
                value = value.substr(1);
                console.log("-----------------------");
                console.log("initialId: " + initialId);
                console.log("peakId: " + peakId);
                console.log("finalId: " + finalId);
            }
        }
        
        ///////////////////////////////////////////////////////////////////////
        // find the final word.
        
        // if the final word is two value.
        var finalStr2 = value.substr(0, 2);
        for (var finalIndex2 in this._finalArray) {
            if (finalStr2 == this._finalArray[finalIndex2]) {
                finalId = finalIndex2;
                _finalIdStr = finalStr2;
                value = value.substr(2);
                console.log("-----------------------");
                console.log("initialId: " + initialId);
                console.log("peakId: " + peakId);
                console.log("finalId: " + finalId);
            }
        }
        
        // if the final word is one value.
        var finalStr1 = value.substr(0, 1);
        for (var finalIndex1 in this._finalArray) {
            if (finalStr1 == this._finalArray[finalIndex1]) {
                finalId = finalIndex1;
                _finalIdStr = finalStr1;
                value = value.substr(1);
                console.log("-----------------------");
                console.log("initialId: " + initialId);
                console.log("peakId: " + peakId);
                console.log("finalId: " + finalId);
            }
        }
        
        this._remains = value;
        console.log("=============================");
        console.log("remains: " + value);
        
        // add
        var _originalString = _initialStr + _peakIdStr + _finalIdStr;
        console.log("originalString: " + _originalString);
        console.log("=============================");
                
        console.log("_getLetterId end.");
        console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
        return {
            originalString: _originalString,
            initialId: initialId,
            peakId: peakId,
            finalId: finalId,
            remains: null
        };
    }
}