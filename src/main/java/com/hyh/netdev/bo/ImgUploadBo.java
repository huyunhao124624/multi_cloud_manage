package com.hyh.netdev.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * /api/img/upload
 *
 * @author hyh
 */
@Data
@AllArgsConstructor
public class ImgUploadBo {
    private String filename;
    private String url;
}
