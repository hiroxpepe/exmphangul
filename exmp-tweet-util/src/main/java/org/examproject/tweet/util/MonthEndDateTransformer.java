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

import java.util.Calendar;

import org.apache.commons.collections.Transformer;

/**
 * @author hiroxpepe
 */
public class MonthEndDateTransformer implements Transformer {

    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    public Object transform(Object input) {
        DateValue dateValue = (DateValue) input;
        Calendar calendar = Calendar.getInstance();
        calendar.set(
            dateValue.getYear(),
            (dateValue.getMonth() - 1),
            getEndDayOfMonth(dateValue.getYear(), dateValue.getMonth()),
            23,
            59,
            59
        );
        return calendar.getTime();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    private int getEndDayOfMonth(int year, int month) {
        if (month == 2) {
            return (28 + getLeap(year));
        }
        else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            return 30;
        }
        return 31;
    }
    
    private int getLeap(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return 1;
        } else {
            return 0;
        }
    }
    
}
