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
 * update the HTML table of the entry list.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.dhtml.CalendarClosure = {
    
    daynames: ['sun','mon','tue','wed','thu','fri','sat'],
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        console.log("exmp.tweet.functor.dhtml.CalendarClosure#execute");
        
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _leap: function (year){
        return year % 4 ? 0 : year % 100 ? 1 : year % 400 ? 0 : 1;
    },

    _make_cal_array: function(year){
        var months = [31, 28 + _leap(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        var result = [];
        for (var m = 0; m < 12; m++){
            result[m] = [];
            var dofw1 = (new Date(year, m, 1, 0, 0, 0)).getDay();
            for (var d = 1; d <= months[m]; d++){
                result[m][d + dofw1 - 1] = d;
            }
        }
        return result;
    },

    _make_cal_yearly: function(year){
        var cal = _make_cal_array(year);
        var tbody = document.createElement('tbody');
        for (var m = 0; m < 12; m++){
            var tr = document.createElement('tr');
            var th = document.createElement('th');
            th.innerHTML = m + 1;
            tr.appendChild(th);
            for (var d = 0, l = cal[m].length; d < l; d++){
            var td = document.createElement('td');
            if (cal[m][d]){
                td.innerHTML = cal[m][d];
                td.className = daynames[d % 7];
            }
            tr.appendChild(td);
            }
            tbody.appendChild(tr);
        }
        var table = document.createElement('table');
        table.className = 'ycal';
        var caption = document.createElement('caption');
        caption.innerHTML = year;
        table.appendChild(caption);
        table.appendChild(tbody);
        return table;
        },

    _make_cal_monthly: function(year, m) {
        var cal = _make_cal_array(year);
        var table = document.createElement('table');
        // header
        var tr = document.createElement('tr');
        for (var d = 0; d < 7; d++){
            var th = document.createElement('th');
            th.innerHTML = th.className = daynames[d];
            tr.appendChild(th);
        }
        var thead = document.createElement('thead');
        thead.appendChild(tr);
        table.appendChild(thead);
        // body;
        var tbody = document.createElement('tbody');
        for (var d = 0, l = cal[m].length; d < l; d++){
        if (d % 7 == 0) tr = document.createElement('tr');
        var td = document.createElement('td');
        if (cal[m][d]){
            td.innerHTML = cal[m][d];
            td.className = daynames[d % 7];
        }
        tr.appendChild(td);
        if (d % 7 == 6) tbody.appendChild(tr);
        }
        tbody.appendChild(tr);
        table.className = 'mcal';
        var caption = document.createElement('caption');
        caption.innerHTML = year + '.' + (m+1);
        table.appendChild(caption);
        table.appendChild(tbody);
        return table;
    },

    make_calendars: function(year, p){
        p.innerHTML = '';
        p.appendChild(_make_cal_yearly(year));
        for (var m = 0; m < 12; m++){
            var mcal = _make_cal_monthly(year, m);
            p.appendChild(mcal);
                if (m % 3 == 2){
                var br = document.createElement('br');
                br.clear = 'all';
                p.appendChild(br);
            }
        };
    }

}