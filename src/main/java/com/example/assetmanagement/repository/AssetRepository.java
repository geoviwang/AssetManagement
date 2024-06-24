package com.example.assetmanagement.repository;

import com.example.assetmanagement.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByPersonId(Long personId);


    Optional<Asset> findByPersonIdAndSymbol(Long personId, String symbol);

    void deleteByPersonId(Long personId);

}
