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

package org.examproject.tweet.util;

import org.apache.commons.collections.Predicate;

/**
 * @author hiroxpepe
 */
public class IsContainJaKanaCodePredicate implements Predicate {
    
    @Override
    public boolean evaluate(Object object) {
        String str = (String) object;
        for (int i =0; i < str.length(); i++) {
            char c = str.charAt(i);
            int code = (int) c;
            // hiragana
            // range: 0x3040 ~ 0x309F
            if ((code >= 12352) && (code <= 12447)) {
                return true;
            }
            // katakana
            // range: 0x30A0 ~ 0x30FF
            if ((code >= 12448) && (code <= 12543)) {
                return true;
            }
            // range: 0x31F0 ~ 0x31FF
            if ((code >= 12784) && (code <= 12799)) {
                return true;
            }
        }
        return false;
    }
    
}
