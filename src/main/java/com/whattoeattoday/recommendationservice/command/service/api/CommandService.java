package com.whattoeattoday.recommendationservice.command.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.command.request.*;


/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/15/23
 */
public interface CommandService {

    BaseResponse Insert(InsertRequest request);

    /**
     * Insert an instance into the table
     * @param request
     * @return
     */
    BaseResponse Delete(DeleteRequest request);

    /**
     * Delete an instance from the table
     * @param request
     * @return
     */

    BaseResponse Update(UpdateRequest request);

    /**
     * Update an instance
     * @param request
     * @return
     */

}
