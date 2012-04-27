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
exmp.tweet.core.Controller = window; {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    /**
     * the initialization method of the Controller class.
     * this method should be called.
     */
    exmp.tweet.core.Controller.init = function() {
        
        var controller = exmp.tweet.core.Controller;
        
        controller._initializeComponent();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // event handler methods
    
    /**
     * an event handler that called when 
     * the button of tweet is clicked.
     */
    exmp.tweet.core.Controller._doTweetButtonOnClick = function() {
        
        var updateClosure = exmp.tweet.functor.request.TweetUpdateClosure;
        var replyClosure = exmp.tweet.functor.request.TweetReplyClosure;
        
        ///////////////////////////////////////////////////
        // update the tweet status.
        
        // not reply or simple reply
        if (($("#reply-status-id").val() == "") && ($("#reply-user-name").val() == "")) {
            updateClosure.execute({
                content: $("#tweet").val(),
                userId: $("#user-id").val()
            });
            return;
        }
        
        // reply to selected status
        if (($("#reply-status-id").val()) && ($("#reply-user-name").val())) {
            replyClosure.execute({
                content: $("#tweet").val(),
                userId: $("#user-id").val(),
                statusId: $("#reply-status-id").val()
            })
            return;
        }
    }
    
    /**
     * an event handler that called when 
     * the button of setting is clicked.
     */
    exmp.tweet.core.Controller._doSettingButtonOnClick = function() {
        
        var settingClosure = exmp.tweet.functor.request.SettingClosure;
        var formFactory = exmp.tweet.functor.value.TweetFormFactory;
        
        settingClosure.execute(
            formFactory.create()
        );
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    /**
     * initializes a div of the tabs area.
     */
    exmp.tweet.core.Controller._initializeTabsDiv = function() {
        
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
    exmp.tweet.core.Controller._initializeTweetListDiv = function() {
        
        var listClosure = exmp.tweet.functor.request.TweetListClosure;
        var tagcrowdClosure = exmp.tweet.functor.request.TagcrowdClosure;
        var pageUrl = location.href;
        
        if (!(pageUrl.indexOf("index.html") == -1)) {
            listClosure.execute({
                userId: $("#user-id").val()
            });
            
            // TODO: add, ch user-id ??
            tagcrowdClosure.execute({
                tweet: ""
            });
        }
    }
    
    /**
     * initializes a response list mode select of form.
     */
    exmp.tweet.core.Controller._initializeResponseListModeSelect = function() {
        $("#response-list-mode").append($('<option value="home">home</option>'));
        $("#response-list-mode").append($('<option value="user">user</option>'));
        $("#response-list-mode").append($('<option value="list">list</option>'));
        //$("#response-list-mode").val("home");
    }
    
    /**
     * initialize a component of the view class.
     */
    exmp.tweet.core.Controller._initializeComponent = function() {
        
        var controller = exmp.tweet.core.Controller;
        var eventClosure = exmp.hangul.functor.event.KeyupEventClosure; 
        var colorClosure = exmp.hangul.functor.event.KeyupColorClosure;
        
        // calls for the initialization methods.
        
        controller._initializeTabsDiv();
        
        controller._initializeTweetListDiv();
        
        controller._initializeResponseListModeSelect();
        
        // set the control's event handler.
        
        $("#tweet").keyup(function(event) {
            // clear reply param.
            // TODO: search user name?
            if ($("#tweet").val() == "") {
                $("#reply-status-id").val("")
                $("#reply-user-name").val("")
                console.log("reply-status-id: ");
                console.log("reply-user-name: ");
            }
        });
        
        $("#alphabet").keyup(function(event) {
            eventClosure.execute({
                value: $("#alphabet").val()
            });
            
            // show color.
            colorClosure.execute(
                event
            );
        });
        
        $("#tweet-button").click(function() { 
            controller._doTweetButtonOnClick();
        });
        
        $("#setting-button").click(function() {
            controller._doSettingButtonOnClick();
        });
        
        // and do a some initialize.
        
        $("#alphabet-grid").dialog({
            width: 380
        });
    }
}