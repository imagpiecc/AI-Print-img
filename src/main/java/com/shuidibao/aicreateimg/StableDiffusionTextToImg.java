package com.shuidibao.aicreateimg;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StableDiffusionTextToImg implements Serializable {

    /**
     * 去噪强度
     */
    private Integer denoising_strength;
    private Integer firstphase_width;
    private Integer firstphase_height;

    /**
     * 高清修复
     * 缩写hr代表的就是webui中的"高分辨率修复 (Hires. fix)"，相关的参数对应的是webui中的这些选项：
     */
    private Boolean enable_hr;
    /**
     * default 2
     */
    private Integer hr_scale;
    private String hr_upscaler;
    private Integer hr_second_pass_steps;
    private Integer hr_resize_x;
    private Integer hr_resize_y;
    private String hr_sampler_name;
    private String hr_prompt;
    private String hr_negative_prompt;

    /**
     * 正向提示词, 默认 ""
     * lora 需要放在 prompt 里
     */
    private String prompt;

    /**
     * 反向提示词, 默认 ""
     */
    private String negative_prompt;

    private List<String> styles;

    /**
     * 随机数种子 (Seed)
     */
    private Integer seed;

    private Integer clip_skip;


    /**
     *
     */
    private Integer subseed;

    /**
     *
     */
    private Integer subseed_strength;

    /**
     * 高度
     */
    private Integer seed_resize_from_h;

    /**
     * 宽度
     */
    private Integer seed_resize_from_w;


    /**
     * 采样方法 (Sampler), 默认 null
     */
    private String sampler_name;

    /**
     * 采样方法 (Sampler) 下标
     */
    private String sampler_index;

    /**
     * 批次数 default: 1
     */
    private Integer batch_size;

    /**
     * 每批的数量 default: 1
     */
    private Integer n_iter;

    /**
     * 迭代步数 (Steps), 默认 50
     */
    private Integer steps;

    /**
     * 提示词引导系数, 默认7
     */
    private Double cfg_scale;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 面部修复, 默认 false
     */
    private Boolean restore_faces;

    /**
     * 平铺图 默认 false
     */
    private Boolean tiling;

    /**
     * 默认 false
     */
    private Boolean do_not_save_samples;

    /**
     * 默认 false
     */
    private Boolean do_not_save_grid;

    /**
     * 默认 null
     */
    private Integer eta;

    /**
     * 默认 0
     */
    private Integer s_min_uncond;

    /**
     * 默认 0
     */
    private Integer s_churn;

    /**
     * 默认 null
     */
    private Integer s_tmax;

    /**
     * 默认 0
     */
    private Integer s_tmin;

    /**
     * 默认 1
     */
    private Integer s_noise;

    /**
     * 默认 null
     */
    private OverrideSettings override_settings;

    /**
     * 默认 true
     */
    private Boolean override_settings_restore_afterwards;


    private List<Object> script_args;

    /**
     * 默认 null
     */
    private String script_name;

    /**
     * 默认 true
     */
    private Boolean send_images;

    /**
     * 默认 false
     */
    private Boolean save_images;

    /**
     * 默认 {}
     */
    private AlwaysonScripts alwayson_scripts;
}
