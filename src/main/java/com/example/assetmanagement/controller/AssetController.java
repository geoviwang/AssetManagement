package com.example.assetmanagement.controller;

import com.example.assetmanagement.controller.request.AssetParam;
import com.example.assetmanagement.controller.response.RestResponse;
import com.example.assetmanagement.exception.ApiErrorException;
import com.example.assetmanagement.model.Asset;
import com.example.assetmanagement.service.AssetService;
import com.example.assetmanagement.service.PersonService;
import com.example.assetmanagement.utils.GsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
    @Autowired
    private PersonService personService;

    @Autowired
    private AssetService assetService;

    @GetMapping("/{personId}")
    @Operation(summary = "Get Assets By Person Id", description = "取得指定使用者資產")
    public RestResponse<List<Asset>> getAssetsByPersonId(@PathVariable Long personId) {
        personService.checkPerson(personId);
        return new RestResponse<>(assetService.findByPersonId(personId));
    }

    @PutMapping(value = "/{personId}/increase", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Increase Assets", description = "增加使用者資產")
    public RestResponse<?> increaseAssets(@PathVariable Long personId, @RequestBody AssetParam param) throws ApiErrorException {
        personService.checkPerson(personId);
        Asset asset = GsonMapper.convert(param, Asset.class);
        assetService.increaseAssets(personId, asset);
        return new RestResponse<>();
    }

    @PutMapping("/{personId}/decrease")
    @Operation(summary = "Decrease Assets", description = "減少使用者資產")
    public RestResponse<?> decreaseAssets(@PathVariable Long personId, @RequestBody AssetParam param) {
        personService.checkPerson(personId);
        Asset asset = GsonMapper.convert(param, Asset.class);
        assetService.decreaseAssets(personId, asset);
        return new RestResponse<>();
    }

}
