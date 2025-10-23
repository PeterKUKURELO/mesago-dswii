package com.mesago.mscatalogomenu.repository;

import com.mesago.mscatalogomenu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
