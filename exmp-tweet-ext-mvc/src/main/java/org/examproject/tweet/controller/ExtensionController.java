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

package org.examproject.tweet.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.examproject.tweet.dto.TagcrowdDto;
import org.examproject.tweet.model.TagcrowdModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.examproject.tweet.response.TagcrowdResponse;
import org.examproject.tweet.service.TagcrowdService;

/**
 * @author hiroxpepe
 */
@Controller
@Scope(value="session")
public class ExtensionController {
    
    private static final Log LOG = LogFactory.getLog(
        ExtensionController.class
    );
    
    private static final String TAGCROWD_SERVICE_BEAN_ID = "tagcrowdService";
                
    private static final String TAGCROWD_RESPONSE_BEAN_ID = "tagcrowdResponse";
    
    @Inject
    private final ApplicationContext context = null;
    
    @Inject
    private final Mapper mapper = null;

    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    @RequestMapping(
        value="/tagcrowd",
        method=RequestMethod.POST,
        headers="Accept=application/json"
    )
    public @ResponseBody TagcrowdResponse doReply(
        @RequestParam(value="tweet", defaultValue="")
        String content,
        @RequestParam(value="user_id", defaultValue="")
        String requestUserId,
        @RequestParam(value="status_id", defaultValue="")
        String statusId,
        @CookieValue(value="__exmphangul_request_token", defaultValue="")
        String requestToken,
        @CookieValue(value="__exmphangul_access_token", defaultValue="")
        String oauthToken,
        @CookieValue(value="__exmphangul_token_secret", defaultValue="")
        String oauthTokenSecret,
        @CookieValue(value="__exmphangul_user_id", defaultValue="")
        String userId,
        @CookieValue(value="__exmphangul_screen_name", defaultValue="")
        String screenName,
        @CookieValue(value="__exmphangul_response_list_mode", defaultValue="")
        String responseListMode,
        @CookieValue(value="__exmphangul_user_list_name", defaultValue="")
        String userListName
     ) {
        LOG.debug("called.");     
        try {
            // TODO: debug
            LOG.debug("screenName: " + screenName);
            
            // get the service object.
            TagcrowdService service = (TagcrowdService) context.getBean(
                TAGCROWD_SERVICE_BEAN_ID
            );
            
            // get the tagcrowd.
            List<TagcrowdDto> tagcrowdDtoList = service.getList(
                screenName
            );

            // map the object.
            List<TagcrowdModel> tagcrowdModelList=  new ArrayList<TagcrowdModel>();
            for (TagcrowdDto tagcrowdDto : tagcrowdDtoList) {
                TagcrowdModel tagcrowdModel = context.getBean(TagcrowdModel.class);
                // map the form-object to the dto-object.
                mapper.map(
                    tagcrowdDto,
                    tagcrowdModel
                );
                tagcrowdModelList.add(tagcrowdModel);
            }

            // return the response object.
            return new TagcrowdResponse(
                tagcrowdModelList
            );
        
        } catch(Exception e) {
            LOG.fatal(e.getMessage());
            return (TagcrowdResponse) context.getBean(
                TAGCROWD_RESPONSE_BEAN_ID,
                true,
                e.getMessage()
            );
        } 
    }
}
