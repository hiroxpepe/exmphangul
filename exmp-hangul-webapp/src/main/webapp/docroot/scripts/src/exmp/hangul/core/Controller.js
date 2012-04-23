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
    
    /**
     * an event handler that called when 
     * the button of tweet is clicked.
     */
    exmp.hangul.core.Controller._doTweetButtonOnClick = function() {
        
        var updateClosure = exmp.hangul.functor.request.TweetUpdateClosure;
        
        // update the tweet status.
        updateClosure.execute({
            content: $("#hangul").val(),
            userId: $("#user-id").val()
        });
        return;
    }
    
    /**
     * an event handler that called when 
     * the button of setting is clicked.
     */
    exmp.hangul.core.Controller._doSettingButtonOnClick = function() {
        
        var settingClosure = exmp.hangul.functor.request.SettingClosure;
        var formFactory = exmp.hangul.functor.value.TweetFormFactory;
        
        settingClosure.execute(
            formFactory.create()
        );
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    /**
     * initializes a div of the tabs area.
     */
    exmp.hangul.core.Controller._initializeTabsDiv = function() {
        
        $("div.tab-content div.tab").hide();
        $("div.tab-content div.tab:first").show();
        $("div.tab-content ul li:first").addClass("active");
        $("div.tab-content ul li a").click(function(){
            $("div.tab-content ul li").removeClass("active");
            $(this).parent().addClass("active");
            var currentTab = $(this).attr("href");
            $("div.tab-content div.tab").hide();
            $(currentTab).show();
            return false;
        });
    }
    
    /**
     * initializes a div of entry list.
     * an http request of ajax for get the tweet data.
     */
    exmp.hangul.core.Controller._initializeTweetListDiv = function() {
        
        var listClosure = exmp.hangul.functor.request.TweetListClosure;
        var pageUrl = location.href;
        
        if (!(pageUrl.indexOf("index.html") == -1)) {
            listClosure.execute({
                userId: $("#user-id").val()
            });
        }
    }
    
    /**
     * initialize a component of the view class.
     */
    exmp.hangul.core.Controller._initializeComponent = function() {
        
        var controller = exmp.hangul.core.Controller;
        var closure = exmp.hangul.functor.event.KeyupEventClosure;
        
        // calls for the initialization methods.
        
        controller._initializeTabsDiv();
        
        controller._initializeTweetListDiv();
        
        // set the control's event handler.
        
        $("#alphabet").keyup(function(event) {            
            closure.execute({
                value: $("#alphabet").val()
            });
        });
        
        $("#tweet-button").click(function() { 
            controller._doTweetButtonOnClick();
        });
        
        $("#setting-button").click(function() {
            controller._doSettingButtonOnClick();
        });
        
        // and do a some initialize.
        
        $("#alphabet-grid").dialog({
            width: auto
        });
    }
}