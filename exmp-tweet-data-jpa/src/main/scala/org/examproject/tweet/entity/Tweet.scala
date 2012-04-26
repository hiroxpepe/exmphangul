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

package org.examproject.tweet.entity

import java.lang.Boolean
import java.lang.Long
import java.io.Serializable
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal

import org.springframework.stereotype.Component

import scala.SerialVersionUID
import scala.reflect.BeanProperty

/**
 * @author hiroxpepe
 */
@Entity
@Table(name="tweets")
@Component
@SerialVersionUID(-8712872385957386182L)
class Tweet extends Serializable {
    
    @Id
    @Column(unique=true)
    @BeanProperty
    var id: Long = _
    
    @Column(name="date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @BeanProperty
    var date: Date = new Date()
    
    @Column(name="name")
    @BeanProperty
    var name: String = _
    
    @Column(name="text")
    @BeanProperty
    var text: String = _
    
}
