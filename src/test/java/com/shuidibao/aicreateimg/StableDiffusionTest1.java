package com.shuidibao.aicreateimg;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Slf4j
public class StableDiffusionTest1 {

    @Test
    public void testSdApi() throws IOException {
        StableDiffusionTextToImg body = getArtisticWordStableDiffusionTextToImg();
        final List<String> images = callSdApi(body);
        for (String image : images) {
            writeBase642ImageFile(image, String.format("./%s.png", UUID.randomUUID().toString().replaceAll("-", "")));
        }
    }

    public static void writeBase642ImageFile(String image, String fileName) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            byte[] imageBytes = Base64.getDecoder().decode(image);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            log.info("图片写入成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StableDiffusionTextToImg getArtisticWordStableDiffusionTextToImg() throws IOException {
        final String base64SrcImg = convertImageToBase64("/Users/shuidi/Desktop/3cb54a1c5929456490b0fc20c0ef597d!1242x9999.jpeg");
        Args args1 = Args.builder()
                .enabled(true)
                .control_mode(0)
                .guidance_start(0)
                .guidance_end(0.5)
                .weight(0.3)
                .pixel_perfect(true)
                .resize_mode(1)
                .model("control_v11p_sd15_softedge [a8575a2a]")
                .module("softedge_pidinet")
                .input_image(base64SrcImg)
                .build();

        Args args2 = Args.builder()
                .enabled(true)
                .control_mode(0)
                .guidance_start(0)
                .guidance_end(0.5)
                .weight(0.75)
                .pixel_perfect(true)
                .resize_mode(1)
                .model("control_v11f1p_sd15_depth [cfd03158]")
                .module("depth_midas")
                .input_image(base64SrcImg)
                .build();

        String vae = "vae-ft-mse-840000-ema-pruned.safetensors";
        StableDiffusionTextToImg body = StableDiffusionTextToImg.builder().sampler_name("")
                .prompt("(cake:1.8),( 3D:1.8),( shadow:1.8),(best quality:1.25),( masterpiece:1.25), (ultra high res:1.25), (no human:1.3),<lora:tachi-e:1>,(white background:2)")
                .negative_prompt("EasyNegative, paintings, sketches, (worst quality:2), (low quality:2), (normal quality:2), lowres, normal quality, ((monochrome)), ((grayscale)), skin spots, acnes, skin blemishes, age spot, glans,extra fingers,fewer fingers,strange fingers,bad hand,backlight, (worst quality, low quality:1.4), watermark, logo, bad anatomy,lace,rabbit,back,")
                .sampler_index("DPM++ SDE Karras")
                .seed(-1)
                .width(768)
                .height(512)
                .restore_faces(false)
                .tiling(false)
                .clip_skip(2)
                .batch_size(4)
                .script_args(new ArrayList<>())
                .alwayson_scripts(AlwaysonScripts.builder().controlnet(ControlNet.builder()
                        .args(Lists.newArrayList(args1, args2)).build()).build())
                .steps(28).override_settings(OverrideSettings.builder()
                        .sd_model_checkpoint("chosenMix_chosenMix.ckpt [dd0aacadb6]")
                        .sd_vae(vae)
                        .build())
                .cfg_scale(7.0).build();
        return body;
    }

    public static String convertImageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] imageData = new byte[(int) file.length()];
        fileInputStream.read(imageData);
        fileInputStream.close();
        return Base64.getEncoder().encodeToString(imageData);
    }

    private List<String> callSdApi(StableDiffusionTextToImg body) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StableDiffusionTextToImg> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<JSONObject> entity = restTemplate.postForEntity("http://127.0.0.1:7860/sdapi/v1/txt2img", requestEntity, JSONObject.class);
        final StableDiffusionTextToImgResponse stableDiffusionTextToImgResponse = handleResponse(entity);
        final List<String> images = stableDiffusionTextToImgResponse.getImages();

        if (CollectionUtils.isEmpty(images)) {
            log.info("empty images");
            return Lists.newArrayList();
        }

        return images;
    }


    private StableDiffusionTextToImgResponse handleResponse(ResponseEntity<JSONObject> response) {
        if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
            log.warn("call stable diffusion api status code: {}", JSONObject.toJSONString(response));
        }

        final JSONObject body = response.getBody();
        if (Objects.isNull(body)) {
            log.error("send request failed. response body is empty");
        }
        return body.toJavaObject(StableDiffusionTextToImgResponse.class);
    }
}

