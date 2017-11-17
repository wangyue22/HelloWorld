package com.cmos.edccommon.web.remote;

import com.cmos.common.remote.Response;
import com.cmos.common.remote.annotation.RemoteService;
import com.cmos.common.remote.annotation.ServiceEndpoint;
import com.cmos.common.remote.annotation.ServiceEntity;
import com.cmos.edccommon.beans.common.GetGztPhotoDTO;


/**
 * @ClassName:  IRemoteSV
 * @Description:国政通图片使用的远程调用服务
 * @author: 王海洋
 * @date:   2017年11月17日 下午2:39:39
 */
@RemoteService
public interface IRemoteGztFileSV {
    
    /**
     * 下载国政通图片时调用，如果根据路径下载不到图片，则调用此服务去国政通拿图片
     * @param paramDto
     * @return
     */
    @ServiceEndpoint(name = "getGztPhotoByPath")
    Response getGztPhotoByPath(@ServiceEntity GetGztPhotoDTO paramDto);

}
