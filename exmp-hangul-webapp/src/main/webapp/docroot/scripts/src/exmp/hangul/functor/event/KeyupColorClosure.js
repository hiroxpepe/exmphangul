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
exmp.hangul.functor.event.KeyupColorClosure = {
    
    _tmp: "",
    
    _letterArray: [
        "g","kk","n","d","tt","r","m","b","pp","s",
        "ss","ng","j","jj","ch","k","t","p","h",
        "a","ae","ya","yae","eo","e","yeo","ye","o","wa","wae",
        "oe","yo","u","wo","we","wi","yu","eu","ui","i"
    ],
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("exmp.hangul.functor.event.KeyupColorClosure#execute");
        //console.log("event.keyCode: " + obj.keyCode);
        
        var valueTransformer = exmp.hangul.functor.value.InputValueTransformer
        
        var value = valueTransformer.transform({
            value: this._getChar(obj.keyCode)
        });
        
        if (value != null) {
            $("#" + this._tmp).css({
                color: "black",
                backgroundColor: "#f3f3f3"
            });
            $("#" + value).css({
                color: "red",
                backgroundColor: "pink"
            });
            this._tmp = value;
            
            // reset. TODO: need char table..
            for (var i = 0; i < this._letterArray.length; i++) {
                var letter = this._letterArray[i];
                if (value == letter) {
                    //console.log("value: " + value);
                    //console.log("letter: " + letter);
                    valueTransformer.transform({
                        value: " "
                    });
                    break;
                }
            }
        }
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _getChar: function(code) {
        if (code == 65) return "a";
        if (code == 66) return "b";
        if (code == 67) return "c";
        if (code == 68) return "d";
        if (code == 69) return "e";
        if (code == 70) return "f";
        if (code == 71) return "g";
        if (code == 72) return "h";
        if (code == 73) return "i";
        if (code == 74) return "j";
        if (code == 75) return "k";
        if (code == 76) return "l";
        if (code == 77) return "m";
        if (code == 78) return "n";
        if (code == 79) return "o";
        if (code == 80) return "p";
        if (code == 81) return "q";
        if (code == 82) return "r";
        if (code == 83) return "s";
        if (code == 84) return "t";
        if (code == 85) return "u";
        if (code == 86) return "v";
        if (code == 87) return "w";
        if (code == 88) return "x";
        if (code == 89) return "y";
        if (code == 90) return "z";
        if (code == 32) return " ";
        
        return null;
    }

}