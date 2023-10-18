package com.whattoeattoday.recommendationservice.command.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.command.request.*;


/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/15/23
 */
public interface CommandService {

    BaseResponse insert(InsertRequest request);

    /**
     * insert an instance into the table
     * @param request
     * @return
     */
    BaseResponse delete(DeleteRequest request);

    /**
     * delete an instance from the table
     * @param request
     * @return
     */

    BaseResponse update(UpdateRequest request);

    /**
     * update an instance
     * @param request
     * @return
     */

}
