package com.shuidibao.aicreateimg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverrideSettings {
    private String sd_model_checkpoint;
    private String sd_vae;
}

