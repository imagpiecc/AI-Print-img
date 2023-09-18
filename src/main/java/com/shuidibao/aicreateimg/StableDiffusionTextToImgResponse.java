package com.shuidibao.aicreateimg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StableDiffusionTextToImgResponse implements Serializable {

    /**
     * 生成的图片结果 base64
     */
    private List<String> images;

    /**
     * 入参和默认值
     */
    private StableDiffusionTextToImg parameters;

    /**
     * 参数的组合字符串
     */
    private String info;
}

