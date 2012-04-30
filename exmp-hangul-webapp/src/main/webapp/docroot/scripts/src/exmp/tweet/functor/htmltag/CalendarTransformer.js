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
 * this class is a transformer that JSON data get by
 * Ajax HTTP requests and convert to HTML tags.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.htmltag.CalendarTransformer = {
    
    _daynames: ['Su','Mo','Tu','We','Th','Fr','Sa'],
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        //console.log("exmp.tweet.functor.htmltag.CalendarTransformer#transform");
        
        // TODO: need null if user new.
        
        // dynamically generate an html tags.
        if (obj.calendarModelList != null) {
            // set real date.
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth();
            return this._getCalendar(
                year,
                month + 1,
                $('#tweet-calendar'),
                obj
            );
        }
        return null;
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _getCalendar: function(year, month, jelem, obj) {
        return this._getCalendarTag(
            year,
            month,
            jelem.get(0),
            obj
        );
    },
    
    _getLeap: function (year){
        return year % 4 ? 0 : year % 100 ? 1 : year % 400 ? 0 : 1;
    },
    
    _createCalendarArray: function(year) {
        var months = [31, 28 + this._getLeap(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        var result = [];
        for (var m = 0; m < 12; m++) {
            result[m] = [];
            var dofw1 = (new Date(year, m, 1, 0, 0, 0)).getDay();
            for (var d = 1; d <= months[m]; d++) {
                result[m][d + dofw1 - 1] = d;
            }
        }
        return result;
    },
    
    _createCalendarMonthly: function(year, month, obj) {
        var m = month - 1;
        var calendar = this._createCalendarArray(year);
        var table = document.createElement('table');
        // header
        var tr = document.createElement('tr');
        for (var d = 0; d < 7; d++) {
            var th = document.createElement('th');
            th.innerHTML = th.className = this._daynames[d];
            tr.appendChild(th);
        }
        var thead = document.createElement('thead');
        thead.appendChild(tr);
        table.appendChild(thead);
        // body
        var tbody = document.createElement('tbody');
        for (var d = 0, l = calendar[m].length; d < l; d++) {
            if (d % 7 == 0) { 
                tr = document.createElement('tr');
            }
            var td = document.createElement('td');
            if (calendar[m][d]) {
                //console.log("day: " + d);
                var dIdx = d;
                if (obj.calendarModelList[dIdx + 1].isExist == true) {
                    td.innerHTML = 
                        "<a href='" + obj.calendarModelList[dIdx + 1].linkUrl + "'>" + 
                            "<b>" + calendar[m][d] + "</b>" + 
                        "</a>";
                } else {
                    td.innerHTML = calendar[m][d];
                }
                td.className = this._daynames[d % 7];
            }
            tr.appendChild(td);
            if (d % 7 == 6) {
                tbody.appendChild(tr);
            }
        }
        tbody.appendChild(tr);
        table.className = 'calendar-table';
        var caption = document.createElement('caption');
        caption.innerHTML = year + '.' + (month);
        table.appendChild(caption);
        table.appendChild(tbody);
        return table;
    },
    
    _getCalendarTag: function(year, month, element, obj) {
        element.innerHTML = "";
        var calendarTable = this._createCalendarMonthly(year, month, obj);
        element.appendChild(calendarTable);
        return element.innerHTML;
    }
}