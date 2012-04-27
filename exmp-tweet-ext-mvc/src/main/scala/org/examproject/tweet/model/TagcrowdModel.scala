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

package org.examproject.tweet.model

import reflect.BeanProperty

/**
 * @author hiroxpepe
 */
class TagcrowdModel {

    @BeanProperty
    var userProfileImageURL: String = _

    @BeanProperty
    var userName: String = _

    @BeanProperty
    var text: String = _

    // must string class !!
    @BeanProperty
    var statusId: String = _

    @BeanProperty
    var isFavorited: Boolean = false
    
    @BeanProperty
    var isRetweetedByMe: Boolean = false

}