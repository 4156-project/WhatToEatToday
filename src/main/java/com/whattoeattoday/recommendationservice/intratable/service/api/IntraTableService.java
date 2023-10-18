package com.whattoeattoday.recommendationservice.intratable.service.api;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.intratable.request.*;


/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/15/23
 */
public interface IntraTableService {
    /**
     * insert an instance into the table
     * @param request
     * @return
     */
    BaseResponse insert(InsertRequest request);
    /**
     * update an instance
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

}
