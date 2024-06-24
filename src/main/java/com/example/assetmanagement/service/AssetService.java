package com.example.assetmanagement.service;

import com.example.assetmanagement.exception.ApiErrorException;
import com.example.assetmanagement.exception.AssetNotFoundException;
import com.example.assetmanagement.exception.InsufficientAssetException;
import com.example.assetmanagement.model.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetService {

    List<Asset> findByPersonId(Long personId);

    void increaseAssets(Long personId, Asset asset) throws ApiErrorException;

    void decreaseAssets(Long personId, Asset asset) throws AssetNotFoundException, InsufficientAssetException, ApiErrorException;

}
