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

package org.examproject.hangul.value;

/**
 * @author hiroxpepe
 */
public enum TweetCookie {
    
    REQUEST_TOKEN("__exmphangul_request_token"),
    
    ACCESS_TOKEN("__exmphangul_access_token"),
    
    TOKEN_SECRET("__exmphangul_token_secret"),
    
    USER_ID("__exmphangul_user_id"),
    
    SCREEN_NAME("__exmphangul_screen_name");
     
    TweetCookie(String name) {
        this.name = name;
    }
    
    private String name;
     
    public String getName() {
        return name;
    }
}