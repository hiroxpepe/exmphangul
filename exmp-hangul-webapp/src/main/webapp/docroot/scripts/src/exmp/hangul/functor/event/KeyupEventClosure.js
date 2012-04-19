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
        var letterIdTransformer = exmp.hangul.functor.value.LetterIdArrayTransformer;
        var decodedValueTransformer = exmp.hangul.functor.value.DecodedValueTransformer;
     
        // get the sentence.
        var sentence = obj.value;
        console.log("sentence: " + sentence);
        
        // divide to array.
        var array = sentenceDivideTransformer.transform({
            value: sentence
        });
        
        // get the word as object.
        var text = "";
        for (var index in array) {
            console.log("word: " + array[index]);

            var oneWord = array[index];
            
            // make a space.
            var sp = oneWord;
            if (sp == "") {
                console.log("word is a space.");
                text = text + " ";
                continue;
            }

            // get the letter id value as a object.
            var letterIdObj = letterIdTransformer.transform({
                value: oneWord
            });
            if (letterIdObj == null) {
                console.log("not match word: " + oneWord);
                continue;
            }
            
            // loop..
            for (var i in letterIdObj) {
                
                // get the UTF-16 code.
                var code = decodedValueTransformer.transform(
                    letterIdObj[i]
                );
                if (code == null) {
                    console.log("not match word: " + oneWord);
                    continue;
                }
            
                // get the decoded string.
                var decodedString = utf.packUTF16([code]);

                // merge the text.
                text = text + decodedString;
            }
        }
        
        $("#hangul").val(text);
    }
}