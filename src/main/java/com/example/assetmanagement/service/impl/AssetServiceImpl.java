package com.example.assetmanagement.service.impl;

import com.example.assetmanagement.dto.Stock;
import com.example.assetmanagement.exception.ApiErrorException;
import com.example.assetmanagement.exception.AssetNotFoundException;
import com.example.assetmanagement.exception.InsufficientAssetException;
import com.example.assetmanagement.model.Asset;
import com.example.assetmanagement.repository.AssetRepository;
import com.example.assetmanagement.service.AssetService;
import com.example.assetmanagement.utils.ApiUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Cacheable(value = "asset", key = "#personId", condition = "#result != null")
    @Override
    public List<Asset> findByPersonId(Long personId) {
        return assetRepository.findByPersonId(personId);
    }

    @CacheEvict(value = "asset", key = "#personId")
    @Override
    @Transactional
    public void increaseAssets(Long personId, Asset asset) throws ApiErrorException {
        String symbol = asset.getSymbol();
        Stock stock = getStock(symbol);

        Asset entity = assetRepository.findByPersonIdAndSymbol(personId, symbol)
                .orElseGet(() -> Asset.builder()
                        .personId(personId)
                        .symbol(symbol)
                        .currency(stock.getCurrency())
                        .amount(BigDecimal.ZERO)
                        .build());

        BigDecimal amount = entity.getAmount().add(asset.getAmount());
        entity.setAmount(amount);
        entity.setLastPrice(stock.getPrice());
        assetRepository.save(entity);

        sendWs(symbol, amount);
    }

    @CacheEvict(value = "asset", key = "#personId")
    @Override
    @Transactional
    public void decreaseAssets(Long personId, Asset asset) throws AssetNotFoundException, InsufficientAssetException, ApiErrorException {
        String symbol = asset.getSymbol();

        Asset dbAsset = assetRepository.findByPersonIdAndSymbol(personId, symbol)
                .orElseThrow(AssetNotFoundException::new);

        BigDecimal amount = dbAsset.getAmount().subtract(asset.getAmount());
        if(amount.compareTo(BigDecimal.ZERO) < 0)
            throw new InsufficientAssetException();

        if(amount.compareTo(BigDecimal.ZERO) == 0) {
            assetRepository.delete(dbAsset);
        } else {
            Stock stock = getStock(symbol);
            dbAsset.setLastPrice(stock.getPrice());
            dbAsset.setAmount(amount);
            assetRepository.save(dbAsset);
        }
        sendWs(symbol, amount);
    }

    private Stock getStock(String symbol) throws ApiErrorException {
        Stock stock = apiUtils.getStock(symbol);
        if(stock.isSuccess())
            return stock;
        throw new ApiErrorException(stock.getError());
    }

    private void sendWs(String symbol, BigDecimal amount) {
        messagingTemplate.convertAndSend("/topic/amountUpdate", "Amount of " + symbol + " updated to " + amount);
    }

}
