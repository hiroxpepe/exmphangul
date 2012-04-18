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
exmp.hangul.functor.event.KeyupEventClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("exmp.hangul.functor.event.KeyupEventClosure#execute");
        
        var sentenceDivideTransformer = exmp.hangul.functor.value.SentenceDivideTransformer;
        var wordDivideTransformer = exmp.hangul.functor.value.WordDivideTransformer;
        var letterIdTransformer = exmp.hangul.functor.value.LetterIdTransformer;
        
        var decodedValueTransformer = exmp.hangul.functor.value.DecodedValueTransformer;
     
        // get the sentence.
        var sentence = obj.value;
        console.log("sentence: " + sentence);
        
        // to array.
        var array = sentenceDivideTransformer.transform({
            value: sentence
        });
        
        // get the word as object.
        var text = "";
        for (var index in array) {
            console.log("word: " + array[index]);

            var letterIdObj = letterIdTransformer.transform({
                value: array[index]
            });
            if (letterIdObj == null) {
                console.log("not match word: " + array[index]);
                continue;
            }
            
            var code = decodedValueTransformer.transform(
                letterIdObj
            );
            if (code == null) {
                console.log("not match word: " + array[index]);
                continue;
            }
            
            var array2 = [code];
            var ret = utf.packUTF16(array2);
            
            text = text + ret;
        }
        
        $("#hangul").val(text);
    }
}