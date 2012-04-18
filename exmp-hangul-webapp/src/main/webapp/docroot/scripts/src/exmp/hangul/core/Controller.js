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
 * a controller class of the application.
 * 
 * @author hiroxpepe
 */
exmp.hangul.core.Controller = window; {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    /**
     * the initialization method of the Controller class.
     * this method should be called.
     */
    exmp.hangul.core.Controller.init = function() {
        
        var controller = exmp.hangul.core.Controller;
        
        controller._initializeComponent();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // event handler methods
    
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    /**
     * initialize a component of the view class.
     */
    exmp.hangul.core.Controller._initializeComponent = function() {
        
        var controller = exmp.hangul.core.Controller;
        
//        var closure1 = exmp.hangul.functor.event.KeyupEventClosure1;
        var closure = exmp.hangul.functor.event.KeyupEventClosure;
        
        $("#roman").keyup(function(event) {
            //$("#hangul").val("hoge");
            
//            closure1.execute({
//                keyCode: event.keyCode,
//                shiftKey: event.shiftKey,
//                ctrlKey: event.ctrlKey
//            });
            
            closure.execute({
                value: $("#roman").val()
            });
            
        });
    }
}